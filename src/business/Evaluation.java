package business;

import dataaccess.ConsoleIO;

import java.util.ArrayList;

public class Evaluation {

    private ArrayList<String> excludedMethods;
    private ArrayList<String> unique_methods;
    private Parser parser;

    public Evaluation(String path) {
        parser = new Parser(path);
    }

    public ArrayList<String> getExcludedMethods(){
        ConsoleIO console = new ConsoleIO();
        console.writeOutput("Please write the methods to be excluded. This may increase the accuracy of the measurement.\nYou can write \"done\" or press enter to skip or end the entry.");
        excludedMethods = console.takeInput();
        return excludedMethods;
    }

    public ArrayList<String> getUniqueMethods(){
        unique_methods = new ArrayList<>();
        for (Interaction interaction : parser.getInteractions()){
            String method = interaction.getMethodName();
            if (this.isExcludedMethodFound(method) && !unique_methods.contains(method) && !method.equals("main") && !method.equals("new"))
                unique_methods.add(method);
        }
        return unique_methods;
    }

    public boolean isExcludedMethodFound(String method){
        for (String excludedMethod : excludedMethods){
            if (method.toLowerCase().contains(excludedMethod.toLowerCase()))
                return false;
        }
        return true;
    }

    public int result(){
        return unique_methods.size();
    }

    public void displayResults(){
        System.out.println("\nMethods that are counted are below.\n");
        int count = 1;
        for (String methodName : unique_methods){
            System.out.println(count + ") " + methodName);
            count++;
        }
        System.out.println("\nTotal CFP is "+ this.result());
    }

    public void start(){
        this.getExcludedMethods();
        this.getUniqueMethods();
        this.displayResults();
    }

}
