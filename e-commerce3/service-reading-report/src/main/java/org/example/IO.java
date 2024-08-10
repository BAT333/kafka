package org.example;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class IO {
    public static void copyTo(Path source, File target) throws IOException {
        target.getParentFile().mkdirs();
        Files.copy(source,target.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }

    public static void append(File target, String s, String uuid){
        try(var fileW = new FileWriter(target,true);
        var file = new BufferedWriter(fileW);) {

            file.write(s+ " "+ uuid );
            file.newLine();
            file.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
