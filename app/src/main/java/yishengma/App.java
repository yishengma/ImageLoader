package yishengma;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * Created by asus on 18-8-29.
 */

public class App extends Application {
    @SuppressLint("StaticFieldLeak")
    private static Context context;// 这里是Application Context


    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
