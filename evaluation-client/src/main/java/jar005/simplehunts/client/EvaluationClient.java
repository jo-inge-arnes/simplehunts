package jar005.simplehunts.client;

import jar005.simplehunts.classification.Classifier;
import jar005.simplehunts.model.BooleanAttribute;
import jar005.simplehunts.model.Node;
import jar005.simplehunts.model.Record;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class EvaluationClient {
    public static void main(String[] args) {
        if (!validateArgs(args))
            return;

        Node root = readJsonTree(args[0]);

        if(root == null) {
            System.out.println("Couldn't deserialize tree from JSON-file");
            return;
        }

        List<Record> records = importRecords(args[1]);

        if(records.size() == 0) {
            System.out.println("No records in CSV-file");
            return;
        }

        Classifier classifier = new Classifier(root);
        double accuracy = classifier.accuracy(records);

        System.out.println(accuracy);
    }

    private static Node readJsonTree(String fileNamePath) {
        ObjectMapper mapper = new ObjectMapper();
        Node node;

        try {
            node = mapper.readValue(new File(fileNamePath), Node.class);
        } catch(Exception e) {
            node = null;
            e.printStackTrace();
        }

        return node;
    }

    private static List<Record> importRecords(String csvFilePath) {
        List<Record> records = new ArrayList<>();

        BufferedReader br = null;
        String line;
        String csvSplitBy = ",";

        try {
            br = new BufferedReader(new FileReader(csvFilePath));

            if((line = br.readLine()) == null)
                return records;

            String[] attributeNames = line.split(csvSplitBy);
            for(int i = 0; i < attributeNames.length; i++)
                attributeNames[i] = attributeNames[i].trim();

            while ((line = br.readLine()) != null) {
                if("".equals(line.trim()))
                    continue;

                records.add(recordFromStrings(attributeNames, line.split(csvSplitBy)));
            }
        } catch (Exception e) {
            records.clear();
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return records;
    }

    private static Record recordFromStrings(String[] attributeNames, String[] stringValues) {
        validateRecordsFromStringsParameters(attributeNames, stringValues);

        List<BooleanAttribute> attributes = new ArrayList<>();
        for(int i = 0; i < stringValues.length - 1; i++) {
            attributes.add(booleanAttributeFromString(attributeNames[i], stringValues[i]));
        }

        BooleanAttribute target = booleanAttributeFromString(
                attributeNames[attributeNames.length - 1],
                stringValues[stringValues.length - 1]);

        return new Record(attributes, target);
    }

    private static BooleanAttribute booleanAttributeFromString(String attributeName, String stringValue) {
        String valueString = stringValue.trim();

        if(!("0".equals(valueString) || "1".equals(valueString)))
            throw new IllegalArgumentException("Boolean values must be given on the format 0 or 1 in the CSV-file.");

        return new BooleanAttribute(attributeName, "1".equals(valueString));
    }

    private static void validateRecordsFromStringsParameters(String[] attributeNames, String[] stringValues) {
        if(stringValues == null || stringValues.length == 0 || attributeNames == null || attributeNames.length == 0)
            throw new IllegalArgumentException("Neither attributesNames nor stringValues can be empty or null");

        if(stringValues.length != attributeNames.length)
            throw new IllegalArgumentException("Mismatch between number of attribute names and values");
    }

    private static boolean validateArgs(String[] args) {
        boolean argsAreValid = true;

        if (args.length != 2) {
            argsAreValid = false;
            System.out.println("Usage: java -jar evaluate.jar tree.json records.csv");
        }

        return argsAreValid;
    }
}
