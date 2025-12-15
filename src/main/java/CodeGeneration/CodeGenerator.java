package CodeGeneration;

import Models.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import Models.CD.Method; // Assuming Method class exists based on your previous dump

public class CodeGenerator {

    public CodeGenerator() {
    }

    public void generateCode(Model model) {
        if (model instanceof ClassModel) {
            generateClassCode((ClassModel) model);
        } else if (model instanceof InterfaceModel) {
            generateInterfaceCode((InterfaceModel) model);
        } else {
            System.out.println("Unsupported model type for code generation.");
        }
    }

    private void generateClassCode(ClassModel classModel) {
        // Prepare the class header
        StringBuilder code = new StringBuilder();
        code.append(classModel.isAbstract() ? "public abstract class " : "public class ");
        code.append(classModel.getClassName()).append(" {\n");

        // Add attributes
        List<String> attributes = classModel.getAttributes();
        for (String attribute : attributes) {
            code.append("\tprivate ").append(attribute).append(";\n");
        }

        // Add methods
        List<Method> methods = classModel.getMethods();
        for (Method method : methods) {
            code.append(methodToJava(method));
        }

        code.append("}\n");

        // Write the code to a file
        try (FileWriter fileWriter = new FileWriter(classModel.getClassName() + ".java")) {
            fileWriter.write(code.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void generateInterfaceCode(InterfaceModel interfaceModel) {
        // Prepare the interface header
        StringBuilder code = new StringBuilder();
        code.append("public interface ").append(interfaceModel.getInterfaceName()).append(" {\n");

        // Add methods
        List<String> methods = interfaceModel.getMethods();
        for (String method : methods) {
            code.append("\t").append(method).append(";\n");
        }

        code.append("}\n");

        // Write the code to a file
        try (FileWriter fileWriter = new FileWriter(interfaceModel.getInterfaceName() + ".java")) {
            fileWriter.write(code.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String methodToJava(Method method) {
        StringBuilder methodCode = new StringBuilder();
        methodCode.append("\tpublic ").append(" ").append(method.getText()).append("() {\n");
        methodCode.append("\t\t// TODO: Implement ").append(method.getText()).append("\n");
        methodCode.append("\t}\n");
        return methodCode.toString();
    }
}
