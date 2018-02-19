import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        //set look and feel
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Throwable e) {}

        new Instructor();

        //new Selector();
    }
}