package com;


import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

public class Test extends UDF
{
    private static String key="abcdefgh12345678";

    private Text result = new Text();


    public Text evaluate(Text str, String choice) throws Exception
    {
        if (str == null | choice == null )      return null;

        if(choice.equals("1"))  result.set(encrupt(str.toString()));
        if(choice.equals("0"))  result.set(decrupt(str.toString()));

        return result;
    }

//    private String encrypt(String strToEncrypt, byte[] key) throws Exception
//    {
//        SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
//
//        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
//        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
//
//        String encryptedString = Base64.encodeBase64String(cipher.doFinal(strToEncrypt.getBytes()));
//
//        return encryptedString.trim();

    public String encrupt(String plain_text) throws Exception
    {
        SecretKey key = new SecretKeySpec("abcdefgh12345678".getBytes("UTF-8"), "AES");

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE,key);

        byte[] encrypted = cipher.doFinal(plain_text.getBytes("UTF-8"));
        return Base64.encodeBase64String(encrypted);

    }


    public String decrupt(String plain_text) throws Exception
    {
        SecretKey key = new SecretKeySpec("abcdefgh12345678".getBytes("UTF-8"), "AES");

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE,key);

        byte[] original = cipher.doFinal(Base64.decodeBase64(plain_text));

        return new String(original);

    }
}
