/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Properties;

/**
 *
 * @author Luc
 */
public class Import {

    private Properties p = null;

    public Properties Load(File file) {

        try {
            Reader reader = new InputStreamReader(new FileInputStream(file), "UTF-8");
            try (BufferedReader fin = new BufferedReader(reader)) {
                p = new Properties();
                p.load(fin);
            }
        } catch (IOException io) {
        }
        return p;
    }

    public String getData(String light_Name) {
        return p.getProperty(light_Name);
    }
}
