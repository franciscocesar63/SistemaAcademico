/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fafic.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;



public class SenhaGeneration {

    private static SecretKeySpec secretKeySpec;
    private static Cipher cipher;

    private static byte[] getKey() {
        String keySecreta = "Fr[#*!@#ce]";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return md.digest(keySecreta.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            Logger.getLogger(SenhaGeneration.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static String encryptSenha(String senha) {

        try {
            byte[] cifraKey = getKey();
            secretKeySpec = new SecretKeySpec(cifraKey, "AES");
            cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            byte[] resultado = cipher.doFinal(senha.getBytes("UTF-8"));
            return new String(Hex.encodeHex(resultado));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | UnsupportedEncodingException | IllegalBlockSizeException | BadPaddingException ex) {
            Logger.getLogger(SenhaGeneration.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static byte[] decryptSenha(String senha) {
        try {
            byte[] keyPass = getKey();
            byte[] bufferSenha = Hex.decodeHex(senha);
            secretKeySpec = new SecretKeySpec(keyPass, "AES");
            cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            return cipher.doFinal(bufferSenha);
        } catch (DecoderException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
            Logger.getLogger(SenhaGeneration.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
