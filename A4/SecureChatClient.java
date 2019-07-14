
import java.util.*;
import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

import java.math.*;

public class SecureChatClient extends JFrame implements Runnable, ActionListener {

  public static final int PORT = 8765;

  ObjectInputStream myReader;
  ObjectOutputStream myWriter;
  JTextArea outputArea;
  JLabel prompt;
  JTextField inputField;
  String myName, serverName;
  Socket connection;

  BigInteger E;
  BigInteger N;
  String cipher_type;
  SymCipher sym_cipher;

  public SecureChatClient ()
  {
      try {

      serverName = JOptionPane.showInputDialog(this, "Enter the server name: ");
      InetAddress addr =
              InetAddress.getByName(serverName);
      connection = new Socket(addr, PORT);   // Connect to server with new
                                             // Socket

    // create output stream
    myWriter = new ObjectOutputStream(connection.getOutputStream());
    myWriter.flush(); // prevent deadlock

    // create input stream
    myReader = new ObjectInputStream(connection.getInputStream());


      // receive public key E
      E = (BigInteger) myReader.readObject();
      System.out.println("E received from server: "+E);
      // receive mod value N
      N = (BigInteger) myReader.readObject();
      System.out.println("N received from server: "+N);

      // receive "Sub" or "Add"
      cipher_type = (String) myReader.readObject();

      if (cipher_type.equals("Sub")){
        System.out.println("Substition cipher selected");
        sym_cipher = new Substitute();
      } else {
        System.out.println("Add128 cipher selected");
        sym_cipher = new Add128();
      }

      byte[] byteKey = sym_cipher.getKey();
      BigInteger big_key = new BigInteger(1, byteKey);
      System.out.println("Key:");
      for (int i = 0; i < byteKey.length; i++)
        System.out.print(((int)byteKey[i]) + " ");
      System.out.println();
      System.out.println("BigInt Key: "+ big_key);

      // RSA -encrypt key and send to server
      big_key = big_key.modPow(E,N);
      System.out.println("RSA-encrypted: "+ big_key);
      myWriter.writeObject(big_key);


      myName = JOptionPane.showInputDialog(this, "Enter your user name: ");
      myWriter.writeObject(sym_cipher.encode(myName));   // Send name to Server.  Server will need
                                                        // this to announce sign-on and sign-off
                                                        // of clients

      this.setTitle(myName);      // Set title to identify chatter

      Box b = Box.createHorizontalBox();  // Set up graphical environment for
      outputArea = new JTextArea(8, 30);  // user
      outputArea.setEditable(false);
      b.add(new JScrollPane(outputArea));

      outputArea.append("Welcome to the Chat Group, " + myName + "\n");

      inputField = new JTextField("");  // This is where user will type input
      inputField.addActionListener(this);

      prompt = new JLabel("Type your messages below:");
      Container c = getContentPane();

      c.add(b, BorderLayout.NORTH);
      c.add(prompt, BorderLayout.CENTER);
      c.add(inputField, BorderLayout.SOUTH);

      Thread outputThread = new Thread(this);  // Thread is to receive strings
      outputThread.start();                    // from Server

      addWindowListener(
              new WindowAdapter()
              {
                  public void windowClosing(WindowEvent e)
                  {
                    try{
                      myWriter.writeObject(sym_cipher.encode("CLIENT CLOSING"));
                    }
                    catch (IOException ie){};
                    System.exit(0);

                  }
              }
          );

      setSize(500, 200);
      setVisible(true);

      }
      catch (Exception e)
      {
          System.out.println(e+" Problem starting client!");
      }


  }

  public ObjectInputStream getReader(){
    return myReader;
  }
  public ObjectOutputStream getWriter(){
    return myWriter;
  }

  public void run()
  {
      byte[] received = null;
      String currMsg = null;
      while (true)
      {
           try {
             received = (byte[])myReader.readObject();
             if (received != null){
               System.out.println("\r\nMessage Decryption:\r\n");
               System.out.println("Received bytes: ");
               for (byte b: received)
                 System.out.print(b + " ");
               System.out.println();

               currMsg = sym_cipher.decode(received);
               System.out.println("Decrypted bytes: ");
               for (byte b : currMsg.getBytes())
                 System.out.print(b+" ");
               System.out.println();

               System.out.println("As String:\r\n" + currMsg);
               outputArea.append(currMsg+"\n");
             }
           }
           catch (Exception e)
           {
              System.out.println(e +  ", closing client!");
              break;
           }
      }
      System.exit(0);
  }

  public void actionPerformed(ActionEvent e)
  {
      System.out.println("\r\nMessage Encryption:\r\n");
      String currMsg = e.getActionCommand();      // Get input value
      inputField.setText("");

      System.out.println("Original:\r\n"+myName + ": "+currMsg);
      System.out.println("Bytes: ");
      for (byte b: (myName + ": "+currMsg).getBytes())
        System.out.print((int)(b)+" ");
      System.out.println();

      byte[] encrypted = sym_cipher.encode(myName + ": "+currMsg);
      System.out.println("Encrypted: ");
      for (byte b : encrypted)
        System.out.print((int)(b)+" ");
      System.out.println();

      try{
        myWriter.writeObject(encrypted);   // Add name and send it
      }                                   // to Server
      catch (IOException ie){}
  }

  public static void main(String [] args)
  {
       SecureChatClient JR = new SecureChatClient();
       JR.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
  }

}
