package com.SelfTourGuide.singapore.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by a on 2017/5/8.
 */

public class KMD1 {

    public static void encrypt(String filePath){
        byte[] tempbytes = new byte[5000];
        try {
            InputStream in = new FileInputStream(filePath);
            OutputStream out = new FileOutputStream(filePath.subSequence(0, filePath.lastIndexOf("."))+"2.png");
            while (in.read(tempbytes) != -1) {//简单的交换
                byte a = tempbytes[0];
                tempbytes[0] = tempbytes[1];
                tempbytes[1] = a;
                out.write(tempbytes);//写文件
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * AES方式解密文件
     * @param sourceFile
     * @return
     */
    public static Bitmap decryptFile(File sourceFile, String fileType, String sKey){
        Bitmap image = null;
        File decryptFile = null;
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            decryptFile = File.createTempFile(sourceFile.getName(),fileType);
            Cipher cipher = initAESCipher(sKey, Cipher.DECRYPT_MODE);
            inputStream = new FileInputStream(sourceFile);
            outputStream = new FileOutputStream(decryptFile);
            CipherOutputStream cipherOutputStream = new CipherOutputStream(outputStream, cipher);
            byte [] buffer = new byte [1024];
            int r;
            while ((r = inputStream.read(buffer)) >= 0) {
                cipherOutputStream.write(buffer, 0, r);
            }
            image = BitmapFactory.decodeByteArray(buffer, 0, buffer.length);
            cipherOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        return image;
    }


    /**
     * 初始化 AES Cipher
     * @param sKey
     * @param cipherMode
     * @return
     */
    public static Cipher initAESCipher(String sKey, int cipherMode) {
        //创建Key gen
        KeyGenerator keyGenerator = null;
        Cipher cipher = null;
        try {
            keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128, new SecureRandom(sKey.getBytes()));
            SecretKey secretKey = keyGenerator.generateKey();
            byte[] codeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(codeFormat, "AES");
            cipher = Cipher.getInstance("AES");
            //初始化
            cipher.init(cipherMode, key);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InvalidKeyException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return cipher;
    }



    public static Bitmap getImageFromAssets(Context context, String fileName) {
        Bitmap image = null;
        AssetManager am = context.getResources().getAssets();
        try {
            InputStream is = am.open(fileName);
            byte[] buffer = new byte[1500000];//足够大
            is.read(buffer);
            for(int i=0; i<buffer.length; i+= 5000){//与加密相同
                byte temp = buffer[i];
                buffer[i] = buffer[i+1];
                buffer[i+1] = temp;
            }
            image = BitmapFactory.decodeByteArray(buffer, 0, buffer.length);
            if (is!=null){
                is.close();
            }

        } catch (IOException e) {
            e.printStackTrace();

        }
        return image;
    }

}
