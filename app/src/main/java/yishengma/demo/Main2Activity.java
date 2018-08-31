package yishengma.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import yishengma.App;
import yishengma.imageloader.R;

public class Main2Activity extends AppCompatActivity {
    private static final String TAG = "Main2Activity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Log.e(TAG, "onCreate: "+ App.anInt);
    }
}
