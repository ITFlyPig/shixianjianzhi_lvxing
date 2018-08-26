package com.example;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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

}
