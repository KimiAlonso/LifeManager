package com.zbd.lifemanager.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.Space;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zbd.lifemanager.Json.NBAJson.NBAjson;
import com.zbd.lifemanager.MainActivity;
import com.zbd.lifemanager.R;

import java.util.ArrayList;
import java.util.List;


public class Fm04 extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    Context context;

    private List<String> mDatas;
    NBAjson nbaJson;
    RecyclerView recyclerView;
    NBAAdapter nbaAdapter;
    LinearLayout space;
    SwipeRefreshLayout swipe;
    String TAG = "Fmo4";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fm04, null);
        context = ((MainActivity) getActivity()).getContext();

        initData();
        onRefresh();


        swipe = (SwipeRefreshLayout) view.findViewById(R.id.NBASwip);
        swipe.setOnRefreshListener(this);

        nbaAdapter = new NBAAdapter();


        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
//        recyclerView.setAdapter(nbaAdapter);


        return view;
    }

    protected void initData() {
        mDatas = new ArrayList<String>();
        for (int i = 'A'; i < 'z'; i++) {
            mDatas.add("" + (char) i);
        }
    }

    @Override
    public void onRefresh() {
        Log.e(TAG, "onRefresh: succeed");
        NBAHandler mHandler = new NBAHandler();
        mHandler.sendEmptyMessage(0);
    }

    public class NBAHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            recyclerView.setAdapter(nbaAdapter);
            swipe.setRefreshing(false);

        }
    }

    class NBAAdapter extends RecyclerView.Adapter<NBAAdapter.MyViewHolder> {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder viewHolder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item, parent, false));
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {


            holder.tv.setText(mDatas.get(position));

        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tv;

            public MyViewHolder(View view) {
                super(view);
                tv = (TextView) view.findViewById(R.id.id_num);

            }

        }
    }


}
