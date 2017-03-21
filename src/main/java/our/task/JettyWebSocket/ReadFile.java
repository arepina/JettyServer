package our.task.JettyWebSocket;


import java.io.*;
import java.util.BitSet;
import java.util.*;


class ReadFile {

    static Map<String, BitSet> formMatrix(String filePath, ArrayList<String> features) {
        Map<String, ArrayList<Byte>> matrix_prepare = new LinkedHashMap<>();
        BufferedReader br;
        String line;
        int count = 0;
        for (String feature : features) {
            if (matrix_prepare.size() == 100)
                break;
            matrix_prepare.put(feature, null);
            count += 1;
        }
        try {
            br = new BufferedReader(new FileReader(filePath));
            while ((line = br.readLine()) != null) {
                ArrayList<String> bits = new ArrayList<>(Arrays.asList(line.split(";")));
                bits.remove(0);
                count = 0;
                for (String key : matrix_prepare.keySet()) {
                    byte b = Byte.parseByte(bits.get(count));
                    if (matrix_prepare.get(key) != null) {
                        ArrayList<Byte> l = matrix_prepare.get(key);
                        l.add(b);
                        matrix_prepare.put(key, l);
                    } else {
                        ArrayList<Byte> l = new ArrayList<>();
                        l.add(b);
                        matrix_prepare.put(key, l);
                    }
                    count += 1;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<String, BitSet> matrix = new LinkedHashMap<>();
        for (Map.Entry<String, ArrayList<Byte>> entry : matrix_prepare.entrySet()) {
            Byte[] byte_arr = entry.getValue().toArray(new Byte[entry.getValue().size()]);
            BitSet bitSet = new BitSet();
            for (int i = 0; i < byte_arr.length; i++)
               if (byte_arr[i] == 1)
                   bitSet.set(i);
            matrix.put(entry.getKey(), bitSet);
        }
        return matrix;
    }

    static ArrayList<String> readHeaders(String csvFile) {
        BufferedReader br;
        String line;
        ArrayList<String> head = new ArrayList<>();
        try {
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                head.add(line.substring(line.indexOf(",") + 1, line.length()).replace("\"", ""));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return head;
    }
}
