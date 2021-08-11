package guiapp;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.event.*;

public class DialogInserirEncomendas extends JDialog{
    private JTextField NomeField;
    private JTextField NumeroField;
    private JTextField EnderecoField;
    private JButton OKButton;
    private JButton cancelButton;
    private JTextField DataField;
    private JPanel Container;
    private DAL dal;

    public void setActiveDal(DAL dal) {
        this.dal = dal;
    }
    public DialogInserirEncomendas() {
        setContentPane(Container);
        setModal(true);
        getRootPane().setDefaultButton(OKButton);

        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });
        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        Container.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        // add your code here
        try {
            String nome=NomeField.getText();
            int numero = Integer.parseInt(NumeroField.getText());
            String data= DataField.getText();
            String endereco=EnderecoField.getText();
            dal.insertEncomenda(nome, numero, data, endereco);
        } catch (Exception ex) {
            Message.showMessage("Erro na inserção dos dados da Encomenda", "Try again");
        }
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
