package com.javadev;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        BookBot bot = new BookBot();
        try {
            bot.run("src/main/resources/test.txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}