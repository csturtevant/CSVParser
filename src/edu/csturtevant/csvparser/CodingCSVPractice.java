package edu.csturtevant.csvparser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by chrissturtevant0 on 2/4/15.
 * This is for practicing using and modifying array values.
 */
/*

public class CodingCSVPractice {
    private String[] mI = null; //Magazine Information
    public static void main (String[] args) {
    }


    public void run() {

        String csvFile = "/Users/chrissturtevant0/Google Drive/Programming/Projects/GitHub/College/Machine Learning/CSVParser/Data/DirtyData1.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        try {

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {


                // use comma as separator
                mI = line.split(cvsSplitBy);
                clients.add(mI);

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        outputClients();
    }
    }
}
*/