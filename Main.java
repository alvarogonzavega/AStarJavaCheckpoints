package astar;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Main {

    private static ArrayList<ArrayList<Character>> tablero;
    private static ArrayList<Casilla> keys;
    private static ArrayList<ArrayList<Integer>> blocksArray;
    private static Character[][] tableroArray;

    public static void main(String args[]) {
        String fileName = args[0];
        String line;
        try {
            FileReader fileReader =
                    new FileReader(fileName);
            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);
            int iterator = 0;
            int cols = 0;
            Casilla initialCasilla = null, finalCasilla = null;
            tablero = new ArrayList<>();
            keys = new ArrayList<>();
            while ((line = bufferedReader.readLine()) != null) {
                cols = line.length();
                System.out.println(line);
                tablero.add(new ArrayList<>());
                for (int i = 0; i < line.length(); i++) {
                    char test = line.charAt(i);
                    tablero.get(iterator).add(test);
                    if (test == 'E') {
                        finalCasilla = new Casilla(iterator, i);
                    } else if (test == 'A') {
                        initialCasilla = new Casilla(iterator, i);
                    } else if (test == 'K') {
                        keys.add(new Casilla(iterator, i));
                    }
                }
                iterator++;
            }
            bufferedReader.close();
            int rows = iterator;
            AEstrella aEstrella = new AEstrella(rows, cols, initialCasilla, finalCasilla, keys);
            tableroArray = new Character[tablero.size()][];
            for (int i = 0; i < tablero.size(); i++) {
                ArrayList<Character> aTablero = tablero.get(i);
                tableroArray[i] = aTablero.toArray(new Character[aTablero.size()]);
            }


            for (int i = 0; i < tableroArray.length; i++) {
                for (int j = 0; j < tableroArray[i].length; j++) {
                    if (tableroArray[i][j] == '%') aEstrella.setParedes(i, j);
                }
            }
            aEstrella.empezarAlgoritmo();
            ArrayList<Casilla> caminoResultado = aEstrella.getCamino();
            int coste = 0;
            Casilla testCasilla = new Casilla(0, 0);
            for (int i = 0; i < tableroArray.length; i++) {
                System.out.println();
                for (int j = 0; j < tableroArray[i].length; j++) {
                    testCasilla.setRow(i);
                    testCasilla.setCol(j);
                    if (tableroArray[i][j] == 'A' || tableroArray[i][j] == 'E' || tableroArray[i][j] == 'K') {
                        System.out.print(tableroArray[i][j]);
                    } else if (caminoResultado.contains(testCasilla)) {
                        System.out.print('*');
                        coste += 1;
                    } else {
                        System.out.print(tableroArray[i][j]);
                    }
                }
            }
            System.out.println("\n\nEl coste total es de: " + coste);


        } catch (FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" +
                            fileName + "'");
        } catch (IOException ex) {
            System.out.println(
                    "Error reading file '"
                            + fileName + "'");

        }


    }
}