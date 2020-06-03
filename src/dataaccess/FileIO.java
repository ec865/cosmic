package dataaccess;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileIO {

    private Scanner scanner;

    public FileIO(String path) {
        try {
            this.openFile(path);
        } catch (FileNotFoundException e) {
            System.err.println("Please enter the correct path");
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void openFile(String path) throws FileNotFoundException {
        File file = new File(path);
        scanner = new Scanner(file);
    }

    public String readFile(){
        scanner.useDelimiter(":|,|\n");
        String fileString = "";
        while (scanner.hasNext()){
            fileString = fileString.concat(scanner.next().replaceAll("[\\s+\"{}\\[\\]]","")+"\n"); //Gereksiz karakterlerden arındırıp alt alta string olarak kaydediyor
        }
        scanner.close();
        return fileString;
    }

}
