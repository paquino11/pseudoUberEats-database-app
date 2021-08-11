package guiapp;

import javax.swing.*;
import java.awt.event.*;

public class DialogComprarProdutos extends JDialog {
    private JTextField IDFornField;
    private JTextField IDEstField;
    private JTextField CodProdField;
    private JTextField QuantidadeField;
    private JButton OKButton;
    private JButton cancelButton;
    private JPanel Container;
    private DAL dal;

    public void setActiveDal(DAL dal) {
        this.dal = dal;
    }

    public DialogComprarProdutos() {
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
            int idEst=Integer.parseInt(IDEstField.getText());
            int idForn=Integer.parseInt(IDFornField.getText());
            int codigo= Integer.parseInt(CodProdField.getText());
            int quantidade= Integer.parseInt(QuantidadeField.getText());
            boolean inserido=dal.CheckInserido(codigo, idEst, idForn);
            if (inserido)
                dal.UpdateQuantidade(idEst,codigo, quantidade);
            else
                dal.insertComprarProduto(idEst, idForn, codigo, quantidade);
        } catch (Exception ex) {
            Message.showMessage("Erro na inserção dos dados da Compra", "Try again");
        }
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
