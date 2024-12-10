package csc230project4;
import csc230project4.LinkedListwHHLSort.Node;


/**
 * ChainedHashTable class defines a hash table implemented with chaining.
 * It uses a linked list to handle collisions and supports operations like
 * insertion, resizing, and searching based on specified headings.
 *
 * @param <T> generic type extending Comparable to filter elements
 * 
 * @author Marcus V.
 */
public class ChainedHashTable<T extends Comparable<T>> {
    private LinkedListwHHLSort<T>[] table; // Implementation via array of Linked List
    private int size; // Number of buckets
    private int HTcomparisons = 0; // number of comparisons made every time the table is searched

    /**
     * Creates a Hash table using a specified user size
     * 
     * @param size the number of buckets to initialize the table
     */
    @SuppressWarnings("unchecked")
	public ChainedHashTable(int size) {
        this.size = size;
        table = new LinkedListwHHLSort[size];
        for (int i = 0; i < size; i++) {
            table[i] = new LinkedListwHHLSort<>();
        }
    }// end Constructor
    
    /**
     * Retrieves the array representing the hash table.
     *
     * @return The array representing the hash table.
     */
    public LinkedListwHHLSort<T>[] getTable() {
        return table;
    }

    /**
     * Retrieves the size of the hash table.
     *
     * @return The size of the hash table.
     */
    public int getSize() {
        return size;
    }
    
    /**
     * Retrieves the comparisons of the hash table.
     *
     * @return The comparisons of the hash table.
     */
    public int getHTcomparisons() {
        return HTcomparisons;
    }
    
    /**
     * Sets the number of comparisons made during hash table searches.
     *
     * @param resetNum the value to set for resetting comparisons
     */
    public void setHTcomparisons(int resetNum) {
        HTcomparisons = resetNum;
    }


    /**
     * Hash function to calculate the index for a given HipHopLyrics line based on specified headings.
     *
     * @param headings an array of headings to use for hashing
     * @param line the element for which to calculate the hash
     * 
     * @return the calculated index in the hash table
     */
    private int hash(String[] headings, HipHopLyrics line) {
        int hashCode = 0;
        /*
         * Adds the ASCII values of each char in each string under each heading the user select 
         */
        for (String heading : headings) {
            for (char c : getFieldValue(line, heading).toCharArray()) {
                hashCode += Math.abs((int) c);
            }
        } 
        
        // Same way but using Strings hash code function which you said not to do but left just in case
        
//        for (String heading : headings) {
//        	String value = getFieldValue(line, heading);
//        	hashCode += value.hashCode();
//        }
        
        // System.out.println(Math.abs(hashCode) % size);
        
        return Math.abs(hashCode) % size;
    } // end of hash()

    /**
     * Inserts an element into the hash table.
     *
     * @param element the element to be inserted
     * @param headings an array of headings to use for hashing
     */
    public void insert(T element, String[] headings) {
        // Check if resizing is needed before inserting
        checkForResize(headings);

        int index = hash(headings, (HipHopLyrics) element);
        table[index].insert(element);
    }

    /**
     * Gets the amount of elements in the hash table 
     * 
     * @return total elements in the list
     */
    public int getTotalElements() {
    	  int totalElements = 0;
          for (LinkedListwHHLSort<T> list : table) {
              totalElements += list.getSize();
          }
          
          return totalElements;
    }
    
    /**
     * Resize the table and rehash if necessary.
     *
     * @param headings an array of headings to use for hashing
     */
    private void checkForResize(String[] headings) {
        int totalElements = getTotalElements();
        
        if (totalElements >= size) {
            // Resize the table
            size = size * 2;
            @SuppressWarnings("unchecked")
			LinkedListwHHLSort<T>[] newTable = new LinkedListwHHLSort[size];
            //Create a new Linked List in each bucket
            for (int i = 0; i < size; i++) {
                newTable[i] = new LinkedListwHHLSort<>();
            }
            
            // Rehash and insert elements into the new table
            for (LinkedListwHHLSort<T> list : table) {
                Node<T> current = list.getfront();
                while (current != null) {
                    // Calculate hash for each element in the list
                    int newIndex = hash(headings, (HipHopLyrics) current.getData());                
                    newTable[newIndex].insert(current.getData());
                    current = current.getNext();
                }
            }

            // Update the table and size
            table = newTable;
           
        }
    } // end of resize method


