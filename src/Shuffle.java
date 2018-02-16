import java.util.ArrayList;
import java.util.Random;

public class Shuffle {
    public int[] data;


    public void generateSequential(int limit) {
        data = new int[limit];

        for (int i = 0; i < data.length; i++)
            data[i] = i;
    }

    public void shuffle() {
        ArrayList<Integer> unsorted = new ArrayList<>();

        Random R = new Random();

        for (int i = 0; i < data.length; i++) {
            int index = R.nextInt(unsorted.size()+1);
            unsorted.add(index, data[i]);
        }

        for (int i = 0; i < unsorted.size(); i++)
            data[i] = unsorted.get(i);
    }
}
