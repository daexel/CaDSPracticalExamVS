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
public class RobotfactoryIDL {

/**
 * @param args
 * @throws IOException 
 * @throws FileNotFoundException 
 * @throws ParseException 
 */
public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {
	 System.out.println("#############################");
        System.out.println("Start - IDL_Parser_SkeletonServices.");
        System.out.println();

        // Read IDL Example
        String fileName = "idl_examples/idls/RobotFactory_idl.json";
        System.out.println("::: read IDL from file: " + fileName + " :::");
        String jsonText = readEntirefile(fileName);
        System.out.println(jsonText);

        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(jsonText);
        String objectName = (String) json.get("ServiceName");
        String extentionName = (String) json.get("Extention");	 
        JSONArray imports = (JSONArray) json.get("Imports");
        JSONArray variables = (JSONArray) json.get("Variables");
        JSONArray methodes = (JSONArray) json.get("Functions");
        
        

        // Fill Description Arrays
        List<String> importPathes = new ArrayList<>();
        List<String> annotations = new ArrayList<>();
        List<String> methodeNames = new ArrayList<>();
        Map<String, Map<Integer, Map<String, String>>> methodeParameterMap = new HashMap<>();
        Map<Integer, Map<String, String>> VariableParameterPositionMap = new HashMap<>();
        Map<String, String> methodeReturnMap = new HashMap<>();
        Map<String, String> methodeAnnotationMap = new HashMap<>();
    
        
        for(Object obj : imports) {
        	JSONObject jsonObj = (JSONObject) obj;
            String importPath = (String) jsonObj.get("Path");
            importPathes.add(importPath);
        }
        


        for (Object parameterObj : variables) {
            JSONObject jsonParameterObj = (JSONObject) parameterObj;
            HashMap<String, String> parameterDescriptionMap = new HashMap<>();

            System.out.println(jsonParameterObj);
            System.out.println(jsonParameterObj.get("position"));
            Integer positionInteger = Integer.parseInt(jsonParameterObj.get("position").toString());
            parameterDescriptionMap.put("type", "private "+(String) jsonParameterObj.get("type"));
            parameterDescriptionMap.put("name", (String) jsonParameterObj.get("name"));

            VariableParameterPositionMap.put(positionInteger, parameterDescriptionMap);
        }
    
        
        //Fill the Methodes
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
        fileName = "idl_examples/plain_texts/robotfactory_plain_text_methode.txt";
        System.out.println("::: read plaintext methode from file: " + fileName + " :::");

        String plainTextMethode = readEntirefile(fileName);
        System.out.println(plainTextMethode);

        // Create Methode Strings and Class String
        StringBuffer parametersBufferA;
        StringBuffer parametersBufferB;
        StringBuffer methodesBuffer = new StringBuffer();
        StringBuffer importBuffer = new StringBuffer();
        StringBuffer variableBuffer = new StringBuffer();
        
        int k = 1;
        Map<String, String> parameter = VariableParameterPositionMap.get(new Integer(k++));
        while (parameter != null) {
            if (k > 2) {
            	variableBuffer.append("\n");
            }
            variableBuffer.append(parameter.get("type"));
            variableBuffer.append(" ");
            variableBuffer.append(parameter.get("name")+";");

            parameter = VariableParameterPositionMap.get(new Integer(k++));
        }
 
        
        int importCounter=0;
        for (String importPath : importPathes) {
        	importCounter++;
        	if(importCounter>=2) {
        		importPath="\nimport "+importPath;
        	}
        	importBuffer.append(importPath+";");
        }
        int methodeCounter=0;

    	String methodeNameA = methodeNames.get(methodeCounter);
    	String annotationA = methodeAnnotationMap.get(methodeNameA);
        parametersBufferA = new StringBuffer();
        Map<Integer, Map<String, String>> parameterPositionMap = methodeParameterMap.get(methodeNameA);
        int i = 1;
        Map<String, String> parameterA = parameterPositionMap.get(new Integer(i++));

        while (parameterA != null) {
            if (i > 2) {
                parametersBufferA.append(", ");
            }

            parametersBufferA.append(parameterA.get("type"));
            parametersBufferA.append(" ");
            parametersBufferA.append(parameterA.get("name"));

            parameterA = parameterPositionMap.get(new Integer(i++));
        }
        String returnTypeA = methodeReturnMap.get(methodeNameA);
        String returnStatementA = "";
        
        // Here every supported return type has to be listed
        switch (returnTypeA) {
        case "int":
            returnStatementA = "return 0;";
            break;
        case "long":
            returnStatementA = "return 0L;";
            break;
        case "String":
            returnStatementA = "return null;";
            break;
        }
       methodeCounter++;
       String methodeNameB = methodeNames.get(methodeCounter);
       String annotationB = methodeAnnotationMap.get(methodeNameB);
        parametersBufferB = new StringBuffer();
        Map<Integer, Map<String, String>> parameterPositionMapB = methodeParameterMap.get(methodeNameB);
        int b = 1;
        Map<String, String> parameterB = parameterPositionMapB.get(new Integer(b++));

        while (parameterB != null) {
            if (b > 2) {
                parametersBufferB.append(", ");
            }

            parametersBufferB.append(parameterB.get("type"));
            parametersBufferB.append(" ");
            parametersBufferB.append(parameterB.get("name"));
            
            parameterB = parameterPositionMapB.get(new Integer(i++));
        }
        String returnTypeB = methodeReturnMap.get(methodeNameB);
        String returnStatementB = "";
        
        // Here every supported return type has to be listed
        switch (returnTypeB) {
        case "int":
            returnStatementB = "return 0;";
            break;
        case "long":
            returnStatementB = "return 0L;";
            break;
        case "String":
            returnStatementB = "return null;";
            break;
        }
        //}
        methodesBuffer.append(String.format(plainTextMethode,annotationA, returnTypeA, methodeNameA, parametersBufferA.toString(), returnStatementA,
        		annotationB,returnTypeB, methodeNameB, parametersBufferB.toString(),returnStatementB));
        // Generate Methode and Class Strings
        System.out.println("::: Generate the class files :::");
        String pathName = "cads.parser.generated";

        
        System.out.println("Now generating : " + objectName + ".java");
        String classString = String.format(plainTextClass, pathName,importBuffer, objectName, extentionName,variableBuffer, methodesBuffer.toString());

        writeToFile(objectName, classString);

        System.out.println();
        System.out.println("End - IDL_Parser_SkeletonIDL.");
        System.out.println("###########################");
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
