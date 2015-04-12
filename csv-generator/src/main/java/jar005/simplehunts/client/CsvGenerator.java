package jar005.simplehunts.client;

import jar005.simplehunts.me.canadian2003.CanadianConsensusCriteriaRecord;

import java.io.FileWriter;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CsvGenerator {
    private final SecureRandom rnd = new SecureRandom();

    public static void main(String[] args) {
        int numRecords = Integer.parseInt(args[0]);
        double criteriaProbability = Double.parseDouble(args[1]);
        String outFilePath = args[2];

        CsvGenerator generator = new CsvGenerator();
        generator.generate(numRecords, criteriaProbability, outFilePath);
    }

    private void generate(int totalNumRecords, double criteriaProbability, String outFilePath) {
        List<CanadianConsensusCriteriaRecord> records = new ArrayList<>();

        int numTrueRecords = (int) Math.round(totalNumRecords * criteriaProbability);
        int i = 0;

        while(i < numTrueRecords) {
            records.add(createTrueRecord());
            i++;
        }

        while(i < totalNumRecords) {
            records.add(createFalseRecord());
            i++;
        }

        writeRecordsToCsv(outFilePath, records);
    }

    private void writeRecordsToCsv(String fileNamePath, List<CanadianConsensusCriteriaRecord> records) {
        try
        {
            FileWriter writer = new FileWriter(fileNamePath);

            writer.append("ccc_1,");
            writer.append("ccc_2_a,ccc_2_b,ccc_2_c,ccc_2_d,");
            writer.append("ccc_3,");
            writer.append("ccc_4,");
            writer.append("ccc_5_a,ccc_5_b,ccc_5_c,ccc_5_d,ccc_5_e,ccc_5_f,");
            writer.append("ccc_6_a_i,ccc_6_a_ii,ccc_6_a_iii,ccc_6_a_iv,ccc_6_a_v,ccc_6_a_vi,ccc_6_a_vii,ccc_6_a_viii,");
            writer.append("ccc_6_b_i,ccc_6_b_ii,ccc_6_b_iii,ccc_6_b_iv,ccc_6_b_v,");
            writer.append("ccc_6_c_i,ccc_6_c_ii,ccc_6_c_iii,ccc_6_c_iv,ccc_6_c_v,");
            writer.append("ccc_7,");
            writer.append("MATCHES THE SYSTEM'S CRITERIA FOR ME/CFS");
            writer.append('\n');

            for(CanadianConsensusCriteriaRecord record : records) {
                writer.append(getStringValue(record.isCriteria_1()));
                writer.append(',');

                for(boolean value : record.getCriteria_2()) {
                    writer.append(getStringValue(value));
                    writer.append(',');
                }

                writer.append(getStringValue(record.isCriteria_3()));
                writer.append(',');

                writer.append(getStringValue(record.isCriteria_4()));
                writer.append(',');

                for(boolean value : record.getCriteria_5()) {
                    writer.append(getStringValue(value));
                    writer.append(',');
                }

                for(boolean[] subcatValues : record.getCriteria_6()) {
                    for (boolean value : subcatValues) {
                        writer.append(getStringValue(value));
                        writer.append(',');
                    }
                }

                writer.append(getStringValue(record.isCriteria_7()));
                writer.append(',');

                writer.append(getStringValue(record.isCriteriaMatch()));
                writer.append('\n');
            }

            writer.flush();
            writer.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    private char getStringValue(boolean value) {
        if(value)
            return '1';
        else
            return '0';
    }

    private CanadianConsensusCriteriaRecord createTrueRecord() {
        CanadianConsensusCriteriaRecord record = new CanadianConsensusCriteriaRecord();

        record.setCriteria_1(true);

        Arrays.fill(record.getCriteria_2(), true);

        record.setCriteria_3(true);

        record.setCriteria_4(true);

        setAtLeastTwoCriteria_5(record);

        setAtLeastOneFromAtLeastTwoSubCategoriesCriteria_6(record);

        record.setCriteria_7(true);

        record.setCriteriaMatch(true);

        return record;
    }

    private CanadianConsensusCriteriaRecord createFalseRecord() {
        CanadianConsensusCriteriaRecord record;

        do {
            record = new CanadianConsensusCriteriaRecord();

            record.setCriteria_1(rnd.nextBoolean());
            record.setCriteria_3(rnd.nextBoolean());
            record.setCriteria_4(rnd.nextBoolean());
            record.setCriteria_7(rnd.nextBoolean());

            for (int i = 0; i < record.getCriteria_2().length; i++)
                record.getCriteria_2()[i] = rnd.nextBoolean();

            for (int i = 0; i < record.getCriteria_5().length; i++)
                record.getCriteria_5()[i] = rnd.nextBoolean();

            for (int i = 0; i < 3; i++)
                for (int j = 0; j < record.getCriteria_6()[i].length; j++)
                    record.getCriteria_6()[i][j] = rnd.nextBoolean();

        } while(recordMatchesMeCriteria(record));

        record.setCriteriaMatch(false);

        return record;
    }

    private boolean recordMatchesMeCriteria(CanadianConsensusCriteriaRecord record) {
        if(!record.isCriteria_1())
            return false;

        if(!record.isCriteria_3())
            return false;

        if(!record.isCriteria_4())
            return false;

        if(!record.isCriteria_7())
            return false;

        for(boolean value : record.getCriteria_2())
            if(!value)
                return false;

        int numCriteria_5 = 0;
        for(boolean value : record.getCriteria_5())
            if(value)
                numCriteria_5++;

        if(numCriteria_5 < 2)
            return false;

        int numTrueSubCategoriesCriteria_6 = 0;
        for(boolean[] subcatValues : record.getCriteria_6()) {
            for(boolean value : subcatValues) {
                if(value) {
                    numTrueSubCategoriesCriteria_6++;
                    break;
                }
            }
        }

        return numTrueSubCategoriesCriteria_6 >= 2;

    }

    private void setAtLeastTwoCriteria_5(CanadianConsensusCriteriaRecord record) {
        setAtLeast(record.getCriteria_5(), 2);
    }

    private void setAtLeastOneFromAtLeastTwoSubCategoriesCriteria_6(CanadianConsensusCriteriaRecord record) {
        int numTrueSubCategories = rnd.nextInt(2) + 2;

        int subcategoryToExclude = -1;
        if(numTrueSubCategories == 2)
            subcategoryToExclude = rnd.nextInt(3);

        for(int i = 0; i < 3; i++)
            if(i != subcategoryToExclude)
                setAtLeast(record.getCriteria_6()[i], 1);
    }

    private void setAtLeast(boolean[] criteria, int numAtLeast) {
        int numToSet = rnd.nextInt(criteria.length - numAtLeast + 1) + numAtLeast;

        Arrays.fill(criteria, true);

        List<Integer> falseIndices = new ArrayList<>(criteria.length);
        for(int i = 0; i < criteria.length; i++)
            falseIndices.add(i);

        for(int i = 0; i < numToSet; i++) {
            falseIndices.remove(rnd.nextInt(falseIndices.size()));
        }

        for(int falseIndex : falseIndices)
            criteria[falseIndex] = false;
    }
}