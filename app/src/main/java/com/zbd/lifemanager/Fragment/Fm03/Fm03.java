package com.zbd.lifemanager.Fragment.Fm03;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.zbd.lifemanager.Json.NBAJson.NBAjson;
import com.zbd.lifemanager.Json.NBAJson.TeamJson;
import com.zbd.lifemanager.MainActivity;
import com.zbd.lifemanager.R;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Fm03 extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    SwipeRefreshLayout srl;
    Context context;
    String url = "http://op.juhe.cn/onebox/basketball/nba?key=645b5a36905d5e6f2da6d7a10cbb564c";
    String TAG = "Fm03";
    List<Map<String,Object>> mDataList = new ArrayList<>();
    List<Map<String,Object>> mTeamList = new ArrayList<>();
    ListView lv;
    MHandler mHandler;
    TextView mainTeam;
    ImageView team01logo;
    ImageView team02logo;
    ImageView team03logo;
    ImageView team04logo;
    ImageView team05logo;
    ImageView team06logo;
    TextView time1;
    TextView time2;
    TextView time3;
    View mView;
    ImageView mainTeanImg;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fm03,null);
        context = ((MainActivity) getActivity()).getContext();

        mView = view;






        mHandler = new MHandler();


        srl = (SwipeRefreshLayout) view.findViewById(R.id.NBASwip);
        srl.setOnRefreshListener(this);
        lv = (ListView) view.findViewById(R.id.NBAList);
        onRefresh();

        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                boolean enable = false;
                if(lv != null && lv.getChildCount() > 0){
                    // check if the first item of the list is visible
                    boolean firstItemVisible = lv.getFirstVisiblePosition() == 0;
                    // check if the top of the first item is visible
                    boolean topOfFirstItemVisible = lv.getChildAt(0).getTop() == 0;
                    // enabling or disabling the refresh layout
                    enable = firstItemVisible && topOfFirstItemVisible;
                }
                srl.setEnabled(enable);
            }
        });






        return view;
    }


    @Override
    public void onRefresh() {
        Thread update = new Thread(){
            @Override
            public void run() {
                RequestQueue mRequestQueue = Volley.newRequestQueue(context);
                StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Gson NBAGson = new Gson();

                        NBAjson nbaJson = NBAGson.fromJson(s,NBAjson.class);
                        for (int i=0;i<nbaJson.getResult().getList().get(1).getTr().size();i++){
                            String player1 = nbaJson.getResult().getList().get(1).getTr().get(i).getPlayer1();
                            String player2 = nbaJson.getResult().getList().get(1).getTr().get(i).getPlayer2();
                            String player1Logo = nbaJson.getResult().getList().get(1).getTr().get(i).getPlayer1logo();
                            String player2Logo = nbaJson.getResult().getList().get(1).getTr().get(i).getPlayer2logo();
                            String score = nbaJson.getResult().getList().get(1).getTr().get(i).getScore();
                            Map<String,Object> map = new HashMap<String, Object>();
                            map.put("player1",player1);
                            map.put("player2",player2);
                            map.put("player1logo",player1Logo);
                            map.put("player2logo",player2Logo);
                            map.put("score",score);
                            mDataList.add(map);

                        }



                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                });

                String url1 = "http://op.juhe.cn/onebox/basketball/team?dtype=&team="+"马刺"+"&key=645b5a36905d5e6f2da6d7a10cbb564c";

                StringRequest request1 = new StringRequest(Request.Method.GET, url1, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Gson TeamGson = new Gson();
                        TeamJson teamJson = TeamGson.fromJson(s, TeamJson.class);
                        for (int i = 0;i<=3;i++){
                            String player1logo = teamJson.getResult().getList().get(i).getPlayer1logo();
                            String player2logo = teamJson.getResult().getList().get(i).getPlayer2logo();
                            String time = teamJson.getResult().getList().get(i).getM_time();

                            Map<String,Object> map = new HashMap<String ,Object>();
                            map.put("player1logo",player1logo);
                            map.put("player2logo",player2logo);
                            map.put("time",time);

                            mTeamList.add(map);

                            mHandler.sendEmptyMessage(0);

                        }
                        srl.setRefreshing(false);


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                });


                mRequestQueue.add(request);
                mRequestQueue.add(request1);
                mRequestQueue.start();
            }
        };
        update.run();
    }

    public class MHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            Fm03_Adapter adapter = new Fm03_Adapter(context,mDataList);
            adapter.notifyDataSetChanged();
            lv.setAdapter(adapter);

            mainTeanImg = (ImageView) mView.findViewById(R.id.main_team_img);
            team01logo = (ImageView) mView.findViewById(R.id.player01logo) ;
            team02logo = (ImageView) mView.findViewById(R.id.player02logo) ;
            team03logo = (ImageView) mView.findViewById(R.id.player03logo) ;
            team04logo = (ImageView) mView.findViewById(R.id.player04logo) ;
            team05logo = (ImageView) mView.findViewById(R.id.player05logo) ;
            team06logo = (ImageView) mView.findViewById(R.id.player06logo) ;
            time1 = (TextView) mView.findViewById(R.id.time1);
            time2 = (TextView) mView.findViewById(R.id.time2);
            time3 = (TextView) mView.findViewById(R.id.time3);

            time1.setText(mTeamList.get(0).get("time").toString());
            time2.setText(mTeamList.get(1).get("time").toString());
            time3.setText(mTeamList.get(2).get("time").toString());

            mainTeanImg.setImageResource(R.drawable.spurs);

            String url01 = mTeamList.get(0).get("player1logo").toString();
            Glide.with(context).load(url01).into(team01logo);
            String url02 = mTeamList.get(0).get("player2logo").toString();
            Glide.with(context).load(url02).into(team02logo);
            String url03 = mTeamList.get(1).get("player1logo").toString();
            Glide.with(context).load(url03).into(team03logo);
            String url04 = mTeamList.get(1).get("player2logo").toString();
            Glide.with(context).load(url04).into(team04logo);
            String url05 = mTeamList.get(2).get("player1logo").toString();
            Glide.with(context).load(url05).into(team05logo);
            String url06 = mTeamList.get(2).get("player2logo").toString();
            Glide.with(context).load(url06).into(team06logo);


//
//
//
//
//





        }
    }

//    public boolean isListViewReachBottomEdge(final ListView listView) {
//        boolean result = false;
//        if (listView.getLastVisiblePosition() == (listView.getCount() - 2)) {
//            final View bottomChildView = listView.getChildAt(listView.getLastVisiblePosition() - listView.getFirstVisiblePosition());
//            result = (listView.getHeight() >= bottomChildView.getBottom());
//        }
//        return result;
//    }
//
//    public boolean isListViewReachTopEdge(final ListView listView) {
//        boolean result=false;
//        if(listView.getFirstVisiblePosition()==0){
//            final View topChildView = listView.getChildAt(0);
//            result=topChildView.getTop()==0;
//        }
//        return result ;
//    }
}
