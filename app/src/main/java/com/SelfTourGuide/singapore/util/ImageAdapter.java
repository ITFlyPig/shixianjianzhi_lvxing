package com.SelfTourGuide.singapore.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by a on 2017/5/17.
 */

public class ImageAdapter {
   static final String TAG=ImageAdapter.class.getSimpleName();
    public static Bitmap readBitmap(Context context, String fileName) {
        Bitmap bitmap = null;
        List<Byte> list = new ArrayList();
        try {
            InputStream is = context.getAssets().open(fileName);
            int read;
            while ((read = is.read()) > -1) {
                read = read ^ 0X99;
                list.add((byte)read);
            }
            byte[] arr = new byte[list.size()];
            int i = 0;
            for(Byte item : list) {
                arr[i++] = item;
            }


            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeByteArray(arr, 0, arr.length, options);
            options.inJustDecodeBounds = false;
            int be = (int) (options.outHeight / (float) 2);
            if (be <= 0)
            {
                be = 1;
            } else if (be > 3)
            {
                be = 3;
            }
            options.inSampleSize = be;
            try
            {
                bitmap = BitmapFactory.decodeByteArray(arr, 0, arr.length, options); //返回缩略图
            } catch (OutOfMemoryError e)
            {
                // TODO: handle exception
                System.out.println("Tile Loader (241) Out Of Memory Error " + e.getLocalizedMessage());
                System.gc();
                bitmap =null;
            }

            Log.i(TAG, "readBitmap: "+arr.toString());
         /*   bitmap = BitmapFactory.decodeByteArray(arr, 0, list.size());
            System.out.println(bitmap);*/
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
