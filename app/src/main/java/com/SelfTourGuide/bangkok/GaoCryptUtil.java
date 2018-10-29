package com.SelfTourGuide.bangkok;

/**
 * Created by a on 2017/5/17.
 */

public class GaoCryptUtil {

    /**
     * 获取解密后的数据
     * @param eCode 传入加密过的数据
     * @return
     */
    public static String getEString(String eCode){
        StringBuilder builder = new StringBuilder();
        long lenth = eCode.length();
        for (int i = 0; i < lenth; i++){
            builder.append((char)(eCode.charAt(i)- i%5));
        }
        return builder.toString();
    }
}
