import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class Frame extends JPanel {
    final JFrame frame = new JFrame();
    private final JTabbedPane tabbedPane;
    private static SlangDictionary dict;
    private final JTextField pS_top_textField;
    private final JTextArea pS_textArea;
    private final JRadioButton pS_radio_slang;
    private final JRadioButton pS_radio_meaning;
    private final JTextArea pH_textArea;
    private final JTextField pA_word_textField;
    private final JTextField pA_meaning_textField;
    private final JTextField pM_wordEdit_textField;
    private final JTextField pM_word_textField;
    private final JTextField pM_meaning_textField;
    private final JButton pQ_startSlang;
    private final JButton pQ_startMeaning;
    private final JButton pQ_end;
    private Slang quizCorrect;
    private JLabel pQ_question;
    private JButton pQ_answer1;
    private JButton pQ_answer2;
    private JButton pQ_answer3;
    private JButton pQ_answer4;
    private int pQ_count = 0;
    private int pQ_correct = 0;
    private int pQ_type = 0;
    public void copyDict(SlangDictionary i_dict) {
        dict = new SlangDictionary(i_dict);
    }
    public Frame() {
        setLayout(new BorderLayout());

        // Tabs
        tabbedPane = new JTabbedPane();

        // SEARCH
        JComponent tabPanelS = createTabPanel();

        // Search bar panel
        JPanel pS_top = new JPanel();

        JLabel pS_top_label = new JLabel("Search:");
        pS_top.add(pS_top_label);
        pS_top_textField = new JTextField(20);
        pS_top.add(pS_top_textField);
        JButton pS_top_button = new JButton("Search");
        pS_top_button.addActionListener(new SearchSlang());
        pS_top.add(pS_top_button);

        tabPanelS.add(pS_top);

        // Radio buttons panel
        JPanel pS_radio = new JPanel();

        pS_radio_slang = new JRadioButton("by Slang", true);
        pS_radio_meaning = new JRadioButton("by Meaning");
        ButtonGroup pS_radio_group = new ButtonGroup();
        pS_radio_group.add(pS_radio_slang);
        pS_radio_group.add(pS_radio_meaning);
        pS_radio.add(pS_radio_slang);
        pS_radio.add(pS_radio_meaning);

        tabPanelS.add(pS_radio);

        // Text area panel
        JPanel pS_body = new JPanel();
        pS_body.setLayout(new BorderLayout());
        pS_body.setBorder(BorderFactory.createEmptyBorder(10, 5, 5, 5));

        pS_textArea = new JTextArea();
        pS_textArea.setEditable(false);
        JScrollPane pS_textArea_scroll = new JScrollPane(pS_textArea);
        pS_body.add(pS_textArea_scroll);

        tabPanelS.add(pS_body);

        // Random slang panel
        JPanel pS_bottom = new JPanel();

        JButton pS_random = new JButton("Random Slang");
        pS_random.addActionListener(new RandomSlang());
        pS_bottom.add(pS_random);

        tabPanelS.add(pS_bottom);

        tabbedPane.addTab("Search", null, tabPanelS, "Search slang words");

        // HISTORY
        JComponent tabPanelH = createTabPanel();

        // Text area panel
        JPanel pH_body = new JPanel();
        pH_body.setLayout(new BorderLayout());
        pH_body.setBorder(BorderFactory.createEmptyBorder(10, 5, 5, 5));

        pH_textArea = new JTextArea();
        pH_textArea.setEditable(false);
        JScrollPane pH_textArea_scroll = new JScrollPane(pH_textArea);
        pH_body.add(pH_textArea_scroll);

        tabPanelH.add(pH_body);

        tabbedPane.addTab("History", null, tabPanelH, "Show history");

        // ADD
        JComponent tabPanelA = createTabPanel();

        // Input panel
        JPanel pA_input = new JPanel();
        pA_input.setLayout(new GridLayout(0, 1));
        pA_input.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JLabel pA_word_label = new JLabel("Word:");
        pA_input.add(pA_word_label);
        pA_word_textField = new JTextField(20);
        pA_input.add(pA_word_textField);
        JLabel pA_meaning_label = new JLabel("Definition(s):");
        pA_input.add(pA_meaning_label);
        pA_meaning_textField = new JTextField(20);
        pA_input.add(pA_meaning_textField);
        pA_input.add(Box.createRigidArea(new Dimension(0, 5)));

        tabPanelA.add(pA_input);

        // Button panel
        JPanel pA_bottom = new JPanel();

        JButton pA_add = new JButton("Add Slang");
        pA_add.addActionListener(new AddSlang());
        pA_bottom.add(pA_add);

        tabPanelA.add(pA_bottom);

        tabbedPane.addTab("Add", null, tabPanelA, "Add new slang words");

        // MODIFY
        JComponent tabPanelM = createTabPanel();

        // Input panel
        JPanel pM_input = new JPanel();
        pM_input.setLayout(new GridLayout(0, 1));
        pM_input.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JLabel pM_wordEdit_label = new JLabel("Word to modify:");
        pM_input.add(pM_wordEdit_label);
        pM_wordEdit_textField = new JTextField(20);
        pM_input.add(pM_wordEdit_textField);
        JLabel pM_word_label = new JLabel("Edit word:");
        pM_input.add(pM_word_label);
        pM_word_textField = new JTextField(20);
        pM_input.add(pM_word_textField);
        JLabel pM_meaning_label = new JLabel("Edit definition(s):");
        pM_input.add(pM_meaning_label);
        pM_meaning_textField = new JTextField(20);
        pM_input.add(pM_meaning_textField);
        pM_input.add(Box.createRigidArea(new Dimension(0, 5)));

        tabPanelM.add(pM_input);

        // Button panel
        JPanel pM_bottom = new JPanel();

        JButton pM_edit = new JButton("Edit Slang");
        pM_edit.addActionListener(new EditSlang());
        pM_bottom.add(pM_edit);
        JButton pM_delete = new JButton("Delete Slang");
        pM_delete.addActionListener(new DeleteSlang());
        pM_bottom.add(pM_delete);
        JButton pM_reset = new JButton("Reset Original List");
        pM_reset.addActionListener(new ResetSlangList());
        pM_bottom.add(pM_reset);

        tabPanelM.add(pM_bottom);

        tabbedPane.addTab("Modify", null, tabPanelM, "Modify existing slang words");

        // QUIZ
        JComponent tabPanelQ = createTabPanel();

        // Question panel
        JPanel pQ_top = new JPanel();

        pQ_question = new JLabel();
        pQ_top.add(pQ_question);

        tabPanelQ.add(pQ_top);

        // Answers panel
        JPanel pQ_answers = new JPanel();
        pQ_answers.setLayout(new GridLayout(2, 2));

        pQ_answer1 = new JButton();
        pQ_answer2 = new JButton();
        pQ_answer3 = new JButton();
        pQ_answer4 = new JButton();
        pQ_answer1.addActionListener(new AnswerQuiz());
        pQ_answer2.addActionListener(new AnswerQuiz());
        pQ_answer3.addActionListener(new AnswerQuiz());
        pQ_answer4.addActionListener(new AnswerQuiz());
        pQ_answer1.setEnabled(false);
        pQ_answer2.setEnabled(false);
        pQ_answer3.setEnabled(false);
        pQ_answer4.setEnabled(false);
        pQ_answers.add(pQ_answer1);
        pQ_answers.add(pQ_answer2);
        pQ_answers.add(pQ_answer3);
        pQ_answers.add(pQ_answer4);

        tabPanelQ.add(pQ_answers);

        // Buttons panel
        JPanel pQ_bottom = new JPanel();

        pQ_startSlang = new JButton("Slang Quiz");
        pQ_startSlang.addActionListener(new SlangQuiz());
        pQ_bottom.add(pQ_startSlang);
        pQ_startMeaning = new JButton("Definition Quiz");
        pQ_startMeaning.addActionListener(new MeaningQuiz());
        pQ_bottom.add(pQ_startMeaning);
        pQ_end = new JButton("End Quiz");
        pQ_end.addActionListener(new EndQuizAction());
        pQ_end.setEnabled(false);
        pQ_bottom.add(pQ_end);

        tabPanelQ.add(Box.createRigidArea(new Dimension(0, 20)));
        tabPanelQ.add(pQ_bottom);

        tabbedPane.addTab("Quiz", null, tabPanelQ, "Play slang words quiz");

        add(tabbedPane);

        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
    }
    protected JPanel createTabPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        return panel;
    }
    public void displaySlang(Slang word) {
        pS_textArea.append(word.getWord() + "\n" + "Definition(s):" + "\n");
        for (String def : word.getDefinition()) {
            pS_textArea.append("- " + def + "\n");
        }
    }
    public void saveHistory(Slang word) {
        StringBuilder text = new StringBuilder(word.getWord() + "\n" + "Definition(s):" + "\n");
        for (String def : word.getDefinition()) {
            text.append("- ").append(def).append("\n");
        }
        text.append("\n");
        try {
            pH_textArea.getDocument().insertString(0, text.toString(), null);
            pH_textArea.setCaretPosition(0);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
    class SearchSlang implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (pS_radio_slang.isSelected()) {
                Integer index = dict.findSlangByWord(pS_top_textField.getText().toUpperCase());
                pS_textArea.setText("");
                if (index == -1) {
                    pS_textArea.setText("Couldn't find that word");
                }
                else {
                    Slang word = dict.getSlangAt(index);
                    displaySlang(word);
                    saveHistory(word);
                    pS_textArea.setCaretPosition(0);
                }
            }
            else {
                ArrayList<Slang> words = dict.findSlangByMeaning(pS_top_textField.getText());
                pS_textArea.setText("");
                if (words.isEmpty()) {
                    pS_textArea.setText("Couldn't find any matching word");
                }
                else {
                    pS_textArea.append("Found " + words.size() + " words matching this definition:" + "\n\n");
                    for (Slang word : words) {
                        displaySlang(word);
                        saveHistory(word);
                        pS_textArea.append("\n");
                    }
                    pS_textArea.setCaretPosition(0);
                }
            }
        }
    }
    class RandomSlang implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            pS_textArea.setText("");
            Slang word = dict.randomSlang();
            displaySlang(word);
        }
    }
    class AddSlang implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String word = pA_word_textField.getText();
            String meaning = pA_meaning_textField.getText();
            Integer index = dict.findSlangByWord(word.toUpperCase());
            if (Objects.equals(word, "")) {
                JOptionPane.showMessageDialog(frame, "You haven't entered a word yet.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
            else if (Objects.equals(meaning, "")) {
                JOptionPane.showMessageDialog(frame, "You haven't entered a definition yet.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
            else if (index > -1) {
                Object[] options = {"Overwrite", "Duplicate", "Cancel"};
                int choice = JOptionPane.showOptionDialog(frame, "This word already exists.",
                        "Warning", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE,
                        null, options, "Cancel");
                if (choice == 0) {
                    dict.deleteSlang(word.toUpperCase());
                    dict.addSlang(new Slang(word.toUpperCase(), meaning));
                    JOptionPane.showMessageDialog(frame, "Slang added to dictionary.",
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                    try {
                        dict.saveToFile();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                else if (choice == 1) {
                    String[] split = meaning.split("\\|");
                    dict.getSlangAt(index).addDefinition(split);
                    JOptionPane.showMessageDialog(frame, "Slang added to dictionary.",
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                    try {
                        dict.saveToFile();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
            else {
                dict.addSlang(new Slang(word, meaning));
                JOptionPane.showMessageDialog(frame, "Slang added to dictionary.",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                try {
                    dict.saveToFile();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }
    class EditSlang implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String wordEdit = pM_wordEdit_textField.getText();
            String word = pM_word_textField.getText();
            String meaning = pM_meaning_textField.getText();
            Integer index = dict.findSlangByWord(wordEdit.toUpperCase());
            if (Objects.equals(wordEdit, "")) {
                JOptionPane.showMessageDialog(frame, "You haven't entered a word yet.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
            else if (index == -1) {
                JOptionPane.showMessageDialog(frame, "This word doesn't exist.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
            else if (Objects.equals(word, "") && Objects.equals(meaning, "")) {
                JOptionPane.showMessageDialog(frame, "You haven't entered something to edit.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
            else if (dict.findSlangByWord(word.toUpperCase()) > -1) {
                JOptionPane.showMessageDialog(frame, "The edited word already exists.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
            else {
                Slang s = dict.getSlangAt(index);
                if (!Objects.equals(word, "")) {
                    s.setWord(word.toUpperCase());
                    dict.updateSlangIndex(wordEdit.toUpperCase(), word.toUpperCase());
                }
                if (!Objects.equals(meaning, "")) {
                    String[] split = meaning.split("\\|");
                    s.setDefinition(split);
                }
                JOptionPane.showMessageDialog(frame, "Slang edited.",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                try {
                    dict.saveToFile();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }
    class DeleteSlang implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String word = pM_wordEdit_textField.getText();
            Integer index = dict.findSlangByWord(word.toUpperCase());
            if (Objects.equals(word, "")) {
                JOptionPane.showMessageDialog(frame, "You haven't entered a word yet.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
            else if (index == -1) {
                JOptionPane.showMessageDialog(frame, "This word doesn't exist.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
            else {
                String[] options = {"Yes", "No"};
                int choice = JOptionPane.showOptionDialog(frame, "Are you sure you want to delete?",
                        "Warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE,
                        null, options, "No");
                if (choice == 0) {
                    dict.deleteSlang(word.toUpperCase());
                    JOptionPane.showMessageDialog(frame, "Slang deleted.",
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                    try {
                        dict.saveToFile();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        }
    }
    class ResetSlangList implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            dict.resetOriginal();
            JOptionPane.showMessageDialog(frame, "Resetted to original slang list.",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            try {
                dict.saveToFile();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    public String randomElement(ArrayList<String> list) {
        return list.get((int)(Math.random() * list.size()));
    }
    public void startQuiz() {
        pQ_startSlang.setEnabled(false);
        pQ_startMeaning.setEnabled(false);
        pQ_end.setEnabled(true);
        pQ_answer1.setEnabled(true);
        pQ_answer2.setEnabled(true);
        pQ_answer3.setEnabled(true);
        pQ_answer4.setEnabled(true);
        tabbedPane.setEnabled(false);
    }
    public void endQuiz() {
        pQ_question.setText("Congratulations! You scored " + pQ_correct + "/10");

        pQ_answer1.setText("");
        pQ_answer2.setText("");
        pQ_answer3.setText("");
        pQ_answer4.setText("");

        pQ_correct = 0;
        pQ_count = 0;

        pQ_answer1.setEnabled(false);
        pQ_answer2.setEnabled(false);
        pQ_answer3.setEnabled(false);
        pQ_answer4.setEnabled(false);

        pQ_startSlang.setEnabled(true);
        pQ_startMeaning.setEnabled(true);
        pQ_end.setEnabled(false);
        tabbedPane.setEnabled(true);
    }
    public void generateSlangQuiz() {
        ArrayList<Slang> data = dict.randomNSlang(4);
        quizCorrect = data.get(0);
        Collections.shuffle(data);

        pQ_question.setText("Q" + (pQ_count + 1) + ". " + quizCorrect.getWord());
        pQ_answer1.setText(randomElement(data.get(0).getDefinition()));
        pQ_answer2.setText(randomElement(data.get(1).getDefinition()));
        pQ_answer3.setText(randomElement(data.get(2).getDefinition()));
        pQ_answer4.setText(randomElement(data.get(3).getDefinition()));
    }
    public void generateMeaningQuiz() {
        ArrayList<Slang> data = dict.randomNSlang(4);
        quizCorrect = data.get(0);
        Collections.shuffle(data);

        pQ_question.setText("Q" + (pQ_count + 1) + ". " + randomElement(quizCorrect.getDefinition()));
        pQ_answer1.setText(data.get(0).getWord());
        pQ_answer2.setText(data.get(1).getWord());
        pQ_answer3.setText(data.get(2).getWord());
        pQ_answer4.setText(data.get(3).getWord());
    }
    class SlangQuiz implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            pQ_type = 0;
            startQuiz();
            generateSlangQuiz();
        }
    }
    class MeaningQuiz implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            pQ_type = 1;
            startQuiz();
            generateMeaningQuiz();
        }
    }
    class AnswerQuiz implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String choice = e.getActionCommand();

            if (pQ_type == 0) {
                if (quizCorrect.getDefinition().contains(choice)) {
                    pQ_correct++;
                    JOptionPane.showMessageDialog(frame, "The answer is correct.",
                            "Correct", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    String correct = "";
                    for (String d : quizCorrect.getDefinition()) {
                        if (Objects.equals(d, pQ_answer1.getText())) correct = pQ_answer1.getText();
                        if (Objects.equals(d, pQ_answer2.getText())) correct = pQ_answer2.getText();
                        if (Objects.equals(d, pQ_answer3.getText())) correct = pQ_answer3.getText();
                        if (Objects.equals(d, pQ_answer4.getText())) correct = pQ_answer4.getText();
                    }
                    JOptionPane.showMessageDialog(frame, "The correct answer was: " + correct,
                            "Incorrect", JOptionPane.ERROR_MESSAGE);
                }
            }
            else {
                if (Objects.equals(quizCorrect.getWord(), choice)) {
                    pQ_correct++;
                    JOptionPane.showMessageDialog(frame, "The answer is correct.",
                            "Correct", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame, "The correct answer was: " + quizCorrect.getWord(),
                            "Incorrect", JOptionPane.ERROR_MESSAGE);
                }
            }

            pQ_count++;
            if (pQ_count == 10) {
                endQuiz();
            }
            else {
                if (pQ_type == 0) generateSlangQuiz();
                else generateMeaningQuiz();
            }
        }
    }
    class EndQuizAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            endQuiz();
        }
    }
    public void createAndShowGUI() {
        JFrame.setDefaultLookAndFeelDecorated(true);

        JFrame frame = new JFrame("Slang Dictionary");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JComponent content = new Frame();
        content.setOpaque(true);
        frame.setContentPane(content);

        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}