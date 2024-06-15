package models;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Biblioteca implements Serializable {

    ArrayList<Livro> livros;

    public Biblioteca(){
        this.livros = new ArrayList<>();
    }

    public ArrayList<Livro> retornarLivros(){
        return livros;
    }

    public void mostrar(){
        for (Livro livro : livros){
            livro.exibir();
        }
    }

    public void salvar(String nome_arquivo) throws IOException {
        FileOutputStream arquivo = new FileOutputStream(nome_arquivo);
        ObjectOutputStream gravador = new ObjectOutputStream(arquivo);

        gravador.writeObject(this);

        gravador.close();
        arquivo.close();
    }

    public static Biblioteca abrir(String nome_arquivo) throws IOException, ClassNotFoundException {
        Biblioteca biblioteca = null;
        FileInputStream arquivo = new FileInputStream(nome_arquivo);
        ObjectInputStream restaurador = new ObjectInputStream(arquivo);

        biblioteca = (Biblioteca) restaurador.readObject();

        restaurador.close();
        arquivo.close();

        return biblioteca;
    }
}
