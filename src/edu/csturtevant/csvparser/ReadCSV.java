/* Christopher Sturtevant 1.26.15 csturtevant@rollins.edu
 * Program ReadCSV
 * This is a program that has been created to output the contents of
 * a csv to a new file, reducing, and cleaning the csv of duplicates,
 * and unnecessary data. This program is menu driven, and executes
 * with a switch statement.
 *
 * User Responsibility:
 * The user needs to supply the path of the file. //Possible change to a window pane prompt?
 * The user also needs to drive the menu and choose the paths he or she would like to take.
*/

package edu.csturtevant.csvparser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ReadCSV {


    public static void main(String[] args) {

        ReadCSV obj = new ReadCSV();
        obj.run();

    }

    public void run() {

        String csvFile = "/Users/chrissturtevant0/Google Drive/College/SeniorYearSemester2/Machine Learning/Project 1/DirtyData1.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        try {

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] magazineInformation = line.split(cvsSplitBy);

                System.out.println("Client number: " + magazineInformation[0] + "\n" +
                                    "Name: " + magazineInformation[1] + "\n" +
                                    "Address: " + magazineInformation[2] + "\n" +
                                    "Date of Purchase: " + magazineInformation[3] + "\n" +
                                    "Magazine Type: " + magazineInformation[4] + "\n" );

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

        System.out.println("Done");
    }

}