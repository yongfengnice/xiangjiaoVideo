package com.android.baselibrary.service;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.Security;
import java.util.UUID;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AESHelper {
    /***
     * AES key byte size,should be 16bytes,24bytes and 32bytes
     */
    public static final int KEY_BYTE_SIZE16 = 16;
    /***
     * AES key byte size,should be 16bytes,24bytes and 32bytes
     */
    public static final int KEY_BYTE_SIZE24 = 24;
    /***
     * AES key byte size,should be 16bytes,24bytes and 32bytes
     */
    public static final int KEY_BYTE_SIZE32 = 32;

    /***
     * AES Key Generator
     * @param keyByteSize, key figures:16bytes,24bytes,32bytes
     * @return AES key string
     */
    public static String randomPassword(int keyByteSize) {
        String uuid = UUID.randomUUID().toString();
        int random = (int) (Math.random());
        return uuid.substring(random, random + keyByteSize);
    }

    /***
     * AES Encrypt
     * @param encodeRules AES Key String,must be 128,192,256bits
     * @param content String to be encrypted
     * @return encrypted base64 string, if exception happens return null
     */
    public static String AESEncode(String encodeRules, String content) {
        try {
            byte[] rawKey = encodeRules.getBytes();
            SecretKeySpec skeySpec = new SecretKeySpec(rawKey, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            byte[] encrypted = cipher.doFinal(content.getBytes());
            return android.util.Base64.encodeToString(encrypted, android.util.Base64.DEFAULT);
        } catch (NoSuchAlgorithmException
                | NoSuchPaddingException
                | InvalidKeyException
                | IllegalBlockSizeException
                | BadPaddingException
                e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
     * AES decrypt
     * @param encodeRules AES Key String,must be 128,192,256bits
     * @param content String to be decrypted
     * @return decrypted string, if exception happens return null
     */
    /*
     * AES decrypt
     * @param encodeRules AES Key String,must be 128,192,256bits
     * @param content String to be decrypted
     * @return decrypted string, if exception happens return null
     */
    public static String AESDncode(String encodeRules, String content) {
        try {
            byte[] rawKey = encodeRules.getBytes();
            SecretKeySpec skeySpec = new SecretKeySpec(rawKey, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] decrypted = cipher.doFinal(Base64.decode(content));
            return new String(decrypted, "UTF-8");
        } catch (NoSuchAlgorithmException
                | NoSuchPaddingException
                | InvalidKeyException
                | IOException
                | IllegalBlockSizeException
                | BadPaddingException
                e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String decrypt(byte[] cryptograph, String password){
        try {
//            AllowAes256BitKeys.fixKeyLength();
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            SecureRandom securerandom = new SecureRandom(tohash256Deal(password));
            kgen.init(256, securerandom);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding", "BC");

            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] content = cipher.doFinal(Base64.decode(cryptograph));
            return new String(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static byte[] tohash256Deal(String datastr) {
        try {
            MessageDigest digester=MessageDigest.getInstance("SHA-256");
            digester.update(datastr.getBytes());
            byte[] hex=digester.digest();
            return hex;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}