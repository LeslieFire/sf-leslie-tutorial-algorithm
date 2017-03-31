package com.seefeel.leslie.algorithm.util;

import java.io.*;

/**
 * ${DESCRIPTION}
 * Author: Leslie
 * Date: 14/3/17
 * Time: PM9:21
 */
public class IOUtil {

    public static BufferedReader getReader(String filename) throws FileNotFoundException{
        return new BufferedReader(new InputStreamReader(new FileInputStream(new File(filename))));
    }

    public static String getResourcePath(String file){
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String filePath = classLoader.getResource(file).getFile();

        return filePath;
    }
}
