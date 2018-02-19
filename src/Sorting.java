import java.util.ArrayList;

public class Sorting {

    public static ArrayList<Question> Bubble(ArrayList<Question> questions) {
        int progress = 1;

        while (progress != questions.size()) {
            for (int i = 0; i < questions.size() - progress; i++) {
                Question current = questions.get(i);

                if (current.Answer > questions.get(i + 1).Answer) {
                    questions.set(i, questions.get(i + 1));
                    questions.set(i + 1, current);
                }
            }
            progress++;
        }

        return questions;
    }

    public static ArrayList<Question> Insertion(ArrayList<Question> questions) {
        ArrayList<Question> sorted = new ArrayList<>();
        sorted.add(questions.get(0));

        while (sorted.size() != questions.size()) {
            boolean hasBroken = false;
            for (int i = 0; i < sorted.size(); i++) {
                if (sorted.get(i).Answer > questions.get(sorted.size()).Answer) {
                    sorted.add(i, questions.get(sorted.size()));
                    hasBroken = true;
                    break;
                }
            }

            if (!hasBroken)
                sorted.add(questions.get(sorted.size()));
        }
        return sorted;
    }

    public static ArrayList<Question> mergeSort(ArrayList<Question> data) {

        mergeNode root = new mergeNode(data.toArray(new Question[data.size()]), null);

        mergeTraverse(root);

        ArrayList<Question> ret = new ArrayList<>();

        for (int i = 0; i < root.data.length; i++) {
            ret.add(root.data[i]);
        }

        return ret;
    }

    private static void mergeTraverse(mergeNode node) {
        if (node == null)
            return;

        mergeTraverse(node.left);
        mergeTraverse(node.right);

        if (node.parent != null)
            if (node.parent.right == node)
                node.sort();
    }
}

class mergeNode {
    mergeNode left;
    mergeNode right;

    Question[] data;

    mergeNode parent;

    public mergeNode(Question[] data, mergeNode parent) {
        this.data = data;
        this.parent = parent;

        split();
    }

    private void split() {
        if(data.length == 1)
            return;

        int split = data.length/2;

        Question[] l = new Question[split];
        Question[] r = new Question[data.length-split];

        for (int i = 0; i < data.length; i++) {
            if(i < split)
                l[i] = data[i];
            else
                r[i-split] = data[i];
        }

        left = new mergeNode(l, this);
        right = new mergeNode(r, this);
    }

    public void sort() {
        if(parent == null)
            return;

        int leftPointer = 0;
        int rightPointer = 0;

        for (int i = 0; i < parent.data.length; i++) {
            if(rightPointer >= data.length) {
                parent.data[i] = parent.left.data[leftPointer];
                leftPointer++;
                continue;
            }

            if(leftPointer >= parent.left.data.length) {
                parent.data[i] = data[rightPointer];
                rightPointer++;
                continue;
            }

            if(data[rightPointer].Answer < parent.left.data[leftPointer].Answer) {
                parent.data[i] = data[rightPointer];
                rightPointer++;
            } else {
                parent.data[i] = parent.left.data[leftPointer];
                leftPointer++;
            }
        }
    }
}
