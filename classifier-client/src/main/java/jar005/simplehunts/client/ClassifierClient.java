package jar005.simplehunts.client;

import jar005.simplehunts.model.Node;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ClassifierClient {
    private Map<String, String> descriptionMap = new HashMap<>();

    public static void main(String[] args) {
        if (!validateArgs(args))
            return;

        ClassifierClient classifierClient = new ClassifierClient();
        classifierClient.classify(args[0]);
    }

    public ClassifierClient() {
        initDescriptionMap();
    }

    private void classify(String treeJsonFilePath) {
        ObjectMapper objectMapper = new ObjectMapper();

        Node root;
        try {
            root = objectMapper.readValue(new File(treeJsonFilePath), Node.class);
        } catch (Exception e) {
            root = null;
            e.printStackTrace();
        }

        Scanner scanner = new Scanner(System.in);

        if(root != null) {
            printInstructions();

            do {
                Node node = root;

                while (!node.isLeaf()) {
                    System.out.println(description(node.getAttributeName()));

                    boolean validAnswer;
                    do {
                        String answer = scanner.nextLine().toLowerCase();
                        if ("y".equals(answer)) {
                            node = node.getTrueBranchNode();
                            validAnswer = true;
                        } else if ("n".equals(answer)) {
                            node = node.getFalseBranchNode();
                            validAnswer = true;
                        } else if ("q".equals(answer)) {
                            System.out.println("Bye!");
                            return;
                        } else {
                            System.out.println("Valid answers are y, n and q");
                            validAnswer = false;
                        }
                    } while (!validAnswer);
                }

                String decision = node.getValue() ? "Positive" : "Negative";
                System.out.println(String.format("%s: %s", description(node.getAttributeName()), decision));

                System.out.println("\nOne more time?");
            } while(oneMoreTime(scanner));

            System.out.println("Bye!");
        }
    }

    private String description(String attributeName) {
        if(descriptionMap.containsKey(attributeName))
            return descriptionMap.get(attributeName);
        else
            return attributeName;
    }

    private void printInstructions() {
        System.out.println("Welcome to the classifier!");
        System.out.println("Answer questions with the letters y for yes and n for no. Enter q to quit.");
        System.out.println("");
    }

    private boolean oneMoreTime(Scanner scanner) {
        boolean validAnswer = true;

        do {
            String answer = scanner.nextLine().toLowerCase().trim();

            if ("y".equals(answer))
                return true;

            if(!("n".equals(answer) || "q".equals(answer))) {
                validAnswer = false;
                System.out.println("Valid answers are y, n and q");
            } else {
                validAnswer = true;
            }

        } while(!validAnswer);

        return false;
    }

    private void initDescriptionMap() {
        descriptionMap.put("ccc_1",
                "1. Fatigue:\nThe patient must have a significant degree of new onset, unexplained, persistent, or " +
                    "recurrent physical and mental fatigue that substantially reduces activity level.");

        descriptionMap.put("ccc_2_a",
                "2. Post-Exertional Malaise and/or Fatigue:\n" +
                        "There is an inappropriate loss of physical and mental stamina, " +
                        "rapid muscular and cognitive fatigability.");

        descriptionMap.put("ccc_2_b",
                "2. Post-Exertional Malaise and/or Fatigue:\n" +
                "Post exertional malaise and/or fatigue and/or pain");

        descriptionMap.put("ccc_2_c",
                "2. Post-Exertional Malaise and/or Fatigue:\n" +
                        "A tendency for other associated symptoms within the patient’s cluster of symptoms to worsen");

        descriptionMap.put("ccc_2_d",
                "2. Post-Exertional Malaise and/or Fatigue:\n" +
                        "There is a pathologically slow recovery period - usually 24 hours or longer.");

        descriptionMap.put("ccc_3",
                "3. Sleep Dysfunction:\nThere is unrefreshed sleep or sleep quantity or rhythm disturbances such " +
                    "as reversed or chaotic diurnal sleep rhythms.");

        descriptionMap.put("ccc_4",
                "4. Pain:\nThere is a significant degree of myalgia. Pain can be experienced in the muscles, and/or " +
                        "joints, and is often widespread and migratory in nature. Often there are significant " +
                        "headaches of new type, pattern or severity.");

        descriptionMap.put("ccc_5_a",
                "5. Neurological/Cognitive Manifestations:\n" +
                        "Impairment of concentration and short-term memory consolidation");

        descriptionMap.put("ccc_5_b",
                "5. Neurological/Cognitive Manifestations:\n" +
                        "Confusion");

        descriptionMap.put("ccc_5_c",
                "5. Neurological/Cognitive Manifestations:\n" +
                        "Disorientation");

        descriptionMap.put("ccc_5_d",
                "5. Neurological/Cognitive Manifestations:\n" +
                        "Difficulty with information processing");

        descriptionMap.put("ccc_5_e",
                "5. Neurological/Cognitive Manifestations:\n" +
                        "Difficulty with information categorizing and word retrieval");

        descriptionMap.put("ccc_5_f",
                "5. Neurological/Cognitive Manifestations: " +
                        "Perceptual and sensory disturbances " +
                        "– e.g. spatial instability and disorientation and inability to " +
                        "focus vision. Ataxia, muscle weakness and fasciculations are common. " +
                        "Overload phenomena: cognitive, sensory " +
                        "– e.g. photophobia and hypersensitivity to noise - " +
                        "and/or emotional overload, which may lead to “crash” periods and/or anxiety.");

        descriptionMap.put("ccc_6_a_i",
                "6. a. Autonomic Manifestations: " +
                        "Orthostatic intolerance - neurally mediated hypotension (NMH)");

        descriptionMap.put("ccc_6_a_ii",
                "6. a. Autonomic Manifestations: " +
                        "Postural orthostatic tachycardia syndrome (POTS)");

        descriptionMap.put("ccc_6_a_iii",
                "6. a. Autonomic Manifestations: " +
                        "Delayed postural hypotension");

        descriptionMap.put("ccc_6_a_iv",
                "6. a. Autonomic Manifestations: " +
                        "Light-headedness");

        descriptionMap.put("ccc_6_a_v",
                "6. a. Autonomic Manifestations: " +
                        "Extreme pallor, nausea and irritable bowel syndrome");

        descriptionMap.put("ccc_6_a_vi",
                "6. a. Autonomic Manifestations: " +
                        "Palpitations with or without cardiac arrhythmias");

        descriptionMap.put("ccc_6_a_vii",
                "6. a. Autonomic Manifestations: " +
                        "Exertional dyspnea");

        descriptionMap.put("ccc_6_a_viii",
                "6. a. Autonomic Manifestations: " +
                    "Urinary frequency and bladder dysfunction");


        descriptionMap.put("ccc_6_b_i",
                "6. b. Neuroendocrine Manifestations: " +
                        "Loss of thermostatic stability – subnormal body " +
                        "temperature and marked diurnal fluctuation, " +
                        "sweating episodes, recurrent feelings of " +
                        "feverishness and cold extremities");

        descriptionMap.put("ccc_6_b_ii",
                "6. b. Neuroendocrine Manifestations: " +
                        "Intolerance of extremes of heat and cold");

        descriptionMap.put("ccc_6_b_iii",
                "6. b. Neuroendocrine Manifestations: " +
                        "Marked weight change - anorexia or abnormal appetite");

        descriptionMap.put("ccc_6_b_iv",
                "6. b. Neuroendocrine Manifestations: " +
                        "Loss of adaptability");

        descriptionMap.put("ccc_6_b_v",
                "6. b. Neuroendocrine Manifestations: " +
                        "Worsening of symptoms with stress");

        descriptionMap.put("ccc_6_c_i",
                "6. c. Immune Manifestations: " +
                        "Tender lymph nodes");

        descriptionMap.put("ccc_6_c_ii",
                "6. c. Immune Manifestations: " +
                        "Recurrent sore throat");

        descriptionMap.put("ccc_6_c_iii",
                "6. c. Immune Manifestations: " +
                        "Recurrent flu-like symptoms");

        descriptionMap.put("ccc_6_c_iv",
                "6. c. Immune Manifestations: " +
                        "General malaise");

        descriptionMap.put("ccc_6_c_v",
                "6. c. Immune Manifestations: " +
                        "New sensitivities to food, medications and/or chemicals");

        descriptionMap.put("ccc_7",
                "7. The illness persists for at least six months: " +
                    "It usually has a distinct onset, **although it may be gradual. " +
                        "Preliminary diagnosis may be possible earlier. Three months is appropriate for children.");

    }

    private static boolean validateArgs(String[] args) {
        boolean argsAreValid = true;

        if (args.length != 1) {
            argsAreValid = false;
            System.out.println("Usage: java -jar classify.jar tree.json");
        }

        return argsAreValid;
    }
}
