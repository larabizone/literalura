import java.util.List;
import java.util.Scanner;

public class LiterAluraApp {
    private final Scanner scanner;
    private final LivroService livroService;

    public LiterAluraApp(Scanner scanner) {
        this.scanner = scanner;
        this.livroService = new LivroService();
    }

    public void start() {
        int opcao;
        do {
            System.out.println("\n=== LiterAlura ===");
            System.out.println("1. Buscar livro por título");
            System.out.println("2. Listar todos os livros");
            System.out.println("3. Filtrar por autor");
            System.out.println("4. Remover livro por título");
            System.out.println("5. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = Integer.parseInt(scanner.nextLine());

            switch (opcao) {
                case 1 -> buscarLivro();
                case 2 -> listarLivros();
                case 3 -> filtrarPorAutor();
                case 4 -> removerLivro();
                case 5 -> System.out.println("Saindo...");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 5);
    }

    private void buscarLivro() {
        System.out.print("Digite o título do livro: ");
        String titulo = scanner.nextLine();
        livroService.buscarELimparLivro(titulo);
    }

    private void listarLivros() {
        livroService.listarLivros();
    }

    private void filtrarPorAutor() {
        System.out.print("Digite o nome do autor: ");
        String autor = scanner.nextLine();
        livroService.filtrarPorAutor(autor);
    }

    private void removerLivro() {
        System.out.print("Digite o título do livro a remover: ");
        String titulo = scanner.nextLine();
        livroService.removerLivro(titulo);
    }
}
