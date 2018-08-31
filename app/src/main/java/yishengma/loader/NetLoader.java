package yishengma.loader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import yishengma.request.BitmapRequest;
import yishengma.utils.CloseUtil;

/**
 * Created by asus on 18-8-31.
 */

public class NetLoader extends AbsLoader {
    private static final String TAG = "NetLoader";

    @Override
    Bitmap onLoadImage(BitmapRequest request) {

        InputStream inputStream=null;
        ByteArrayOutputStream outputStream=null;
        try {
            URL url=new URL(request.imageUri);
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
