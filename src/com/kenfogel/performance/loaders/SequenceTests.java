package com.kenfogel.performance.loaders;

import java.util.*;

import com.butlerpress.dict.Dictionary;
import com.kenfogel.performance.models.SequenceSpeedTableModel;

/**
 * Performs a set of tests to determine the Big-O performance of an array, array
 * list, array deque, and linked list. Does multiple REPETITIONS on data
 * structures of a given SIZE. Displays the results in a table
 *
 * Uses Dictionary.jar Copyright (c) 2004-2007 Scott Willson that generates
 * random words from a set of words derived from a public source dictionary. See
 * Dictionary.jar for more information
 *
 * Corrected error where I was using the copy constructor of the ArrayList.
 * Needed to be replaced with a for loop. Updated to use Java 1.8 syntax
 *
 * @author Ken Fogel
 * @version 4.1
 *
 */
public class SequenceTests {

    private final static int REPETITIONS = 1000;
    private final static int SIZE = 1000;
    private String[] dataArray = null;
    private String string = null;

    private SequenceSpeedTableModel sequenceSpeedTableModel = null;

    /**
     * Constructor Receives reference to the table model that will hold the
     * results
     *
     * @param sequenceSpeedTableModel
     */
    public SequenceTests(SequenceSpeedTableModel sequenceSpeedTableModel) {
        this.sequenceSpeedTableModel = sequenceSpeedTableModel;
        loadArray();
    }

    /**
     * Load words from the dictionary into a set to eliminate duplication and
     * then into an array
     */
    private void loadArray() {
        // Load Array
        dataArray = new String[SIZE];
        HashSet<String> dataSet = new HashSet<>();
        for (int x = 0; x < SIZE; ++x) {
            do { // This loop will repeat if the random word in not unique
                string = Dictionary.getRandomWordTermCommonNameOrConnector();
                dataArray[x] = string;
            } while (!dataSet.add(string));
        }
    }

    /**
     * Perform tests on an array
     */
    public void doArrayTests() {

        String[] array = null;
        long startTime, endTime;
        long runningTotal;

        // Load Array
        runningTotal = 0;
        for (int y = 0; y < REPETITIONS; ++y) {
            array = new String[SIZE];
            startTime = System.nanoTime();
            for (int x = 0; x < SIZE; ++x) {
                array[x] = dataArray[x];
            }
            endTime = System.nanoTime() - startTime;
            runningTotal += endTime;
        }
        sequenceSpeedTableModel.setValueAt(runningTotal / REPETITIONS, 0, 1);

        // Test access first element
        runningTotal = 0;
        for (int x = 0; x < REPETITIONS; ++x) {
            startTime = System.nanoTime();
            string = array[0];
            endTime = System.nanoTime() - startTime;
            runningTotal += endTime;
        }
        sequenceSpeedTableModel.setValueAt(runningTotal / REPETITIONS, 1, 1);

        // Test access last element
        runningTotal = 0;
        for (int x = 0; x < REPETITIONS; ++x) {
            startTime = System.nanoTime();
            string = array[SIZE - 1];
            endTime = System.nanoTime() - startTime;
            runningTotal += endTime;
        }
        sequenceSpeedTableModel.setValueAt(runningTotal / REPETITIONS, 2, 1);

        // Test access middle element
        runningTotal = 0;
        for (int x = 0; x < REPETITIONS; ++x) {
            startTime = System.nanoTime();
            string = array[SIZE / 2];
            endTime = System.nanoTime() - startTime;
            runningTotal += endTime;
        }
        sequenceSpeedTableModel.setValueAt(runningTotal / REPETITIONS, 3, 1);

        // Does not support insert at start
        sequenceSpeedTableModel.setValueAt(-1L, 4, 1);
        // Does not support insert at end
        sequenceSpeedTableModel.setValueAt(-1L, 5, 1);
        // Does not support insert at middle
        sequenceSpeedTableModel.setValueAt(-1L, 6, 1);
    }

