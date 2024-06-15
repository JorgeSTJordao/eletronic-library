package app;

import  models.Biblioteca;
import models.Livro;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class GUI implements ActionListener {

    //Objetos de escopo global
    private JFrame frame;
    private JTabbedPane navPanel;
    private Boolean isIn = false;

    //Paineis
    private JPanel panelMain;
    private JPanel panelCreate;
    private JPanel panelUpdate;

    //Tabela
    private DefaultTableModel model = new DefaultTableModel();
    private JTable tableBooks = new JTable(model);
    private int selectedRow = 0;
    private String selectedRowNome = "";
    private String selectedRowGenero = "";
    private String selectedRowNPaginas = "";


    //Botões
    private JButton buttonCreate;
    private JButton buttonUpdate;


    //TextField
    private JTextField textFieldNome;
    private JTextField textFieldGenero;
    private JTextField textFieldNPaginas;

    //TextField Novo
    private JTextField textFieldNomeNovo;
    private JTextField textFieldGeneroNovo;
    private JTextField textFieldNPaginasNovo;

    private ArrayList<Livro> livrosGUI;


    public GUI (ArrayList<Livro> livros) {
        livrosGUI = livros;

        frame = new JFrame("Eletronic Library");

        //BARRA DE NAVEGAÇÃO
        navPanel = new JTabbedPane();

        //TELAS
        panelMain = new JPanel(); //livros
        panelMain.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));

        panelCreate = new JPanel(); //criação
        panelMain.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));

        panelUpdate = new JPanel(); //edição
        panelMain.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));


        //CONTEÚDO DAS PÁGINAS
        //Main
        panelMain.setLayout(null);

        JLabel mainText = new JLabel("TABELA DE LIVROS");
        mainText.setBounds(240, 20, 170, 20);

        panelMain.add(mainText);

        //Criando a tabela (Main)
        creatingTable();

        //Pegando a linha selecionada
        getSelectedRow();


        //Create (defina o estilo antes do conteúdo)
        //panelCreate.setLayout(new GridLayout(5, 2));
        panelCreate.setLayout(null);


        //Campos de texto (create)
        panelCreate.add(new JLabel("CADASTRO DE LIVROS")).setBounds(240, 20, 170, 20);
        JLabel labelNome = new JLabel("Nome: ");
        textFieldNome = new JTextField(15);
        panelCreate.add(labelNome).setBounds(30, 60, 100, 20);
        panelCreate.add(textFieldNome).setBounds(30, 100, 190, 30);

        JLabel labelGenero = new JLabel("Gênero: ");
        textFieldGenero = new JTextField(15);
        panelCreate.add(labelGenero).setBounds(30, 160, 100, 20);
        panelCreate.add(textFieldGenero).setBounds(30, 200, 190, 30);

        JLabel labelNPaginas = new JLabel("Nº de páginas: ");
        textFieldNPaginas = new JTextField(4);
        panelCreate.add(labelNPaginas).setBounds(30, 260, 100, 20);
        panelCreate.add(textFieldNPaginas).setBounds(30, 300, 190, 30);

        //Botões (create)
        buttonCreate = new JButton("Cadastrar livro");
        panelCreate.add(buttonCreate).setBounds(240, 400, 170, 30);
        buttonCreate.addActionListener(this);


        //Atualizar livro
        panelUpdate.setLayout(null);

        //Título
        panelUpdate.add(new JLabel("ATUALIZAR LIVRO")).setBounds(240, 20, 170, 20);

        //Campos de texto (novo)
        JLabel labelNomeNovo = new JLabel("Novo Nome: ");
        textFieldNomeNovo = new JTextField(15);
        panelUpdate.add(labelNomeNovo).setBounds(30, 60, 100, 20);
        panelUpdate.add(textFieldNomeNovo).setBounds(30, 100, 190, 30);

        JLabel labelGeneroNovo = new JLabel("Novo Gênero: ");
        textFieldGeneroNovo = new JTextField(15);
        panelUpdate.add(labelGeneroNovo).setBounds(30, 160, 100, 20);
        panelUpdate.add(textFieldGeneroNovo).setBounds(30, 200, 190, 30);

        JLabel labelNPaginasNovo = new JLabel("Novo Nº de páginas: ");
        textFieldNPaginasNovo = new JTextField(4);
        panelUpdate.add(labelNPaginasNovo).setBounds(30, 260, 100, 20);
        panelUpdate.add(textFieldNPaginasNovo).setBounds(30, 300, 190, 30);

        //Botões (create)
        buttonUpdate = new JButton("Atualizar livro");
        panelUpdate.add(buttonUpdate).setBounds(240, 400, 170, 30);
        buttonUpdate.addActionListener(this);


        //TÓPICOS
        navPanel.add("Home", panelMain);
        navPanel.add("Cadastro", panelCreate);

        frame.add(navPanel);

        navPanel.getModel().addChangeListener(e -> {

            //O segundo painel está presente, mas o usuário saiu da tela
            if (navPanel.getSelectedIndex() != 2 && isIn){
                navPanel.removeTabAt(2);

                isIn = false;
                tableBooks.clearSelection();
            }
        });

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack(); //define o tamanho do elemento (frame)
        frame.setMinimumSize(new Dimension(620, 540));
        frame.setMaximumSize(new Dimension(620, 540));
        frame.setLocationRelativeTo(null); //centro
        frame.setResizable(false); //tamanho inalterável
        frame.setVisible(true);
    }

    //INÍCIO
    public static void main(String[] args) {
        //Lendo de outro programa
        try {
            Biblioteca biblioteca = Biblioteca.abrir("new.txt");
            new GUI(biblioteca.retornarLivros());
        } catch (IOException e){
            System.out.println("Exceção de IO");
            System.out.println(e.getMessage());

        } catch (ClassNotFoundException e){
            System.out.println("Classe desconhecida");
            System.out.println(e.getMessage());
        }
    }

    //FUNÇÕES DE BOTÕES
    @Override
    public void actionPerformed(ActionEvent e) {
        //botão de cadastro
        if (e.getSource() == buttonCreate){
            String txtNome = textFieldNome.getText();
            String txtGenero = textFieldGenero.getText();
            String txtNPaginas = textFieldNPaginas.getText();

            try {
                verifyTextField(txtNome, txtGenero, txtNPaginas, "c");
            } catch (LivroInvalidoException ex) {
                //Null - caixa centralizada / Caixa na mensagem de diálogo
                JOptionPane.showMessageDialog(panelCreate, ex.getMessage());
            }

            textFieldNome.setText("");
            textFieldGenero.setText("");
            textFieldNPaginas.setText("");

            navPanel.setSelectedIndex(0);
        }
        //botão de atualização
        else if (e.getSource() == buttonUpdate) {
            String txtNome = textFieldNomeNovo.getText();
            String txtGenero = textFieldGeneroNovo.getText();
            String txtNPaginas = textFieldNPaginasNovo.getText();

            try {
                verifyTextField(txtNome, txtGenero, txtNPaginas, "u");

            } catch (LivroInvalidoException ex) {
                //Null - caixa centralizada / Caixa na mensagem de diálogo
                JOptionPane.showMessageDialog(panelUpdate, ex.getMessage());
            }
        }
    }

    //VERIFICAÇÕES DE CAMPOS
    public void verifyTextField(String nome, String genero, String nPaginas, String code) throws LivroInvalidoException {
        if (nome.isEmpty() || genero.isEmpty() || nPaginas.isEmpty())  {

            throw new LivroInvalidoException("Algum campo não foi preenchido. Responda corretamente!");

        } else if (code.equals("c")) {

            try {
                isAlreadyCreated(nome);
                model.addRow(new Object[]{nome, genero, nPaginas});
                navPanel.setSelectedIndex(0);
            } catch (LivroJaExistente ex){
                JOptionPane.showMessageDialog(panelCreate, ex.getMessage());
            }

        } else if (code.equals("u")){

            try {
                isSomethingChanged(nome, genero, nPaginas);

                model.setValueAt(nome, selectedRow, 0);
                model.setValueAt(genero, selectedRow, 1);
                model.setValueAt(nPaginas, selectedRow, 2);

                textFieldNomeNovo.setText("");
                textFieldGeneroNovo.setText("");
                textFieldNPaginasNovo.setText("");

                //Limpa a seleção e vai para a página principal caso funcione
                tableBooks.clearSelection();
                navPanel.setSelectedIndex(0);
            } catch (LivroNaoAlterado ex){
                JOptionPane.showMessageDialog(panelUpdate, ex.getMessage());
            }
        }
    }

    public void isAlreadyCreated(String nome) throws LivroJaExistente {
        for (int i = 0; i < tableBooks.getRowCount(); i++){
            if(tableBooks.getValueAt(i, 0).equals(nome)){
                throw new LivroJaExistente("Esse livro já está cadastrado");
            }
        }
    }

    public void isSomethingChanged(String nome, String genero, String nPaginas) throws LivroNaoAlterado{
        if (nome.equals(selectedRowNome) && genero.equals(selectedRowGenero) && nPaginas.equals(selectedRowNPaginas))
            throw new LivroNaoAlterado("Livro não alterado");
    }


    //CRIANDO E RESGATANDO DADOS DA TABELA
    public void creatingTable(){
        String[] columnNomes = {"Nome", "Gênero", "Nº de Páginas", ""};

        model.addColumn(columnNomes[0]);
        model.addColumn(columnNomes[1]);
        model.addColumn(columnNomes[2]);

        panelMain.add(tableBooks.getTableHeader()).setBounds(50, 60, 500, 40);
        panelMain.add(tableBooks).setBounds(50, 100, 500, 280);

        if (!livrosGUI.isEmpty()){
            for (Livro livro : livrosGUI){
                model.addRow(new Object[]{
                        livro.retornoAtributo("nome"),
                        livro.retornoAtributo("genero"),
                        livro.retornoAtributo("")
                });
            }
        }
    }

    //Selecionar uma linha da tabela de livros
    public void getSelectedRow() {
        tableBooks.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                selectedRow = tableBooks.getSelectedRow();
                if (!e.getValueIsAdjusting()){
                    if (selectedRow >= 0){
                        selectedRowNome = (String) tableBooks.getValueAt(selectedRow, 0);
                        selectedRowGenero = (String) tableBooks.getValueAt(selectedRow, 1);
                        selectedRowNPaginas = (String) tableBooks.getValueAt(selectedRow, 2);

                        textFieldNomeNovo.setText(selectedRowNome);
                        textFieldGeneroNovo.setText(selectedRowGenero);
                        textFieldNPaginasNovo.setText(selectedRowNPaginas);

                        //Adiciona a opção de atualização
                        navPanel.add("Atualização", panelUpdate);
                        navPanel.setSelectedComponent(panelUpdate);

                        isIn = true;
                    }
                }
            }
        });
    }
}
