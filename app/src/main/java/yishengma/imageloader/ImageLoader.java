package yishengma.imageloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import yishengma.utils.CloseUtil;

/**
 * Created by asus on 18-8-29.
 */

public final class ImageLoader {
    private static ImageLoader imageLoader;

    //图片缓存，抽象，接口隔离原则
    private ImageCache mImageCache;
    //线程池，线程数量为 CPU 数量
    private ExecutorService mExecutorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    private static final String TAG = "ImageLoader";

    private ImageLoader() {
        initImageLoader();
    }


    /**
     * 单例模式
     * @return
     */
    public static ImageLoader getInscance(){
        if (imageLoader==null){
            synchronized (ImageLoader.class){
                if (imageLoader==null){
                    imageLoader = new ImageLoader();
                }
            }
        }

        return imageLoader;
    }

    private void initImageLoader() {
        //依赖细节
        mImageCache = new MemoryCache();
    }

    /**
     * 实现可以注入不同的 ImageCache ,依赖抽象
     * @param imageCache
     */
    public void setImageCache(ImageCache imageCache) {
        mImageCache = imageCache;
    }

    public void displayImage(final String url, final ImageView imageView) {
        Bitmap bitmap = mImageCache.get(url);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
            Log.e(TAG, "displayImage: 缓存");
            return;
        }

        imageView.setTag(url);
        mExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = downloadImage(url);
                if (bitmap == null) {
                    return;

                }
                if (imageView.getTag().equals(url)) {

                    imageView.setImageBitmap(bitmap);
                }
                mImageCache.put(url, bitmap);


            }
        });


    }

    public  Bitmap downloadImage(String imageUrl) {
        Log.e(TAG, "downloadImage: 加载" );
        InputStream inputStream=null;
        ByteArrayOutputStream outputStream=null;
        try {
            URL url=new URL(imageUrl);
            HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setReadTimeout(2000);
            httpURLConnection.connect();

            if(httpURLConnection.getResponseCode()==200){
                inputStream = httpURLConnection.getInputStream();
                outputStream = new ByteArrayOutputStream();

                byte buffer[]=new byte[1024*8];
                int len=-1;
                while ((len = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, len);
                }

                byte[] bu=outputStream.toByteArray();
                return BitmapFactory.decodeByteArray(bu, 0, bu.length);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            CloseUtil.close(inputStream);
            CloseUtil.close(outputStream);
        }
        return null;
    }



}
