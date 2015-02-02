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
import java.util.*;

public class ReadCSV {

    private ArrayList<String[]> clients = new ArrayList<String[]>();
    private String[] mI = null;

    public static void main(String[] args) {

        ReadCSV obj = new ReadCSV();
        obj.run();
        System.out.println( "Starting first cleanUp\n------------------------------------------------------");
        obj.cleanUp();
        System.out.println( "First cleanUp finished" );
        System.out.println( "Starting first deDupe\n------------------------------------------------------");
        obj.deDupe();
        System.out.println( "First deDupe finished" );
        System.out.println( "Starting second deDupe\n------------------------------------------------------");
        obj.deDupe();
        System.out.println( "Second deDupe finished" );
        System.out.println( "Starting second cleanUp\n------------------------------------------------------");
        obj.cleanUp();
        System.out.println( "Second cleanUp finished");
        //System.out.println( "String Similarity: " + obj.similarity( "Banzinar", "Robin" ) );

    }

    /* Right now this is getting the data from the CSV file
     * and putting it into the collection "clients"
     */
    public void run() {

        String csvFile = "/Users/chrissturtevant0/Dropbox/Parser0.0.2/DirtyData1.csv ";
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

    /* This is going to check for a duplicate. If found,
     * the user will be promted to delete and the method
     * will end. This only finds one duplicate at a time
     * because altering the collection mid analysis causes
     * problems.
     */
    public void deDupe() {

        int row = 1;
        String[] compareRow = null;
        Scanner scan = new Scanner(System.in);
        boolean removed = false;
        int j = -1;

        do
        {
            j++;
            String[] mI = clients.get(j);
            do
            {
        /* This if prevents the method from trying to compare
         * the last client with anything, which is impossible
         * because there is nothing left to compare.
         */
                if((clients.indexOf(mI) + row) >= clients.size() )
                    break;
                compareRow = clients.get((clients.indexOf(mI)) + row);
                if(mI[0].equals(compareRow[0])) { //This is comparing the ID numbers
                    if(mI[4].equals(compareRow[4])) { //If those are equal, it checks magazine type
                        //If both are equal, it is suggested there is a duplicate
                        System.out.println( "Client 1: " );
                        for(int i = 0; i < mI.length; i++) //Output first client's info
                            System.out.print( mI[i] + "\t\t" );
                        System.out.println( "\nClient 2: " );
                        for(int i = 0; i < mI.length; i++)
                            System.out.print( compareRow[i] + "\t\t" ); //Output second client's info
                        //The user is asked to review the suggested duplicates and act acordingly
                        System.out.println("\n\nThese clients seem to be duplicates." +
                                "\nPlease enter number to be deleted, 1 or 2 (0 if not duplicate)" );
                        int choice = scan.nextInt();
                        //Removes the chosen client
                        if( choice == 1 ) {
                            clients.remove(clients.indexOf(mI));
                            removed = true;
                        }
                        else if( choice == 2 ) {
                            clients.remove(clients.indexOf(compareRow));
                            removed = true;
                        }
                    }
                }
                row++;
            } while((clients.indexOf(mI) + row) < clients.size() && removed != true);
            row = 1;

        } while( removed != true && j < clients.size() - 1);
        outputClients(); //Output altered collection for review
        if (removed == true)
            System.out.println("\n*********************************************************************************" +
                    "\n*Removes one duplicate at a time. Please run again to assure no more duplicates.*" +
                    "\n*********************************************************************************");
    }

    /* This will be run after deDupe to clean up names, dates, addresses, and
     * client number. It calls the other methods 'nameClean' and 'dateClean'.
     */
    public void cleanUp() {
        nameClean();
        dateClean();
        outputClients();
    }

    public void nameClean() {

        int row = 1;
        String[] compareRow = null;
        Scanner scan = new Scanner(System.in);
        double percentSame = 0.0;
        int j = -1;

        do
        {
            j++;
            String[] mI = clients.get(j);
            do
            {
        /* This if prevents the method from trying to compare
         * the last client with anything, which is impossible
         * because there is nothing left to compare.
         */
                if((clients.indexOf(mI) + row) >= clients.size() )
                    break;
                compareRow = clients.get((clients.indexOf(mI)) + row);
                percentSame = similarity(mI[1], compareRow[1]);
                if (percentSame >= 0.75 && percentSame < 1.00) {
                    if (mI[2].equals(compareRow[2])) {
                        System.out.println( "Client 1: " );
                        for(int i = 0; i < mI.length; i++) //Output first client's info
                            System.out.print( mI[i] + "\t\t" );
                        System.out.println( "\nClient 2: " );
                        for(int i = 0; i < mI.length; i++)
                            System.out.print( compareRow[i] + "\t\t" ); //Output second client's info
                        System.out.println("\n\nThese clients seem to have an error, please choose the correct one.\n" +
                                "The other will be adjusted accordingly. (1, or 2, or 0 if not an error)");
                        int choice = scan.nextInt();
                        if (choice == 1) {
                            compareRow[1] = mI[1];
                            compareRow[0] = mI[0];
                        } else if (choice == 2) {
                            mI[1] = compareRow[1];
                            mI[0] = compareRow[0];
                        }


                    }
                }
                row++;
            } while((clients.indexOf(mI) + row) < clients.size());
            row = 1;
        } while( j < clients.size() - 1);
    }


    /* This method is called by method 'cleanUp' and checks if the date
     * values of clients are acceptable. Unacceptable formats will be
     * changed to null.
     */
    public void dateClean() {
        for(String[] mI : clients) {
            if (mI[3].equals("11-11-1111") || mI[3].equals("99-99-9999"))
                mI[3] = "Null";
        }
    }

    /* This will calculate the similarity in percentage of two
     * strings.
     */
    public double similarity(String s1, String s2) {
        String longer = s1;
        String shorter = s2;
        if (s1.length() < s2.length()) {
            longer = s2;
            shorter = s1;
        }
        int longerLength = longer.length();
        if (longerLength == 0) { return 1.0; /*both string length zero*/ }
        return (longerLength - editDistance(longer, shorter)) / (double) longerLength;
    }

    /* Implementation of the Levenshtein Edit Distance
     * use in calculating the similarity between strings
     */
    public int editDistance(String s1, String s2) {
        s1 = s1.toLowerCase();
        s2 = s2.toLowerCase();

        int[] costs = new int[s2.length() + 1];
        for (int i = 0; i <= s1.length(); i++) {
            int lastValue = i;
            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0)
                    costs[j] = j;
                else {
                    if (j > 0) {
                        int newValue = costs[j - 1];
                        if (s1.charAt(i - 1) != s2.charAt(j - 1))
                            newValue = Math.min(Math.min(newValue, lastValue),
                                    costs[j]) + 1;
                        costs[j - 1] = lastValue;
                        lastValue = newValue;
                    }
                }
            }
            if (i > 0)
                costs[s2.length()] = lastValue;
        }
        return costs[s2.length()];
    }

    /* This method outputs the data from "clients"
     * in a readable table-like format.
     */
    public void outputClients() {

        for(int i = 0; i < clients.size(); i++) {
            mI = clients.get(i);
            for(int j = 0; j < mI.length; j++) {
                if( i != 0 && j == 0 )
                    System.out.print( mI[j] + "\t\t" );
                else if( i == 0 && j == 2 )
                    System.out.print( mI[j] + "\t\t\t" );
                else if( j == 2 && mI[j].length() <= 21 && mI[j].length() > 10 )
                    System.out.print( mI[j] + "\t\t" );
                else if( j == 2 && mI[j].length() <= 11 )
                    System.out.print( mI[j] + "\t\t\t" );
                else if( i != 0 && j == 3 )
                    System.out.print( mI[j] + "\t\t" );
                else
                    System.out.print( mI[j] + '\t' );
            }
            System.out.println( '\n' );
        }
        System.out.println("Done");
    }
}