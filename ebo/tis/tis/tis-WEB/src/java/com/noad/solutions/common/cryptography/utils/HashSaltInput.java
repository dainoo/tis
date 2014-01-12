/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noad.solutions.common.cryptography.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Daud
 */
public class HashSaltInput {

    private static final String saltText = "Random$SaltValue#WithSpecialCharacters12@$@4&#%^$*";
    private static final Logger LOG = Logger.getLogger(HashSaltInput.class.getName());

    public static String hashAndSaltMessageText(String messageText) {
        String messageDigest = null;
        if (null == messageText) {
            return null;
        }
        try {
//            Create MessageDigest object for MD5
            MessageDigest digest = MessageDigest.getInstance("MD5");
//            Update messageText in message digest
            messageText += saltText;
            digest.update(messageText.getBytes(), 0, messageText.length());
//            Converts message digest value in base 16 (hex)
            messageDigest = new BigInteger(1, digest.digest()).toString(16);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "ERROR HASHING MESSAGE TEXT", e.getCause());

        }
        return messageDigest;

    }
}
