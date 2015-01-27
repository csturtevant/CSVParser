


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ReadCVS {


    public static void main(String[] args) {

        ReadCVS obj = new ReadCVS();
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