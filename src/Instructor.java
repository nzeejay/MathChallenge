import Network.ServerNet;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.Random;

public class Instructor {
    private JPanel Table;
    private JPanel Title;
    private JTextField QuestionText;
    private JTextField AnswerText;
    private JTable AnswerTable;
    private JButton SortBubble;
    private JButton SortInsertion;
    private JButton SortMerge;
    private JButton QuestionSend;
    private JTextField LinkedListText;
    private JTextField BinaryText;
    private JButton BinaryDisplay;
    private JButton exitButton;
    private JButton PreorderDisplay;
    private JButton PreorderSave;
    private JPanel Content;
    private JButton InorderDisplay;
    private JButton InorderSave;
    private JButton PostorderDisplay;
    private JButton PostorderSave;

    JFrame frame;

    Question currentQuestion;

    BinaryTree currentBT = new BinaryTree();
    ArrayList<Question> questions = new ArrayList<Question>();

    ServerNet server;

    public Instructor() {
        //pre-display operations
        setupTable();

        //opening welcoming screen/file manager
        frame = new JFrame("Instructor");
        frame.setContentPane(Content);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setResizable(true);
        frame.setVisible(true);

        setupListeners();

        getPort();

        PreorderSave.addActionListener(e -> {
            Random r = new Random();

            for (int i = 0; i < 25; i++)
                sendQuestion(new Question(1,1,1, (r.nextFloat()-0.5f) * 50));
        });
    }

    private void getPort() {
        JTextField port = new JTextField();
        Object[] message = {
                "Port:", port
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Port",
                                                    JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            server = new ServerNet(Integer.parseInt(port.getText()));
        }
    }

    private void setupListeners() {
        QuestionText.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                try {
                    currentQuestion = new Question(QuestionText.getText());
                    QuestionText.setBackground(Color.white);
                    QuestionSend.setEnabled(true);
                    AnswerText.setText("" + currentQuestion.getAnswer());
                } catch (Throwable T)
                {
                    QuestionSend.setEnabled(false);
                    QuestionText.setBackground(Color.red);
                }
                super.focusLost(e);
            }
        });

        QuestionSend.addActionListener(e -> sendQuestion());

        PreorderDisplay.addActionListener(e -> BinaryText.setText(currentBT.travPreOrder()));

        InorderDisplay.addActionListener(e -> BinaryText.setText(currentBT.travInOrder()));

        PostorderDisplay.addActionListener(e -> BinaryText.setText(currentBT.travPostOrder()));

        BinaryDisplay.addActionListener(e -> new BTDisplay(currentBT));

        //sorts
        SortBubble.addActionListener(e -> setTableQuestion(Sorting.Bubble(questions)));
        SortInsertion.addActionListener(e -> setTableQuestion(Sorting.Insertion(questions)));
        SortMerge.addActionListener(e -> setTableQuestion(Sorting.mergeSort(questions)));
    }

    private void sendQuestion() {
        Question temp = new Question(currentQuestion.Number1,
                                     currentQuestion.Operator,
                                     currentQuestion.Number2,
                                     currentQuestion.getAnswer());

        questions.add(temp);

        addTableQuestion(temp);

        currentBT.addNode(temp);
    }

    private void sendQuestion(Question temp) {

        addTableQuestion(temp);

        questions.add(temp);

        currentBT.addNode(temp);
    }

    private void setupTable() {
        AnswerTable.setTableHeader(null);
        String[] columnNames = {"Num1", "Opp", "Num2", "=", "Ans"};
        AnswerTable.setModel(new DefaultTableModel(null, columnNames));

        DefaultTableCellRenderer numberRender = new DefaultTableCellRenderer();
        numberRender.setHorizontalAlignment( JLabel.CENTER );

        DefaultTableCellRenderer symbolRender = new DefaultTableCellRenderer();
        symbolRender.setHorizontalAlignment( JLabel.CENTER );
        symbolRender.setBackground(Color.lightGray);

        AnswerTable.getColumnModel().getColumn(0).setCellRenderer(numberRender);

        AnswerTable.getColumnModel().getColumn(1).setCellRenderer(symbolRender);
        AnswerTable.getColumnModel().getColumn(1).setMaxWidth(14);

        AnswerTable.getColumnModel().getColumn(2).setCellRenderer(numberRender);

        AnswerTable.getColumnModel().getColumn(3).setCellRenderer(symbolRender);
        AnswerTable.getColumnModel().getColumn(3).setMaxWidth(14);

        AnswerTable.getColumnModel().getColumn(4).setCellRenderer(numberRender);

        AnswerTable.setCellSelectionEnabled(false);
        AnswerTable.setDefaultEditor(Object.class, null);
    }

    public void addTableQuestion(Question item) {
        DefaultTableModel temp = (DefaultTableModel)AnswerTable.getModel();
        Object[] data = {item.Number1, item.getOperator(), item.Number2, "=", item.Answer};
        temp.addRow(data);
        AnswerTable.setModel(temp);
    }

    public void setTableQuestion(ArrayList<Question> items) {
        DefaultTableModel temp = (DefaultTableModel)AnswerTable.getModel();
        temp.setRowCount(0);

        for (Question item : items) {
            Object[] data = {item.Number1, item.getOperator(), item.Number2, "=", item.Answer};
            temp.addRow(data);
        }

        AnswerTable.setModel(temp);
    }
}
