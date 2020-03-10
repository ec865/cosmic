import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.io.File;

public class Parser {

    private ArrayList<ArrayList<String>> classNames;
    private ArrayList<ArrayList<String>> classAttributes;
    private ArrayList<ArrayList<String>> methodNames;
    private ArrayList<ArrayList<String>> methodAttributes;
    private ArrayList<ArrayList<String>> argNames;
    private ArrayList<ArrayList<String>> argTypes;
    private ArrayList<ArrayList<String>> returnType;
    private ArrayList<Integer> hasCodes;
    private String file;

    public static void main(String[] args){
        Parser parser = new Parser("src/input/output.txt"); //Buraya sequence diagram plugin'in çıktısının konumunu veriyoruz
        System.out.println(parser);
    }

    public Parser(String path){
        try {
            file = openFile(path);
        } catch (FileNotFoundException e) {
            System.err.println("Please enter the correct path");
            e.printStackTrace();
            System.exit(1);
        }
        classNames = getObjects(file, "_className", "_attributes");
        classAttributes = getObjects(file, "_attributes", "_methodName");
        methodNames = getObjects(file, "_methodName", "_attributes");
        methodAttributes = getObjects(file, "_attributes", "_argNames");
        argNames = getObjects(file, "_argNames", "_argTypes");
        argTypes = getObjects(file, "_argTypes", "_returnType");
        returnType = getObjects(file, "_returnType", "_hashCode");
        hasCodes = getHashCodes(file);
    }

    //Setter ve getterlar, özel bir şey yok

    public ArrayList<ArrayList<String>> getClassNames() {
        return classNames;
    }

    public ArrayList<ArrayList<String>> getClassAttributes() {
        return classAttributes;
    }

    public ArrayList<ArrayList<String>> getMethodNames() {
        return methodNames;
    }

    public ArrayList<ArrayList<String>> getMethodAttributes() {
        return methodAttributes;
    }

    public ArrayList<ArrayList<String>> getArgNames() {
        return argNames;
    }

    public ArrayList<ArrayList<String>> getArgTypes() {
        return argTypes;
    }

    public ArrayList<ArrayList<String>> getReturnType() {
        return returnType;
    }

    public ArrayList<Integer> getHasCodes() {
        return hasCodes;
    }

    public String getFile() {
        return file;
    }

    public void setClassNames(ArrayList<ArrayList<String>> classNames) {
        this.classNames = classNames;
    }

    public void setClassAttributes(ArrayList<ArrayList<String>> classAttributes) {
        this.classAttributes = classAttributes;
    }

    public void setMethodNames(ArrayList<ArrayList<String>> methodNames) {
        this.methodNames = methodNames;
    }

    public void setMethodAttributes(ArrayList<ArrayList<String>> methodAttributes) {
        this.methodAttributes = methodAttributes;
    }

    public void setArgNames(ArrayList<ArrayList<String>> argNames) {
        this.argNames = argNames;
    }

    public void setArgTypes(ArrayList<ArrayList<String>> argTypes) {
        this.argTypes = argTypes;
    }

    public void setReturnType(ArrayList<ArrayList<String>> returnType) {
        this.returnType = returnType;
    }

