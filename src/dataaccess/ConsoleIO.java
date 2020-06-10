package dataaccess;

import java.util.ArrayList;
import java.util.Scanner;

public class ConsoleIO {

    public ConsoleIO() {
    }

    public ArrayList<String> takeInput(){
        Scanner input = new Scanner(System.in);
        ArrayList<String> inputs = new ArrayList<>();
        String inputString = input.nextLine();
        while (!inputString.toLowerCase().equals("done") && !inputString.toLowerCase().equals("") ){ //continue to take input until user writes "done" or presses enter
            inputs.add(inputString);
            inputString = input.nextLine();
        }
        input.close();
        return inputs;
    }

    public void writeOutput(String output){
        System.out.println(output);
    }
}
