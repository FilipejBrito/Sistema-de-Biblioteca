package org.SistemaBiblioteca;


import org.SistemaBiblioteca.Database.DatabaseConecta;
import org.SistemaBiblioteca.Model.Livros;

import java.sql.*;
import java.util.Scanner;

public class GerenciadorBiblioteca {
    public static void main(String[] args) {
        criarTabela();
        Scanner teclado = new Scanner(System.in);

        System.out.println("Escolha uma opcao a seguir");

        while (true) {
            System.out.println("1. Adicionar livro");
            System.out.println("2. Listar livro");
            System.out.println("3. Editar livro");
            System.out.println("4. Remover livro");
            System.out.println("5. Sair ");
            int opcao = teclado.nextInt();
            teclado.nextLine();

            switch (opcao) {
                case 1:
                    addLivro(teclado);
                    break;
                case 2:
                    listaLivro();
                    break;
                case 3:
                    editarLivro(teclado);
                    break;
                case 4:
                    removerLivro(teclado);
                    break;
                case 5:
                    System.out.println("Saindo do sistema...");
                    return;
                default:
                    System.out.println("Opcao invalida!");
            }
        }
    }
    private static void criarTabela(){
        String CREATED = "CREATE TABLE IF NOT EXISTS livros ("
                +"id INTEGER PRIMARY KEY AUTOINCREMENT,"
                +"titulo TEXT NOT NULL,"
                +"autor TEXT NOT NULL"
                +");";

        try(Connection conn = DatabaseConecta.conectar();
            Statement stm = conn.createStatement()){
            stm.execute(CREATED);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void addLivro(Scanner teclado){
        System.out.println("Titulo do livro:");
        String titulo = teclado.nextLine();


        System.out.println("Autor:");
        String autor = teclado.nextLine();


        String ADICIONA = "INSERT INTO livros (titulo,autor) VALUES(?, ?)";

        try(Connection conn = DatabaseConecta.conectar();
            PreparedStatement pst = conn.prepareStatement(ADICIONA)){
            pst.setString(1, titulo);
            pst.setString(2, autor);
            pst.executeUpdate();
            System.out.println("Livro adicionado com sucesso");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void listaLivro(){
        String LISTA = "SELECT * FROM livros";

        try(Connection conn =  DatabaseConecta.conectar();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(LISTA)){
            while(rs.next()){
                Livros livro = new Livros(rs.getInt("id"),rs.getString("titulo"), rs.getString("autor"));
                System.out.println(livro);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void editarLivro(Scanner teclado){
            listaLivro();
        System.out.println("ID do livro que deseja editar");
        int id = teclado.nextInt();
        teclado.nextLine();

        System.out.println("Novo titulo:");
        String novoTitulo = teclado.nextLine();


        System.out.println("Novo autor:");
        String novoAutor = teclado.nextLine();


        String EDITA = "UPDATE livros SET titulo = ?, autor = ? WHERE id = ?";

        try(Connection conn = DatabaseConecta.conectar();
            PreparedStatement pst = conn.prepareStatement(EDITA)){
            pst.setString(1, novoTitulo);
            pst.setString(2, novoAutor);
            pst.setInt(3, id);
            pst.executeUpdate();
            System.out.println("livro atualizado!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void removerLivro(Scanner teclado){
        listaLivro();
        System.out.println("ID do livro que deseja remover");
        int id = teclado.nextInt();

        String REMOVE = "DELETE FROM livros WHERE id = ?";

        try(Connection conn = DatabaseConecta.conectar();
            PreparedStatement pst = conn.prepareStatement(REMOVE)){
            pst.setInt(1, id);
            pst.executeUpdate();
            System.out.println("Livro removido com sucesso");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
