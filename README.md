# assignment-carpiness

[![Build Status](https://travis-ci.org/Madzi/assignment-carpiness.svg?branch=master)](https://travis-ci.org/Madzi/assignment-carpiness)
[![codecov](https://codecov.io/gh/Madzi/assignment-carpiness/branch/master/graph/badge.svg)](https://codecov.io/gh/Madzi/assignment-carpiness)
[![codebeat badge](https://codebeat.co/badges/d34eb13e-84d4-46bf-b399-eec6980af150)](https://codebeat.co/projects/github-com-madzi-assignment-carpiness-master)

## Java Assignment
The project corresponds to the received specification

## Build instructions
The project can be build with [Maven](https://maven.apache.org) 3.5.0 or above:

    mvn clean package

After it is built you can run project from maven:

    mvn exec:java

or directly from `target` folder:

    java -jar java-assignment-0.0.1-SNAPSHOT-jar-with-dependencies.jar

The brief documentation can be generated with maven:

    mvn site

or

    mvn javadoc:javadoc

## Parameters

When you run the program, you can use some parameters in the command line:

- *--help* - display short usage description
- *--ignore-case - allow program get keywords in different cases like _Extra-Margin_ or _EXTRA_MARGIN_
- *--ignore-error - allow skip unrecognized line in input files, also not fail if one ore more files from batch not found

all other parameters recognized as file names (without extensions) for batch processing. Extensions must be '.in' .
The result of batch processing is stored at files with the same name and extension '.out'

## Current features

- Support console invoice input/output
- Support batch files invoice processing

## Known issues
