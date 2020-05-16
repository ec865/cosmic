import java.util.ArrayList;

public class Evaluation {
    public static void main(String[] args) {
        Parser parser = new Parser("src/input/employee_payroll_system.txt");
        System.out.println(parser.getInteractions());
        ArrayList<String> unique_methods = new ArrayList<>();
        for (Interaction interaction : parser.getInteractions()){
            if (interaction.getMethodName().equals("??"))
                unique_methods.add(interaction.getMethodName());
            else if (!unique_methods.contains(interaction.getMethodName()) && !interaction.getMethodName().equals("main")){
                unique_methods.add(interaction.getMethodName());
            }
        }
        for (String methodName : unique_methods)
            System.out.println(methodName);
        System.out.println("Total CFP is "+ unique_methods.size());
    }
}
