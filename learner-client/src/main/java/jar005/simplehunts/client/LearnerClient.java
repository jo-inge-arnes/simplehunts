package jar005.simplehunts.client;

import jar005.simplehunts.algorithm.Learner;
import jar005.simplehunts.model.BooleanAttribute;
import jar005.simplehunts.model.Node;
import jar005.simplehunts.model.Record;
import jar005.simplehunts.model.RecordList;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LearnerClient {
    public static void main(String args[]) {
        if(!validateArgs(args))
            return;

        RecordList records = importRecords(args[0]);

        if(records.size() == 0) {
            System.out.println("No records in CSV-file");
            return;
        }

        Learner learner = new Learner();
        Node root = learner.decisionTree(
                records,
                new ArrayList<>(records.get(0).getIndependentAttributeNames()));

        ObjectMapper objectMapper = new ObjectMapper();
        String outfilePath;
        if(args.length == 2)
            outfilePath = args[1];
        else
            outfilePath = "output.json";

        try {
            objectMapper.writeValue(new File(outfilePath), root);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private static RecordList importRecords(String csvFilePath) {
        RecordList records = new RecordList();

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
            e.printStackTrace();
            records.clear();
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
            throw new IllegalArgumentException(String.format("Mismatch between number of attribute names and values"));
    }

    private static boolean validateArgs(String[] args) {
        boolean argsAreValid = true;

        if(args.length < 1 || args.length > 2) {
            argsAreValid = false;
            System.out.println("Usage: java -jar learn.jar records.csv [output.json]");
        }

        return argsAreValid;
    }
}
