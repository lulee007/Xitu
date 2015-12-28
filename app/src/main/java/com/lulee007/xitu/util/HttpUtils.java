package com.lulee007.xitu.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * User: lulee007@live.com
 * Date: 2015-12-28
 * Time: 21:26
 */
public class HttpUtils {
    /**
     * 获取网络图片
     *
     * @param urlString
     *            如：http://f.hiphotos.baidu.com/image/w%3D2048/sign=3
     *            b06d28fc91349547e1eef6462769358
     *            /d000baa1cd11728b22c9e62ccafcc3cec2fd2cd3.jpg
     * @return
     * @date 2014.05.10
     */
    public static Bitmap getNetWorkBitmap(String urlString) {
        URL imgUrl = null;
        Bitmap bitmap = null;
        try {
            imgUrl = new URL(urlString);
            // 使用HttpURLConnection打开连接
            HttpURLConnection urlConn = (HttpURLConnection) imgUrl
                    .openConnection();
            urlConn.setDoInput(true);
            urlConn.connect();
            // 将得到的数据转化成InputStream
            InputStream is = urlConn.getInputStream();
            // 将InputStream转换成Bitmap
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            System.out.println("[getNetWorkBitmap->]MalformedURLException");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("[getNetWorkBitmap->]IOException");
            e.printStackTrace();
        }
        return bitmap;
    }
}
