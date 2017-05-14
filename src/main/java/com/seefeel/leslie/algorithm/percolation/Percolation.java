package com.seefeel.leslie.algorithm.percolation;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * ${DESCRIPTION}
 * Author: Leslie
 * Date: 14/5/17
 * Time: PM2:42
 */
public class Percolation {
    private WeightedQuickUnionUF uf;
    private boolean[] site;
    private int n;
    private int size;
    private int virtualTop;
    private int virtualBottom;
    private int nOpen;
    private final int[][] direction = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

    public Percolation(int n) {                // create n-by-n grid, with all sites blocked

        if (n <= 0) {
            throw new IllegalArgumentException("n must be large than 0");
        }
        this.n = n;
        this.nOpen = 0;

        size = n * n;
        virtualTop = size;
        virtualBottom = size + 1;
        uf = new WeightedQuickUnionUF(size + 2);

        site = new boolean[size];
        for (int i = 0; i < size; ++i) {
            site[i] = false;
        }
    }

    public void open(int row, int col) {    // open site (row, col) if it is not open already

        validArgument(row, col);
        int x = getSiteNum(row, col);
        if (site[x]) return;

        site[x] = true;
        nOpen++;

        if (x < n) {
            uf.union(x, virtualTop);
        }
        if (x >= size - n) {
            uf.union(x, virtualBottom);
        }

        // union 4 neiborhod x
        for (int[] ints : direction) {
            try {
                int i = getSiteNum(row + ints[0], col + ints[1]);

                if (site[i]) {
                    uf.union(x, i);
                }
            } catch (IndexOutOfBoundsException e) {
                continue;
            }
        }
    }

    public boolean isOpen(int row, int col) {  // is site (row, col) open?

        validArgument(row, col);

        int i = getSiteNum(row, col);

        return site[i];
    }

    public boolean isFull(int row, int col) {  // is site (row, col) full?

        validArgument(row, col);
        int x = getSiteNum(row, col);
        return uf.connected(x, virtualTop);
    }

    public int numberOfOpenSites() {       // number of open sites

        return nOpen;
    }

    public boolean percolates() {              // does the system percolate?
        return uf.connected(virtualTop, virtualBottom);
    }

    public static void main(String[] args) {   // test client (optional)


    }

    private int getSiteNum(int row, int col) {

        validArgument(row, col);

        return (row - 1) * n + (col - 1);
    }

    private void validArgument(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new IndexOutOfBoundsException("row(" + row + ") or col(" + col + ") range outside of 1~" + n);
        }
    }
}
