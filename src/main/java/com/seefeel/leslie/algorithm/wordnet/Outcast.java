package com.seefeel.leslie.algorithm.wordnet;

import com.seefeel.leslie.algorithm.util.IOUtil;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

/**
 * ${DESCRIPTION}
 * Author: Leslie
 * Date: 5/3/17
 * Time: PM1:36
 */
public class Outcast {
    private final WordNet wordNet;

    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        this.wordNet = wordnet;
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        int maxDist = Integer.MIN_VALUE;
        String outcast = null;

        for (final String noun : nouns) {
            int dist = Arrays.stream(nouns).mapToInt(s -> {
                if (noun.equals(s)) {
                    return 0;
                }
                return wordNet.distance(noun, s);

            }).sum();

            if (dist > maxDist){
                maxDist = dist;
                outcast = noun;
            }
        }

        return outcast;
    }

    // see test client below
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(IOUtil.getResourcePath(args[0]), IOUtil.getResourcePath(args[1]));
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(IOUtil.getResourcePath(args[t]));
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}
