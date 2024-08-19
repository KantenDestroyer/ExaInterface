package net.kanten.utils;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Cryptographic{
    private SecretKey key;
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
        this.key = key;
    }
    public void setKeyByString(String keyString){
        this.key = convertStringToSecretKey(keyString);
    }
    public SecretKey getKey(){
        return key;
    }
    public static String convertSecretKeyToString(SecretKey sk) throws NoSuchAlgorithmException {
        byte[] rawData = sk.getEncoded();
        return Base64.getEncoder().encodeToString(rawData);
    }
    public static SecretKey convertStringToSecretKey(String encodedKey) {
        byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
    }
    public static String encrypt(String blankText, SecretKey sk) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
        cipher.init(Cipher.ENCRYPT_MODE, sk);
        byte[] textBytes = blankText.getBytes();
        byte[] cryptText = cipher.doFinal(textBytes);
        return Base64.getEncoder().encodeToString(cryptText);
    }
    public static String decrypt(String cryptedText, SecretKey sk) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        cipher.init(Cipher.DECRYPT_MODE, sk);
        byte[] tobyte = Base64.getDecoder().decode(cryptedText);
        byte[] decrypted = cipher.doFinal(tobyte);
        return new String(decrypted);
    }
}