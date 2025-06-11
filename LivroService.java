import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.*;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

public class LivroService {
    private final Connection conn;

    public LivroService() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:livros.db");
            criarTabela();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void criarTabela() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS livros (titulo TEXT, autor TEXT)";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

    public void buscarELimparLivro(String titulo) {
        try {
            String url = "https://www.googleapis.com/books/v1/volumes?q=" + titulo.replace(" ", "+");
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

            HttpResponse<String> response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());

            JSONObject json = new JSONObject(response.body());
            JSONArray items = json.getJSONArray("items");

            JSONObject volumeInfo = items.getJSONObject(0).getJSONObject("volumeInfo");
            String tituloLivro = volumeInfo.getString("title");
            String autor = volumeInfo.getJSONArray("authors").getString(0);

            salvarLivro(new Livro(tituloLivro, autor));
            System.out.println("Livro salvo: " + tituloLivro + " - " + autor);

        } catch (Exception e) {
            System.out.println("Erro ao buscar livro: " + e.getMessage());
        }
    }

    public void salvarLivro(Livro livro) {
        String sql = "INSERT INTO livros (titulo, autor) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, livro.getTitulo());
            stmt.setString(2, livro.getAutor());
            stmt.execute();
        } catch (SQLException e) {
            System.out.println("Erro ao salvar livro: " + e.getMessage());
        }
    }

    public void listarLivros() {
        String sql = "SELECT titulo, autor FROM livros";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.println("Título: " + rs.getString("titulo") + " | Autor: " + rs.getString("autor"));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar livros: " + e.getMessage());
        }
    }

    public void filtrarPorAutor(String autor) {
        String sql = "SELECT titulo FROM livros WHERE autor LIKE ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + autor + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                System.out.println("Título: " + rs.getString("titulo"));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao filtrar livros: " + e.getMessage());
        }
    }

    public void removerLivro(String titulo) {
        String sql = "DELETE FROM livros WHERE titulo LIKE ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + titulo + "%");
            int deleted = stmt.executeUpdate();
            if (deleted > 0) {
                System.out.println("Livro removido.");
            } else {
                System.out.println("Livro não encontrado.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao remover livro: " + e.getMessage());
        }
    }
}
