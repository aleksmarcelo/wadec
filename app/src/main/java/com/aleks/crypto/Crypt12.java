package com.aleks.crypto;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.security.Security;
import java.util.Arrays;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 *
 * @author Marcelo Aleksandravicius
 */
public class Crypt12 extends WABaseDecrypt {

  public Crypt12(String _strKey, String _strCrypt) {
    super(_strKey, 158, _strCrypt, 67);
  }

  @Override
  public Error decrypt(String strOutput) throws Error {

    readKeysAndSignatures(126, 51, 30, 3);

    // Compare files's signatures
    if (!Arrays.equals(S1, S2))
      throw Error.ERROR_KEY_AND_CRYPT_NOT_MATCH;

    try {
      // FileOutputStream out = new FileOutputStream(fileOutput);
      InputStream inCrypted = new BufferedInputStream(new FileInputStream(fileCrypted));
      // Jump the header - first 67 bytes
      inCrypted.read(new byte[67]);

      Security.addProvider(new BouncyCastleProvider());
      // Using AES with BouncyngCastle
      Cipher decipher = Cipher.getInstance("AES/GCM/NoPadding", "BC");

      decipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(KEY, "AES"), new IvParameterSpec(IV));
      CipherInputStream decipherStream = new CipherInputStream(
          inCrypted,
          decipher);

      // Creates a new decompressor.
      InflaterInputStream deflateDecrypted = new InflaterInputStream(
          decipherStream,
          new Inflater(false));

      // Deflate decrypted file
      FileOutputStream osDecrypted = new FileOutputStream(new File(strOutput));
      byte[] CryptBuffer = new byte[4000];
      int bytes;
      while ((bytes = deflateDecrypted.read(CryptBuffer)) != -1)
        osDecrypted.write(CryptBuffer, 0, bytes);

      osDecrypted.close();
      deflateDecrypted.close();
    } catch (Exception ex) {
      throw Error.ERROR_DECRYPTION_FAILED.param(ex.getMessage());
    }

    return Error.SUCCESS_DATABASE_DECRYPTED;
  }

}
