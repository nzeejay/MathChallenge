import javax.swing.*;

public class Selector {
    private JPanel Content;
    private JButton StudentButton;
    private JButton teacherButton;

    private JFrame frame;

    public Selector() {
        //opening welcoming screen/file manager
        frame = new JFrame("Selector");
        frame.setContentPane(Content);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);

        //listeners
        StudentButton.addActionListener(e -> {
            new Student();
            frame.setVisible(false);
        });

        teacherButton.addActionListener(e -> {
            new Instructor();
            frame.setVisible(false);
        });

    }
}
