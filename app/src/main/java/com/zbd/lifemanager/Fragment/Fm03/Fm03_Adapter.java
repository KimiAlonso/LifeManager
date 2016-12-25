package com.zbd.lifemanager.Fragment.Fm03;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zbd.lifemanager.R;

import java.util.List;
import java.util.Map;

/**
 * Created by Amarok on 2016/12/25.
 */

public class Fm03_Adapter extends BaseAdapter {

    Context context;
    List<Map<String, Object>> list;
    public Fm03_Adapter(Context context, List list) {
        this.list = list;
        this.context = context;

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
         MViewHolder holder = new MViewHolder();
        convertView = LayoutInflater.from(context).inflate(R.layout.fm03_item,null);
        holder.player1logo = (ImageView) convertView.findViewById(R.id.player1logo);
        holder.player2logo = (ImageView) convertView.findViewById(R.id.player2logo);
        holder.player1 = (TextView) convertView.findViewById(R.id.player1);
        holder.player2 = (TextView) convertView.findViewById(R.id.player2);
        holder.score = (TextView) convertView.findViewById(R.id.score);

        holder.player1.setText(list.get(position).get("player1").toString());
        holder.player2.setText(list.get(position).get("player2").toString());
        Glide.with(context).load(list.get(position).get("player1logo").toString()).into(holder.player1logo);
        Glide.with(context).load(list.get(position).get("player2logo").toString()).into(holder.player2logo);
        holder.score.setText(list.get(position).get("score").toString());










        return convertView;
    }
    class MViewHolder{
        ImageView player1logo;
        ImageView player2logo;
        TextView player1;
        TextView player2;
        TextView score;
    }
}
