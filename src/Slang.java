import java.util.ArrayList;
import java.util.Arrays;

public class Slang {
    private String word;
    private ArrayList<String> definition;
    public Slang(String i_word, ArrayList<String> i_def) {
        word = i_word;
        definition = i_def;
    }
    public Slang(String i_word, String i_def) {
        String[] split = i_def.split("\\|");
        ArrayList<String> defs = new ArrayList<>();
        for (String def : split) {
            defs.add(def.trim());
        }
        word = i_word;
        definition = defs;
    }
    public String getWord() {
        return word;
    }
    public ArrayList<String> getDefinition() {
        return definition;
    }
    public void setWord(String i_word) {
        word = i_word;
    }
    public void setDefinition(String[] defs) {
        definition.clear();
        definition.addAll(Arrays.asList(defs));
    }
    public void addDefinition(String[] defs) {
        definition.addAll(Arrays.asList(defs));
    }
    public void print() {
        System.out.println(word + "`" + String.join("|", definition));
    }
}