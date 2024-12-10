package csc230project4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import csc230project4.LinkedListwHHLSort.Node;

/**
 * Name: Marcus Valbrun N00920668
 * Project 4 App Class
 * 16 December 2023
 * 
 * 
 * This program reads in the genius_hip_hop_lyrics csv file 
 * It then allows a user to Pipe the data into separate data structures
 * including a Unsorted and Sorted Linked List, a Hash Table and a Binary Search Tree
 * based on the headings the user selects
 * 
 * Finally it allows the user to search for a line in the csv file using the 
 * corresponding values to the headings selected to ultimately inspect
 * which list have faster and fewer searches depending on the value(s) searched for.
 * 
 * I was assigned: genius_hip_hop_lyrics.csv
 * 
 */
public class Project4 {
	// Variables to keep track of comparisons made
	public static int totalUnsortedListComparisons = 0;
	public static int totalSortedListComparisons = 0;
	public static int unsortedListComparisons = 0;
	public static int sortedListComparisons, stoppedShort = 0;
	public static int totalHashTableComparisons = 0;
	public static int totalBSTcomparisons = 0;




	/**
	 * The main method of the program.
	 * 
	 * User Controlled Loop
	 * 
	 * @param args the command-line arguments
	 */
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the CSV file name: ");
        String fileName = scanner.nextLine();
        
        //Allows User to set the heading(s) used for the search
        System.out.print("\nEnter 1-5 headings (comma-separated) excluding the ID heading (the order will be used to create a sorted list): ");
        String[] headings = scanner.nextLine().split(",");
        while (headings[0] == "" || headings.length > 5) {
            System.out.println("Please enter 1-5 comma-separated headings.");
            headings = scanner.nextLine().split(",");
        }

        // Trim whitespace from headings
        for (int i = 0; i < headings.length; i++) {
            headings[i] = headings[i].trim();
        }


        // Create an empty singly linked list to store objects 
        LinkedListwHHLSort<HipHopLyrics> HHlinkedList = new LinkedListwHHLSort<HipHopLyrics>();
        
        //Create a empty sorted linked list
        LinkedListwHHLSort<HipHopLyrics> sortedHHlinkedList = new LinkedListwHHLSort<HipHopLyrics>();
        
        //Create a Chained Hash Table starting with size 3
        ChainedHashTable<HipHopLyrics> HHLhashTable = new ChainedHashTable<>(3);
        
        //Create a HipHopLyricsBinarySearchTree
        HipHopLyricsBinarySearchTree HHLBST = new HipHopLyricsBinarySearchTree();


        /**
         * Read in lines through a buffer from a csv file until there are no more
         * Split each value with the comma delim and parse them in a HipHopLyric object
         * Add each HHL object each data structure type
         */
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            boolean skipHeader = true; // Skip the header row

            while ((line = br.readLine()) != null) {
                // Skip the header row
                if (skipHeader) {
                    skipHeader = false;
                    continue;
                }

                // Split the CSV line into fields, considering potential variations
                String[] parts = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

                // Ensure the line has at least the minimum required number of fields
                if (parts.length >= 9) {
                    String id = parts[0].trim();
                    String candidate = parts[1].trim();
                    String song = parts[2].trim();
                    String artist = parts[3].trim();
                    String sentiment = parts[4].trim();
                    String theme = parts[5].trim();
                    String albumReleaseDate = parts[6].trim();
                    String lyricsLine = parts[7].trim();
                    String url = parts[8].trim();

                    // Create a HipHopLyrics object
                    HipHopLyrics lyrics = new HipHopLyrics(id, candidate, song, artist, sentiment,
                            theme, albumReleaseDate, lyricsLine, url);

                    // Add the object to the linked list... both unsorted and sorted
                    HHlinkedList.insert(lyrics);
                    sortedHHlinkedList.insertSorted(lyrics, headings); 
                    
                    // Insert the object into the hash table
                    HHLhashTable.insert(lyrics, headings);
                    
                    //Insert the object into BST
                    HHLBST.insert(lyrics, headings);
                } 
                
                else {
                    System.err.println("Skipping invalid line: " + line);
                }
            } // end of while loop that reads the lines insert into the data structures

