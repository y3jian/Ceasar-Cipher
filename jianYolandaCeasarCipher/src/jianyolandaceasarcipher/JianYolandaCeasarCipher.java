/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jianyolandaceasarcipher;

import java.util.Scanner;
import java.util.StringTokenizer;

/**
 *
 * @author yolandajian
 */
public class JianYolandaCeasarCipher {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        char typeArr[] = {'i', 's'};
        char option = 'z';
        boolean goodOption = false;
        boolean goodStr = false;
        boolean goodInt = false;
        int shiftBy;
        int correctShift = 0;
        String correctStr = "";
        String[] commonWords = {"the", "of", "and", "a", "to", "in", "is", "you", "that", "it",
            "he", "was", "for", "on", "are", "as", "with", "his", "they", "I", "at", "be", "this", "have",
            "from", "or", "one", "had", "by", "word", "but", "not", "what", "all", "were", "we", "when", "your",
            "can", "said", "there", "use", "an", "each", "which", "she", "do", "how", "their", "if", "will", "up",
            "other", "about", "out", "many", "then", "them", "these", "so", "some", "her", "would", "make", "like",
            "him", "into", "time", "has", "look", "two", "more", "write", "go", "see", "number", "way", "could",
            "people", "my", "than", "first", "water", "been", "call", "who", "oil", "its", "now", "find", "long",
            "down", "day", "did", "get", "come", "made", "may", "part", "hi"};

