/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphproject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author minhnhat
 */
public class StrFileReader {

    BufferedReader br = null;
    FileReader fr = null;

    public StrFileReader(String fileName) throws FileNotFoundException {
        fr = new FileReader(fileName);
        br = new BufferedReader(fr);

        String sCurrentLine;

    }

    public String readLine() throws IOException {
        String line = br.readLine();
        return line;
    }

    public List<String> readAllLine() throws IOException {
        String sCurrentLine;
        List<String> allLine = new ArrayList<>();

        while ((sCurrentLine = br.readLine()) != null) {
            allLine.add(sCurrentLine);
        }
        return allLine;
    }
    
    public void close() throws IOException{
        br.close();
        fr.close();
    }
}
