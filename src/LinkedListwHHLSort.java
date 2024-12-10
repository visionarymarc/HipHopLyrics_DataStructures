package csc230project4;

/**
 * Defines a List implemented with Nodes... 
 * A LinkedList represents a linear data structure that consists of a sequence of nodes, 
 * where each node stores a reference to an element and a reference to the next node in the sequence. 
 * The nodes are linked together by these references, forming a chain of nodes.
 * 
 * 
 * 
 * <br>
 * This Linked List is tailored to sort a List of HipHopLyrics Object with methods to do so.
 * Methods to get the front node, check if its empty, insert and store data, get the size, and represent by string are available
 * 
 * @author M. Valbrun, F.Graham
 *
 * @param <T> generic type to filter list with
 */
public class LinkedListwHHLSort<T extends Comparable<T>>{
	
	/**
	 * Defines a Node... A node is a fundamental building block of a linked list. 
	 * It typically consists of two fields: one for storing the element contained in the node, 
	 * and one for storing a reference to the next node in the linked list.
	 * 
	 * <br>
	 * Methods to get the data and reference to the next node are available
	 * 
	 * @author M. Valbrun, F.Graham
	 *
	 * @param <E> generic type to filter the Node with
	 */
	public static class Node<E> {
		private E data;
		private Node<E> next;

		/**
		 * Default Constructor
		 * Both data and next is set to null
		 */
		public Node() {
			data = null;
			next = null;
		}

		/**
		 * Parameterized Constructor which creates a node with specified user data
		 * @param d specified user data to create the node with
		 */
		public Node(E d) {
			data = d;
			next = null;
		}

		/**
		 * Parameterized Constructor which creates a node with specified user data and next
		 * @param d specified user data to create the node with
		 * @param n specified user next to create the node with
		 */
		public Node(E d, Node<E> n) {
			data = d;
			next = n;
		}

		/**
		 * Accessor to get the reference to the next node
		 * @return next
		 */
		public Node<E> getNext() {
			return next;
		}

		/**
		 * Sets next to a specified user reference to next Node
		 * @param n the specified user next
		 */
		public void setNext(Node<E> n) {
			next = n;
		}

		/**
		 * Accessor to get the data stored in a node
		 * @return data
		 */
		public E getData() {
			return data;
		}

		/**
		 * Sets a nodes data stored to a user-specified piece of data
		 * @param data the user specified piece of data to be set
		 */
		public void setData(E data) {
			this.data = data;
		}
	}
	protected Node<T> head = new Node<T>(); // dummy Node
	protected Node<T> rear = new Node<T>(); // dummy Rear Node

	
	protected int numItems;
		
		/**
		 * Accessor to get the front Node
		 * @return head
		 */
		public Node<T> getfront(){
			return head.getNext();
		}
		
		/**
		 * Accessor to get the rear Node
		 * @return head
		 */
		public Node<T> getRear(){
			return rear.getNext();
		}
		
		
		/**
		 * Accessor to get the size of the list
		 * @return the number of items in the list numItems
		 */
		public int getSize(){
			return numItems;
		}
		
		/**
		 * Checks to see if the list is empty
		 * @return the boolean result of the numItems equaling 0
		 */
		public boolean isEmpty(){
			return numItems == 0;
		}
		
		/**
		 * Inserts an item in the list
		 * @param insertItem the item to be inserted
		 */
		public void insert(T insertItem) {
			if(insertItem == null)
				throw new NullPointerException();
			
			Node<T> item = new Node<T>(insertItem);
			
			if (head.next == null) {
				head.next = item;
				rear.next = item;
			}
			else {
				rear.next.next = item;
				rear.next = item;
			}
			++numItems;
		}
		
		
		
		/**
		 * Inserts the data in a sorted manner based on the specified headings.
		 *
		 * @param data the data to be inserted
		 * @param headings the array of headings to be used for sorting
		 */
		public void insertSorted(HipHopLyrics data, String[] headings) {
	        Node<T> current = head.next;
	        Node<T> prev = null;
	        while (current != null) {
	            if (compareData((HipHopLyrics) current.getData(), data, headings) > 0) {
	                break;
	            }
	            prev = current;
	            current = current.getNext();
	        }
	        
	        @SuppressWarnings("unchecked")
			Node<T> newNode = new Node<T>((T) data, current);
	        if (prev == null) {
	            head.next = (Node<T>) newNode;
	        } else {
	            prev.setNext(newNode);
	        }
	        if (current == null) {
	            rear.next = (Node<T>) newNode;
	        }
	        numItems++;
	    }
		
		/**
		 * Retrieves the value of the specified field in the HipHopLyrics object.
		 *
		 * @param lyrics the HipHopLyrics object to retrieve the field value from
		 * @param heading the heading of the field to be retrieved
		 * @return the value of the specified field in the HipHopLyrics object
		 */
		public String getFieldValue(HipHopLyrics lyrics, String heading) {
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
		 * Tracks the total number of comparisons made creating the sorted list.
		 */
		public int creationSortedListNumComparisons = 0;

		
		/**
		 * Compares two HipHopLyrics objects based on the specified headings.
		 *
		 * @param obj1 the first HipHopLyrics object for comparison
		 * @param obj2 the second HipHopLyrics object for comparison
		 * @param headings the array of headings to be used for comparison
		 * @return the comparison result based on the specified headings
		 */
	    public int compareData(HipHopLyrics obj1, HipHopLyrics obj2, String[] headings) {
	        for (String heading : headings) {	        	
	        	creationSortedListNumComparisons++;
	            int compareResult = getFieldValue(obj1, heading).compareTo(getFieldValue(obj2, heading));
	            if (compareResult != 0) {
	                return compareResult;
	            }
	        }
	        return 0;
	    }
		

		/**
		 * Prints the sorted list.
		 */
		public void printSortedList() {
			System.out.println("Sorted List:\n");
			Node<T> trav = head.next;
			int numSortedLine = 1;
			while(trav != null) {
				System.out.println("Sorted Line " + numSortedLine++);
				System.out.println(trav.data + "\n");
				trav = trav.next;
			}
		}
		
}// end of LinkedListwHHLSort 
