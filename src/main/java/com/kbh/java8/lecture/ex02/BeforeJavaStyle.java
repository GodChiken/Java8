package com.kbh.java8.lecture.ex02;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * Created by ohjic on 2016-02-23.
 */
public class BeforeJavaStyle {
    public static void main(String[] args) throws IOException{

        //Java8 Before Style
        try (final FileReader fileReader = new FileReader(new File("C:\\Users\\ohjic\\IdeaProjects\\KBHTest\\src\\main\\java\\com\\kbh\\java8\\lecture\\ex02\\webina.txt"));
             final BufferedReader bufferedReader = new BufferedReader(fileReader);){

            final List<String> uniqueWords = new ArrayList<>();
            String line = bufferedReader.readLine();
            while (line != null){
                final String[] words = line.split("[\\s]+");
                for (String word : words){
                    if(!uniqueWords.contains(word)){
                        uniqueWords.add(word);
                    }
                }
                line = bufferedReader.readLine();
            }
            Collections.sort(uniqueWords);
            System.out.println(uniqueWords);
        } catch(Exception e){
            e.printStackTrace();
        }

        //Java8 Style
        try (final Stream<String> lines = Files.lines(Paths.get("C:\\Users\\ohjic\\IdeaProjects\\KBHTest\\src\\main\\java\\com\\kbh\\java8\\lecture\\ex02\\webina.txt"))){
            System.out.println(
                    lines.map(line -> line.split("[\\s]+"))
                            .flatMap(Arrays::stream)
                            .distinct()
                            .sorted()
                            .collect(toList())
            );
        }
    }
}
