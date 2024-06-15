package models;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Leitor {
    public static void main(String[] args) {
        Biblioteca biblioteca = new Biblioteca();

        //Criando um objeto
        try {
            biblioteca.livros = Livro.Abrir("root.txt");
            biblioteca.mostrar();
        } catch (FileNotFoundException e){
            System.out.println("Exceção de Arquivo");
            System.out.println(e.getMessage());
        }

        System.out.println("-----------------------------------");
        //Salvando em um arquivo Txt
        try {
            biblioteca.salvar("new.txt");
            System.out.println("Classe salva com sucesso");
        } catch (IOException e){
            System.out.println("Exceção de IO");
            System.out.println(e.getMessage());
        }

    }
}
