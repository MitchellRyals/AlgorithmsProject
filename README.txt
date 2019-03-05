A program for my algorithms & data structures final. It has a compiled java file with several errors and one without errors. When executed, it asks which file you would like to check for missing symbols like ({[/ and then uses a stack to see if any are missing from the file.

*My code is under src/Main.java

To run the program, navigate to this folder in the command prompt.

Then type:
	java -jar AlgorithmsProject.jar

The program will list all files in the current directory. Type the number of the
one you want to test and press enter. It will then check for errors.
If there are none it will print "no errors found."

If errors are found it will throw a custom RuntimeException message and exit.

To view the source code, navigate to src/Main.java
