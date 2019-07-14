
import java.util.*;

public class Substitute implements SymCipher {
  byte[] key;
  byte[] inv_key;
  static int keySize = 256;

  public Substitute( byte[] byteKey){
    key = byteKey;
    inv_key = toInverse(key);
  }

  public Substitute(){
      Random R = new Random();
      int pos;
      byte temp;

      key = new byte[keySize];
      //initial byte array 0-255
      for (int i=0; i < keySize; i++){
        key[i] = (byte)i;
      }

      // shuffle for random permutation
      for (int i =0; i<keySize; i++){
        pos = R.nextInt(keySize);
        temp = key[i];
        key[i] = key[pos];
        key[pos] = temp;
      }


      inv_key = toInverse(key);

  }

  private byte[] toInverse(byte[] byteKey){
    int i = 0;
    byte [] inv = new byte[256];
    for (byte b: byteKey){
      inv[(int)(b&0xFF)] = (byte)i++;
    }
    return inv;
  }

  // Return an array of bytes that represent the key for the cipher
  public byte [] getKey(){
    return key;
  }

  // Encode the string using the key and return the result as an array of
  // bytes.  Note that you will need to convert the String to an array of bytes
  // prior to encrypting it.  Also note that String S could have an arbitrary
  // length, so your cipher may have to "wrap" when encrypting.
  public byte [] encode(String S){
    int i = 0;
    byte[] bytes = S.getBytes();
    for (byte b: bytes){
      bytes[i++] = (key[(int)b&0xFF]); // mask sign bit to achieve 0-255
    }
    return bytes;
  }

  // Decrypt the array of bytes and generate and return the corresponding String.
  public String decode(byte [] bytes){
    int i = 0;
    for (byte b:bytes){
      bytes[i++] = inv_key[(int)b&0xFF]; // mask sign bit to achieve 0-255
    }
    return new String(bytes);
  }
}
