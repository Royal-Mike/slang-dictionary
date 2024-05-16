import javax.swing.*;
import java.io.*;

public class Main extends JPanel {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("./slang.txt"));
        BufferedReader bro = new BufferedReader(new FileReader("./slang_original.txt"));
        SlangDictionary dict = new SlangDictionary(br);

        dict.addOriginalList(bro);

        SwingUtilities.invokeLater(() -> {
            Frame sf = new Frame();
            sf.copyDict(dict);
            sf.createAndShowGUI();
        });
    }
}