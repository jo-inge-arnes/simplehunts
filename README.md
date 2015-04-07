Code reading tip: 
A good place to start examining the code is by reading the java class in simplehunts/learning/src/main/java/jar005/simplehunts/algorithm/Learner.class

Requirements to build the project:

- Java 7 JDK must be installed
- Maven must be installed. 
	See: https://maven.apache.org/download.cgi 
	For Ubuntu, install with apt-get: 
		http://www.mkyong.com/maven/how-to-install-maven-in-ubuntu/
		http://www.sysads.co.uk/2014/05/install-apache-maven-3-2-1-ubuntu-14-04/

To build all modules:

- Cd into the 'simplehunts' directory
- Run mvn package
- Cd into the 'target' directory
- In the directory you will now have 4 executable jar-files

1) Dataset generator for training and evaluation data:

	java -jar csvgenerate.jar 20000 0.5 training.csv
	java -jar csvgenerate.jar 20000 0.5 evaluation.csv

	The first parameter (20000) is for generating 20000 random records.
	The second parameter means a distribution of 50% true records (matching ME criteria)
	The third parameter is the output file name

2) Learning algorithm (Hunt's algorithm)
	
	java -jar learn.jar training.csv output.json
	
	Reads the file training.csv and outputs a JSON-file with the decision tree (filename param for JSON is optional)

3) Evaluation of accuracy
	
	java -jar evaluate output.json evaluation.csv

	Reads the decision tree from the JSON-file and uses the evaluation CSV-file for calculating accuracy

4) Using the decision tree for asking questions
	
	java -jar classify.jar output.json
