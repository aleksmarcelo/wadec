package com.aleks.crypto;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Arrays;

/**
 *
 * @author Aleks
 */
public abstract class WABaseDecrypt {
  protected byte[] bytesKeyFile;  //all key file data content
  protected byte[] bytesCryptedHeader; //header
  protected byte[] KEY, IV, S1, S2;


  protected File fileKey = null;
  protected File fileCrypted = null;
//  protected File fileOutput = null;
  String strCrypt;
  String strKey;
  String strOutput;

  // Error error;

  abstract public Error decrypt(String _strOutput) throws Error;



  public WABaseDecrypt( String _strKey, int lengthKeyFile, String _strCrypt, int lengthCryptHeader) {
    strKey = _strKey;
    strCrypt = _strCrypt;
    
    bytesKeyFile = new byte[lengthKeyFile];
    bytesCryptedHeader = new byte[lengthCryptHeader];
    S1 = new byte[32];
    S2 = new byte[32];
  }



  /**
   * Reads the position of the AES key and the IV; also reads the file signatures; these 32
   * bytes starting in S1 and S2 must match.
   * 
   * @param lenKeyFile Length the key file in bytes
   * @param posKey
   * @param posIV
   * @param posS1         signature from Key file
   * @param posS2         signature from crypt file
   */
  protected void readKeysAndSignatures( int posKey, int posIV, int posS1, int posS2) throws Error {

    fileKey = new File(strKey);
    fileCrypted = new File(strCrypt);

    if (!fileKey.exists())
      throw Error.ERROR_KEY_FILE_NOT_FOUND;

    if (!fileCrypted.exists())
      throw Error.ERROR_CRYPT_NOT_FOUND;

    if (fileKey.length() != bytesKeyFile.length)
      throw Error.ERROR_KEY_SIZE_WRONG;


    try (
      InputStream isKey = new FileInputStream(fileKey)) {
      InputStream isCrypt = new FileInputStream(fileCrypted);

      isKey.read(bytesKeyFile);
      isCrypt.read(bytesCryptedHeader);

      // Read values
      KEY = Arrays.copyOfRange(bytesKeyFile, posKey, posKey + 32);
      IV = Arrays.copyOfRange(bytesCryptedHeader, posIV, posIV + 16);
      
      S1 = Arrays.copyOfRange(bytesKeyFile, posS1, posS1 + 32);
      S2 = Arrays.copyOfRange(bytesCryptedHeader, posS2, posS2 + 32);
      
      isKey.close();
      isCrypt.close();
     

    } catch (IOException e) {
      throw Error.ERROR_READING_FILE;
    }
  }



}
