package virgil.com.okhttpdemo.activity;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.LogRecord;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import virgil.com.okhttpdemo.R;
import virgil.com.okhttpdemo.adapter.MyRecyclerViewAdapter;
import virgil.com.okhttpdemo.bean.MyDataBean;
import virgil.com.okhttpdemo.utils.UrlConfig;

public class MainActivity extends AppCompatActivity {

    private Context mContext = this;//上下文
    private RecyclerView mRecyclerview_main_content;//RecyclerView展示数据
    private ProgressBar mProgressbar_main;//进度条
    private List<MyDataBean.DataBean.ItemsBean> mTotalList = new ArrayList<>();//数据源
    private MyRecyclerViewAdapter mMyRecyclerViewAdapter;//RecyclerView的适配器
    private OkHttpClient mOkHttpClient = new OkHttpClient();//OkHttp客户端对象
    private Gson gson = new Gson();

    //Handler消息处理
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                //网络访问失败
                case 0:
                    Toast.makeText(mContext, "网络访问失败", Toast.LENGTH_SHORT).show();
                    break;
                //网络访问成功，创建适配器
                case 1:
                    //设置布局管理器
                    LinearLayoutManager manager = new LinearLayoutManager(mContext);
                    mRecyclerview_main_content.setLayoutManager(manager);
                    //设置固定高度
                    mRecyclerview_main_content.setHasFixedSize(true);
                    //设置适配器
                    mMyRecyclerViewAdapter = new MyRecyclerViewAdapter(mContext, mTotalList);
                    mRecyclerview_main_content.setAdapter(mMyRecyclerViewAdapter);
                    //让进度条消失
                    mProgressbar_main.setVisibility(View.GONE);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //调用初始化控件的方法
        initView();
        //调用初始化数据的方法(Get同步阻塞的方式)
        initDataBySynchronous();
    }

    ///////////////////////////////////////////////////////////////////////////
    // 自定义的方法
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 初始化控件
     */
    private void initView() {
        mRecyclerview_main_content = (RecyclerView) findViewById(R.id.recyclerview_main_content);
        mProgressbar_main = (ProgressBar) findViewById(R.id.progressbar_main);
    }

    /**
     * 初始化数据(Get同步阻塞的方式)
     */
    private void initDataBySynchronous() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //创建请求对象
                Request request = new Request.Builder()
                        .url(UrlConfig.BASEURL)
                        .build();
                try {
                    //获取响应对象
                    Response response = mOkHttpClient.newCall(request).execute();
                    String json = response.body().string();
                    MyDataBean myDataBean = gson.fromJson(json, new TypeToken<MyDataBean>() {
                    }.getType());
                    mTotalList = myDataBean.getData().getItems();
                    handler.sendEmptyMessage(1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
