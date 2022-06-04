package com.keylina.core;

import java.io.File;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Collections;

public class AppParams {


    public static String getValueFromAppParams(String param) {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("application.properties");
        System.console().printf("inputStream");



        return param;
    }

}
