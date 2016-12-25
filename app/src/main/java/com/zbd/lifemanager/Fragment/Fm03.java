package com.zbd.lifemanager.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.zbd.lifemanager.Json.NBAJson.NBAjson;
import com.zbd.lifemanager.MainActivity;
import com.zbd.lifemanager.R;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.login.LoginException;

public class Fm03 extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    SwipeRefreshLayout srl;
    Context context;
    String url = "http://op.juhe.cn/onebox/basketball/nba?key=645b5a36905d5e6f2da6d7a10cbb564c";
    String TAG = "Fm03";
    List<Map<String,Object>> mDataList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fm03,null);
        context = ((MainActivity) getActivity()).getContext();


        srl = (SwipeRefreshLayout) view.findViewById(R.id.NBASwip);
        srl.setOnRefreshListener(this);




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
                        Log.e(TAG, "onResponse: OK" );
                        NBAjson nbaJson = NBAGson.fromJson(s,NBAjson.class);
                        for (int i=0;i<nbaJson.getResult().getList().get(0).getTr().size();i++){
                            String player1 = nbaJson.getResult().getList().get(0).getTr().get(i).getPlayer1();
                            String player2 = nbaJson.getResult().getList().get(0).getTr().get(i).getPlayer2();
                            String player1Logo = nbaJson.getResult().getList().get(0).getTr().get(i).getPlayer1logo();
                            String player2Logo = nbaJson.getResult().getList().get(0).getTr().get(i).getPlayer2logo();
                            String score = nbaJson.getResult().getList().get(0).getTr().get(i).getScore();
                            Map<String,Object> map = new HashMap<String, Object>();
                            map.put("player1",player1);
                            map.put("player2",player2);
                            map.put("plauer1logo",player1Logo);
                            map.put("player2logo",player2Logo);
                            map.put("score",score);
                            mDataList.add(map);



                        }


                        srl.setRefreshing(false);










                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                });
                mRequestQueue.add(request);
                mRequestQueue.start();








            }
        };

        update.run();
    }
}
