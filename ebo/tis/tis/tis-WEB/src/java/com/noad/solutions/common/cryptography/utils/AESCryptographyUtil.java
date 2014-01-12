/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noad.solutions.common.cryptography.utils;

import java.security.Key;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 *
 * @author Daud
 */
public class AESCryptographyUtil {

    private static final Logger LOG = Logger.getLogger(AESCryptographyUtil.class.getName());
    private static final String ALGO = "AES";
    private static String keyGenerated;
    private static final byte[] keyValue = new byte[]{'T', 'h', 'e', 'B', 'e', 's', 't',
        'S', 'e', 'c', 'r', 'e', 't', 'K', 'e', 'y'}; //16 letter keys

    public static String encrypt(String plainText) throws Exception {

        String encryptedValue = null;

        if (null == plainText) {
            return null;
        }
        try {
            Key key = generateKey();
            Cipher c = Cipher.getInstance(ALGO);
            c.init(Cipher.ENCRYPT_MODE, key);
            byte[] encVal = c.doFinal(plainText.getBytes());
            encryptedValue = new BASE64Encoder().encode(encVal);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "ERROR ENCRIPTING PLAIN TEXT", e.getCause());
        }
        return encryptedValue;
    }

    public static String decrypt(String cipherText) throws Exception {

        String plainText = null;

        if (null == cipherText) {
            return null;
        }
        try {
            Key key = generateKey();
            Cipher c = Cipher.getInstance(ALGO);
            c.init(Cipher.DECRYPT_MODE, key);
            byte[] decordedValue = new BASE64Decoder().decodeBuffer(cipherText);
            byte[] decValue = c.doFinal(decordedValue);
            plainText = new String(decValue);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "ERROR DECRIPTING PLAIN TEXT", e.getCause());
        }
        return plainText;
    }

    private static Key generateKey() throws Exception {
        Key key = new SecretKeySpec(keyValue, ALGO);
        keyGenerated = key.getEncoded().toString();
        return key;
    }

    public static String getKeyGenerated() {
        return keyGenerated;
    }

    public static void setKeyGenerated(String keyGenerated) {
        AESCryptographyUtil.keyGenerated = keyGenerated;
    }
}
