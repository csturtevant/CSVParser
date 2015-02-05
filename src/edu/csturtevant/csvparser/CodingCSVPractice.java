package edu.csturtevant.csvparser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by chrissturtevant0 on 2/4/15.
 * This is for practicing using and modifying array values.
 */

public class CodingCSVPractice {
    private ArrayList<ArrayList<String>> clients = new ArrayList<ArrayList<String>>();
    private ArrayList<String> mI = null;
    public static void main (String[] args) {
        CodingCSVPractice obj = new CodingCSVPractice();
        obj.run();

    }

    public void outputClients() {

        for(int i = 0; i < clients.size(); i++) {
            mI = clients.get(i);
            for(int j = 0; j < mI.size(); j++) {
                if( i != 0 && j == 0 )
                    System.out.print( mI.get(j) + "\t\t" );
                else if( i == 0 && j == 2 )
                    System.out.print( mI.get(j) + "\t\t\t" );
                else if( j == 2 && mI.get(j).length() >= 21 )
                    System.out.print( mI.get(j) + "\t" );
                else if( j == 2 && mI.get(j).length() <= 21 && mI.get(j).length() > 10 )
                    System.out.print( mI.get(j) + "\t\t" );
                else if( j == 2 && mI.get(j).length() <= 11 )
                    System.out.print( mI.get(j) + "\t\t\t" );
                else if( i != 0 && j == 3 )
                    System.out.print( mI.get(j) + "\t\t" );
                else if( j == 1 && mI.get(j).length() >= 13 )
                    System.out.print( mI.get(j) + "\t\t" );
                else if( j == 1 )
                    System.out.print( mI.get(j) + "\t\t\t" );
                else
                    System.out.print( mI.get(j) + '\t' );
            }
            System.out.println( '\n' );
        }
        System.out.println("Done");
    }

    public void coding() { //This function is used for cleaning the code and setting simpler human readable values
        //mI = clients.get(1); //Get row 1
        //System.out.println(mI.get(2)); //Get Column 2
        //region = mI.get(2).charAt(0);
        //mI.set(2, Character.toString(region));
        //System.out.println(mI.get(2));
        char region;
        for(int i = 0; i < clients.size(); i++) { //Modify the whole csv
            mI = clients.get(i);
            region = mI.get(2).charAt(0);
            mI.set(2, Character.toString(region));
            for(int j = 0; j < mI.size(); j++) { //Output the entire row

                System.out.print( mI.get(j) + "\t\t" );
            }
            System.out.println();
        }

    }

    /* Right now this is getting the data from the CSV file
         * and putting it into the collection "clients"
         */
    public void run() {

        String csvFile = "/Users/chrissturtevant0/Google Drive/Programming/Projects/GitHub/College/" +
                         "Machine Learning/CSVParser/Combined_Data_Unclean.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        String[] wordsArray;

        try {

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {

                // use comma as separator

                if ( !(line.equals(",,,,")) )
                {
                    wordsArray = line.split(cvsSplitBy);
                    mI = new ArrayList<String>();
                    for(String each : wordsArray) {
                        mI.add(each);
                    }
                    clients.add(mI);
                }

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
        coding();
    }


}
