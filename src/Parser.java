import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.io.File;

public class Parser {

    private ArrayList<Interaction> interactions;
    private String file;

    public Parser(String path){
        try {
            file = openFile(path);
        } catch (FileNotFoundException e) {
            System.err.println("Please enter the correct path");
            e.printStackTrace();
            System.exit(1);
        }
        interactions = createInteractions(file);
    }

    //Setter ve getterlar, özel bir şey yok

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public ArrayList<Interaction> getInteractions() {
        return interactions;
    }

    public void setInteractions(ArrayList<Interaction> interactions) {
        this.interactions = interactions;
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
    public static ArrayList<Interaction> createInteractions(String file){
        ArrayList<ArrayList<String>> classNames = getObjects(file, "_className", "_attributes");
        ArrayList<ArrayList<String>> classAttributes = getObjects(file, "_attributes", "_methodName");
        ArrayList<ArrayList<String>> methodNames = getObjects(file, "_methodName", "_attributes");
        ArrayList<ArrayList<String>> methodAttributes = getObjects(file, "_attributes", "_argNames");
        ArrayList<ArrayList<String>> argNames = getObjects(file, "_argNames", "_argTypes");
        ArrayList<ArrayList<String>> argTypes = getObjects(file, "_argTypes", "_returnType");
        ArrayList<ArrayList<String>> returnType = getObjects(file, "_returnType", "_hashCode");
        ArrayList<Integer> hasCodes = getHashCodes(file);
        ArrayList<Interaction> interactions = new ArrayList<>();
        for (int i = 0; i < classNames.size(); i++){
            interactions.add(new Interaction(classNames.get(i).get(0),classAttributes.get(i),methodNames.get(i).get(0),methodAttributes.get(i),argNames.get(i),argTypes.get(i),returnType.get(i).get(0),hasCodes.get(i)));
        }
        return interactions;
    }

    @Override
    public String toString() {
        return interactions.toString();
    }
}


