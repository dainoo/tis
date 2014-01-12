/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noad.solutions.common.cryptography.utils;

/**
 *
 * @author Daud
 */
public class CryptographyTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        String valueHashed = HashSaltInput.hashAndSaltMessageText("DAUDA");
        System.out.println(" THE HASHED VALUE IS " + valueHashed);
        String plainText = "GOod";
        String cipherText = AESCryptographyUtil.encrypt(plainText);
        System.out.println("THE ENCRIPTED TEXT IS " + cipherText);
        System.out.println("");
        System.out.println("THE DECRIPTED TEXT IS " + AESCryptographyUtil.decrypt(cipherText));
    }
}
