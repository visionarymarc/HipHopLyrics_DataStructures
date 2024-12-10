# Hip-Hop Lyrics Data Structure Analysis

This project is a Java-based application that processes a CSV file of hip-hop lyrics mentioning 2016 presidential candidates. It implements and compares various data structures (Linked List, Hash Table, Binary Search Tree) for data storage and search efficiency. 

The program highlights the trade-offs between these data structures in terms of insertion, sorting, and search performance.

---

## **Features**
- Reads and processes the `genius_hip_hop_lyrics.csv` file.
- Supports multiple data structures:
  - **Unsorted Linked List**
  - **Sorted Linked List**
  - **Chained Hash Table**
  - **Binary Search Tree**
- Allows the user to:
  - Select up to five headings to sort or search by.
  - Insert data into all data structures simultaneously.
  - Search for specific lines of lyrics using selected headings and values.
  - Compare search performance across all data structures.

---

## **Project Highlights**
- **Data Structures Implemented**:
  - **Linked List**:
    - Supports both sorted and unsorted data insertion.
    - Custom sorting functionality for the `HipHopLyrics` class.
  - **Chained Hash Table**:
    - Handles collisions using linked lists.
    - Dynamically resizes based on load factor.
  - **Binary Search Tree**:
    - Implements in-order traversal for sorted output.
    - Supports efficient searching based on user-defined headings.

- **Performance Analysis**:
  - Tracks and outputs the number of comparisons for each search operation in all data structures.

---

## **Project Files**
  Project4.java: Main application logic.
  HipHopLyrics.java: Class to represent and store data for each lyrics line.
  LinkedListwHHLSort.java: Custom implementation of a linked list with sorting capabilities.
  ChainedHashTable.java: Hash table implementation with chaining for collision handling.
  HipHopLyricsBinarySearchTree.java: Binary search tree implementation for lyrics data.
  genius_hip_hop_lyrics.csv: Sample data file for testing.


## **How to Run**
  1. Clone the repository:
     ```bash
     git clone https://github.com/yourusername/HipHopLyrics_DataStructures.git
     cd HipHopLyrics_DataStructures
  2. Use Eclipse or Java IDE (Recommendation)

