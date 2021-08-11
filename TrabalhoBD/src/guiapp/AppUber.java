package guiapp;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AppUber {
    private JPanel MainPanel;
    private JScrollPane results;
    private JButton buttonListMenus;
    private JTable tableDados;
    private JButton btexit;
    private JButton butTop;
    private JLabel resultsLabel;
    private JButton butInsertMenu;
    private JButton butShowImg;
    private JComboBox comboBoxListFotos;
    private JLabel listfotosLabel;
    private JButton TotMenVendButton;
    private JButton verAsMinhasRecomendaçõesButton;
    private JButton qualOTotalPagoButton;
    private JButton lucroDeCadaEstabelecimentoButton;
    private JButton menusMaisEncomendadosButton;
    private JButton estabelecimentoComMaiorPontauacaoButton;
    private JButton quaisOsFornecedoresQueButton;
    private JButton quaisOsMenusComButton;
    private JButton listaDeEstabelecimentosButton;
    private JButton inserirNovoEstabelecimentoButton;
    private JButton inserirFornecedorButton;
    private JButton inserirTransportadoraButton;
    private JButton inserirNovoProdutoButton;
    private JButton listaDeFornecedoresButton;
    private JButton menusQueNinguemEncomendaButton;
    private JButton comprarProdutosAosFornecedoresButton;
    private JButton inserirEncomendasButton;
    private JButton RecomendacaoButton;
    private DAL dal;

    public AppUber() {
        buttonListMenus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // action to list musics
                ShowDBaseRows(dal.ListMenus());
            }
        });
        btexit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    dal.closeDB();
                } catch (SQLException throwables) {
                    Message.showMessage("BD close error", "App Uber");
                }
                System.exit(0);
            }
        });
        butTop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                DialogInsertDate dialog = new DialogInsertDate();
                dialog.setTitle("Inserir a data");
                dialog.setActiveDal(dal);
                dialog.pack();
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
                ShowDBaseRows(dal.TopMenuDate());
            }
        });
        butInsertMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Iniciar DialogInsertMusic
                DialogInsertMenu dialog = new DialogInsertMenu();
                dialog.setTitle("Inserir novo Menu");
                dialog.setActiveDal(dal);
                dialog.pack();
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            }
        });
        butShowImg.addActionListener(e -> {
            JFrame frame = new JFrame();
            frame.setLocationRelativeTo(null);  //centrar ecrãn
            String FotoNameFile = "Fotos\\" + comboBoxListFotos.getSelectedItem().toString();
            ImageIcon icon = new ImageIcon(FotoNameFile);
            JLabel label = new JLabel(icon);
            frame.add(label);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
        });
