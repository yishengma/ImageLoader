package yishengma.imageloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;


import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;

import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import yishengma.cache.BitmapCache;
import yishengma.cache.MemoryCache;
import yishengma.config.DisplayConfig;
import yishengma.config.ImageLoaderConfig;
import yishengma.policy.SerialPolicy;
import yishengma.request.BitmapRequest;
import yishengma.request.RequestQueue;
import yishengma.utils.CloseUtil;

/**
 * Created by asus on 18-8-29.
 */

public final class ImageLoader {
    private static ImageLoader imageLoader;
    public ImageLoaderConfig mConfig;
    //图片缓存，抽象，接口隔离原则
    private BitmapCache mBitmapCache;
    //网络请求队列
    private RequestQueue mRequestQueue;


    private static final String TAG = "ImageLoader";

    private ImageLoader() {

    }


    /**
     * 单例模式
     * @return
     */
    public static ImageLoader getInstance(){
        if (imageLoader==null){
            synchronized (ImageLoader.class){
                if (imageLoader==null){
                    imageLoader = new ImageLoader();
                }
            }
        }

        return imageLoader;
    }

    public void init(ImageLoaderConfig config){
        mConfig = config;
        mBitmapCache = config.mBitmapCache;
        mRequestQueue = new RequestQueue(config.mThreadCount);
        checkConfig();
        mRequestQueue.start();


    }

    private void checkConfig(){
        if (mConfig==null){
            throw new RuntimeException("The config of ImageLoader is NUll,please call the init method to initialize!");
        }
        if (mConfig.mLoadPolicy==null){
            mConfig.mLoadPolicy = new SerialPolicy();

        }
        if (mBitmapCache==null){
            mBitmapCache = new MemoryCache();
        }

    }

    public void displayImage(ImageView imageView, String uri) {
        displayImage(imageView, uri, null, null);
    }

    public void displayImage(ImageView imageView, String uri, DisplayConfig config) {
        displayImage(imageView, uri, config, null);
    }

    public void displayImage(ImageView imageView, String uri, ImageListener listener) {
        displayImage(imageView, uri, null, listener);
    }

    public void displayImage(ImageView imageView,String uri,DisplayConfig config,ImageListener listener){
        BitmapRequest request = new BitmapRequest(imageView,uri,config,listener);
        request.displayConfig = request.displayConfig!=null?request.displayConfig:mConfig.mDisplayConfig;
        request.imageListener = listener;
        mRequestQueue.addRequest(request);
    }

    public void stop() {
        mRequestQueue.stop();
    }



    public BitmapCache getBitmapCache() {
        return mBitmapCache;
    }

    /**
     * 图片加载Listener
     * 在加载成功后进行回调
     * @author mrsimple
     */
    public  interface ImageListener {
         void onComplete(ImageView imageView, Bitmap bitmap, String uri,boolean isSuccess);
    }

}
