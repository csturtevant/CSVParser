package edu.csturtevant.csvparser;

/**
 * Created by chrissturtevant0 on 2/3/15.
 */
// Joining Class

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class JoinCSV
{

    private ArrayList<String[]> clients = new ArrayList<String[]>();
    private ArrayList<String[]> clientInfo = new ArrayList<String[]>();
    private ArrayList<ArrayList<String>> joinInfo = new ArrayList<ArrayList<String>>();
    private String[] mI = null;

    public static void main(String[] args)
    {

        JoinCSV obj = new JoinCSV();
        obj.joining();
    }

    public void joining()
    {
        String csvClient = "DirtyData1.csv";
        BufferedReader br = null;
        String order = "";
        String cvsSplitBy = ",";

        try {

            br = new BufferedReader(new FileReader(csvClient));
            while ((order = br.readLine()) != null) {


                // use comma as separator
                mI = order.split(cvsSplitBy);
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

        clients.trimToSize();

        String csvJoin = "DirtyData1Join.csv";
        br = null;
        String info = "";
        mI = null;

        try {

            br = new BufferedReader(new FileReader(csvJoin));
            while ((info = br.readLine()) != null) {


                // use comma as separator
                mI = info.split(cvsSplitBy);
                clientInfo.add(mI);

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

        clientInfo.trimToSize();

        int i = 0;
        ArrayList<String> m1 = null;
        ArrayList<String> m2 = null;

        while( i < clients.size() )
        {
            m1 = new ArrayList<String>();
            m2 = new ArrayList<String>();
            for ( int j = 0; j < clients.get(i).length; j++ )
                m1.add( clients.get( i )[j] );

            for ( int j = 1; j < clientInfo.get(i).length; j++ )
                m2.add( clientInfo.get( i )[j] );

            m1.addAll( m2 );


            for( int j = 0; j < m1.size(); j++ )
            {
                System.out.print( m1.get(j) );
                if ( m1.get(j).length() <= 10 )
                    System.out.print( "\t\t" );
                else
                    System.out.print( "\t" );
                if ( j == 2 && m1.get(j).length() <= 20 )
                    System.out.print( "\t" );
            }
            System.out.print( "\n" );

            joinInfo.add( m1 );


            i++;


        }

    /*for (i = 0; i < joinInfo.size(); i++)
    {
      mI = joinInfo.get(i);
      for(int j = 0; j < mI.length; j++) {
        if( j == 0 )
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
        if ( j == mI.length - 1 )
          System.out.print( "\n" );
      }
    }*/
    }
}

