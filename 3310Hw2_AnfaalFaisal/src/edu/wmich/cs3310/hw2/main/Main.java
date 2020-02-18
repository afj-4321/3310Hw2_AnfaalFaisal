package edu.wmich.cs3310.hw2.main;

import java.io.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    /**
     * This method declares and populates the list with values.
     * Then returns the resulting list as a result
     */
    static public  DoublyLinkList<Integer>  BuildOneTwoThree(){
        var ListItem = new DoublyLinkList<Integer>();
        for(int i = 1;i<=4;i++){
            ListItem.PushEnd(i);
        }
        return  ListItem;
    }

    /**
     *  AllSequenxes
     * We declare a variable in which all sequences found in the file will be stored
     */
    static DoublyLinkList<String> AllSequenxes = new DoublyLinkList<String>();

    /**
     * This method runs the entire program for execution.
     *  Get the file path from the entered string
     * And when we read line
     * by line from the file,
     *  at the same moment we will
     *  transfer the resulting sequence
     *   to a method for analyzing the sequence
     */
    public static void main(String[] args) {

        Demonstration();

        System.out.println("Enter file path");
        Scanner in = new Scanner(System.in);
        String path = in.nextLine();
        try(BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line = "";
            int count=0;
            while ((line = br.readLine()) != null) {
                /**
                 * when entering, we will also
                 * pass the line number to the method
                 */
                count++;
                Mirror(line,count);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
        /**
         * After we review all the lines,
         * the resulting sequences
         * will calculate their number
         */
        Counter();

    }

    /**
     * This input method receives a string with a number
     * further converts the string into a character set.
     * After that, we go through the loop on this set.
     * Since we are interested in sequences from 4 to 17 characters in length,
     * therefore, passing through the cycle in a symbol,
     * we will collect the symbols in a sequence. The original sequence consists of 4 elements.
     * After that, the cycle shifts the desired set by one unit and thus we get the next set.
     * @param  str - input string
     * @param number - number of string
     */
    public static void Mirror(String str,int number) {

        String Output = "";
        String pattern = "[a-zA-Z&&[^AUTCG]]";
        Pattern ptrn = Pattern.compile(pattern);
        Matcher matcher = ptrn.matcher(str);
        long firstTimeElapsed =0;
        long allTimeElapsed =0;
        var needSequence = new DoublyLinkList<String>();
        var temp1 = new DoublyLinkList<String>();
        var chars = str.toCharArray();
        var sequenceList = new DoublyLinkList<Character>();
        if (matcher.find()) {
            System.out.println("DNA sequence " +number+" : "+ str);
            Output+="DNA sequence " +number+" : "+ str+"\n";
            System.out.println(str + " contains characters that do not denote DNA molecules. Skipped.");
            Output+= str + " contains characters that do not denote DNA molecules. Skipped.\n";
        } else {

            for (int i = 0; i < chars.length; i++) {
                sequenceList.PushEnd(chars[i]);
            }
            long start1 = System.nanoTime()/1000000;
            for (int lengthSequence = 3; lengthSequence <= 16; lengthSequence++) {
                for (int j = 1; j < sequenceList.Length(); j++) {
                    String t = "";
                    if (j < sequenceList.Length()) {
                        for (int m = j; m <= j + lengthSequence; m++) {
                            if (m <= sequenceList.Length()) {
                                t += sequenceList.GetNth(m);
                            } else {
                                t += "+";
                            }
                        }
                    }
                    if (t.length() >= 4 & !t.contains("+") & t.length() <= 17)
                        temp1.PushEnd(t);
                }
            }

            /**
             * After receiving the list of characters,
             * we begin to analyze each set
             * from the list. We get the set,
             * turn it over and compare the correspondence
             * between the characters of the direct
             * and inverted sets.
             */
            for (int i = 1; i <= temp1.Length(); i++) {
                var word = temp1.GetNth(i);
                var reverse = new StringBuffer(word).reverse().toString();
                var WordArray = word.toCharArray();
                var reverseArray = reverse.toCharArray();
                int wordLength = 0;
                for (int j = 0; j < word.length(); j++) {

                    String compliment = WordArray[j] + Character.toString(reverseArray[j]);
                    if (compliment.equals("AT") || compliment.equals("TA") || compliment.equals("GC") || compliment.equals("CG") || compliment.equals("AU") || compliment.equals("UA")) {
                        wordLength++;
                    }
                }
                /**@see,
                 * if in the set the mirror reflection
                 * matches the necessary match and the
                 * number of characters matches the word
                 * being checked, this word falls into
                 * the desired sequence.
                 * As well as a list of all sequences
                 */
                if (wordLength == WordArray.length) {
                    AllSequenxes.PushEnd(word);
                    needSequence.PushEnd(word);
                    long finish = System.nanoTime()/1000000;
                    firstTimeElapsed =  finish - start1;
                }
            }
            long end = System.nanoTime()/1000000;
            allTimeElapsed = end-start1;
            /**
             * after all the cycles in the method
             * have completed. We display the result
             */
            if(needSequence.Length()>0){
                System.out.println("DNA sequence " +number+" : "+ str);
                Output+= "DNA sequence " +number+" : "+ str+"\n";
                System.out.println("This sequence contain "+needSequence.Length()+ " genetic palindromes.");
                Output+= "This sequence contain "+needSequence.Length()+ " genetic palindromes.\n";

                for (int item = 1; item <= needSequence.Length(); item++) {
                    var s = needSequence.GetNth(item);
                    System.out.println(s);
                }
                Output+="time to test whether a genomic subsequence in Sequence "+ number+" is palindromic: "+firstTimeElapsed+ " milliseconds\n";
                System.out.println("time to test whether a genomic subsequence in Sequence "+ number+" is palindromic: "+firstTimeElapsed+ " milliseconds");
                Output+="time to find all palindromic subsequences of length [4, 17] in Sequence "+number+" : "+allTimeElapsed+" milliseconds\n";
                System.out.println("time to find all palindromic subsequences of length [4, 17] in Sequence "+number+" : "+allTimeElapsed+" milliseconds");
            }
            if(needSequence.Length()==0){
                Output+="DNA sequence " +number+" : "+ str+"\n";
                System.out.println("DNA sequence " +number+" : "+ str);
                Output+="This sequence does not contain  genetic palindromes\n";
                System.out.println("This sequence does not contain  genetic palindromes.");
            }
        }
        try(FileWriter writer = new FileWriter("Output.txt", true))
        {

            writer.write(Output);


            writer.flush();
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
    }

    /**
     * This method reads the number of received
     * sequences and displays their number in the file.
     */
    public   static void Counter(){
        long start1 = System.nanoTime()/1000000;
        int countSeq=0;
        /**
         * Announce a list of unique sequences
         */
        var uniqString = new DoublyLinkList<String>();

        /**
         * First, let's go through the list of all sequences in the file.
         * Next, we check if in the unique list of
         * sequences there is no this element, it will be added
         */
        for(int i=1;i<=AllSequenxes.Length();i++) {
            String seq = AllSequenxes.GetNth(i);
            boolean check = true;
            for (int s = 1; uniqString.Length() >= s; s++) {
                if (uniqString.GetNth(s).equals(seq)) check = false;
            }
            if (check) uniqString.PushEnd(seq);
        }
        /**
         * After that we go through all the
         * unique values ​​and compare
         * these elements with the total number.
         * If the elements match, increase the counter
         */
        for(int d=1;d<=uniqString.Length();d++) {
            String uniq = uniqString.GetNth(d);
            for(int i =1;i<=AllSequenxes.Length();i++){
                if(uniq.equals(AllSequenxes.GetNth(i))){
                    countSeq++;
                }
            }
            long finish = System.nanoTime()/1000000;
            long timeElps = finish-start1;
            System.out.println("\""+uniq+"\" has "+ countSeq+" similar sequences in the file, and\n" +
                    "it took "+timeElps+" milliseconds to determine that." );
            countSeq=0;
        }
    }

    /**
     * This method demonstrates the operation
     * of methods defined in the DoublylinkList class.
     */
    public static void Demonstration(){
        System.out.println("We create a list object by calling\n a method that creates a list\n and adds objects to it " );
        var List = BuildOneTwoThree();
        System.out.println("List elements : " );
        for(int i = 1; i<=List.Length(); i++){
            System.out.print(" "+List.GetNth(i) + " ");
        }
        System.out.println();
        System.out.println("Insert the item at the top of the list. " );

        List.PushToBegin(13);

        System.out.println("List elements : " );
        for(int i = 1; i<=List.Length(); i++){
            System.out.print(" "+List.GetNth(i) + " ");
        }
        var len = List.Length();
        System.out.println("Display the length of the list." );
        System.out.println("List length : "+len );

        System.out.println("Count the number of identical elements");
        List.PushEnd(2);
        List.PushEnd(3);
        List.PushToBegin(1);
        System.out.println("List elements : " );
        for(int i = 1; i<=List.Length(); i++){
            System.out.print(" "+List.GetNth(i) + " ");
        }
        System.out.println();
        int need = 2;
        int count = List.Count(need);

        System.out.println("List contains "+need+" : "+count +" times");
        var stringList = new DoublyLinkList<String>();
        stringList.PushEnd("Hi");
        stringList.PushEnd("Good Morning");
        stringList.PushEnd("Good Afternoon");
        stringList.PushEnd("By");


        System.out.print("List elements : " );
        for(int i = 1; i<=stringList.Length(); i++){
            System.out.print(" "+stringList.GetNth(i) + " ");
        }
        System.out.println();
        int index = 2;
        var needWord = stringList.GetNth(index);

        System.out.println(" element at "+index+" : " +needWord);

        var str =  stringList.DeleteList();

        System.out.println(str);

        System.out.println("Get deleted items first and from the end of the list");
        var items = BuildOneTwoThree();
        var a = items.PopEnd();
        var b = items.PopEnd();
        var c = items.PopEnd();
        System.out.println("a "+a+" b "+b+" c "+c);
        var items2 = BuildOneTwoThree();
        var a1 = items2.PopBegin();
        var b1 = items2.PopBegin();
        var c1 = items2.PopBegin();
        System.out.println("a "+a1+" b "+b1+" c "+c1);
        var myL = BuildOneTwoThree(); // start with the empty list
        System.out.println("Add an item to the position after which the list moves");

        myL.InsertNth(2, 42); // build {13, 42}

        System.out.print("List elements : " );
        for(int i = 1; i<=myL.Length(); i++){
            System.out.print(" "+myL.GetNth(i) + " ");
        }
        System.out.println();
        System.out.println("Create two lists and add one to the other");
        var s = BuildOneTwoThree();
        var n = BuildOneTwoThree();
        n.Append(s);
        for(int i =1;i<=n.Length();i++){
            System.out.println(n.GetNth(i));
        }

        System.out.println("Demonstration completed");

    }
}
