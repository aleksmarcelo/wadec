package com.aleks.crypto.test;

import com.aleks.crypto.Crypt12;
import com.aleks.crypto.Crypt14;
import com.aleks.crypto.Error;
import com.aleks.crypto.WABaseDecrypt;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marcelo Aleksandravicius <marcelo.aleks -at- gmail.com>
 */
public class Test {

    public static void main(String[] args) {

      System.out.printf("args: %s\n", Arrays.asList(args));
        
      
        if (args.length != 2 &&  args.length != 3) { 
            System.err.println("Usage: java -jar wadec.jar  <key>  <database.db.cryptedXX>  [output] ");
            return;
        }
        
        WABaseDecrypt crypt;
        if (args[1].endsWith(".crypt12"))
            crypt = new Crypt12 (args[0], args[1] );
        else if (args[1].endsWith(".crypt14"))
            crypt = new Crypt14 (args[0], args[1] );
          else {
            System.err.println("Usage: Database must ends with crypt12 or crypt14 (for now!-)");
            return;

          }
            
        String output =  args[1].replaceAll("\\.crypt..", "");
        if (args.length == 3)
          output = args[2];

        Error err = Error.ERROR_NONE;
        try {
            err = crypt.decrypt(output);
        } catch (Error ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
           System.out.println(err.getMessage());
        }

    }

}
