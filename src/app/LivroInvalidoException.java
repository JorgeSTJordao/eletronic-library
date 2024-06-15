package app;

import javax.swing.*;

public class LivroInvalidoException extends Exception {
    public LivroInvalidoException(String mensagem){
        super(mensagem);
    }
}
