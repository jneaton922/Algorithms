/*************************************************************************
 *  Compilation:  javac LZW.java
 *  Execution:    java LZW - < input.txt   (compress)
 *  Execution:    java LZW + < input.txt   (expand)
 *  Dependencies: BinaryIn.java BinaryOut.java
 *
 *  Compress or expand binary input from standard input using LZW.
 *
 *
 *************************************************************************/

 import java.io.BufferedInputStream;
 import java.io.BufferedOutputStream;
 import java.io.IOException;
 import TriePackage.HybridTrieST;

public class LZWmod {
    private static final int R = 256;        // number of input chars
    private static final int L = 4096;       // number of codewords = 2^W
    private static final int W = 12;         // codeword width

    public static void compress() throws IOException{
        BufferedInputStream input = new BufferedInputStream(System.in);

        HybridTrieST<Integer> st = new HybridTrieST<Integer>(2);
        StringBuilder key;

        //initialize symbol table with base characters
        for (int i = 0; i < R; i++){
            key = new StringBuilder(""+(char)i);
            st.put(key, i);
        }
        int code = R+1;  // R is codeword for EOF

        key  = new StringBuilder(""+(char)input.read());
        char c;
        int pref;
        while (input.available() > 0) {
            pref = st.searchPrefix(key);
            while(input.available() > 0 && (pref==3 || pref==2)){
              key.append((char)input.read());
              pref=st.searchPrefix(key);
            }
            c  = key.charAt(key.length()-1);
            key.deleteCharAt(key.length()-1);

            /*System.out.println("PrefixValue:");
            System.out.println(st.searchPrefix(key));
            System.out.println("Key:");
            System.out.println(key.toString());*/

            BinaryStdOut.write(st.get(key), W);      // Print s's encoding.

            key.append(c);
            if (code < L && input.available()>0)    // Add s to symbol table.
                st.put(key, code++);
            key= new StringBuilder(""+c);
        }
        BinaryStdOut.write(R, W);
        BinaryStdOut.close();
    }


    public static void expand() {
        String[] st = new String[L];
        int i; // next available codeword value

        // initialize symbol table with all 1-character strings
        for (i = 0; i < R; i++)
            st[i] = "" + (char) i;
        st[i++] = "";                        // (unused) lookahead for EOF

        int codeword = BinaryStdIn.readInt(W);
        String val = st[codeword];

        while (true) {
            BinaryStdOut.write(val);
            codeword = BinaryStdIn.readInt(W);
            if (codeword == R) break;
            String s = st[codeword];
            if (i == codeword) s = val + val.charAt(0);   // special case hack
            if (i < L) st[i++] = val + s.charAt(0);
            val = s;
        }
        BinaryStdOut.close();
    }


    public static void main(String[] args) throws IOException{
        if      (args[0].equals("-")) compress();
        else if (args[0].equals("+")) expand();
        else throw new RuntimeException("Illegal command line argument");
    }

}
