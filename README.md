# literalura
# LiterAlura 📚

LiterAlura é um catálogo de livros interativo via console, desenvolvido em Java. Ele consome dados da API do Google Books, armazena os resultados em um banco de dados SQLite e permite diversas opções de consulta.

## Funcionalidades

- 🔍 Buscar livros pela API do Google Books
- 💾 Armazenar dados dos livros em um banco SQLite
- 📚 Listar todos os livros cadastrados
- 🧑 Filtrar livros por autor
- ✏️ Filtrar livros por título
- ❌ Remover livros por título
- 🚪 Sair do programa

## Requisitos

- Java 11+
- SQLite JDBC Driver
- Internet ativa para consumir a API

## Como rodar

```bash
git clone https://github.com/seu-usuario/literalura.git
cd literalura
javac Main.java
java Main
