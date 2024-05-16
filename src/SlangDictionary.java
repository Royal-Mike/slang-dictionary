import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class SlangDictionary {
    private List<Slang> dataList;
    private Map<String, Integer> dataMap;
    private List<Slang> dataListOriginal;
    private Map<String, Integer> dataMapOriginal;
    public SlangDictionary() {
        dataList = new ArrayList<>();
        dataListOriginal = new ArrayList<>();
        dataMap = new HashMap<>();
        dataMapOriginal = new HashMap<>();
    }
    public SlangDictionary(SlangDictionary copyDict) {
        dataList = new ArrayList<>(copyDict.getList());
        dataMap = new HashMap<>();
        dataMap.putAll(copyDict.getMap());
        dataListOriginal = new ArrayList<>(copyDict.getListOriginal());
        dataMapOriginal = new HashMap<>();
        dataMapOriginal.putAll(copyDict.getMapOriginal());
    }
    public SlangDictionary(BufferedReader br) throws IOException {
        dataList = new ArrayList<>();
        dataListOriginal = new ArrayList<>();
        dataMap = new HashMap<>();
        dataMapOriginal = new HashMap<>();

        br.readLine();

        String line;
        while ((line = br.readLine()) != null) {
            String[] split = line.split("`");

            String word = split[0];

            String[] defs = split[1].split("\\|");
            ArrayList<String> defs_list = new ArrayList<>();
            for (String def : defs) {
                defs_list.add(def.trim());
            }

            Slang s = new Slang(word, defs_list);
            addSlang(s);
        }

        br.close();
    }
    public Map<String, Integer> getMap() {
        return dataMap;
    }
    public List<Slang> getList() {
        return dataList;
    }
    public Map<String, Integer> getMapOriginal() {
        return dataMapOriginal;
    }
    public List<Slang> getListOriginal() {
        return dataListOriginal;
    }
    public void addOriginalList(BufferedReader br) throws IOException {
        br.readLine();

        String line;
        while ((line = br.readLine()) != null) {

            String[] split = line.split("`");

            String word = split[0];

            String[] defs = split[1].split("\\|");
            ArrayList<String> defs_list = new ArrayList<>();
            for (String def : defs) {
                defs_list.add(def.trim());
            }

            Slang s = new Slang(word, defs_list);
            dataMapOriginal.put(s.getWord(), dataListOriginal.size());
            dataListOriginal.add(s);
        }

        br.close();
    }
    public void resetOriginal() {
        dataList = new ArrayList<>(dataListOriginal);
        dataMap.clear();
        dataMap.putAll(dataMapOriginal);
    }
    public Slang getSlangAt(Integer i) {
        return dataList.get(i);
    }
    public void addSlang(Slang s) {
        dataMap.put(s.getWord(), dataList.size());
        dataList.add(s);
    }
    public void deleteSlang(Object o) {
        Integer indexInt = dataMap.remove(o);
        if (indexInt == null) {
            return;
        }
        int index = indexInt;
        int last = dataList.size() - 1;
        Slang s = dataList.remove(last);
        if (index != last) {
            dataMap.put(s.getWord(), index);
            dataList.set(index, s);
        }
    }
    public void updateSlangIndex(Object o, String newKey) {
        Integer i = dataMap.remove(o);
        dataMap.put(newKey, i);
    }
    public Integer findSlangByWord(String word) {
        if (dataMap.containsKey(word)) {
            return dataMap.get(word);
        }
        return -1;
    }
    public ArrayList<Slang> findSlangByMeaning(String meaning) {
        ArrayList<Slang> list = new ArrayList<>();
        for (Slang s : dataList) {
            for (String m : s.getDefinition()) {
                if (m.toUpperCase().contains(meaning.toUpperCase())) {
                    list.add(s);
                    break;
                }
            }
        }
        return list;
    }
    public Slang randomSlang() {
        return dataList.get((int)(Math.random() * dataList.size()));
    }
    public ArrayList<Slang> randomNSlang(int n) {
        ArrayList<Slang> copyList = new ArrayList<>(dataList);
        Collections.shuffle(copyList);
        ArrayList<Slang> result = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            result.add(copyList.get(i));
        }
        return result;
    }
    public void saveToFile() throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter("./slang.txt"));
        bw.write("Slang`Meaning");
        bw.newLine();
        for (Slang s : dataList) {
            bw.write(s.getWord() + "`");
            String meaning = String.join("|", s.getDefinition());
            bw.write(meaning);
            bw.newLine();
        }
        bw.close();
    }
    public void printAll() {
        for (Slang s : dataList) {
            s.print();
        }
    }
}