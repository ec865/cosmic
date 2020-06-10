package business;

import dataaccess.FileIO;

import java.util.ArrayList;
import java.util.Collections;

public class Parser {

    private ArrayList<Interaction> interactions;

    public Parser(String path){
        FileIO file = new FileIO(path);
        String contents = file.readFile();
        this.createInteractions(contents);
    }

    public ArrayList<Interaction> getInteractions() {
        return interactions;
    }

    public void setInteractions(ArrayList<Interaction> interactions) {
        this.interactions = interactions;
    }

    public static ArrayList<ArrayList<String>> getObjects(String file, String startObject, String finishObject ) { //Takes the data in the given string that contains file content
        ArrayList<String> temp = new ArrayList<>();                                                                //startObject and finishObject indicates the locations of the data
        ArrayList<ArrayList<String>> objectList = new ArrayList<>();                                               //Since there are two variables named "_attributes", this causes issue
        String[] fileArray = file.split("\n");                                                              //That's why their data are read in reverse order
        if (finishObject.equals("_methodName") || finishObject.equals("_argNames")){ //This if for the ones that need to be read in reverse order
            for (int i = 0; i < fileArray.length;  i++) {
                if (fileArray[i].equals(finishObject)) {
                    int count = 0;      //Counts the number of times that we go back, to be able to point the i where it was
                    while (!fileArray[i-1].equals(startObject)){
                        temp.add(fileArray[i-1]);
                        i--;
                        count++;
                    }
                    Collections.reverse(temp); //Since the data read in reverse order, we need to reverse the arraylist
                    objectList.add(temp);
                    temp = new ArrayList<>();
                    i += count;
                }
            }
        }
        else{                                                        //This for the data in normal order. That's why, no need for a count variable
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
        return objectList;
    }

    public static ArrayList<Integer> getHashCodes(String file){ //Hash codes taken in this method because it returns integer arraylist
        ArrayList<Integer> hashCodeList = new ArrayList<>();
        String[] fileArray = file.split("\n");
        for (int i = 0; i < fileArray.length;  i++) {
            if (fileArray[i].equals("_hashCode")){
                hashCodeList.add(Integer.parseInt(fileArray[i+1]));
            }
        }
        return hashCodeList;
    }

    public void createInteractions(String file){
        ArrayList<ArrayList<String>> classNames = getObjects(file, "_className", "_attributes");
        ArrayList<ArrayList<String>> classAttributes = getObjects(file, "_attributes", "_methodName");
        ArrayList<ArrayList<String>> methodNames = getObjects(file, "_methodName", "_attributes");
        ArrayList<ArrayList<String>> methodAttributes = getObjects(file, "_attributes", "_argNames");
        ArrayList<ArrayList<String>> argNames = getObjects(file, "_argNames", "_argTypes");
        ArrayList<ArrayList<String>> argTypes = getObjects(file, "_argTypes", "_returnType");
        ArrayList<ArrayList<String>> returnType = getObjects(file, "_returnType", "_hashCode");
        ArrayList<Integer> hasCodes = getHashCodes(file);
        interactions = new ArrayList<>();
        for (int i = 0; i < classNames.size(); i++){
            interactions.add(new Interaction(classNames.get(i).get(0),classAttributes.get(i),methodNames.get(i).get(0),methodAttributes.get(i),argNames.get(i),argTypes.get(i),returnType.get(i).get(0),hasCodes.get(i)));
        }
    }

    @Override
    public String toString() {
        return interactions.toString();
    }
}