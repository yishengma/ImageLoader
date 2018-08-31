package yishengma.loader;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by asus on 18-8-31.
 */

public class LoaderManager {

    public static final String HTTP = "http";
    public static final String HTTPS = "https";
    public static final String FILE = "file";

    public static LoaderManager INSTANCE ;

    private Map<String,Loader> mLoaderMap = new HashMap<>();

    private Loader mNullLoader = new NullLoader();

    public static LoaderManager getInstance(){
        if (INSTANCE==null){
            synchronized (LoaderManager.class){
                if (INSTANCE==null){
                    INSTANCE = new LoaderManager();
                }
            }
        }
        return INSTANCE;
    }
    private LoaderManager() {
         register(HTTP,new NetLoader());
         register(HTTPS,new NetLoader());
         register(FILE,new LocalLoader());

    }

    public Loader getLoader(String schema){
        if (mLoaderMap.containsKey(schema)) {
            return mLoaderMap.get(schema);
        }
        return mNullLoader;
    }


    public final synchronized void register(String schema, Loader loader) {
        mLoaderMap.put(schema, loader);
    }
}
