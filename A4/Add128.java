


public class Add128 implements SymCipher {
  byte[] key;
  static int keySize = 256;

  public Add128(){
    SecureRandom random = new SecureRandom();
    key = new byte[keySize];
    random.nextBytes(key);
  }

  public Add128(byte [] byteKey){
    key = byteKey;
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
    byte[] bytes = s.getBytes();
    int i = 0;
    for (byte b : bytes){
      bytes[i] += key[i];
      i = (i+1)%keySize;
    }
    return bytes;
  }

	// Decrypt the array of bytes and generate and return the corresponding String.
	public String decode(byte [] bytes){
    int i = 0;
    for (byte b : bytes){
      bytes[i] -= key[i];
      i = (i+1)%keySize;
    }
    return bytes.toString();
  }

}
