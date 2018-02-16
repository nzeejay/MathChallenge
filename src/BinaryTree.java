public class BinaryTree {
    Node root;

    private String retString = "";

    public void addNode(Question item) {

        if (root == null) {
            root = new Node(item);
        } else {
            root.add(item);
        }
    }

    private void travPreOrder(Node node) {

        if(node == null)
            return;

        //add string
        String print = String.format("%.2f(%.2f%s%.2f), ",  node.value.Answer,
                node.value.Number1,
                node.value.getOperator(),
                node.value.Number2);

        retString += print;
        //then left
        travPreOrder(node.left);
        //then right
        travPreOrder(node.right);
    }

    private void travInOrder(Node node) {

        if(node == null)
            return;
        //left
        travInOrder(node.left);
        //then add string
        String print = String.format("%.2f(%.2f%s%.2f), ",  node.value.Answer,
                node.value.Number1,
                node.value.getOperator(),
                node.value.Number2);

        retString += print;
        //then right
        travInOrder(node.right);
    }

    private void travPostOrder(Node node) {

        if(node == null)
            return;
        //left
        travPostOrder(node.left);
        //then right
        travPostOrder(node.right);
        //then add string
        String print = String.format("%.2f(%.2f%s%.2f), ",  node.value.Answer,
                                                            node.value.Number1,
                                                            node.value.getOperator(),
                                                            node.value.Number2);

        retString += print;
    }

    public String travPreOrder() {
        travPreOrder(root);

        String ret = retString;
        retString = "";

        return ret;
    }

    public String travInOrder() {
        travInOrder(root);

        String ret = retString;
        retString = "";

        return ret;
    }

    public String travPostOrder() {
        travPostOrder(root);

        String ret = retString;
        retString = "";

        return ret;
    }
}

class Node {
    Question value = null;

    Node left;
    Node right;

    boolean isDrawSelection = false;

    public Node(Question val)
    {
        value = val;
    }

    public void add(Question item) {

        if(item.Answer < value.Answer) {

            if(left == null)
                left = new Node(item);
            else
                left.add(item);
        } else {

            if(right == null)
                right = new Node(item);
            else
                right.add(item);
        }
    }
}
