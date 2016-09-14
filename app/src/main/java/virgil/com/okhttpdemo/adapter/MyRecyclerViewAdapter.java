package virgil.com.okhttpdemo.adapter;

import android.content.Context;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import virgil.com.okhttpdemo.R;
import virgil.com.okhttpdemo.bean.MyDataBean;

/**
 * Created by virgil on 2016/7/29.
 * email 841864388@qq.com
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter {
    private Context mContext = null;
    private List<MyDataBean.DataBean.ItemsBean> list = null;
    private LayoutInflater inflater = null;

    public MyRecyclerViewAdapter(Context context, List<MyDataBean.DataBean.ItemsBean> list) {
        this.mContext = context;
        this.list = list;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * 通过打气筒获取view对象
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_recyclerview, parent, false);
        return new MyViewHolder(view);
    }

    /**
     * 给控件设置数据
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof MyViewHolder) {
            Glide.with(mContext).load(list.get(position).getCoverImageUrl()).into(((MyViewHolder) holder).image_main_cover);
            ((MyViewHolder) holder).textview_main_title.setText(list.get(position).getTitle() + "");
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        //定义item布局中的控件
        private ImageView image_main_cover;
        private TextView textview_main_title;

        /**
         * 在构造方法中对控件进行初始化
         *
         * @param itemView
         */
        public MyViewHolder(View itemView) {
            super(itemView);
            image_main_cover = (ImageView) itemView.findViewById(R.id.image_main_cover);
            textview_main_title = (TextView) itemView.findViewById(R.id.textview_main_title);
        }
    }
}
