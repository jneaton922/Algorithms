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
    //private static final int L = 4096;       // number of codewords = 2^W
    private static final int L = 130560;   // max num codewords = 2^9 + 2^10 + ... + 2^16
    private static final int W = 16;         // codeword width

    public static void compress(int reset_flag) throws IOException{
        BufferedInputStream input = new BufferedInputStream(System.in);

        HybridTrieST<Integer> st = new HybridTrieST<Integer>(2);
        StringBuilder key;


        // initialize codeword max to 2^9 and word width to 9
        int currentLength=512;
        int currentWidth=9;

        //write dictionary reset indicator
        BinaryStdOut.write(reset_flag,1);

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

            BinaryStdOut.write(st.get(key), currentWidth);      // Print s's encoding.

            key.append(c);
            if (code < currentLength && input.available()>0)    // Add s to symbol table.
                st.put(key, code++);

            if (code == currentLength && (currentWidth < W || reset_flag == 1)){
              currentLength = currentLength<<1;
              currentWidth++;
              if (currentWidth == W && reset_flag == 1){
                st = new HybridTrieST<Integer>(2);
                for (int i = 0; i < R; i++){
                    key = new StringBuilder(""+(char)i);
                    st.put(key, i);
                }
                code = R+1;
                currentLength = 512;
                currentWidth = 9;
              }else{
                key= new StringBuilder(""+c);
              }
            }else{
              key= new StringBuilder(""+c);
            }
        }
        BinaryStdOut.write(R, currentWidth);
        BinaryStdOut.close();
    }


    public static void expand() {
        String[] st = new String[L];
        int i; // next available codeword value
        int reset_flag; // indicator for dictionary reset in compression
        // initialize codeword max to 2^9 and word width to 9
        int currentLength=512;
        int currentWidth=9;

        // initialize symbol table with all 1-character strings
        for (i = 0; i < R; i++)
            st[i] = "" + (char) i;
        st[i++] = "";                        // (unused) lookahead for EOF

        reset_flag = BinaryStdIn.readInt(1);
        int codeword = BinaryStdIn.readInt(currentWidth);
        String val = st[codeword];

        while (true) {
            BinaryStdOut.write(val);
            codeword = BinaryStdIn.readInt(currentWidth);
            if (codeword == R) break;
            String s = st[codeword];
            if (i == codeword) s = val + val.charAt(0);   // special case hack
            if (i < currentLength)st[i++] = val + s.charAt(0);
            if (i == currentLength-1 && (currentWidth < W || reset_flag == 1)){
              currentLength = currentLength<<1;
              currentWidth++;
              if(currentWidth == W && reset_flag == 1){
                currentLength = 512;
                currentWidth = 9;
                for (i = 0; i < R; i++)
                    st[i] = "" + (char) i;
                st[i++] = "";
                codeword = BinaryStdIn.readInt(currentWidth);
                val = st[codeword];
              }else{
                val = s;
              }
            }else{
              val = s;
            }
        }
        BinaryStdOut.close();
    }


    public static void main(String[] args) throws IOException{
        if      (args[0].equals("-")){
          if (args[1].equals("r")) compress(1);
          else  compress(0);
        }
        else if (args[0].equals("+")) expand();
        else throw new RuntimeException("Illegal command line argument");
    }

}
