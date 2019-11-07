package game;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class MapReader {
    private String csvFile;
    private DataInputStream myInput;
    private String line = "";

    private HashMap<Integer, ArrayList<Integer>> mapCollision = new HashMap<>();

    public MapReader(){
        csvFile =  "server/src/main/resources/worldMap_CollisionLayer.csv";
    }

    public void readMap() {

        try {
            FileInputStream fis = new FileInputStream(csvFile);
            myInput = new DataInputStream(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        int i = 0;
        String[][] data = new String[0][];
        while (true) {
            try {
                if ((line = myInput.readLine()) == null) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            ++i;

            String[][] newdata = new String[i][2];

            String[] strar = line.split(",");
            newdata[i - 1] = strar;

            System.arraycopy(data, 0, newdata, 0, i - 1);
            data = newdata;
//            data = rotateClockWise(data);
        }
        for (int j = 0; j < data.length; j++) {
            ArrayList<Integer> tempArray = new ArrayList<>();
            for (int k = 0; k < data[j].length; k++) {
                if (!data[j][k].equals("-1")) {
                    tempArray.add(k);
                    System.out.printf("x; %s y; %s\n", j, k);
                }
            }
            mapCollision.put(j, tempArray);
        }
    }
    private String[][] rotateClockWise(String[][] matrix) {
        int totalRowsOfRotatedMatrix = matrix[0].length; //Total columns of Original Matrix
        int totalColsOfRotatedMatrix = matrix.length; //Total rows of Original Matrix

        String[][] rotatedMatrix = new String[totalRowsOfRotatedMatrix][totalColsOfRotatedMatrix];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                rotatedMatrix[j][ (totalColsOfRotatedMatrix-1)- i] = matrix[i][j];
            }
        }
        return rotatedMatrix;
    }

    public HashMap<Integer, ArrayList<Integer>> getMapCollision() {
        return mapCollision;
    }
}
