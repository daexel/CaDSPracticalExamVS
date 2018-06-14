/**
 * 
 */
package cads.parser.examples;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
/**
 * @author daexel
 *
 */
public class StubIDL {
/**
 * @param args
 * @throws IOException 
 * @throws FileNotFoundException 
 * @throws ParseException 
 */
public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {
	 System.out.println("#############################");
        System.out.println("Start - IDL_Parser_StubServices.");
        System.out.println();
        List<String> jsonFiles = new ArrayList<>();
        jsonFiles.add("IDLs/Stub/EstopStub_idl.json");
        jsonFiles.add("IDLs/Stub/GrabberStub_idl.json");
        jsonFiles.add("IDLs/Stub/HorizontalStub_idl.json");
        jsonFiles.add("IDLs/Stub/VerticalStub_idl.json");
        for (String fileName : jsonFiles) {
        // Read IDL Example
        System.out.println("::: read IDL from file: " + fileName + " :::");
        String jsonText = readEntirefile(fileName);
        System.out.println(jsonText);

        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(jsonText);
        String objectName = (String) json.get("ServiceName");
        String extentionName = (String) json.get("Extention");
        JSONArray imports = (JSONArray) json.get("Imports");
        JSONArray methodes = (JSONArray) json.get("Functions");
        

        // Fill Description Arrays
        List<String> importPathes = new ArrayList<>();
        List<String> annotations = new ArrayList<>();
        List<String> methodeNames = new ArrayList<>();
        Map<String, Map<Integer, Map<String, String>>> methodeParameterMap = new HashMap<>();
        Map<String, String> methodeReturnMap = new HashMap<>();
        Map<String, String> methodeAnnotationMap = new HashMap<>();
    
        for(Object obj : imports) {
        	JSONObject jsonObj = (JSONObject) obj;
            String importPath = (String) jsonObj.get("Path");
            importPathes.add(importPath);
        }
       
        
        for (Object obj : methodes) {
            JSONObject jsonObj = (JSONObject) obj;
            String annotation = (String) jsonObj.get("annotation");
            annotations.add(annotation);
            String methodeName = (String) jsonObj.get("name");
            methodeNames.add(methodeName);
            JSONArray parameterArray = (JSONArray) jsonObj.get("parameters");
            Map<Integer, Map<String, String>> parameterPositionMap = new HashMap<>();

            for (Object parameterObj : parameterArray) {
                JSONObject jsonParameterObj = (JSONObject) parameterObj;
                HashMap<String, String> parameterDescriptionMap = new HashMap<>();

                System.out.println(jsonParameterObj);
                System.out.println(jsonParameterObj.get("position"));
                Integer positionInteger = Integer.parseInt(jsonParameterObj.get("position").toString());
                parameterDescriptionMap.put("type", (String) jsonParameterObj.get("type"));
                parameterDescriptionMap.put("name", (String) jsonParameterObj.get("name"));

                parameterPositionMap.put(positionInteger, parameterDescriptionMap);
            }
            methodeParameterMap.put(methodeName, parameterPositionMap);
            methodeReturnMap.put(methodeName, (String) jsonObj.get("returnType"));
            methodeAnnotationMap.put(methodeName,annotation);
        }

        // Read Plain Text Class
        System.out.println();
        fileName = "idl_examples/plain_texts/plain_text_class.txt";
        System.out.println("::: read plaintext class from file: " + fileName + " :::");

        String plainTextClass = readEntirefile(fileName);
        System.out.println(plainTextClass);

        // Read Plain Text Methode
        System.out.println();
        fileName = "idl_examples/plain_texts/stubServices_plain_text_methode.txt";
        System.out.println("::: read plaintext methode from file: " + fileName + " :::");

        String plainTextMethode = readEntirefile(fileName);
        System.out.println(plainTextMethode);

        // Create Methode Strings and Class String
        StringBuffer parametersBuffer;
        StringBuffer methodesBuffer = new StringBuffer();
        StringBuffer importBuffer = new StringBuffer();
        
        int importCounter=0;
        for (String importPath : importPathes) {
        	importCounter++;
        	if(importCounter>=2) {
        		importPath="\nimport "+importPath;
        	}
        	importBuffer.append(importPath+";");
        }
        int methodeCounter=0;
        //for (String methodeName : methodeNames) {
        	String methodeName = methodeNames.get(methodeCounter);
        	String annotation = methodeAnnotationMap.get(methodeName);
            parametersBuffer = new StringBuffer();
            Map<Integer, Map<String, String>> parameterPositionMap = methodeParameterMap.get(methodeName);
            int i = 1;
            Map<String, String> parameter = parameterPositionMap.get(new Integer(i++));

            while (parameter != null) {
                if (i > 2) {
                    parametersBuffer.append(", ");
                }

                parametersBuffer.append(parameter.get("type"));
                parametersBuffer.append(" ");
                parametersBuffer.append(parameter.get("name"));

                parameter = parameterPositionMap.get(new Integer(i++));
            }
            String returnTypeA = methodeReturnMap.get(methodeName);
            String returnStatement = "";
            
            // Here every supported return type has to be listed
            switch (returnTypeA) {
            case "int":
                returnStatement = "return 0;";
                break;
            case "long":
                returnStatement = "return 0L;";
                break;
            case "String":
                returnStatement = "return null;";
                break;
            }
           
        methodesBuffer.append(String.format(plainTextMethode,annotation, returnTypeA, methodeName, parametersBuffer.toString(), returnStatement));
        // Generate Methode and Class Strings
        System.out.println("::: Generate the class files :::");
        String pathName = "cads.parser.generated";

        
        System.out.println("Now generating : " + objectName + ".java");
        String classString = String.format(plainTextClass, pathName,importBuffer, objectName, " extends "+extentionName,"\n", methodesBuffer.toString());

        writeToFile(objectName, classString);

        System.out.println();
        System.out.println("End - IDL_Parser_Stub_IDL.");
        System.out.println("###########################");
    }
 }

    private static void writeToFile(String objectName, String classString) throws IOException {
        String fileName;
        fileName = "src/cads/parser/generated/"+objectName + ".java";
        PrintWriter writer = new PrintWriter(new FileWriter(new File(fileName)));

        writer.print(classString);
        writer.flush();
        writer.close();
    }

    private static String readEntirefile(String fileName) throws FileNotFoundException, IOException {
        BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)));

        String line = "";
        StringBuffer buffer = new StringBuffer();
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
            buffer.append("\n");
        }

        reader.close();
        String jsonText = buffer.toString();
        return jsonText;
    }
}
