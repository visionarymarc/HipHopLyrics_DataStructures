package csc230project4;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Node Helper class for Binary Search Tree.
 */
class BSTNode {
    HipHopLyrics data;
    BSTNode left;
    BSTNode right;

    public BSTNode(HipHopLyrics data) {
        this.data = data;
        this.left = null;
        this.right = null;
    }
}

/**
 * Binary Search Tree class defines a search tree tailored for the HipHopLyrics class.
 * Uses binary nodes for implementation.
 * Methods to insert and search HHL objects in the tree with the number of comparisons marked
 * 
 * @author Marcus V
 */
public class HipHopLyricsBinarySearchTree {
    private BSTNode root;
    private int size;

    public HipHopLyricsBinarySearchTree() {
        this.root = null;
        this.size = 0;
    }
    
    /**
     * Returns the size of the binary search tree.
     *
     * @return the size of the tree
     */
    public int getSize() {
        return size;
    }

    /**
     * Inserts a new HipHopLyrics object into the binary search tree.
     * @param lyrics the HipHopLyrics object to be inserted
     * @param headings the array of headings to use for comparison
     */
    public void insert(HipHopLyrics lyrics, String[] headings) {
        root = insertRecursive(root, lyrics, headings);
        size++;
    }

    /**
     * Recursive helper method to insert a new HipHopLyrics object into the binary search tree.
     * @param root the root of the current subtree
     * @param lyrics the HipHopLyrics object to be inserted
     * @param headings the array of headings to use for comparison
     * @return the root of the modified subtree
     */
    private BSTNode insertRecursive(BSTNode root, HipHopLyrics lyrics, String[] headings) {
        if (root == null) {
            return new BSTNode(lyrics);
        }

        int compareResult = compareData(root.data, lyrics, headings);

        if (compareResult >= 0) {
            root.right = insertRecursive(root.right, lyrics, headings);  // Insert on the right subtree
        } else if (compareResult < 0) {
            root.left = insertRecursive(root.left, lyrics, headings);  // Insert on the left subtree
        }

        return root;
    }

    
    
	public int creationBSTNumComparisons = 0; /** Tracks the total number of comparisons made creating the sorted list.  */

    /**
     * Compares the provided HipHopLyrics objects based on the specified headings.
     * @param nodeData the data stored in the current node
     * @param lyrics the HipHopLyrics object to compare
     * @param headings the array of headings to use for comparison
     * @return the comparison result
     */
    private int compareData(HipHopLyrics nodeData, HipHopLyrics lyrics, String[] headings) {
        for (String heading : headings) {
        	
            String nodeValue = getFieldValue(nodeData, heading);
            String lyricsValue = getFieldValue(lyrics, heading);

            int compareResult = lyricsValue.compareTo(nodeValue); creationBSTNumComparisons++;
            if (compareResult != 0) {
                return compareResult;
            }
        }
        return 0; // Indicate a match if all fields are equal
    }

    /**
     * Retrieves the value of the specified field in the HipHopLyrics object.
     * @param lyrics the HipHopLyrics object to retrieve the field value from
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
    
    
    public int BSTsearchComparisons = 0; /** amount of comparisons made when searching the tree */
    
    /**
     * Compares the provided values with the corresponding fields of the given HipHopLyrics object.
     * @param lyrics the HipHopLyrics object to compare
     * @param headings an array of headings to compare the values with
     * @param values an array of values to compare with the corresponding fields
     * @return the match result or other comparison indicators
     */
    public int matchesValues(HipHopLyrics lyrics, String[] headings, String[] values) {
        // Check if the length of headings is equal to the length of values
        if (headings.length != values.length) {
            return -1; // Indicator for mismatch in lengths
        }

        // Iterate over each heading and corresponding value
        for (int i = 0; i < headings.length; i++) {
            String heading = headings[i];
            String value = values[i];

            // Retrieve the field value from the HipHopLyrics object
            String fieldValue = getFieldValue(lyrics, heading);

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
     * Search for an element in the binary search tree based on specified headings and values.
     * @param headings an array of headings to use for searching
     * @param values an array of values to search for
     * @return the found element or null if not found
     */
    public HipHopLyrics search(String[] headings, String[] values) {
        return searchRecursive(root, headings, values);
    }

    
    
    private int level = 1; // just a helper variable to show what tree level the search stopped at
    					   // also logically helps calculate the amount of comparisons made
    
 
    
    /**
     * Recursive helper method to search for an element in the binary search tree.
     * @param root the root of the current subtree
     * @param headings an array of headings to use for searching
     * @param values an array of values to search for
     * @return the found element or null if not found
     */
    private HipHopLyrics searchRecursive(BSTNode root, String[] headings, String[] values) {
        if (root == null) {
            return null; // Element not found
        }
        
        
        int compareResult = matchesValues(root.data, headings, values); 

        if (compareResult == 0) {
        	System.out.println("\nThe first match in the Binary Search Tree was found at tree level " + level) ;
        	
        	BSTsearchComparisons = level * headings.length; // Amount of BST search comparisons is 
        													// the amount of levels it traversed times the amount of user heading values compared
        	level = 1;
            return root.data; // Match found
        } 
        
        else if (compareResult < 0) {
            // Search in the left subtree
        	level++;
            return searchRecursive(root.left, headings, values);
        } 
        
        else {
            // Search in the right subtree
        	level++;
            return searchRecursive(root.right, headings, values);
        }
    }



    /**
     * Prints the binary search tree using in-order traversal (Same print as Sorted List).
     */
    public void printTree() {
        System.out.println("Binary Search Tree:");
        printTreeRecursive(root);
        System.out.println();
    }

    /**
     * Recursive helper method to print the binary search tree in-order.
     * @param root the root of the current subtree
     */
    private void printTreeRecursive(BSTNode root) {
        if (root != null) {
            printTreeRecursive(root.left);
            System.out.println("\n" + root.data); 
            printTreeRecursive(root.right);
        }
    }
    

    /**
     * Prints the level order traversal (Array Version) of the binary search tree.
     */
    public void printLevelOrder() {
        if (root == null) {
            System.out.println("Tree is empty.");
            return;
        }

        Queue<BSTNode> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            BSTNode current = queue.poll();
            System.out.print(current.data.getId() + " ");

            if (current.left != null) {
                queue.add(current.left);
            }

            if (current.right != null) {
                queue.add(current.right);
            }
        }

        System.out.println();
    }


}// end of HipHopLyricsBinarySearchTree