    /**
     * Perform tests on an ArrayList
     */
    public void doArrayListTests() {
        ArrayList<String> arrayList = new ArrayList<>();
        ArrayList<String> arrayListX;
        long startTime, endTime;
        long runningTotal;
        int pos = SIZE / 2;

        // Load ArrayList
        runningTotal = 0;
        for (int y = 0; y < REPETITIONS; ++y) {
            arrayList = new ArrayList<>();
            startTime = System.nanoTime();
            for (int x = 0; x < SIZE; ++x) {
                arrayList.add(dataArray[x]);
            }
            endTime = System.nanoTime() - startTime;
            runningTotal += endTime;
        }
        sequenceSpeedTableModel.setValueAt(runningTotal / REPETITIONS, 0, 2);

        // Test access first element
        runningTotal = 0;
        for (int x = 0; x < REPETITIONS; ++x) {
            startTime = System.nanoTime();
            string = arrayList.get(0);
            endTime = System.nanoTime() - startTime;
            runningTotal += endTime;
        }
        sequenceSpeedTableModel.setValueAt(runningTotal / REPETITIONS, 1, 2);

        // Test access last element
        runningTotal = 0;
        for (int x = 0; x < REPETITIONS; ++x) {
            startTime = System.nanoTime();
            string = arrayList.get(SIZE - 1);
            endTime = System.nanoTime() - startTime;
            runningTotal += endTime;
        }
        sequenceSpeedTableModel.setValueAt(runningTotal / REPETITIONS, 2, 2);

        // Test access middle element
        runningTotal = 0;
        for (int x = 0; x < REPETITIONS; ++x) {
            startTime = System.nanoTime();
            string = arrayList.get(pos);
            endTime = System.nanoTime() - startTime;
            runningTotal += endTime;
        }
        sequenceSpeedTableModel.setValueAt(runningTotal / REPETITIONS, 3, 2);

        // Insert at start
        runningTotal = 0;
        for (int x = 0; x < REPETITIONS; ++x) {
            arrayListX = new ArrayList<>();
            for (int y = 0; y < arrayList.size(); ++y) {
                arrayListX.add(arrayList.get(y));
            }
            startTime = System.nanoTime();
            arrayListX.add(0, "Dawson College");
            endTime = System.nanoTime() - startTime;
            runningTotal += endTime;
        }
        sequenceSpeedTableModel.setValueAt(runningTotal / REPETITIONS, 4, 2);

        // Insert at end
        runningTotal = 0;
        for (int x = 0; x < REPETITIONS; ++x) {
            arrayListX = new ArrayList<>();
            for (int y = 0; y < arrayList.size(); ++y) {
                arrayListX.add(arrayList.get(y));
            }
            startTime = System.nanoTime();
            arrayListX.add("Dawson College");
            endTime = System.nanoTime() - startTime;
            runningTotal += endTime;
        }
        sequenceSpeedTableModel.setValueAt(runningTotal / REPETITIONS, 5, 2);

        // Insert in middle
        runningTotal = 0;
        for (int x = 0; x < REPETITIONS; ++x) {
            arrayListX = new ArrayList<>();
            for (int y = 0; y < arrayList.size(); ++y) {
                arrayListX.add(arrayList.get(y));
            }
            startTime = System.nanoTime();
            arrayListX.add(pos, "Dawson College");
            endTime = System.nanoTime() - startTime;
            runningTotal += endTime;
        }
        sequenceSpeedTableModel.setValueAt(runningTotal / REPETITIONS, 6, 2);
    }

    /**
     * Perform tests on an ArrayDeque
     */
    public void doDequeTests() {

        long startTime, endTime;
        long runningTotal;
        ArrayDeque<String> arrayDeque = new ArrayDeque<>();
        ArrayDeque<String> arrayDequeX;

        // Load ArrayDeque
        runningTotal = 0;
        for (int y = 0; y < REPETITIONS; ++y) {
            arrayDeque = new ArrayDeque<>();
            startTime = System.nanoTime();
            for (int x = 0; x < SIZE; ++x) {
                arrayDeque.add(dataArray[x]);
            }
            endTime = System.nanoTime() - startTime;
            runningTotal += endTime;
        }
        sequenceSpeedTableModel.setValueAt(runningTotal / REPETITIONS, 0, 3);

        // Test access first element
        runningTotal = 0;
        for (int x = 0; x < REPETITIONS; ++x) {
            startTime = System.nanoTime();
            string = arrayDeque.getFirst();
            endTime = System.nanoTime() - startTime;
            runningTotal += endTime;
        }
        sequenceSpeedTableModel.setValueAt(runningTotal / REPETITIONS, 1, 3);

        // Test access last element
        runningTotal = 0;
        for (int x = 0; x < REPETITIONS; ++x) {
            startTime = System.nanoTime();
            string = arrayDeque.getLast();
            endTime = System.nanoTime() - startTime;
            runningTotal += endTime;
        }
        sequenceSpeedTableModel.setValueAt(runningTotal / REPETITIONS, 2, 3);

        // Access middle element not supported
        sequenceSpeedTableModel.setValueAt(-1L, 3, 3);

        // Insert at start
        runningTotal = 0;
        for (int x = 0; x < REPETITIONS; ++x) {
            arrayDequeX = new ArrayDeque<>(arrayDeque);
            startTime = System.nanoTime();
            arrayDequeX.addFirst("Dawson College");
            endTime = System.nanoTime() - startTime;
            runningTotal += endTime;
        }
        sequenceSpeedTableModel.setValueAt(runningTotal / REPETITIONS, 4, 3);

        // Insert at end
        runningTotal = 0;
        for (int x = 0; x < REPETITIONS; ++x) {
            arrayDequeX = new ArrayDeque<>(arrayDeque);
            startTime = System.nanoTime();
            arrayDequeX.addLast("Dawson College");
            endTime = System.nanoTime() - startTime;
            runningTotal += endTime;
        }
        sequenceSpeedTableModel.setValueAt(runningTotal / REPETITIONS, 5, 3);

        // Does not support insert at middle
        sequenceSpeedTableModel.setValueAt(-1L, 6, 3);
    }

