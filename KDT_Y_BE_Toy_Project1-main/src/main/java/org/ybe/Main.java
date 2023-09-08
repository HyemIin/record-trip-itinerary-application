package org.ybe;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        MainConverter mainConverter = new MainConverter();
        mainConverter.run();
    }
}