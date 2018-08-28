import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

// Models an N-by-N percolation system.
public class Percolation {
    private int source = 0;
    private boolean[][] x;
    private int count, sink, open;
    private WeightedQuickUnionUF uf;
    
    // Create an N-by-N grid, with all sites blocked.
    public Percolation(int N) {
         //structure = new QuickFindUF(N);
         x = new boolean[N][N];
         count = N;
         sink = N*N + 1;
         uf = new WeightedQuickUnionUF(N*N + 2);
    }

    // Open site (i, j) if it is not open already.
    public void open(int i, int j) {
        x[i][j] = true;
        if (i == 0) { uf.union(encode(i, j), source); }
        if (i == count-1) { uf.union(encode(i, j), sink); }
        if (i > 0 && isOpen(i-1, j)) { 
            uf.union(encode(i, j), encode(i-1, j)); 
        }
        if (i < count-1 && isOpen(i+1, j)) { 
            uf.union(encode(i, j), encode(i+1, j)); 
        }
        if (j > 0 && isOpen(i, j-1)) { 
            uf.union(encode(i, j), encode(i, j-1)); 
        }
        if (j < count-1 && isOpen(i, j+1)) { 
            uf.union(encode(i, j), encode(i, j+1)); 
        }
    }

    // Is site (i, j) open?
    public boolean isOpen(int i, int j) {
        return x[i][j];
    }
    

    // Is site (i, j) full?
    public boolean isFull(int i, int j) {
       // if (x[i][j] == false) { return true; }
        //else { return false; }
        return !x[i][j];
    }

    // Number of open sites.
    public int numberOfOpenSites() {
        open = 0;
        for (int i = 0; i < count; i++) { 
             for (int j = 0; j < count; j++) { 
                 if (x[i][j]) { open++;  }
                }
            }
        return open;  
    }

    // Does the system percolate?
    public boolean percolates() {
        return uf.connected(source, sink); 
    }

    // An integer ID (1...N) for site (i, j).
    private int encode(int i, int j) {
        int en = count * (i) + j;
        return en;
    }

    // Test client. [DO NOT EDIT]
    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        Percolation perc = new Percolation(N);
        while (!in.isEmpty()) {
            int i = in.readInt();
            int j = in.readInt();
            perc.open(i, j);
        }
        StdOut.println(perc.numberOfOpenSites() + " open sites");
        if (perc.percolates()) {
            StdOut.println("percolates");
        }
        else {
            StdOut.println("does not percolate");
        }
        
        // Check if site (i, j) optionally specified on the command line
        // is full.
        if (args.length == 3) {
            int i = Integer.parseInt(args[1]);
            int j = Integer.parseInt(args[2]);
            StdOut.println(perc.isFull(i, j));
        }
    }
}