    public void setHasCodes(ArrayList<Integer> hasCodes) {
        this.hasCodes = hasCodes;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public static String openFile(String path) throws FileNotFoundException { //Dosyayı açıp okumak için
        File file = new File(path);
        Scanner scanner = new Scanner(file);
        scanner.useDelimiter(":|,|\n");
        String fileString = "";
        while (scanner.hasNext()){
            fileString = fileString.concat(scanner.next().replaceAll("[\\s+\"{}\\[\\]]","")+"\n"); //Gereksiz karakterlerden arındırıp alt alta string olarak kaydediyor
        }
        scanner.close();
        return fileString;
    }

    public static ArrayList<ArrayList<String>> getObjects(String file, String startObject, String finishObject ) { //String'in içindeki verileri burada çekiyor
        ArrayList<String> temp = new ArrayList<>();                                                                //start ve finish argümanları çekeceğimiz objenin yerini tam olarak göstermek için
        ArrayList<ArrayList<String>> objectList = new ArrayList<>();                                               //Yalnız "_attributes" isminde iki tane değişken olduğu için sorun çıkıyor
        String[] fileArray = file.split("\n");                                                              //Bu yüzden onlarla ilgili bir şey okunacağı zaman tersten okunuyor
        if (finishObject.equals("_methodName") || finishObject.equals("_argNames")){ //Bu if tersten okunacakların kontrolü için var
            for (int i = 0; i < fileArray.length;  i++) {                            //Çok güzel bir çözüm olmadı ama şu an için çalışıyor gibi
                if (fileArray[i].equals(finishObject)) {                             //İleride burayı düzeltebiliriz
                    int count = 0;      //Kaç defa geri gittiğimizi tutuyor ki en sonda i'yi tekrar olduğu yere geri götürebilelim
                    while (!fileArray[i-1].equals(startObject)){
                        temp.add(fileArray[i-1]);
                        i--;
                        count++;
                    }
                    Collections.reverse(temp); //Tersten okuduğumuz için arraylist'e ters eklendi veriler. Onları burada olması gereken sıraya döndürüyoruz
                    objectList.add(temp);
                    temp = new ArrayList<>();
                    i += count;
                }
            }
        }
        else{                                                         //Burası normal sırada baştan sona kadar ekliyor. Bu yüzden "count" değişkenine ve reverse yapmaya gerek yok
            for (int i = 0; i < fileArray.length;  i++) {
                if (fileArray[i].equals(startObject)) {
                    while (!fileArray[i+1].equals(finishObject)){
                        temp.add(fileArray[i+1]);
                        i++;
                    }
                    objectList.add(temp);
                    temp = new ArrayList<>();
                }
            }
        }
        return objectList; //Bu method'un iç içe iki arraylist döndürmesinin sebebi, okuduğumuz methodların birden fazla argümanı buna bağlı birden fazla argüman tipi olması durumunda bunları gruplamak
                           //Bu sayede bütün arraylistlerin size'ı eşit olabiliyor. Bu size ise seqeunce diagramdaki interactionların sayısı. O da algoritmayı geliştirene kadar bizim o program için CFP ölçümümüz :)
    }                      //Bu sebeple her zaman tekil olacak className, methodName gibi değerler de arraylist içinde olmuş oluyor.
                           //Sonuç olarak her bir arraylistin aynı index'indeki değer sequence diagramdaki aynı interaction'a ait
                           // Örneğin classNames.get(6).get(0) ile argNames.get(6) , 7. interaction'ın class adını ve ilgili methodun argümanlarının adını veriyor
    //Örnek className çıktısı
    // [[business.TextCorrectionApp], [business.EditingOperations], [business.TextSaver], [business.AutoCorrector], [business.Searcher], [business.Composite], [presentation.Menu], [dataaccess.FileOperations]]

    //Örnek argNames çıktısı
    // [[operation], [operation], [operation], [], [newValue, newFileName] [], [fileName], [newValue, newFileName]]
    //Boş olanlar o methodun argümanı olmadığı anlamına geliyor.

    public static ArrayList<Integer> getHashCodes(String file){ //Hash codelar ne kadar işimize yarar bilmiyorum ama integer olduğu için onları ayrı bir methodda alıyoruz
        ArrayList<Integer> hashCodeList = new ArrayList<>();
        String[] fileArray = file.split("\n");
        for (int i = 0; i < fileArray.length;  i++) {
            if (fileArray[i].equals("_hashCode")){
                hashCodeList.add(Integer.parseInt(fileArray[i+1]));
            }
        }
        return hashCodeList;     //Bunun farkı ise direk integer arraylisti olarak sonuç döndürmesi. Yani diğerleri gibi iç içe bir yapı yok
    }

    //Örnek hashcode çıktısı
    // [-1, -1, -1, -1, -1, -1, -1, -1]

    @Override
    public String toString() {
        return  "classNames = " + classNames + "\n" +
                "classAttributes = " + classAttributes + "\n" +
                "methodNames = " + methodNames + "\n" +
                "methodAttributes = " + methodAttributes + "\n" +
                "argNames = " + argNames + "\n" +
                "argTypes = " + argTypes + "\n" +
                "returnType = " + returnType + "\n" +
                "hasCodes = " + hasCodes;
    }
}


