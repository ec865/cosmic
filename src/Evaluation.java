import java.util.ArrayList;

public class Evaluation {
    public static void main(String[] args) {
        Parser parser = new Parser("src/input/addressbook.txt");
        System.out.println(parser.getInteractions());
        ArrayList<String> unique_methods = new ArrayList<>();
        for (Interaction interaction : parser.getInteractions()){
            if (!unique_methods.contains(interaction.getMethodName()) && !interaction.getMethodName().equals("main")){
                unique_methods.add(interaction.getMethodName());
            }
        }
        System.out.println(unique_methods);
        System.out.println("Total CFP is "+ unique_methods.size());
    }
}
