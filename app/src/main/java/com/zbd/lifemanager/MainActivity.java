package com.zbd.lifemanager;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.melnykov.fab.FloatingActionButton;
import com.zbd.lifemanager.Fragment.Fm03.Fm03;
import com.zbd.lifemanager.Fragment.Fm04;
import com.zbd.lifemanager.Fragment.Fm05.Fm05_1;
import com.zbd.lifemanager.Fragment.FragmentDemo;

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
    FloatingActionButton fab;
    private RadioButton dButton1;
    private RadioButton dButton2;
    private RadioButton dButton3;
    private RadioButton dButton4;
    private RadioButton dButton5;
    RadioGroup radioGroup;


    static Context context;

    FragmentDemo fm_1;
    FragmentDemo fm_2;
    Fm03 fm_3;
    Fm04 fm_4;
    Fm05_1 fm_5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer);
        context = MainActivity.this;

//        fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setColorNormal(Color.parseColor("#FF4081"));


        initData();
        initDrawer();

        radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
        SelectFragment(1);

        dButton1 = (RadioButton) findViewById(R.id.main_bt_1);
        dButton2 = (RadioButton) findViewById(R.id.main_bt_2);
        dButton3 = (RadioButton) findViewById(R.id.main_bt_3);
        dButton4 = (RadioButton) findViewById(R.id.main_bt_4);
        dButton5 = (RadioButton) findViewById(R.id.main_bt_5);


//        mRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerview);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mRecyclerView.setAdapter(mAdapter = new HomeAdapter());


        radioGroup.check(dButton1.getId());
        dButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.closeDrawer(Gravity.LEFT);
                SelectFragment(1);
            }
        });
        dButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectFragment(2);
                mDrawer.closeDrawer(Gravity.LEFT);
            }
        });
        dButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectFragment(3);
                mDrawer.closeDrawer(Gravity.LEFT);
            }
        });
        dButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectFragment(4);
                mDrawer.closeDrawer(Gravity.LEFT);
            }
        });
        dButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectFragment(5);
                mDrawer.closeDrawer(Gravity.LEFT);
            }
        });

    }


    public void initDrawer() {

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
                        break;
                }

            }
        });

    }

    protected void initData() {
        mDatas = new ArrayList<String>();
        for (int i = 'A'; i < 'z'; i++) {
            mDatas.add("" + (char) i);
        }
    }

    private void SelectFragment(int a) {

        android.support.v4.app.FragmentTransaction tran = this.getSupportFragmentManager().beginTransaction();

        if (fm_1 != null) {
            tran.hide(fm_1);
        }
        if (fm_2 != null) {
            tran.hide(fm_2);
        }
        if (fm_3 != null) {
            tran.hide(fm_3);
        }
        if (fm_4 != null) {
            tran.hide(fm_4);
        }
        if (fm_5 != null) {
            tran.hide(fm_5);
        }

        if (a == 1) {
            if (fm_1 == null) {
                fm_1 = new FragmentDemo();
                tran.add(R.id.main_fm, fm_1);
            } else tran.show(fm_1);
        }
        if (a == 2) {

            if (fm_2 == null) {
                fm_2 = new FragmentDemo();
                tran.add(R.id.main_fm, fm_2);
            } else tran.show(fm_2);
        }
        if (a == 3) {
            if (fm_3 == null) {
                fm_3 = new Fm03();
                tran.add(R.id.main_fm, fm_3);
            } else tran.show(fm_3);
        }
        if (a == 4) {
            if (fm_4 == null) {
                fm_4 = new Fm04();
                tran.add(R.id.main_fm, fm_4);
            } else tran.show(fm_4);
        }
        if (a == 5) {
            if (fm_5 == null) {
                fm_5 = new Fm05_1();
                tran.add(R.id.main_fm, fm_5);
            } else tran.show(fm_5);
        }
        tran.commit();
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

    public static Context getContext(){

        return context;
    }
}
