package com.aleks.crypto;

import java.io.ObjectInputStream.GetField;

/**
*
* @author Marcelo Aleksandravicius
*/
public class Error extends Exception {
  
  public static final Error SUCCESS_DATABASE_DECRYPTED = new Error(1, "Database decrypted with success!");
  public static final Error ERROR_NONE= new Error(0, "No error!");
  public static final Error ERROR_KEY_FILE_NOT_FOUND= new Error(-1, "Key file not found.");
  public static final Error ERROR_CRYPT_NOT_FOUND= new Error(-2, "Crypted file not found.");
  public static final Error ERROR_KEY_SIZE_WRONG= new Error(-3, "key size is wrong.");
  public static final Error ERROR_KEY_AND_CRYPT_NOT_MATCH= new Error(-4, "key file or Crypt file do not match or corrupt.");
  public static final Error ERROR_READING_FILE= new Error(-5, "Error reading file");
  public static final Error ERROR_DECRYPTION_FAILED= new Error(-6, "Something wrong with decryption.\n %s");
  
  private int id = 0;
  private String message = "";
  private String extraMsgParam = "";
  
  
  
  
  public  Error(int _errorId, String msg) {
    id = _errorId;
    message = msg;
  }
  
  
  /**
   * set Extra param print out the formated messages
   * @param param
   */
  public Error param(String param) {
    extraMsgParam = param;
    return this;
  }

  
  @Override
  public String getMessage() {
    return String.format(message, extraMsgParam);
  }
  
  
  public boolean isError() {
    return id < 0;
  }



  

  
}
