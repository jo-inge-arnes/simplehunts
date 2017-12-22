#### A simple implementation of Hunt's Algorithm
The Java-projects in this repository were originally written as part of a course assignment, which can be found here:

[Assignment PDF](Assignment%202%20-%20Hunt%26%23039%3Bs%20algorithm%20-%20Final%20Version.pdf)

There are several Java-programs in the repository. The main class for the most interesting one, that uses Hunt's Algorithm, can be found here:

[LearnerClient](learner-client/src/main/java/jar005/simplehunts/client/LearnerClient.java)

It reads a CSV-file with training data, and produces a JSON-structure as output. This JSON-structure is a decision tree, that can be read and used in other programs.

The implementation only handles boolean (true/false) values.

#### Tip
The main method of [LearnerClient](learner-client/src/main/java/jar005/simplehunts/client/LearnerClient.java) calls methods on the [Learner-class](learning/src/main/java/jar005/simplehunts/algorithm/Learner.java). The main method of the client in combination with the Learner-class is a good place to start reading to understand the implementation. Note that the class resides in a separate library project, and it can easily be used in other Java-applications.

#### Prerequisites for building the project

- Java 7 JDK must be installed
- Maven must be installed.
	* Maven download: https://maven.apache.org/download.cgi
	* Maven install instructions: https://maven.apache.org/download.cgi#Installation_Instructions
	* Ubuntu-users can install with apt-get

#### Building the modules

- Cd into the `simplehunts` directory
- Run `mvn package`
- Cd into the `target` directory
- In the directory you will now have 4 executable jar-files

1. __Dataset generator for training and evaluation data__

		java -jar csvgenerate.jar 20000 0.5 training.csv
		java -jar csvgenerate.jar 20000 0.5 evaluation.csv

	The first parameter (20000) is for generating 20000 random records.
	The second parameter means a distribution of 50% true records (matching ME criteria).
	The third parameter is the output file name.

2. __Learning algorithm (Hunt's algorithm)__
	
		java -jar learn.jar training.csv output.json
	
	Reads the file training.csv and outputs a JSON-file with the decision tree. 
	The filename param for JSON is optional.

3. __Evaluation of accuracy__
	
		java -jar evaluate.jar output.json evaluation.csv

	Reads the decision tree from the JSON-file and uses the evaluation CSV-file for calculating accuracy

4. __Using the decision tree for asking questions__
	
		java -jar classify.jar output.json