    /**
     * Search for an element in the hash table 
     * based on specified headings and correspond values specified with a project3 application.
     *
     * @param headings an array of headings to use for searching
     * @param values an array of values to search for
     * 
     * @return the found element or null if not found
     */
    public T search(String[] headings, String[] values) {
        int hashCode = 0;
        
        //hash values to find the bucket to look in
        for(String value: values) {
	        for (char c : value.toCharArray()) hashCode += (int) c;
        }
        int index = hashCode % size;
        
        
        System.out.println("\nThe hash bucket to search in: " + index + "\nAmount of HH objects in it: " + table[index].getSize());
        int chainNum = 1;
        
        //If the bucket is empty there's no more reason to search
        if(table[index].getSize() == 0) {
        	System.out.println("There are no elements in this hash. No comparisons necessary\n");
        	return null;
        }
        

        else {
        	//loop through the list in that bucket and find the element that matches
	    	for(Node<T> current = table[index].getfront(); current != null; current = current.getNext()) {
	    		 	
	    		HTcomparisons++;
		        if (matchesValues(current.getData(), headings, values) == 0) {
		        	System.out.println("The first match was found in the Hash Table in Hash " + index + "; Chain Number " + chainNum);
		            return current.getData();
		        }
		        
		        chainNum++;
	    	}
	    }

        return null; // Not found
        
    }// end of search method

    
  	 /**
       * Compares the provided values with the corresponding fields of the given HipHopLyrics object.
       * 
       * @param line the HipHopLyrics object to compare
       * @param headings an array of headings to compare the values with
       * @param values an array of values to compare with the corresponding fields
       * @return the match result or other comparison indicators
       */
    public int matchesValues(T line, String[] headings, String[] values) {
        // Check if the length of headings is equal to the length of values
        if (headings.length != values.length) {
            return -1; // Indicator for mismatch in lengths
        }

        // Cast the object to HipHopLyrics (assuming T is always a HipHopLyrics)
        HipHopLyrics hipHopLine = (HipHopLyrics) line;

        // Iterate over each heading and corresponding value
        for (int i = 0; i < headings.length; i++) {
            String heading = headings[i];
            String value = values[i];

            // Retrieve the field value from the HipHopLyrics object
            String fieldValue = getFieldValue(hipHopLine, heading);

            // Compare the value with the corresponding field of the object
            int compareResult = value.compareTo(fieldValue);
            

            // Update the match based on the comparison result
            if (compareResult != 0) {
                return compareResult;
            }
        }

        return 0; // Indicate a match if all fields are equal
    }

    /**
     * Retrieves the value of the specified field in the HipHopLyrics object.
     *
     * @param lyrics  the HipHopLyrics object to retrieve the field value from
     * @param heading the heading of the field to be retrieved
     * @return the value of the specified field in the HipHopLyrics object
     */
    private String getFieldValue(HipHopLyrics lyrics, String heading) {
        switch (heading.toLowerCase()) {
            case "candidate":
                return lyrics.getCandidate();
            case "song":
                return lyrics.getSong();
            case "artist":
                return lyrics.getArtist();
            case "sentiment":
                return lyrics.getSentiment();
            case "theme":
                return lyrics.getTheme();
            case "album_release_date":
                return lyrics.getAlbumReleaseDate();
            case "line":
                return lyrics.getLine();
            case "url":
                return lyrics.getUrl();
            default:
                return "";
        }
    }
    
    
    /**
     * Prints the Hash Table in to display all the Hash buckets
     * and the HHL lines (denoted by their ID) in each of them.
     */
    public void printHT() {
    	int i = 0;
    	for (LinkedListwHHLSort<T> list : table) {
		 	System.out.println("Table Hash Bucket " + (i++) + ": ");
		 	
    		for (Node<T> current = list.getfront(); current != null; current = current.getNext()) {
    			 System.out.println("HHL Line #" + ( (HipHopLyrics) current.getData() ).getId());
    		}
    		
    		System.out.println("\n\n");
    	}
    }

}// end of ChainedHashTable
