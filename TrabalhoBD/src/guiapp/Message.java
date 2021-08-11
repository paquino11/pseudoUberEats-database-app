package guiapp;

import javax.swing.*;

public class Message {
    public static void showMessage(String msg, String title ) {
        JOptionPane.showMessageDialog(null, msg, title, JOptionPane.WARNING_MESSAGE);
    }
}
