package models;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Scanner;

public class Livro implements Serializable{
    private String nome;
    private String genero;
    private String nPaginas;

    public Livro(String nome, String genero, String nPaginas){
        this.nome = nome;
        this.genero = genero;
        this.nPaginas = nPaginas;
    }

    public void editarNome(String nome){
        this.nome = nome;
    }

    public void editarGenero(String genero){
        this.genero = genero;
    }


    public void editarNPaginas(String nPaginas){
        this.nPaginas = nPaginas;
    }


    public void exibir(){
        System.out.println("Nome: " + nome + "| Gênero: " + genero + "| Nºde páginas: " + nPaginas);
    }

    public String retornoAtributo(String code){
        if (code.equals("nome"))
            return nome;
        else if (code.equals("genero"))
            return genero;
        return nPaginas;
    }

    //Criar uma lista de livros a partir de um arquivo CSV ou TXT
    public static ArrayList<Livro> Abrir(String nome_arquivo) throws FileNotFoundException {
       ArrayList<Livro> livros = new ArrayList<>();

        Scanner arquivo = new Scanner(new File(nome_arquivo));
        arquivo.useDelimiter(";|\n"); //Todos os elementos de split

        while(arquivo.hasNext()){
            String nome = arquivo.next();
            String genero = arquivo.next();
            String nPaginas = arquivo.next();

            Livro novoLivro = new Livro(nome, genero, nPaginas);
            livros.add(novoLivro);
        }
        return livros;
    }
}
