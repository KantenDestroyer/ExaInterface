package net.kanten.utils;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Cryptographic{
    private static SecretKey key;
    private static final Cipher cipher;
    static {
        try {
            String cryptoALG = "AES";
            cipher = Cipher.getInstance(cryptoALG);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException(e);
        }
    }
    public Cryptographic(){}
    public SecretKey createKey() throws NoSuchAlgorithmException {
        String keyALG = "AES";
        KeyGenerator gen = KeyGenerator.getInstance(keyALG);
        gen.init(256);
        return gen.generateKey();
    }
    public void setKeyBySecretKey(SecretKey key){
        Cryptographic.key = key;
    }
    public void setKeyByString(String keyString){
        key = convertStringToSecretKey(keyString);
    }
    public static SecretKey getKey(){
        return key;
    }
    public byte[] getRawKey(){return key.getEncoded();}
    public static String convertSecretKeyToString(SecretKey sk) throws NoSuchAlgorithmException {
        byte[] rawData = sk.getEncoded();
        return Base64.getEncoder().encodeToString(rawData);
    }
    public static SecretKey convertStringToSecretKey(String encodedKey) {
        byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
    }
    public static String encrypt(String blankText) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
        cipher.init(Cipher.ENCRYPT_MODE, getKey());
        byte[] textBytes = blankText.getBytes();
        byte[] cryptText = cipher.doFinal(textBytes);
        return Base64.getEncoder().encodeToString(cryptText);
    }
    public static String decrypt(String cryptedText) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] tobyte = Base64.getDecoder().decode(cryptedText);
        byte[] decrypted = cipher.doFinal(tobyte);
        return new String(decrypted);
    }
}