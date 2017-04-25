package com.bc.lrucachedemo.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Android缓存机制，不会造成00M（内存溢出）
 */

public class LruCacheUtils {

    private static LruCacheUtils lruCacheUtils;
    private LruCache<String, Bitmap> lruCache;
    private String url;
    private ImageView imageView;

    private LruCacheUtils() {
        int size = (int) Runtime.getRuntime().maxMemory();//获取App内存的最大长度
        lruCache = new LruCache<>(size/8);//设置为总内存空间的1/8
    }

    public synchronized static LruCacheUtils getIntances(){
        if (lruCacheUtils == null) {
            lruCacheUtils = new LruCacheUtils();
        }
        return lruCacheUtils;
    }

    public void requestImage(String url,ImageView imageView){
        this.url = url;
        this.imageView = imageView;

        if (lruCache.get(url) != null) {
            imageView.setImageBitmap(lruCache.get(url));
            Log.e("TAG", "lruCache : getlruCache");
        } else {
            MyAsyncTask myAsyncTask = new MyAsyncTask();
            myAsyncTask.execute(url);
            Log.e("TAG", "lruCache : putlruCache");
        }
    }

    class MyAsyncTask extends AsyncTask<String, Void, Bitmap>{

        @Override
        protected Bitmap doInBackground(String... strings) {
            Bitmap bitmap = null;
            String string = strings[0];
            try {
                URL url = new URL(string);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(30*1000);
                conn.setReadTimeout(30*1000);
                conn.connect();

                InputStream inputStream = conn.getInputStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            lruCache.put(url, bitmap);
            imageView.setImageBitmap(bitmap);
        }
    }
}
