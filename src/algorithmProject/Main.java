package algorithmProject;

import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        //runs getUserFileChoice to get the file the user wants to be read
        File fileChosen = getUserFileChoice();
        InputStream inputFile = new FileInputStream(fileChosen);

        //to check for brackets/braces, I created two hashsets for O(1) lookup time and added each individual thing to look for in here.
        //to avoid typing <hashSet>.add() for each one, I made an array and added it to the hashset upon initialization.
        Character openingSymbolsForCompiler[] = {'{', '[', '(', '/'};
        Character closingSymbolsForCompiler[] = {'}', ']', ')', '/'};
        HashSet<Character> openingSymbols = new HashSet<Character>(Arrays.asList(openingSymbolsForCompiler));
        HashSet<Character> closingSymbols = new HashSet<Character>(Arrays.asList(closingSymbolsForCompiler));

        int characters;
        Stack<Character> syntaxStack = new Stack<Character>();

        //read() converts the next character in the input file to ascii.
        //if it's -1 it has reached the end and will exit.
        while ((characters = inputFile.read()) != -1) {
            char inputChar = (char) characters;
            if (closingSymbols.contains(inputChar) && !syntaxStack.isEmpty()) {
                char secondInStack = syntaxStack.peek();
                syntaxStack.push(inputChar);

                //this block seems weird but if I don't put this here, the program prints a ton of my System.out.println() statements to the console
                try {checkForPop(syntaxStack, secondInStack);}
                catch (RuntimeException e) { //catch an error thrown by the function
                    throw new RuntimeException(e);
                }

                System.out.println("In the stack right now: " + Arrays.toString(syntaxStack.toArray()));
            }
            //add the symbol to the stack if it's a '/' or right facing symbol '( { ['
            else if (openingSymbols.contains(inputChar)) {
                syntaxStack.push(inputChar);
                System.out.println("In the stack right now: " + Arrays.toString(syntaxStack.toArray()));
            }
        }

        //throw an error if the stack has anything leftover in it when the file is done being read
        if (!syntaxStack.isEmpty())
            throw new RuntimeException("Compiler error. Missing closing symbols (') } ] /'). Exiting Program.");
        else
            System.out.println("No errors found.");

    }

    public static File getUserFileChoice() {
        /*******THIS FUNCTION GETS ALL FILES IN THE CURRENT DIRECTORY, PRINTS THEM, GETS THE USER INPUT, AND RETURNS THE FILE CHOSEN*********/

        //gets the directory that the java file is operating in
        File currentDirectoryFiles = new File(".");

        //couple of instantiations
        File[] filesWithDir = currentDirectoryFiles.listFiles();
        ArrayList<File> filesWithoutDir = new ArrayList<>();

        //put all files into filesWithoutDir without adding directories to it
        for (File file: filesWithDir) {
            if (file.isFile())
                filesWithoutDir.add(file);
        }

        System.out.println("Files in the current directory:");
        //display contents of filesWithoutDir
        for (int i = 0; i < filesWithoutDir.size(); i++) {
            System.out.print((i + 1) + ") ");
            try { System.out.println(filesWithoutDir.get(i).getCanonicalPath()); }
            catch (IOException e){ System.out.println("error found I guess?"); }
        }
        System.out.println("Type the number of the file you would like to read.");

        //have the user select a file
        Scanner input = new Scanner(System.in);
        int userInput = input.nextInt() - 1;

        return filesWithoutDir.get(userInput);
    }

    public static void checkForPop(Stack<Character> syntaxStack, char secondInStack) {
        char lastInStack = syntaxStack.peek();
        char matchingSymbol = ' ';

        //get the opening symbol or '/' of the passed symbol
        switch (lastInStack) {
            case '}':
                matchingSymbol = '{';
                break;
            case ')':
                matchingSymbol = '(';
                break;
            case ']':
                matchingSymbol = '[';
                break;
            case '/':
                matchingSymbol = '/';
                break;
            default:
                System.out.println("You shouldn't be seeing this. You broke my program. The value being checked was " + lastInStack);
        }

        //if the second to last element matches the new element, pop twice. Otherwise throw an exception because a symbol is missing.
        if (secondInStack == matchingSymbol) {
            System.out.println("Popping: " + syntaxStack.peek());
            syntaxStack.pop();
            System.out.println("Popping: " + syntaxStack.peek());
            syntaxStack.pop();
        } else
        {
            throw new RuntimeException("Compiler error. Expected " + matchingSymbol + ". Got " + secondInStack + ". Exiting Program.");
        }
    }
}
