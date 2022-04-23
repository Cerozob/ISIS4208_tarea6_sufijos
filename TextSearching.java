import java.io.BufferedReader;
import java.io.FileReader;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;

public class TextSearching {

    private class Index {
        public String text;
        public Map.Entry<Integer, String>[] explicitSuffixArray;
        public Integer[] suffixArray; // lexicographical sorted

        private class LexicoGraphicalComparator implements Comparator<Integer> {

            public String text;

            public LexicoGraphicalComparator(String pText) {
                text = pText;
            }

            @Override
            public int compare(Integer o1, Integer o2) {
                return text.substring(o1).compareToIgnoreCase(text.substring(o2));
            }
        }

        public Index(String pText, char pEnd) {
            text = pText;
            buildSuffixArray();
            // buildExplicitSuffixArray();
        }

        public void buildSuffixArray() {
            // long start = System.currentTimeMillis();
            suffixArray = new Integer[text.length()];
            Arrays.setAll(suffixArray, (index) -> {
                return index;
            });
            // ordenar lexicográficamente
            LexicoGraphicalComparator compare = new LexicoGraphicalComparator(text);
            Arrays.sort(suffixArray, compare);
            // long end = System.currentTimeMillis();
            // System.out.println("Suffix array built in " + (end - start) + " ms");
        }

        // public void buildBWT() {
        // // Viendo el enunciado, no hay q hacer esto lololololooooooo
        // }

        private void buildExplicitSuffixArray() {
            explicitSuffixArray = (Map.Entry<Integer, String>[]) new Map.Entry[suffixArray.length];
            for (int i = 0; i < explicitSuffixArray.length; i++) {
                explicitSuffixArray[i] = new AbstractMap.SimpleEntry<Integer, String>(suffixArray[i],
                        text.substring(suffixArray[i]));
            }
        }

        public Integer[] searchOccurencesExplicit(String query) {
            int start = 0;
            int end = explicitSuffixArray.length;
            int querySize = query.length();
            ArrayList<Integer> positions = new ArrayList<>();
            int found = -1;
            int middle = 0;
            while (start < end) {
                middle = (end + start) / 2;
                Map.Entry<Integer, String> middleSuffix = explicitSuffixArray[middle];
                // Duda: case insensitive?, comparación lexicográfica = No
                int comparisonResult = query.compareToIgnoreCase(middleSuffix.getValue());
                if (querySize <= middleSuffix.getValue().length()
                        && middleSuffix.getValue().substring(0, querySize).compareToIgnoreCase(query) == 0) {
                    found = middleSuffix.getKey();
                    // igual debo seguir iterando
                }
                if (comparisonResult > 0) {
                    start = middle + 1;
                } else {
                    end = middle;
                }
                // nunca pasa porque los sufijos acaban en el caracter especial
                // else if (comparisonResult == 0) {
                // positions.add(middle)
                // }
            }
            if (found >= 0) {

                boolean done = false;

                // de aqui para abajo todos los sufijos que contienen en query están seguidos
                while (found < suffixArray.length && !done) {
                    Map.Entry<Integer, String> middleSuffix = explicitSuffixArray[middle];
                    if (middleSuffix.getValue().substring(0, querySize).compareToIgnoreCase(query) == 0) {
                        positions.add(middleSuffix.getKey());
                    } else {
                        done = true;
                    }
                    found++;
                }
            }
            return positions.toArray(new Integer[positions.size()]);
        }

        public void printSuffixArray() {
            for (Integer i : suffixArray) {
                System.out.println("Suffix at position " + i + ":" + text.substring(i));
            }
        }

        public Integer[] searchOccurences(String query) {
            int start = 0;
            int end = suffixArray.length;
            int querySize = query.length();
            ArrayList<Integer> positions = new ArrayList<>();
            int found = -1;
            int middle = 0;
            while (start < end) {
                middle = (end + start) / 2;
                String middleSuffix = text.substring(suffixArray[middle]);
                // Duda: case insensitive?, comparación lexicográfica = No
                if (querySize <= middleSuffix.length()
                        && middleSuffix.substring(0, querySize).compareToIgnoreCase(query) == 0) {
                    found = middle;
                    // igual debo seguir iterando
                }
                if (query.compareToIgnoreCase(middleSuffix) > 0) {
                    start = middle + 1;
                } else {
                    end = middle;
                }
                // nunca pasa porque los sufijos acaban en el caracter especial
                // else if (comparisonResult == 0) {
                // positions.add(middle)
                // }
            }
            if (found >= 0) {

                boolean done = false;

                // de aqui para abajo todos los sufijos que contienen en query están seguidos
                while (found < suffixArray.length && !done) {
                    String middleSuffix = text.substring(suffixArray[found]);
                    if (middleSuffix.substring(0, querySize).compareToIgnoreCase(query) == 0) {
                        positions.add(suffixArray[found]);
                    } else {
                        done = true;
                    }
                    found++;
                }
            }
            return positions.toArray(new Integer[positions.size()]);
        }

    }

    public static void main(String[] args) {
        String path1 = args[0];
        String path2 = args[1];

        TextSearching instance = new TextSearching();
        ArrayList<String> consultasList = new ArrayList<>();
        StringBuilder textBuilder = new StringBuilder();
        // char endCharacter = '\n';
        char endCharacter = '\n';
        try (BufferedReader reader1 = new BufferedReader(new FileReader(path1));
                BufferedReader reader2 = new BufferedReader(new FileReader(path2))) {
            String line;
            while ((line = reader1.readLine()) != null) {
                textBuilder.append(line);
            }

            textBuilder.append(endCharacter);
            while ((line = reader2.readLine()) != null) {
                consultasList.add(line);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        String[] consultas = consultasList.toArray(new String[consultasList.size()]);
        String texto = textBuilder.toString();
        Index indice = instance.new Index(texto, endCharacter);
        // System.out.println(texto);
        // indice.printSuffixArray();
        // Long timestartExplicit = System.currentTimeMillis();
        // Integer[][] resultadosExplicit = new Integer[consultas.length][];
        // for (int i = 0; i < consultas.length; i++) {
        // resultadosExplicit[i] = indice.searchOccurencesExplicit(consultas[i]);
        // }
        // Long timeendExplicit = System.currentTimeMillis();
        // System.out.println("Tiempo con sufijos explicitos: " + (timeendExplicit -
        // timestartExplicit) + " ms");
        boolean print = true;
        // Long timestart = System.currentTimeMillis();
        Integer[][] resultados = new Integer[consultas.length][];
        for (int i = 0; i < consultas.length; i++) {
            resultados[i] = indice.searchOccurences(consultas[i]);
        }
        // Long timeend = System.currentTimeMillis();
        // System.out.println("Tiempo con sufijos implicitos: " + (timeend - timestart)
        // + " ms");
        if (print) {
            for (int i = 0; i < consultas.length; i++) {
                String consulta = consultas[i];
                Integer[] positions = resultados[i];
                System.out.print(consulta);
                System.out.print('\t');
                int j = 0;
                for (; j < positions.length - 1; j++) {
                    System.out.print(positions[j]);
                    System.out.print('\t');
                }
                if (j < positions.length) {
                    System.out.print(positions[j]);
                }
                System.out.println();
            }
        }
    }
}