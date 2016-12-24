package com.zbd.lifemanager;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import at.markushi.ui.ActionView;
import at.markushi.ui.action.BackAction;
import at.markushi.ui.action.DrawerAction;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<String> mDatas;
    private HomeAdapter mAdapter;
    int drawerState = 2;
    DrawerLayout mDrawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer);

        initData();

        final ActionView actionView = (ActionView) findViewById(R.id.actionView);
        actionView.setColor(Color.WHITE);

        mDrawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                drawerState = 0;
                actionView.setAction(new BackAction(), ActionView.ROTATE_COUNTER_CLOCKWISE);

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                drawerState = 1;
                actionView.setAction(new DrawerAction(), ActionView.ROTATE_CLOCKWISE);

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });


        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (drawerState) {
                    case 0:
                        drawerState = 1;
                        actionView.setAction(new DrawerAction(), ActionView.ROTATE_CLOCKWISE);
                        mDrawer.closeDrawer(Gravity.LEFT);
                        break;
                    case 1:
                        actionView.setAction(new BackAction(), ActionView.ROTATE_COUNTER_CLOCKWISE);
                        drawerState = 0;
                        mDrawer.openDrawer(Gravity.LEFT);//打开菜单栏
                        break;
                    case 2:
                        actionView.setAction(new BackAction(), ActionView.ROTATE_COUNTER_CLOCKWISE);
                        mDrawer.openDrawer(Gravity.LEFT);
                        drawerState = 1;
                }

//
            }
        });


        mRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter = new HomeAdapter());


    }

    protected void initData() {
        mDatas = new ArrayList<String>();
        for (int i = 'A'; i < 'z'; i++) {
            mDatas.add("" + (char) i);
        }
    }

    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder viewHolder = new MyViewHolder(LayoutInflater.from(MainActivity.this).inflate(R.layout.item, parent, false));
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

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tv;

            public MyViewHolder(View view) {
                super(view);
                tv = (TextView) view.findViewById(R.id.id_num);
            }
        }
    }
}