//        comboBoxListFotos.addItemListener(new ItemListener() {
//            @Override
//            public void itemStateChanged(ItemEvent e) {
//
//            }
//        });
        TotMenVendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ShowDBaseRows(dal.TotVendEst());
            }
        });
        listaDeEstabelecimentosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ShowDBaseRows(dal.ListaEstabelecimentos());
            }
        });
        inserirNovoEstabelecimentoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DialogInsertEstabelecimento dialog = new DialogInsertEstabelecimento();
                dialog.setTitle("Inserir novo Estabelecimento");
                dialog.setActiveDal(dal);
                dialog.pack();
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            }
        });

        qualOTotalPagoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ShowDBaseRows(dal.TotalPagoTransportadoras());
            }
        });


        lucroDeCadaEstabelecimentoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ShowDBaseRows(dal.LucroEstabelecimentos());
            }
        });
        menusMaisEncomendadosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ShowDBaseRows(dal.TopMenusEncomendados());
            }
        });

        estabelecimentoComMaiorPontauacaoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ShowDBaseRows(dal.EstTopPontuacao());
            }
        });


        quaisOsFornecedoresQueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ShowDBaseRows(dal.TopForMenu());
            }
        });
        inserirFornecedorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DialogInsertFornecedor dialog = new DialogInsertFornecedor();
                dialog.setTitle("Inserir novo Fornecedor");
                dialog.setActiveDal(dal);
                dialog.pack();
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            }
        });
        listaDeFornecedoresButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ShowDBaseRows(dal.ListaFornecedores());
            }
        });
        inserirTransportadoraButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DialogInsertTrans dialog = new DialogInsertTrans();
                dialog.setTitle("Inserir nova Transportadora");
                dialog.setActiveDal(dal);
                dialog.pack();
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            }
        });
        menusQueNinguemEncomendaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ShowDBaseRows(dal.ListaMenusNaoEcomendados());
            }
        });
        quaisOsMenusComButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DialogInsertPreco dialog = new DialogInsertPreco();
                dialog.setTitle("Inserir novo Fornecedor");
                dialog.setActiveDal(dal);
                dialog.pack();
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
                ShowDBaseRows(dal.PrecoMaxMin());
            }
        });
        inserirEncomendasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DialogInserirEncomendas dialog = new DialogInserirEncomendas();
                dialog.setTitle("Pedir nova Encomenda");
                dialog.setActiveDal(dal);
                dialog.pack();
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);

            }
        });
        inserirNovoProdutoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DialogInsertProdutos dialog = new DialogInsertProdutos();
                dialog.setTitle("Inserir novo Produto");
                dialog.setActiveDal(dal);
                dialog.pack();
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            }
        });
        RecomendacaoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DialogInsertRecomendacao dialog = new DialogInsertRecomendacao();
                dialog.setTitle("Inserir Recomendação");
                dialog.setActiveDal(dal);
                dialog.pack();
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            }
        });
        comprarProdutosAosFornecedoresButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DialogComprarProdutos dialog = new DialogComprarProdutos();
                dialog.setTitle("Comprar Produtos");
                dialog.setActiveDal(dal);
                dialog.pack();
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            }
        });
        verAsMinhasRecomendaçõesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ShowDBaseRows(dal.ListRecomendacoes());
            }
        });
    }

    private void ShowDBaseRows(ResultSet rs) {
        List<String[]> rows;
        String[] Headers;
        try {
            ResultSetMetaData meta = rs.getMetaData();
            int colCount = meta.getColumnCount();
            String[] headers = new String[colCount];
            for (int h = 1; h <= colCount; h++) {
                headers[h - 1] = meta.getColumnName(h);
            }
            rows = new ArrayList<String[]>();
            while (rs.next()) {
                String[] record = new String[colCount];
                for (int i = 0; i < colCount; i++) {
                    String val = rs.getString(i + 1);
                    //System.out.println(val);
                    record[i] = val;
                }
                rows.add(record);
            }
            String[][] tabrows = new String[rows.size()][colCount];
            int i = 0;
            for (String[] lin : rows) {
                tabrows[i] = lin;
                i++;
            }
            DefaultTableModel data = new DefaultTableModel(tabrows, headers);
            tableDados.setModel(data);
        } catch (Exception ex) {
            Message.showMessage("Show Data rows error:" + ex.getMessage(), "List Musics");
        } catch (OutOfMemoryError err) {
            Message.showMessage("Out of Memory: Reduza o critério de pesquisa!...\n" + err.getMessage(), "List Musics");
        }
    }


    public static void main(String[] args) {
        DAL dal = new DAL();

        DialogLogin dialog = new DialogLogin();
        dialog.setTitle("LOGIN");
        dialog.setActiveDal(dal);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);


        JFrame frame = new JFrame("AppUber: " + dal.getCurUser());
        AppUber appmusic = new AppUber();
        appmusic.dal = dal;
        frame.setContentPane(appmusic.MainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        //Para Criar o Jframe no centro do écran colocar no static main
        frame.setLocationRelativeTo(null);
        // Para ser sizeable
        frame.setResizable(true);
        frame.setTitle("APP UBER");
        frame.setVisible(true);
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        MainPanel = new JPanel();
        MainPanel.setLayout(new GridBagLayout());
        results = new JScrollPane();
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 8;
        gbc.fill = GridBagConstraints.BOTH;
        MainPanel.add(results, gbc);
        tableDados = new JTable();
        tableDados.setFocusable(true);
        results.setViewportView(tableDados);
        final JPanel spacer1 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 8;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        MainPanel.add(spacer1, gbc);
        final JPanel spacer2 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 9;
        gbc.fill = GridBagConstraints.VERTICAL;
        MainPanel.add(spacer2, gbc);
        final JPanel spacer3 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 10;
        gbc.fill = GridBagConstraints.VERTICAL;
        MainPanel.add(spacer3, gbc);
        buttonListMenus = new JButton();
        buttonListMenus.setText("Lista Menus");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        MainPanel.add(buttonListMenus, gbc);
        butTop = new JButton();
        butTop.setText("Mostrar o menu mais escolhido");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        MainPanel.add(butTop, gbc);
        resultsLabel = new JLabel();
        resultsLabel.setText("Resultado do Query");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.WEST;
        MainPanel.add(resultsLabel, gbc);
        final JPanel spacer4 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.VERTICAL;
        MainPanel.add(spacer4, gbc);
        butShowImg = new JButton();
        butShowImg.setText("ShowFoto");
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        MainPanel.add(butShowImg, gbc);
        comboBoxListFotos = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("PinkFloyd.jpg");
        defaultComboBoxModel1.addElement("RuiVeloso.jpg");
        defaultComboBoxModel1.addElement("AllMusic.jpg");
        comboBoxListFotos.setModel(defaultComboBoxModel1);
        gbc = new GridBagConstraints();
        gbc.gridx = 5;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        MainPanel.add(comboBoxListFotos, gbc);
        listfotosLabel = new JLabel();
        listfotosLabel.setText("Select Foto");
        gbc = new GridBagConstraints();
        gbc.gridx = 5;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        MainPanel.add(listfotosLabel, gbc);
        butInsertMenu = new JButton();
        butInsertMenu.setText("Inserir novo menu");
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        MainPanel.add(butInsertMenu, gbc);
        final JPanel spacer5 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.VERTICAL;
        MainPanel.add(spacer5, gbc);
        final JPanel spacer6 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 6;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        MainPanel.add(spacer6, gbc);
        final JPanel spacer7 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        MainPanel.add(spacer7, gbc);
        final JPanel spacer8 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.VERTICAL;
        MainPanel.add(spacer8, gbc);
        TotMenVendButton = new JButton();
        TotMenVendButton.setText("Total de menus vendidos por estabelecimento");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        MainPanel.add(TotMenVendButton, gbc);
        verAsMinhasRecomendaçõesButton = new JButton();
        verAsMinhasRecomendaçõesButton.setText("Button");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        MainPanel.add(verAsMinhasRecomendaçõesButton, gbc);
        qualOTotalPagoButton = new JButton();
        qualOTotalPagoButton.setText("Qual o total pago a cada transportadora");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 8;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        MainPanel.add(qualOTotalPagoButton, gbc);
        lucroDeCadaEstabelecimentoButton = new JButton();
        lucroDeCadaEstabelecimentoButton.setText("Lucro de cada Estabelecimento");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 9;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        MainPanel.add(lucroDeCadaEstabelecimentoButton, gbc);
        menusMaisEncomendadosButton = new JButton();
        menusMaisEncomendadosButton.setText("Menus mais encomendados");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 10;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        MainPanel.add(menusMaisEncomendadosButton, gbc);
        estabelecimentoComMaiorPontauacaoButton = new JButton();
        estabelecimentoComMaiorPontauacaoButton.setText("Estabelecimento com maior pontauacao");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 11;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        MainPanel.add(estabelecimentoComMaiorPontauacaoButton, gbc);
        quaisOsFornecedoresQueButton = new JButton();
        quaisOsFornecedoresQueButton.setText("Quais os fornecedores que mais contribuem para os menus");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 12;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        MainPanel.add(quaisOsFornecedoresQueButton, gbc);
        quaisOsMenusComButton = new JButton();
        quaisOsMenusComButton.setText("Numero ");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        MainPanel.add(quaisOsMenusComButton, gbc);
        listaDeEstabelecimentosButton = new JButton();
        listaDeEstabelecimentosButton.setText("Lista de Estabelecimentos");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        MainPanel.add(listaDeEstabelecimentosButton, gbc);
        inserirFornecedorButton = new JButton();
        inserirFornecedorButton.setText("Inserir Fornecedor");
        inserirFornecedorButton.setVisible(true);
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        MainPanel.add(inserirFornecedorButton, gbc);
        inserirTransportadoraButton = new JButton();
        inserirTransportadoraButton.setText("Inserir Transportadora");
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 8;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        MainPanel.add(inserirTransportadoraButton, gbc);
        inserirNovoEstabelecimentoButton = new JButton();
        inserirNovoEstabelecimentoButton.setText("Inserir novo Estabelecimento");
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 9;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        MainPanel.add(inserirNovoEstabelecimentoButton, gbc);
        inserirNovoProdutoButton = new JButton();
        inserirNovoProdutoButton.setText("Button");
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 10;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        MainPanel.add(inserirNovoProdutoButton, gbc);
        btexit = new JButton();
        btexit.setText("Exit");
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 12;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        MainPanel.add(btexit, gbc);
        listaDeFornecedoresButton = new JButton();
        listaDeFornecedoresButton.setText("Lista de Fornecedores");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        MainPanel.add(listaDeFornecedoresButton, gbc);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return MainPanel;
    }

}
