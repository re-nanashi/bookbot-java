package com.javadev;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class BookBot {
    public void run(String bookPath) throws FileNotFoundException {
        // Read the text content of the file
        String bookTextContent;
        try (BufferedReader reader = new BufferedReader(new FileReader(bookPath))) {
            bookTextContent = readTextContent(reader);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return;
        }
        // Get number of words
        int numberOfWords = getNumberOfWords(bookTextContent); // Right
        // Create a character count dictionary
        Map<String, Integer> charMap = createCharacterMap(bookTextContent);
        // Store the character count info into list then sort into descending order
        List<Map<String, String>> characterFrequencyListDescending = storeCharDictToSortedList(charMap);
        // Print the details of the book
        System.out.println(String.format("--- Begin report of %s ---", bookPath));
        System.out.println(String.format("%d words found in the document", numberOfWords));
        for (Map<String, String> charInfo : characterFrequencyListDescending ) {
            System.out.println(String.format("The %s character was found %s times", charInfo.get("char"), charInfo.get("count")));
        }
        System.out.println("--- END REPORT ---");
    }

    private List<Map<String, String>> storeCharDictToSortedList(Map<String,Integer> characterFrequencyMap) {
        List<Map<String, String>> sortedList = characterFrequencyMap.entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry::getValue, Comparator.reverseOrder()))
                .map(entry -> {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("char", entry.getKey());
                    map.put("count", String.valueOf(entry.getValue()));
                    return map;
                }).collect(Collectors.toList());
        return sortedList;
    }

    private int getNumberOfWords(String text) {
        List<String> words = Arrays.stream(text.split(" ")).toList();
        return words.size();
    }

    private Map<String, Integer> createCharacterMap(String text) {
        // Count the frequency of characters
        Map<String, Integer> characterMap = new HashMap<>();
        int count = 0;
        for (int i = 0; i < text.length(); i++) {
            char lowercase = Character.toLowerCase(text.charAt(i));
            if (!Character.isAlphabetic(lowercase))
                continue;;
            // characterMap.put(String.valueOf(lowercase), characterMap.getOrDefault(lowercase, 1) + 1);
            String key = String.valueOf(lowercase);
            characterMap.put(key, characterMap.getOrDefault(key, 1) + 1);
        }
        return characterMap;
    }

    private String readTextContent(BufferedReader reader) throws IOException {
        StringBuilder content = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            content.append(line);
            content.append(System.lineSeparator());
        }
        return content.toString();
    }
}