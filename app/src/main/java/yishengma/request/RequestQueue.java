package yishengma.request;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by asus on 18-8-31.
 */

public class RequestQueue {
    //请求 的队列，因为 需要根据 请求的序号进行顺序或者逆序，
    // 因此使用可以根据属性排序的 PriorityBlockingQueue
    private BlockingQueue<BitmapRequest> mRequestBlockingQueue = new PriorityBlockingQueue<>();
    //请求的需要，使用 AtomicInteger 保证线程安全
    private AtomicInteger mSerialNum = new AtomicInteger(0);

    //默认的核心数
    public static  int DEFAULT_CORE_NUM  = Runtime.getRuntime().availableProcessors() +1 ;


    //CPU 核心数 和 分发线程数
    private int mDispatherNums = DEFAULT_CORE_NUM;

    //执行网络请求
    private RequestDispatcher[] mDispatchers ;


    public RequestQueue() {
         this(DEFAULT_CORE_NUM);
    }

    public RequestQueue(int coreNum){
        mDispatherNums = coreNum;
    }

    private final void startDispatcher(){
        mDispatchers = new RequestDispatcher[mDispatherNums];
        for (int i=0;i<mDispatherNums;i++){
            mDispatchers[i] = new RequestDispatcher(mRequestBlockingQueue);
            mDispatchers[i].start();
        }
    }

    /**
     * 开始执行请求的线程
     */
    public void start(){
        stop();
        startDispatcher();
    }

    /**
     * 停止所有线程
     */
    public void stop(){
        if (mDispatchers!=null&&mDispatchers.length>0){
            for (int i = 0; i < mDispatchers.length; i++) {
                mDispatchers[i].interrupt();
            }
        }
    }

    /**
     * 添加请求队列
     * @param request
     */
    public void addRequest(BitmapRequest request){
        if (!mRequestBlockingQueue.contains(request)){
            request.serialNum = generateSerialNumber();
            mRequestBlockingQueue.add(request);
        }
    }

    /**
     * 清除
     */
    public void clear(){
        mRequestBlockingQueue.clear();
    }

    /**
     * 获取所有的请求
     * @return
     */
    public BlockingQueue<BitmapRequest> getAllRequest(){
        return mRequestBlockingQueue;
    }

    /**
     * 为每个请求生成一个系列号,自动增加 1
     *
     * @return 序列号
     */
    private int generateSerialNumber() {
        return mSerialNum.incrementAndGet();
    }
}
