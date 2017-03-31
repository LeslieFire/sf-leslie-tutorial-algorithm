package com.seefeel.leslie.algorithm.wordnet;

import com.seefeel.leslie.algorithm.util.IOUtil;
import com.seefeel.leslie.algorithm.util.Precondition;
import edu.princeton.cs.algs4.Digraph;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * ${DESCRIPTION}
 * Author: Leslie
 * Date: 5/3/17
 * Time: PM1:17
 */
public class WordNet {

    private static final String DELIMITER_1st = ",";
    private static final String DELIMITER_2nd = " ";

    private Map<Integer, String> id2synsets;
    private Digraph digraph;
    private SAP sap;
    private Map<String, Set<Integer>> postTable;


    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms){
        id2synsets = new HashMap<>();
        postTable = new HashMap<>();

        initSynsets(synsets);
        int vertices = id2synsets.size();

        // init graph
        digraph = new Digraph(vertices);

        initHypernyms(hypernyms);

        if (!isRooted()){
            System.out.println(digraph.toString());
            throw new IllegalArgumentException("not a rooted DAG");
        }

        sap = new SAP(digraph);
    }

    private boolean isRooted(){
        int rootCount = 0; // 出度为0的节点即root节点，这样的节点只能有一个
        for (Integer index : id2synsets.keySet()) {
            if (digraph.outdegree(index) == 0){
                rootCount++;
            }
        }

        return rootCount == 1 ? true: false;
    }

    private void initHypernyms(String hypernyms){
        try {
            BufferedReader hyperReader = IOUtil.getReader(hypernyms);
            String line ;
            while((line = hyperReader.readLine()) != null){
                String[] cells = line.split(DELIMITER_1st);
                if (cells.length <= 1) continue;

                int nounId = Integer.valueOf(cells[0]);
                for(int i = 1; i < cells.length; ++i){
                    int hyperId = Integer.valueOf(cells[i]);

                    digraph.addEdge(nounId, hyperId);
                }
            }

        }catch (Exception e){
            System.out.println(e);
        }
    }

    private void initSynsets(String synsets){

        try {
            BufferedReader synsetsReader = IOUtil.getReader(synsets);
            String line ;
            while((line = synsetsReader.readLine()) != null){
                String[] cell = line.split(DELIMITER_1st);
                if (cell.length < 2) continue;

                Integer synId = Integer.valueOf(cell[0]);
                String syns = cell[1];
                id2synsets.putIfAbsent(synId, syns);

                String[] syn = syns.split(DELIMITER_2nd);
                for (String s : syn) {
                    if (!postTable.containsKey(s)){
                        postTable.put(s, new HashSet<>());
                    }
                    postTable.get(s).add(synId);   // 倒排表，方便查找
                }
            }

        }catch (Exception e){
            System.out.println(e);
        }
    }

    // returns all WordNet nouns
    public Iterable<String> nouns(){

        return postTable.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word){

        return postTable.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB){
        Precondition.checkArgument(postTable.containsKey(nounA), "nounA not exist");
        Precondition.checkArgument(postTable.containsKey(nounB), "nounB not exist");

        return sap.length(postTable.get(nounA), postTable.get(nounB));
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB){
        Precondition.checkArgument(postTable.containsKey(nounA), "nounA not exist");
        Precondition.checkArgument(postTable.containsKey(nounB), "nounB not exist");

        int ancestorId = sap.ancestor(postTable.get(nounA), postTable.get(nounB));

        return ancestorId != -1 ? id2synsets.get(ancestorId) : null;
    }

    @Override
    public String toString() {
        return digraph.toString();
    }

    // do unit testing of this class
    public static void main(String[] args){
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String synsetPath = classLoader.getResource("synsets.txt").getFile();
        String hypernymsPath = classLoader.getResource("hypernyms.txt").getFile();

        WordNet wordNet = new WordNet(synsetPath, hypernymsPath);
        System.out.println(wordNet);
    }
}
