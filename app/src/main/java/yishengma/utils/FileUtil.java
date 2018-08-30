package yishengma.utils;

import android.os.Environment;

import java.io.File;

import yishengma.App;

import static android.os.Environment.isExternalStorageRemovable;

/**
 * Created by asus on 18-8-30.
 */

public class FileUtil {

    public static File getDiskCacheDir(String uniqueName) {
        final String cachePath = Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) ||!isExternalStorageRemovable()
                ? App.getContext().getExternalCacheDir().getPath() //storage/emulated/0/Android/data/package_name/cache
                : App.getContext().getCacheDir().getPath();// /data/data/package_name/cache
        return new File(cachePath + File.separator + uniqueName);
    }

}
