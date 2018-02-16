import Network.ClientNet;

import javax.swing.*;

public class Student {
    private JTextField textField1;
    private JButton submitButton;
    private JButton exitButton;
    private JPanel Content;

    JFrame frame;

    ClientNet cn;

    public Student() {
        //opening welcoming screen/file manager
        frame = new JFrame("Student");
        frame.setContentPane(Content);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setResizable(true);
        frame.setVisible(true);

        getServerInfo();
    }

    public void getServerInfo() {
        JTextField addr = new JTextField();
        JTextField port = new JTextField();
        Object[] message = {
                "Address:", addr,
                "Port:", port
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Server info", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION)
            try {
                cn = new ClientNet(addr.getText(), Integer.parseInt(port.getText()));
            } catch (Throwable T) {
                getServerInfo();
            }
        else {
            frame.dispose();
            new Selector();
        }
    }
}
