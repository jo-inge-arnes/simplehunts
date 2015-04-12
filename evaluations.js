#!/usr/bin/env node

var fs = require('fs');
var util = require('util');
var execSync = require('child_process').execSync;

var main = function() {

    var percentages = [0.1, 0.5, 1.0, 10.0, 25.0, 50.0, 75.0, 90.0, 99.0, 99.5, 99.9];
    var numRecordsList = [5, 10, 50, 100, 500, 1000, 10000, 100000, 200000];

    var trueTestCsvPath = 'target/testTrue.csv';
    var falseTestCsvPath = 'target/testFalse.csv';

    createCsv(100000, 1.0, trueTestCsvPath);
    createCsv(100000, 0.0, falseTestCsvPath);

    for(var i = 0; i < numRecordsList.length; i++) {
        for(var j = 0; j < percentages.length; j++) {
            var csvOutPath = 'target/csvout.csv';
            var treeJsonPath = 'target/decisionTree.json';
            createCsv(numRecordsList[i], percentages[j] / 100.0, csvOutPath);
            learnTree(csvOutPath, treeJsonPath);
            var trueTestAccuracy = evaluate(treeJsonPath, trueTestCsvPath);
            var falseTestAccuracy = evaluate(treeJsonPath, falseTestCsvPath);

            var resultString = util.format('%d, %d, %s, %s',
                numRecordsList[i],
                percentages[j],
                trueTestAccuracy.trim(),
                falseTestAccuracy.trim());

            console.log(resultString);
            fs.appendFileSync('accuracy.csv', resultString + '\r\n');
        }
    }

    process.exit();
};

var createCsv = function(numRecords, percentPositiveRecords, csvOutputFileName) {
    var commandString = util.format('java -jar target/csvgenerator.jar %d %d %s',
        numRecords,
        percentPositiveRecords,
        csvOutputFileName);

    executeCommand(commandString);
};

var learnTree = function(csvInPath, treeJsonPath) {
    var commandString = util.format('java -jar target/learn.jar %s %s',
        csvInPath,
        treeJsonPath);

    executeCommand(commandString);
};

var evaluate = function(treeJsonPath, testCsvPath) {
    var commandString = util.format('java -jar target/evaluate.jar %s %s',
        treeJsonPath,
        testCsvPath);

    return executeCommand(commandString);
};

var executeCommand = function(commandString) {
    //console.log('Executing: ' + commandString);

    return execSync(commandString, { encoding: 'utf8' });
};

if (require.main === module) {
    main();
}