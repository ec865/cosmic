import java.util.ArrayList;

public class Evaluation {
    public static void main(String[] args) {
        Parser parser = new Parser("src/input/output3.txt");
        ArrayList<String> b = new ArrayList<>();
        for (ArrayList<String> a : parser.getMethodNames()){
            if (!b.contains(a.get(0))){
                if (!a.get(0).equals("main"))
                    b.add(a.get(0));
            }
        }
        System.out.println(b);
        System.out.println("Total CFP is "+ b.size());
    }
}
