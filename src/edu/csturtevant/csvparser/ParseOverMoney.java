package edu.csturtevant.csvparser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Vector;

/**
 * Created by chrissturtevant0 on 2/4/15.
 * This is a class that I will be using to parse over money strings that contain commas that
 * get plugged into a CSV.
 */
public class ParseOverMoney {
    public static void main(String[] args) {
        try {
            Reader reader = new FileReader("/Users/chrissturtevant0/Google Drive/Programming/Projects/GitHub/College/Machine Learning/CSVParser/Data/ParseOverMoney/DataForParseOverMoney.csv");
            for(int i = 1; i != 16; i++)
            {
                System.out.println(parseLine(reader));
            }

        } catch (IOException i) {
            i.printStackTrace();
        } //catch (FileNotFoundException f) //Not valid!
        catch (Exception e) {
            e.printStackTrace();
        }
        {
          //  f.printStackTrace();
        }
    }
    /**
     * returns a row of values as a list
     * returns null if you are past the end of the input stream
     */
    public static List<String> parseLine(Reader r) throws Exception {
        int ch = r.read();
        while (ch == '\r') {
            //ignore linefeed chars wherever, particularly just before end of file
            ch = r.read();
        }
        if (ch<0) {
            return null;
        }
        Vector<String> store = new Vector<String>();
        StringBuffer curVal = new StringBuffer();
        boolean inquotes = false;
        boolean started = false;
        while (ch>=0) {
            if (inquotes) {
                started=true;
                if (ch == '\"') {
                    inquotes = false;
                }
                else {
                    curVal.append((char)ch);
                }
            }
            else {
                if (ch == '\"') {
                    inquotes = true;
                    if (started) {
                        // if this is the second quote in a value, add a quote
                        // this is for the double quote in the middle of a value
                        curVal.append('\"');
                    }
                }
                else if (ch == ',') {
                    store.add(curVal.toString());
                    curVal = new StringBuffer();
                    started = false;
                }
                else if (ch == '\r') {
                    //ignore LF characters
                }
                else if (ch == '\n') {
                    //end of a line, break out
                    break;
                }
                else {
                    curVal.append((char)ch);
                }
            }
            ch = r.read();
        }
        store.add(curVal.toString());
        return store;
    }
}