    /**
     * Perform tests on a LinkedList
     */
    public void doLinkedListTests() {

        long startTime, endTime;
        long runningTotal;
        int pos = SIZE / 2;

        LinkedList<String> linkedList = new LinkedList<>();
        LinkedList<String> linkedListX;

        // Load LinkedList
        runningTotal = 0;
        for (int y = 0; y < REPETITIONS; ++y) {
            linkedList = new LinkedList<>();
            startTime = System.nanoTime();
            for (int x = 0; x < SIZE; ++x) {
                linkedList.add(dataArray[x]);
            }
            endTime = System.nanoTime() - startTime;
            runningTotal += endTime;
        }
        sequenceSpeedTableModel.setValueAt(runningTotal / REPETITIONS, 0, 4);

        // Test access first element
        runningTotal = 0;
        for (int x = 0; x < REPETITIONS; ++x) {
            startTime = System.nanoTime();
            string = linkedList.getFirst();
            endTime = System.nanoTime() - startTime;
            runningTotal += endTime;
        }
        sequenceSpeedTableModel.setValueAt(runningTotal / REPETITIONS, 1, 4);

        // Test access last element
        runningTotal = 0;
        for (int x = 0; x < REPETITIONS; ++x) {
            startTime = System.nanoTime();
            string = linkedList.getLast();
            endTime = System.nanoTime() - startTime;
            runningTotal += endTime;
        }
        sequenceSpeedTableModel.setValueAt(runningTotal / REPETITIONS, 2, 4);

        // Test access middle element
        runningTotal = 0;
        for (int x = 0; x < REPETITIONS; ++x) {
            startTime = System.nanoTime();
            string = linkedList.get(pos);
            endTime = System.nanoTime() - startTime;
            runningTotal += endTime;
        }
        sequenceSpeedTableModel.setValueAt(runningTotal / REPETITIONS, 3, 4);

        // Insert at start
        runningTotal = 0;
        for (int x = 0; x < REPETITIONS; ++x) {
            linkedListX = new LinkedList<>(linkedList);
            startTime = System.nanoTime();
            linkedListX.addFirst("Dawson College");
            endTime = System.nanoTime() - startTime;
            runningTotal += endTime;
        }
        sequenceSpeedTableModel.setValueAt(runningTotal / REPETITIONS, 4, 4);

        // Insert at end
        runningTotal = 0;
        for (int x = 0; x < REPETITIONS; ++x) {
            linkedListX = new LinkedList<>(linkedList);
            startTime = System.nanoTime();
            linkedListX.addLast("Dawson College");
            endTime = System.nanoTime() - startTime;
            runningTotal += endTime;
        }
        sequenceSpeedTableModel.setValueAt(runningTotal / REPETITIONS, 5, 4);

        // Insert in middle
        runningTotal = 0;
        for (int x = 0; x < REPETITIONS; ++x) {
            linkedListX = new LinkedList<>(linkedList);
            startTime = System.nanoTime();
            linkedListX.add(pos, "Dawson College");
            endTime = System.nanoTime() - startTime;
            runningTotal += endTime;
        }
        sequenceSpeedTableModel.setValueAt(runningTotal / REPETITIONS, 6, 4);
    }
}
