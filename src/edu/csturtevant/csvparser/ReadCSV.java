import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class ReadCSV {

    // http://stackoverflow.com/questions/19575308/read-a-file-separated-by-tab-and-put-the-words-in-an-arraylist
    private ArrayList<ArrayList<String>> clients = new ArrayList<ArrayList<String>>();
    private ArrayList<String> mI = null;

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
    }

    /* Right now this is getting the data from the CSV file
     * and putting it into the collection "clients"
     */
    public void run() {

        String csvFile = "/Users/chrissturtevant0/Dropbox/csvparser/Combined_Data_Unclean.csv";
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
    }

    /* This is going to check for a duplicate. If found,
     * the user will be promted to delete and the method
     * will end. This only finds one duplicate at a time
     * because altering the collection mid analysis causes
     * problems.
     */
    public void deDupe() {
        int row;
        ArrayList<String> compareRow;
        Scanner scan = new Scanner(System.in);
        boolean removed;
        int j;


        // Finds one duplicate at a time, so it repeats until removed != true
        do
        {
            row = 1;
            compareRow = null;
            removed = false;
            j = -1;

            // Iterates through comparing each client with each other client
            do
            {
                j++;
                ArrayList<String> mI = clients.get(j);
                // Starts with the row after the client being compared and continues throught the end of the list
                do
                {
          /* This if prevents the method from trying to compare
           * the last client with anything, which is impossible
           * because there is nothing left to compare.
           */
                    if((clients.indexOf(mI) + row) >= clients.size() )
                        break;
                    compareRow = clients.get((clients.indexOf(mI)) + row);
                    if(mI.get(0).equals(compareRow.get(0))) { //This is comparing the ID numbers
                        if(mI.get(4).equals(compareRow.get(4))) { //If those are equal, it checks magazine type
                            //If both are equal, it is suggested there is a duplicate
                            System.out.println( "Client 1: " );
                            for(int i = 0; i < mI.size(); i++) //Output first client's info
                                System.out.print( mI.get(i) + "\t\t" );
                            System.out.println( "\nClient 2: " );
                            for(int i = 0; i < mI.size(); i++)
                                System.out.print( compareRow.get(i) + "\t\t" ); //Output second client's info
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
        } while( removed == true );
        outputClients(); //Output altered collection for review
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
        ArrayList<String> compareRow = null;
        Scanner scan = new Scanner(System.in);
        double percentSame = 0.0;
        int j = -1;

        do
        {
            j++;
            ArrayList<String> mI = clients.get(j);
            do
            {
        /* This if prevents the method from trying to compare
         * the last client with anything, which is impossible
         * because there is nothing left to compare.
         */
                if((clients.indexOf(mI) + row) >= clients.size() )
                    break;
                compareRow = clients.get((clients.indexOf(mI)) + row);
                percentSame = similarity(mI.get(1), compareRow.get(1));
                if (!(mI.get(0).equals( compareRow.get(0) )) || !(mI.get(1).equals( compareRow.get(1) )) || !(mI.get(2).equals( compareRow.get(2)))) {
                    if (percentSame >= 0.50 && percentSame <= 1.00) {
                        percentSame = similarity(mI.get(2), compareRow.get(2));
                        if (percentSame >= 0.50 && percentSame <= 1.00) {
                            System.out.println( "Client 1: " );
                            for(int i = 0; i < mI.size(); i++) //Output first client's info
                                System.out.print( mI.get(i) + "\t\t" );
                            System.out.println( "\nClient 2: " );
                            for(int i = 0; i < mI.size(); i++)
                                System.out.print( compareRow.get(i) + "\t\t" ); //Output second client's info
                            System.out.println("\n\nThese clients seem to have an error, please choose the correct one.\n" +
                                    "The other will be adjusted accordingly. (1, or 2, or 0 if not an error)");
                            int choice = scan.nextInt();
                            if (choice == 1) {
                                compareRow.set(1, mI.get(1));
                                compareRow.set(0, mI.get(0));
                                compareRow.set(2, mI.get(2));
                            } else if (choice == 2) {
                                mI.set(1, compareRow.get(1));
                                mI.set(0, compareRow.get(0));
                                mI.set(2, compareRow.get(2));
                            }

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
        for(ArrayList<String> mI : clients) {
            if (mI.get(3).equals("11-11-1111") || mI.get(3).equals("99-99-9999") || mI.get(3).equals("00/00/0000"))
                mI.set(3, "NULL");
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
}