        while (option != 'q') {//while q has not been entered
            while (!goodOption) {//loop through until either e, d, b, or q is entered
                System.out.println("Encode (e) Decode (d) BruteForce (b) Quit (q): ");

                try {
                    option = keyboard.nextLine().charAt(0);//finding the letter

                    if ((option == 'e' || option == 'd' || option == 'b' || option == 'q')) {
                        goodOption = true;//breaks out of loop
                    } else {
                        goodOption = false;
                    }

                } catch (Exception e) {
                    System.out.println("Please enter e, d, b, or q");
                }
            }

            if (goodOption) {
                if (option == 'q') {//end code if the option is q
                    goodOption = false;
                } else {

                    switch (option) {
                        case 'e'://encode 
                            try {
                                goodStr = false;
                                goodInt = false;
                                shiftBy = 0;
                                String encodeBy = "";
                                String encodeString = "";

                                while (!goodStr && !goodInt) {//keep asking user until the correct format is entered 
                                    while (!goodStr) {
                                        System.out.print("Phrase to encode: ");//add a loop to keep asking 
                                        encodeString = keyboard.nextLine();
                                        goodStr = typeVerify(encodeString, typeArr[1]);//error checking
                                    }

                                    while (!goodInt) {
                                        System.out.print("Shift right by?: ");
                                        encodeBy = keyboard.nextLine();
                                        goodInt = typeVerify(encodeBy, typeArr[0]);//error checking
                                    }
                                }
                                if (goodStr && goodInt) {
                                    shiftBy = Integer.parseInt(encodeBy);
                                    String outputEncode = changeIt(encodeString, shiftBy);//passing through method
                                    System.out.println(outputEncode);
                                }
                                goodOption = false;

                            } catch (Exception e) {
                            }
                            break;

                        case 'd'://decode
                            try {
                                goodStr = false;
                                goodInt = false;
                                shiftBy = 0;
                                String decodeBy = "";
                                String decodeString = "";
                                while (!goodStr && !goodInt) {//keep asking user until the correct format is entered 
                                    while (!goodStr) {
                                        System.out.println("Phrase to decode: ");
                                        decodeString = keyboard.nextLine();
                                        goodStr = typeVerify(decodeString, typeArr[1]);//error checking
                                    }
                                    while (!goodInt) {
                                        System.out.println("shift left by?: ");
                                        decodeBy = keyboard.nextLine();
                                        goodInt = typeVerify(decodeBy, typeArr[0]);//passing through method
                                    }
                                }
                                if (goodStr && goodInt) {
                                    shiftBy = Integer.parseInt(decodeBy) * -1;//making shift negative so it goes left
                                    String outputDecode = changeIt(decodeString, shiftBy);
                                    System.out.println(outputDecode);
                                }
                                goodOption = false;
                            } catch (Exception e) {
                            }
                            break;

                        case 'b'://brute force
                            try {
                                goodStr = false;
                                while (!goodStr) {
                                    System.out.println("Phrase to Brute Force: ");
                                    String bruteForceString = keyboard.nextLine();
                                    goodStr = typeVerify(bruteForceString, typeArr[1]);//error checking

                                    if (goodStr) {
                                        String[] outputBrute = bruteForce(bruteForceString);//passing to method
                                        for (int index = 1; index <= 26; index++) {//printing each index
                                            System.out.println("For shift of " + index + ", decoded is: " + outputBrute[index]);
                                            StringTokenizer st = new StringTokenizer(outputBrute[index], " ");
                                            //seperating the string to compare the strings to common words
                                            while (st.hasMoreElements()) {
                                                String curElement = (st.nextElement().toString());

                                                for (int pos = 0; pos <= 99; pos++) {//looping through common words array
                                                    if (commonWords[pos].equalsIgnoreCase(curElement)) {//if equal
                                                        correctStr = outputBrute[index];//setting correct variables
                                                        correctShift = index;
                                                    }
                                                }
                                            }
                                        }
                                        System.out.println("The best decode was with key " + correctShift);
                                        System.out.println("Decoded message is: " + correctStr);
                                    }
                                }
                                goodOption = false;//restarting program
                            } catch (Exception e) {
                            }
                            break;
                    }
                }
            }
        }
    }

    public static String changeIt(String encodeDecode, int shift) {
        char curLetter;
        char initialLetter;
        char newLetter = 'a';
        String message = "";
        int initialShift = shift;
        int shiftCounter = shift / Math.abs(shift);//dividing by the absolute value of the shift to get a pos or neg shift
        int checkShift;
        int curShift;
        for (int i = 0; i < encodeDecode.length(); i++) {//going through each letter

            curLetter = encodeDecode.charAt(i);
            initialLetter = curLetter;
            curShift = 0;
            checkShift = 0;
            int round = 0;
            shift = initialShift;

            if (curLetter >= 65 && curLetter <= 90) {//for uppercase letters

                while (round != shift) {//while the desired amount of shifts haven't been reached

                    curShift = curShift + (shiftCounter);//add or subtract 1 depending on the negative or pos shift
                    round = round + (1 * shiftCounter);//adding or subtracting to the round
                    checkShift = curShift + initialLetter;//adding the current shift to curLetter to check if it will exceed 90 (uppercase Z)
                    if (checkShift > 90) {//if it does exceed
                        initialLetter = 'A';//resetting initial letter
                        curShift = 0;
                        curShift = curShift + (shiftCounter);
                        curLetter = 64;//start at A
                    } else if (checkShift < 65) {
                        initialLetter = 'Z';
                        curShift = 0;
                        curShift = curShift + (shiftCounter);
                        curLetter = 91;//start at Z
                    }
                    newLetter = (char) (curLetter + curShift);
                }
                message = message + newLetter;//adding to the new message

            } else if (curLetter >= 97 && curLetter <= 122) {//for lowercase letters 

                while (round != shift) {

                    curShift = curShift + (shiftCounter);
                    round = round + (1 * shiftCounter);
                    checkShift = curShift + initialLetter;//adding the current shift to curLetter to check if it will exceed 112 (lowercase z)
                    if (checkShift > 122) {//if it does exceed
                        initialLetter = 'a';
                        curShift = 0;
                        curShift = curShift + (shiftCounter);
                        curLetter = 96;// start at a
                    } else if (checkShift < 97) {
                        initialLetter = 'z';
                        curShift = 0;
                        curShift = curShift + (shiftCounter);
                        curLetter = 123; //strart at z
                    }
                    newLetter = (char) (curLetter + curShift);
                }
                message = message + newLetter;
            } else {//for spaces and special characters 
                message = message + curLetter;//keeping them the same 
            }
        }
        return message;
    }

    public static String[] bruteForce(String msgln) {
        String[] bruteForce = new String[27];
        for (int index = 1; index <= 26; index++) {//assigning strings to each index
            bruteForce[index] = changeIt(msgln, -index);//negative index for decode
        }
        return bruteForce;
    }

    static boolean typeVerify(String elementCur, char arrCur) {//error checking
        boolean correct = false;
        switch (arrCur) {
            case 'i'://for integers 
                try {
                    int typeInt = Integer.parseInt(elementCur);//converting to int
                    if (typeInt >= 0 && typeInt <= 25) {
                        correct = true;
                    }

                } catch (NumberFormatException e) {//catching the error
                    correct = false;
                    System.out.println("Enter an integer");
                }
                break;

            case 's'://for strings

                for (int i = 0; i <= elementCur.length(); i++) {
                    //uppercase 65 to 90 lowercase 97 to 122
                    if (elementCur.charAt(i) >= 65 && elementCur.charAt(i) <= 90 || elementCur.charAt(i) >= 97 && elementCur.charAt(i) <= 122) {
                        correct = true;
                        break;
                    } else {
                        System.out.println("Enter a String");
                        correct = false;
                        break;
                    }
                }

        }
        return correct;
    }
}
