package yishengma;

import android.app.Application;

import java.io.File;

/**
 * Created by asus on 18-8-29.
 */

public class App extends Application {
   public static  File cacheDir;
    @Override
    public void onCreate() {
        super.onCreate();
        cacheDir = getExternalCacheDir();
    }



}
