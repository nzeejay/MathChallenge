import com.intellij.uiDesigner.core.GridConstraints;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BTDisplay {
    private JPanel Content;
    private JButton preShow;
    private JPanel DrawingPanel;
    private JButton postShow;
    private JButton inShow;
    private JButton exitButton;
    private JButton toggleButton;

    JFrame frame;

    BinaryTree BT;

    drawTree DT;

    private int delay = 800;

    public BTDisplay(BinaryTree BT) {
        this.BT = BT;

        frame = new JFrame("Binary Tree Display");
        frame.setContentPane(Content);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        DT = new drawTree(BT);

        GridConstraints gc = new GridConstraints();
        gc.setFill(3);

        DrawingPanel.add(DT, gc);

        preShow.addActionListener(e -> {

            pre(DT.BT.root);

            reset(DT.BT.root);

            DT.repaint();
        });

        inShow.addActionListener(e -> {

            inOrd(DT.BT.root);

            reset(DT.BT.root);

            DT.repaint();
        });

        postShow.addActionListener(e -> {

            post(DT.BT.root);

            reset(DT.BT.root);

            DT.repaint();
        });

        exitButton.addActionListener(e -> frame.dispose());

        toggleButton.addActionListener(e -> {
            DT.showEquation = !DT.showEquation;
            DT.repaint();

            String str;
            if(DT.showEquation)
                str = "Hide ";
            else
                str = "Show ";

            str += "Equation";

            toggleButton.setText(str);
        });
    }



    private void pre(Node node) {
        if (node == null)
            return;

        draw(node);

        pre(node.left);
        pre(node.right);
    }

    private void inOrd(Node node) {
        if(node == null)
            return;

        inOrd(node.left);

        draw(node);

        inOrd(node.right);
    }

    private void post(Node node) {
        if(node == null)
            return;

        post(node.left);
        post(node.right);

        draw(node);
    }

    private void reset(Node node) {
        if(node == null)
            return;

        node.isDrawSelection = false;

        reset(node.left);
        reset(node.right);
    }

    private void draw(Node node) {

        node.isDrawSelection = true;

        DT.paint(DT.getGraphics());

        try {
            Thread.sleep(delay);
        } catch (Throwable T) { }
    }
}
