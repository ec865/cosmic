import java.util.ArrayList;

public class Evaluation {
    public static void main(String[] args) {
        Parser parser = new Parser("src/input/output3.txt");
        ArrayList<String> unique_methods = new ArrayList<>();
        for (ArrayList<String> methods : parser.getMethodNames()){
            if (!unique_methods.contains(methods.get(0))){
                if (!methods.get(0).equals("main"))
                    unique_methods.add(methods.get(0));
            }
        }
        System.out.println(unique_methods);
        System.out.println("Total CFP is "+ unique_methods.size());
    }
}
