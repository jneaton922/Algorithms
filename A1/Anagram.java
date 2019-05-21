import java.util.*;
import java.lang.*;
import java.io.*;

public class Anagram {

  private static Scanner scanner = new Scanner( System.in );
  private static String dict_fn = "dictionary.txt";
  private static TrieSTNew<String> dict;
  //private static ArrayList<ArrayList<StringBuilder>> sols;
  private static ArrayList<ArrayList<StringBuilder>> sols;


  public static void solve(StringBuilder in, StringBuilder sub,StringBuilder pref){

    //prints for debug/trace
    //System.out.println(in);
    //System.out.println(sub +"\t|\t"+in);
    //System.out.println();
    int num_words;
    ArrayList<StringBuilder> temp_list;

    switch (dict.searchPrefix(sub.toString())) {
      case 0:
        // neither a prefix nor a word
        // backtrack (do nothing)
        break;
      case 1:
        // prefix but not a word
        // recurse with each possible next letter
        for (int i=0; i<(in.length());i++){
          sub.append(in.charAt(i));
          in.deleteCharAt(i);
          solve(in,sub,pref);
          in.insert(i,sub.charAt(sub.length()-1));
          sub.deleteCharAt(sub.length()-1);
        }
        break;
      case 2:
        // word but not a prefix

        // if we're at the end of the input string, this is a sol,
        if (in.length() == 0){
          // number of words is number of spaces
          num_words = 0;
          boolean flag = false;
          if (pref.length()>0){
            num_words = pref.length() - pref.toString().replaceAll(" ","").length()+1;
          }
          while (sols.size() < num_words+1){
            sols.add(new ArrayList<StringBuilder>());
          }
          temp_list = sols.get(num_words);

          StringBuilder itemToAdd;
          if(num_words == 0){
            itemToAdd = new StringBuilder(sub);
          }else{
            itemToAdd = new StringBuilder(sub+" "+pref);
          }

          for (StringBuilder s:temp_list){
            flag = s.toString().equals(itemToAdd.toString());
            if(flag)break;
          }
          if (!flag) temp_list.add(itemToAdd);
        }
        else {
          pref = new StringBuilder((pref+" "+sub).strip());
          solve(in,new StringBuilder(),pref);
        }

        break;
      case 3:
        // word and a prefix

        // if we're at the end of the input, this is a solution
        if (in.length()==0){
          // number of words is number of spaces
          num_words = 0;
          boolean flag = false;
          if (pref.length()>0){
            num_words = pref.length() - pref.toString().replaceAll(" ","").length()+1;
          }
          while (sols.size() < num_words+1){
            sols.add(new ArrayList<StringBuilder>());
          }
          temp_list = sols.get(num_words);
          StringBuilder itemToAdd;
          if(num_words == 0){
            itemToAdd = new StringBuilder(sub);
          }else{
            itemToAdd = new StringBuilder(sub+" "+pref);
          }
          for (StringBuilder s:temp_list){
            flag = s.toString().equals(itemToAdd.toString());
            if(flag)break;
          }
          if (!flag)temp_list.add(itemToAdd);
        }
        else{
        // else recurse with each possible next letter
            for (int i=0; i<(in.length());i++){
              sub.append(in.charAt(i));
              in.deleteCharAt(i);
              solve(in,sub,pref);
              in.insert(i,sub.charAt(sub.length()-1));
              sub.deleteCharAt(sub.length()-1);
            }

            pref = new StringBuilder((pref+" "+sub).strip());
            solve(in,new StringBuilder(),pref);
          }
        break;
    }
    return;
  }

  public static void main(String [] args) throws IOException {
    double start = System.nanoTime();
    dict = new TrieSTNew<String>();
    File file = new File(dict_fn);
    int total_sols;
    String n;
    if (file.exists()){
      Scanner infi = new Scanner(file);
      while(infi.hasNext()){
        n = infi.nextLine();
        dict.put(n,n);
      };
    };

    System.out.print("\r\nEnter input filename: ");
    String infn = scanner.nextLine();
    File infile = new File(infn);
    System.out.print("\r\nEnter output filename: ");
    String outfn = scanner.nextLine();
    File outfile = new File(outfn);
    System.out.println();

    BufferedWriter writer = new BufferedWriter(new FileWriter(outfn));

    StringBuilder instring;
    StringBuilder substring;
    ArrayList<StringBuilder> temp;

    if(infile.exists()){
      Scanner infi = new Scanner(infile);
      start = System.nanoTime();
      while(infi.hasNext()){
        instring = new StringBuilder(infi.nextLine().replaceAll("\\s",""));
        substring = new StringBuilder();
        sols = new ArrayList<ArrayList<StringBuilder>>();
        total_sols = 0;

        solve(instring,substring,new StringBuilder());

        System.out.println("Here are the results for '"+instring+"':");
        writer.write("Here are the results for '"+instring+"':\r\n");
        for (int i=0;i<sols.size();i++){
          temp = sols.get(i);
          Collections.sort(temp);
          System.out.println("There were "+temp.size()+" "+Integer.toString(i+1)+"-Word Solutions:");
          writer.write("There were "+temp.size()+" "+Integer.toString(i+1)+"-Word Solutions:\r\n");
          total_sols += temp.size();
          for (StringBuilder s: temp){
            System.out.println(s);
            writer.write(s.toString()+"\r\n");
          }
        }
        System.out.print("There were a total of "+Integer.toString(total_sols) +" solutions\r\n\r\n");
        writer.write("There were a total of "+Integer.toString(total_sols) +" solutions\r\n\r\n");
      }
      writer.close();
    }
    System.out.println("Runtime: "+Double.toString((((double)System.nanoTime()-start)/1e9)));
  }

}
