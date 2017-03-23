package our.task.JettyWebSocket;


import java.util.*;
import java.util.stream.Collectors;

class RowsFinder {

    static ArrayList<Integer> findFirstRows(String word, Map<String, String> matrix) {
        if (matrix.get(word) != null) {
            String bitSet = matrix.get(word).replace("{", "").replace("}", "");
            bitSet = bitSet.replaceAll("\\s","");
            List<String> nonzeroString = Arrays.asList(bitSet.split(","));
            List<Integer> nonzeroInteger = nonzeroString.stream().map(Integer::valueOf).collect(Collectors.toList());
            return new ArrayList<>(nonzeroInteger);
        } else
            throw new IllegalArgumentException("Unknown word");
    }

    static ArrayList<Integer> findNonZeroRows(String str, Map<String, String> matrix) {
        String[] words = str.split(" ");
        Map<String, ArrayList<Integer>> nonZero = new HashMap<>();
        for (String word : words) // get the nonzero rows for each word
        {
            ArrayList<Integer> nonZeroRows = findFirstRows(word, matrix);
            nonZero.put(word, nonZeroRows);
        }
        Map<Integer, Integer> rowsNumber = new HashMap<>(); // number of each non zero row
        for (ArrayList<Integer> nonZeroRowsBitSet : nonZero.values()) {
            for (Integer row : nonZeroRowsBitSet) {
                if (rowsNumber.containsKey(row))
                    rowsNumber.put(row, rowsNumber.get(row) + 1);
                else
                    rowsNumber.put(row, 1);
            }
        }
        ArrayList<Integer> resultRows = new ArrayList<>();
        for (Integer rowNumber : rowsNumber.keySet()) {
            if (rowsNumber.get(rowNumber) == words.length)  //try to find the full similarity
                resultRows.add(rowNumber);
        }
        return resultRows;
    }

}