            // Print the size of the linked lists
            System.out.println("Total objects inserted in the linked list: " + HHlinkedList.getSize());
            System.out.println("Total objects inserted in the sorted linked list: " + sortedHHlinkedList.getSize());
            System.out.println("Total objects inserted in the Hash Table: " + HHLhashTable.getTotalElements());
            System.out.println("Total objects inserted in the Binary Search Tree: " + HHLBST.getSize());

             
           
           /**
            * Uncomment These To See The Sorted List and Hash Table */
//           System.out.println();
//           sortedHHlinkedList.printSortedList(); System.out.println();
//           HHLhashTable.printHT();
//           System.out.println("\nArray Style Printout of The Binary Search Tree using the HHL IDS (check original list for the line):"); 
//           HHLBST.printLevelOrder(); // Really only readable with Less than 30 entries to the 
//         HHLBST.printTree();
      
           
           
        } // end of File Reader and List Inserter Loop.
        
        catch (IOException e) {
            e.printStackTrace();
            System.out.println("\nMeaning the file wasn't found... check the file string name inputted");
            System.exit( 0 );
        }
        
       System.out.println("\n"); 
       
       
       boolean terminate = false; //user control terminator
       
       /**
        * Beginning of program that search for values specified by user
        * User Loop-Controlled
        */
       while (!terminate) {
           
           // Allows the user to set the value(s) used for the search based on the headings
           System.out.println("Enter values for each heading:");
           String[] values = new String[headings.length];
           for (int i = 0; i < headings.length; i++) {
               System.out.print(headings[i] + ": ");
               values[i] = scanner.nextLine().trim();
           }
           
           
           /**
            * UNSORTED LIST SEARCH
            */
           
           // Initialize variables for comparisons in the unsorted list
           int unsortedCVal = -1;
           int lineNumUSL = 0;
           
           // Search for a line that matches all provided values in the unsorted list
           for (Node<HipHopLyrics> current = HHlinkedList.getfront(); current != null ; current = current.getNext()) {
               HipHopLyrics hhLine = current.getData();
               unsortedCVal= matchesValuesSortedList(hhLine, headings, values);
               unsortedListComparisons += (1 * headings.length);               
               // If a match is found, print the line details and the number of comparisons
               if (unsortedCVal == 0) {
                   System.out.print("\nThe first match in the Unsorted List was found @ Unsorted Line " + (lineNumUSL+1) + "\n");
                   System.out.println(hhLine.toString()); 
                   System.out.println("Amount of Unsorted List Comparisons: " + unsortedListComparisons );
                   break;
               }
               lineNumUSL++;
           } //end of loop that searches through the Unsorted list
           
           // If no match is found in the unsorted list, provide appropriate feedback
           if(unsortedCVal != 0) {
               System.out.print("\nNo match in the unsorted list... ");
               System.out.println("Make sure the headings and the value for it match those of the CSV file.");
               System.out.println("Amount of UnsortedListComparisons: " + unsortedListComparisons );
               System.out.println();
           }
           
           
           /**
            * SORTED LIST SEARCH
            */
           
           
           // Initialize variables for comparisons in the sorted list
           int sortedCVal = -1;
           int lineNumSL = 0;
           
           for (Node<HipHopLyrics> current = sortedHHlinkedList.getfront(); current != null ; current = current.getNext()) {
               HipHopLyrics hhLine = current.getData();
               sortedCVal = matchesValuesSortedList(hhLine, headings, values);
               sortedListComparisons += (1 * headings.length);
               
               // If the search reaches a larger line in the sorted list, discontinue the search and provide feedback
               if(sortedCVal < 0) {
                   System.out.println("\nThe search reached a larger line @ Line " + (lineNumSL + 1) + " with a header value being " + sortedCVal + " lesser " +
                                      "...\nSearch Discontinued since the list is Sorted");
                   sortedListComparisons -= stoppedShort;
                   System.out.println("Amount of Sorted List Comparisons: " + sortedListComparisons);
                   break;
               }
               // If a match is found in the sorted list, print the line details and the number of comparisons
               else if (sortedCVal == 0) {
                   System.out.print("\nThe first match in the Sorted List was found @ ");
                   System.out.println("Sorted Line: " + (lineNumSL+1) + "\n" + hhLine.toString());
                   System.out.println("Amount of Sorted List Comparisons: " + sortedListComparisons);
                   break;
               }
               
               // If no match is found, continue the search
               else {
            	   lineNumSL++;
                   continue;
               }
           
           } //end of loop that searches through sorted list
           
           // If no match is found in the sorted list, provide appropriate feedback
           if (sortedCVal > 0) {
               System.out.print("No match in the sorted list ... ");
               System.out.println("Make sure the headings and the value for it match those of the CSV file.");
               System.out.println("Amount of sortedListComparisons: " + sortedListComparisons );
               System.out.println();
           }
           
	        
	
           /**
            * HASH TABLE SEARCH
            */
           
	        // Initialize variables for comparisons in the hash table
	        int hashTableSearchComparisons = 0;
	
	        // Search for a line that matches all provided values in the hash table
	        HipHopLyrics SearchValue = HHLhashTable.search(headings, values);
	        hashTableSearchComparisons = HHLhashTable.getHTcomparisons() * headings.length;
	
            // If a match is found, print the line details and the number of comparisons
            if (SearchValue != null) {
                System.out.println(SearchValue);
                System.out.println("Amount of Hash Table Search Comparisons: " + hashTableSearchComparisons);
            }
	 
	
	        // If no match is found in the hash table, provide appropriate feedback
	        if (SearchValue == null) {
	            System.out.print("\nNo match in the hash table... ");
	            System.out.println("Make sure the headings and the value for it match those of the CSV file.");
	            System.out.println("Amount of Hash Table Search Comparisons: " + hashTableSearchComparisons);
	            System.out.println();
	        }
	        
	        
	        /**
	         * BINARY SEARCH TREE SEARCH
	         */
	
	        // Search for a line that matches all provided values in the hash table
	        HipHopLyrics BSTsearchResult = HHLBST.search(headings, values);
	      
	        
            // If a match is found, print the line details and the number of comparisons
            if (BSTsearchResult != null) {
                System.out.println(BSTsearchResult);
                System.out.println("Amount of Binary Search Tree Search Comparisons: " + HHLBST.BSTsearchComparisons);
            }
	 
	
	        // If no match is found in the hash table, provide appropriate feedback
	        if (BSTsearchResult == null) {
	            System.out.print("\nNo match in the Binary Search Tree... ");
	            System.out.println("Make sure the headings and the value for it match those of the CSV file.");
                System.out.println("Amount of Binary Search Tree Search Comparisons: " + HHLBST.BSTsearchComparisons);
	            System.out.println();
	        }
	     

	        /*
	         * END OF SEARCHES NOW TO OUTPUT
	         */

           // Update and Print the total comparisons for the unsorted and sorted lists So Far within the loop
           totalUnsortedListComparisons += unsortedListComparisons;
           System.out.println("\nTotal Unsorted List Comparisons throughout Program So Far: " + totalUnsortedListComparisons );
           unsortedListComparisons = 0;
           
           totalSortedListComparisons += sortedListComparisons;
           System.out.println("Total Sorted List Comparisons throughout Program Excluding Construction of Sorted List So far: " + totalSortedListComparisons);
           sortedListComparisons = 0;
       
           // Update and Print the total comparisons for the hash table So Far before program ends
           totalHashTableComparisons += hashTableSearchComparisons;
           System.out.println("Total Hash Table Search Comparisons throughout Program So Far: " + totalHashTableComparisons);
	       HHLhashTable.setHTcomparisons(0);

	       // Update and Print the total comparisons for the Binary Search Tree So Far before program ends
           totalBSTcomparisons += HHLBST.BSTsearchComparisons;
           System.out.println("Total Binary Search Tree Comparisons throughout Program So Far Excluding Construnction of the Tree: " + totalBSTcomparisons);

           
           // Ask if the program should terminate
           System.out.print("\nDo you want to terminate? (yes/no): ");
           String response = scanner.nextLine();
           System.out.println();
           if ("yes".equalsIgnoreCase(response)) {
        	   terminate = true;
        	   
        	   System.out.println();
               
               System.out.println("Total Unsorted List Comparisons throughout Program: " + totalUnsortedListComparisons);
               System.out.println("Total Sorted List Comparisons throughout Program EXCLUDING Construction of Sorted List: " + totalSortedListComparisons);
               System.out.println("Total Sorted List Comparisons throughout Program INCLUDING Constuction of Sorted List: " + 
            		   			 (totalSortedListComparisons + sortedHHlinkedList.creationSortedListNumComparisons) );
              
               System.out.println("Total Hash Table Comparisons throughout Program: " + totalHashTableComparisons);
               
               System.out.println("Total Binary Search Tree Comparisons throughout Program EXCLUDING Construction of BST: " + totalBSTcomparisons);
               System.out.println("Total Sorted List Comparisons throughout Program INCLUDING Constuction of BST: " + 
            		   			 (totalBSTcomparisons + HHLBST.creationBSTNumComparisons) );

               System.out.println();
               
        	   System.out.println("Thank you for using my program.");
           }
           
           
       }// end of Program Loop
       
       scanner.close();
	}//end of Main.

       
	 /**
     * Compares the provided values with the corresponding fields of the given HipHopLyrics object.
     * 
     * @param line the HipHopLyrics object to compare
     * @param headings an array of headings to compare the values with
     * @param values an array of values to compare with the corresponding fields
     * @return the match result or other comparison indicators
     */
   private static int matchesValuesSortedList(HipHopLyrics line, String[] headings, String[] values) {
	   
	   int match = 0;
	   int temp = 0;
	   
       // Check if the length of headings is equal to the length of values
       if (headings.length != values.length) {
           return -1; // Indicator for mismatch in lengths
       }

       // Iterate over each heading and corresponding value
       for (int i = 0; i < headings.length; i++) {
           String heading = headings[i];
           String value = values[i];
           

           // Compare the value with the corresponding field of the HipHopLyrics object
           if ("candidate".equalsIgnoreCase(heading) ) {
        	   temp = value.compareTo(line.getCandidate());
           } 
           else if ("song".equalsIgnoreCase(heading)) {
        	   temp = value.compareTo(line.getSong());
           } 
           else if ("artist".equalsIgnoreCase(heading)) {
        	   temp = value.compareTo(line.getArtist());
           } 
           else if ("sentiment".equalsIgnoreCase(heading)) {
        	   temp = value.compareTo(line.getSentiment());
           } 
           else if ("theme".equalsIgnoreCase(heading)) {
        	   temp = value.compareTo(line.getTheme());
           } 
           else if ("album_release_date".equalsIgnoreCase(heading)) {
        	   temp = value.compareTo(line.getAlbumReleaseDate());
           }
           else if ("line".equalsIgnoreCase(heading)) {
        	   temp = value.compareTo(line.getLine());
           } 
           else if ("url".equalsIgnoreCase(heading)) {
        	   temp = value.compareTo(line.getUrl());
           }
           
           // Update the match based on the comparison result
           if(temp == 0) match += temp; // If the comparison result is 0, it is a match
           if(temp > 0) return temp; // If the comparison result is positive, it stopped short during the search
           if(temp < 0) { stoppedShort = i; return temp;} // Indicates where the search stopped short in the sorted list
       }   
     
       return match;
       
   } //end of matchesValuesSortedList
   
}// end of Project 2 Class
