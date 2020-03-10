import java.util.ArrayList;

public class Interaction {

    private String className;
    private ArrayList<String> classAttributes;
    private String methodName;
    private ArrayList<String> methodAttributes;
    private ArrayList<String> argNames;
    private ArrayList<String> argTypes;
    private String  returnType;
    private Integer hasCode;

    public Interaction(){
    }

    public Interaction(String className, ArrayList<String> classAttributes, String methodName, ArrayList<String> methodAttributes, ArrayList<String> argNames, ArrayList<String> argTypes, String returnType, Integer hasCode) {
        this.className = className;
        this.classAttributes = classAttributes;
        this.methodName = methodName;
        this.methodAttributes = methodAttributes;
        this.argNames = argNames;
        this.argTypes = argTypes;
        this.returnType = returnType;
        this.hasCode = hasCode;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public ArrayList<String> getClassAttributes() {
        return classAttributes;
    }

    public void setClassAttributes(ArrayList<String> classAttributes) {
        this.classAttributes = classAttributes;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public ArrayList<String> getMethodAttributes() {
        return methodAttributes;
    }

    public void setMethodAttributes(ArrayList<String> methodAttributes) {
        this.methodAttributes = methodAttributes;
    }

    public ArrayList<String> getArgNames() {
        return argNames;
    }

    public void setArgNames(ArrayList<String> argNames) {
        this.argNames = argNames;
    }

    public ArrayList<String> getArgTypes() {
        return argTypes;
    }

    public void setArgTypes(ArrayList<String> argTypes) {
        this.argTypes = argTypes;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public Integer getHasCode() {
        return hasCode;
    }

    public void setHasCode(Integer hasCode) {
        this.hasCode = hasCode;
    }

    @Override
    public String toString() {
        return "{" +
                "className='" + className + '\'' +
                ", classAttributes=" + classAttributes +
                ", methodName='" + methodName + '\'' +
                ", methodAttributes=" + methodAttributes +
                ", argNames=" + argNames +
                ", argTypes=" + argTypes +
                ", returnType='" + returnType + '\'' +
                ", hasCode=" + hasCode +
                '}';
    }
}
