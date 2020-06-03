package dataaccess;

import java.util.ArrayList;
import java.util.Scanner;

public class ConsoleIO {

    private String inputString = "";

    public ConsoleIO() {
    }

    public ArrayList<String> takeInput(){
        Scanner input = new Scanner(System.in);
        ArrayList<String> inputs = new ArrayList<>();
        while (!inputString.toLowerCase().equals("done") ){
            inputString = input.nextLine();
            if (!inputString.toLowerCase().equals("done"))
                inputs.add(inputString);
        }
        input.close();
        return inputs;
    }

    public void writeOutput(String output){
        System.out.println(output);
    }
}
