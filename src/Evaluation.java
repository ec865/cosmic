import java.util.ArrayList;
import java.util.Scanner;

public class Evaluation {
    public static void main(String[] args) {
        Parser parser = new Parser("src/input/mail_validator.txt");
        System.out.println(parser.getInteractions());
        ArrayList<String> unique_methods = new ArrayList<>();
        ArrayList<String> excludedMethods = excludedMethods();
        System.out.println(excludedMethods);
        for (Interaction interaction : parser.getInteractions()){
            String method = interaction.getMethodName();
            boolean isFound = true;
            for (String excludedMethod : excludedMethods){
                isFound = !method.toLowerCase().contains(excludedMethod.toLowerCase());
                if (isFound == false)
                    break;
            }
            if (isFound && !unique_methods.contains(method) && !method.equals("main"))
                unique_methods.add(method);

        }
        for (String methodName : unique_methods)
            System.out.println(methodName);

        System.out.println("Total CFP is "+ unique_methods.size());
    }

    public static ArrayList<String> excludedMethods(){
        ArrayList<String> excludedMethods = new ArrayList<>();
        System.out.println("Please write the methods to be excluded. This may increase the accuracy of the measurement.\nYou can write \"done\" to skip or end the entry.");
        String inputString = "";
        while (!inputString.toLowerCase().equals("done")){
            Scanner input = new Scanner(System.in);
            inputString = input.nextLine();
            if (!inputString.toLowerCase().equals("done"))
                excludedMethods.add(inputString);
        }
        return excludedMethods;
    }
}
