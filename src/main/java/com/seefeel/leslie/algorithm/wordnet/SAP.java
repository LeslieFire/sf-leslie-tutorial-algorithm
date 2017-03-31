package com.seefeel.leslie.algorithm.wordnet;

import edu.princeton.cs.algs4.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ${DESCRIPTION}
 * Author: Leslie
 * Date: 5/3/17
 * Time: PM1:35
 */
public class SAP {
    private Digraph digraph;
    private Map<String, SAPProcessor> cache;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G){
        this.digraph = new Digraph(G);
    }

    private void validateVertex(int v){
        if (v < 0 || v >= digraph.V()){
            throw new IndexOutOfBoundsException("节点越界");
        }
    }

    private void validateVertex(Iterable<Integer> vertices){
        for (Integer vertice : vertices) {
            validateVertex(vertice);
        }
    }



    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w){
        validateVertex(v);
        validateVertex(w);

        SAPProcessor sapProcessor = new SAPProcessor(v, w);

        return sapProcessor.distance;
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w){
        validateVertex(v);
        validateVertex(w);

        SAPProcessor sapProcessor = new SAPProcessor(v, w);

        return sapProcessor.ancestor;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w){
        validateVertex(v);
        validateVertex(w);

        SAPProcessor sapProcessor = new SAPProcessor(v, w);

        return sapProcessor.distance;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w){
        validateVertex(v);
        validateVertex(w);

        SAPProcessor sapProcessor = new SAPProcessor(v, w);

        return sapProcessor.ancestor;
    }

    public SAPProcessor cachedResult(int v, int w){
        String key = v + "_" + w;
        if (cache.containsKey(key)){
            //只保留2次调用，第二次调用就清缓存
            SAPProcessor sapProcessor = cache.get(key);
            cache.remove(key);

            return sapProcessor;
        }

        SAPProcessor sapProcessor = new SAPProcessor(v, w);
        cache.put(key, sapProcessor);

        return sapProcessor;
    }

    public SAPProcessor cachedResult(Iterable<Integer> v, Iterable<Integer> w){
        String key = v.toString() + "_" + w.toString();

        if (cache.containsKey(key)){
            //只保留2次调用，第二次调用就清缓存
            SAPProcessor sapProcessor = cache.get(key);
            cache.remove(key);

            return sapProcessor;
        }

        SAPProcessor sapProcessor = new SAPProcessor(v, w);
        cache.put(key, sapProcessor);

        return sapProcessor;
    }


    // do unit testing of this class
    public static void main(String[] args){
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String digraphpath = classLoader.getResource(args[0]).getFile();
        System.out.println(digraphpath);

        In in = new In(digraphpath);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }

    private class SAPProcessor{
        int distance;
        int ancestor;

        public SAPProcessor(int w, int v){
            BreadthFirstDirectedPaths a = new BreadthFirstDirectedPaths(digraph, w);
            BreadthFirstDirectedPaths b = new BreadthFirstDirectedPaths(digraph, v);

            process(a, b);
        }

        public SAPProcessor(Iterable<Integer> w, Iterable<Integer> v){
            BreadthFirstDirectedPaths a = new BreadthFirstDirectedPaths(digraph, w);
            BreadthFirstDirectedPaths b = new BreadthFirstDirectedPaths(digraph, v);

            process(a, b);
        }

        public void process(BreadthFirstDirectedPaths a, BreadthFirstDirectedPaths b){
            List<Integer> ancestors = new ArrayList<>();
            for (int i = 0; i < digraph.V(); i++) {
                if (a.hasPathTo(i) && b.hasPathTo(i)){
                    ancestors.add(i);
                }
            }

            int minDistance = Integer.MAX_VALUE;
            ancestor = -1;
            for (Integer v : ancestors) {
                int dist = a.distTo(v) + b.distTo(v);
                if (dist < minDistance) {
                    minDistance = dist;
                    ancestor = v;
                }
            }

            distance = (minDistance == Integer.MAX_VALUE) ? -1 : minDistance;
        }
    }
}
