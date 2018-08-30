package yishengma.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by asus on 18-8-29.
 */



public class CloseUtil {

    //接口隔离原则。
    public static void close(Closeable closeable){
            if (closeable==null){
                return;
            }
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

    }
}
