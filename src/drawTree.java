import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;

public class drawTree extends JPanel {

    public BinaryTree BT;
    public boolean showEquation = true;

    public drawTree(BinaryTree BT) {
        this.BT = BT;

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                repaint();
            }
        });

        repaint();
    }

    public void paint(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.DARK_GRAY);
        g.setFont(this.getFont());

        layer = 0;
        history = new ArrayList<>();

        drawTrav(g, BT.root, null);
    }

    private int layer = 0;
    private ArrayList<Boolean> history = new ArrayList<>();

    private void drawTrav(Graphics g, Node node, Point rootPos) {
        g.setColor(Color.DARK_GRAY);

        if (node == null) {
            if(history.size() != 0)
                history.remove(history.size()-1);
            return;
        }

        Point thisPos = new Point();

        float layerNodeCount = (float)Math.pow(2, layer) + 1;

        thisPos.x = (int)((getWidth() / layerNodeCount) * (float)getTreeIndex(history));
        thisPos.y = (getHeight() / 10) * (layer+1);

        layer++;

        if(node.isDrawSelection)
            g.setColor(Color.blue);

        String str;

        if(showEquation)
            str = String.format("%.2f(%.2f%s%.2f)", node.value.Answer,
                                node.value.Number1,
                                node.value.getOperator(),
                                node.value.Number2);
        else
            str = String.format("%.2f", node.value.Answer);

        g.drawString(str, thisPos.x, thisPos.y);

        //offset by str
        thisPos.x += str.length() * 2;
        thisPos.y -= 12;

        g.setColor(Color.DARK_GRAY);

        if (rootPos != null)
            g.drawLine(thisPos.x, thisPos.y, rootPos.x, rootPos.y);


        thisPos.y += 14;

        history.add(true);
        drawTrav(g, node.left, thisPos);

        history.add(false);
        drawTrav(g, node.right, thisPos);

        layer--;

        if(history.size() != 0)
            history.remove(history.size()-1);
    }

    private int getTreeIndex(ArrayList<Boolean> hist) {
        if(hist.size() == 0)
            return 1;

        int index = 1;

        for(int i = 0; i < hist.size(); i++) {

            if(!hist.get(i))
                index *= 2;
            else if (index > 1) {
                index *= 2;
                index--;
            }
        }

        return index;
    }
}
