package com.kenfogel.performance.loaders;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

import com.butlerpress.dict.Dictionary;
import com.kenfogel.performance.models.MapSpeedTableModel;

/**
 * Performs a set of tests to determine the Big-O performance of an HashMap and
 * TreeMap
 *
 * Does multiple REPETITIONS on data structures of a given SIZE
 *
 * Displays the results in a table
 *
 * Uses Dictionary.jar Copyright (c) 2004-2007 Scott Willson that generates
 * random words from a set of words derived from a public source dictionary. See
 * Dictionary.jar for more information
 *
 * Updated to use Java 1.8 syntax
 *
 * @author Ken Fogel
 * @version 4.1
 *
 */
public class MapTests {

    private final static int REPETITIONS = 1000; // 10000;
    private final static int SIZE = 1000;
    private final static int SEARCH_SIZE = 10;

    private final MapSpeedTableModel mapSpeedTableModel;
    private Set<String> dataSet;
    private Set<String> searchSet;
    private String[] dataArray;
    private String string;

    private final Random random;

    /**
     * Constructor Receives reference to the table model that will hold the
     * results
     *
     * @param mapSpeedTableModel
     */
    public MapTests(MapSpeedTableModel mapSpeedTableModel) {
        random = new Random();
        this.mapSpeedTableModel = mapSpeedTableModel;

        loadDataSet();
        loadSearchSet();
    }

    /**
     * Load a set with random values and copy the values to an array The array
     * is used to select a SEARCH_SIZE of random words to search for
     */
    private void loadDataSet() {
        // Load Array
        dataSet = new HashSet<>();
        dataArray = new String[SIZE];
        for (int x = 0; x < SIZE; ++x) {
            do { // This loop will repeat if the random word in not unique
                string = Dictionary.getRandomWordTermCommonNameOrConnector();
                dataArray[x] = string;
            } while (!dataSet.add(string));
        }
    }

    /**
     * Select SEARCH_SIZE random words from the array to use in the search test
     */
    @SuppressWarnings("empty-statement")
    private void loadSearchSet() {
        searchSet = new HashSet<>();
        for (int x = 0; x < SEARCH_SIZE; ++x) {
            while (!searchSet.add(dataArray[random.nextInt(SIZE)]));
        }
    }

    /**
     * Carry out the tests on the hash map
     */
    public void doHashMapTests() {
        long startTime, endTime;
        long runningTotal;
        HashMap<String, String> hashMap = new HashMap<>();
        HashMap<String, String> hashMapX;

        // Load Data
        runningTotal = 0;
        for (int y = 0; y < REPETITIONS; ++y) {
            hashMap = new HashMap<>();
            startTime = System.nanoTime();
            for (int x = 0; x < SIZE; ++x) {
                hashMap.put(dataArray[x], dataArray[x]);
            }
            endTime = System.nanoTime() - startTime;
            runningTotal += endTime;
        }
        mapSpeedTableModel.setValueAt(runningTotal / REPETITIONS, 0, 1);

        // Add Hash Map
        hashMapX = new HashMap<>(hashMap);
        runningTotal = 0;
        for (int y = 0; y < REPETITIONS; ++y) {
            // Find a word not in the hash map
            do { // This loop will repeat if the random word is in the hash
                string = Dictionary.getRandomWordTermCommonNameOrConnector();
            } while (hashMapX.containsKey(string));
            startTime = System.nanoTime();
            hashMapX.put(string, string);
            endTime = System.nanoTime() - startTime;
            runningTotal += endTime;
        }
        mapSpeedTableModel.setValueAt(runningTotal / REPETITIONS, 1, 1);

        // Find SEARCH_SIZE elements
        hashMapX = new HashMap<>(hashMap);
        runningTotal = 0;
        for (int y = 0; y < REPETITIONS; ++y) {
            startTime = System.nanoTime();
            Iterator<String> it = searchSet.iterator();
            while (it.hasNext()) {
                string = hashMapX.get(it.next());
            }
            endTime = System.nanoTime() - startTime;
            runningTotal += endTime;
        }
        mapSpeedTableModel.setValueAt(runningTotal / REPETITIONS, 2, 1);
    }

    /**
     * Carry out the tests on a tree map
     */
    public void doTreeMapTests() {
        long startTime, endTime;
        long runningTotal;
        TreeMap<String, String> treeMap = new TreeMap<>();
        TreeMap<String, String> treeMapX;

        // Load Tree Map
        runningTotal = 0;
        for (int y = 0; y < REPETITIONS; ++y) {
            treeMap = new TreeMap<>();
            startTime = System.nanoTime();
            for (int x = 0; x < SIZE; ++x) {
                treeMap.put(dataArray[x], dataArray[x]);
            }
            endTime = System.nanoTime() - startTime;
            runningTotal += endTime;
        }
        mapSpeedTableModel.setValueAt(runningTotal / REPETITIONS, 0, 2);

        // Add Tree Map
        treeMapX = new TreeMap<>(treeMap);
        runningTotal = 0;
        for (int y = 0; y < REPETITIONS; ++y) {
            // Find a word not in the hash map
            do { // This loop will repeat if the random word is in the tree
                string = Dictionary.getRandomWordTermCommonNameOrConnector();
            } while (treeMapX.containsKey(string));

            startTime = System.nanoTime();
            treeMapX.put(string, string);
            endTime = System.nanoTime() - startTime;
            runningTotal += endTime;
        }
        mapSpeedTableModel.setValueAt(runningTotal / REPETITIONS, 1, 2);

        // Find SEARCH_SIZE elements
        treeMapX = new TreeMap<>(treeMap);
        runningTotal = 0;
        for (int y = 0; y < REPETITIONS; ++y) {
            startTime = System.nanoTime();
            Iterator<String> it = searchSet.iterator();
            while (it.hasNext()) {
                string = treeMapX.get(it.next());
            }
            endTime = System.nanoTime() - startTime;
            runningTotal += endTime;
        }
        mapSpeedTableModel.setValueAt(runningTotal / REPETITIONS, 2, 2);
    }
}
