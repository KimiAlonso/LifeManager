package com.zbd.lifemanager.Fragment.Fm05;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zbd.lifemanager.Database.DatabaseHelper;
import com.zbd.lifemanager.Database.User;
import com.zbd.lifemanager.MainActivity;
import com.zbd.lifemanager.R;

import java.sql.SQLException;
import java.util.List;


public class Fm05_1 extends Fragment {

    View mView;
    EditText username;
    EditText password;
    Button login;
    Button insert;
    Button quit;
    DatabaseHelper helper;

    String userName;
    String passWord;

    Context context;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fm05_1,null);

        initView();

        context = MainActivity.getContext();

        helper = new DatabaseHelper(MainActivity.getContext());


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper loginHelper  = new DatabaseHelper(context);
                userName = username.getText().toString();
                passWord = password.getText().toString();
                User userLogin = new User();
                userLogin.setName(userName);
                userLogin.setPassword(passWord);
                try {
                    List<User> users =  loginHelper.getDao(userLogin.getClass()).queryBuilder().where().eq("name",userName)
                            .and().eq("password",passWord).query();
                    if (users.size() != 0){
                        int id ;
                        id = users.get(0).getId();

                        userLogin.setId(id);
                        userLogin.setState(1);
                        loginHelper.getDao(userLogin.getClass()).update(userLogin);
                        cleanEdit();
                    }
                    else {
                        Toast.makeText(context,"用户名不存在或密码错误",Toast.LENGTH_SHORT).show();
                    }




                } catch (SQLException e) {
                    e.printStackTrace();
                    Toast.makeText(context,"用户名不存在或密码错误",Toast.LENGTH_SHORT).show();
                }

                loginHelper.close();

            }
        });

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper insertHelper  = new DatabaseHelper(context);
                Toast.makeText(context,"注册并成功",Toast.LENGTH_SHORT).show();
                userName = username.getText().toString();
                passWord = password.getText().toString();
                User userInsert = new User();
                userInsert.setName(userName);
                userInsert.setPassword(passWord);


                try {
                    insertHelper.getDao(userInsert.getClass()).create(userInsert);
                    userInsert.setState(1);
                    insertHelper.getDao(userInsert.getClass()).update(userInsert);


                } catch (SQLException e) {
                    e.printStackTrace();
                }
                cleanEdit();
                insertHelper.close();
            }
        });

        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper quitHelper = new DatabaseHelper(context);
                User userQuit = new User();
                try {
                    List<User> users = quitHelper.getDao(userQuit.getClass()).queryBuilder().where().eq("state",1).query();
                    if (users.size() != 0){
                        for (int i = 0;i<users.size();i++) {
                            if (users.get(i).getState() == 1){
                                users.get(i).setState(0);
                                quitHelper.getDao(userQuit.getClass()).update(users.get(i));
                            }
                        }

                    }else {
                        Toast.makeText(context,"error",Toast.LENGTH_SHORT).show();
                    }

                } catch (SQLException e) {

                    e.printStackTrace();
                }
                cleanEdit();
                quitHelper.close();
            }
        });





        return mView;
    }

    private void initView() {
        username =  (EditText) mView.findViewById(R.id.username);
        password = (EditText) mView.findViewById(R.id.password);
        login = (Button) mView.findViewById(R.id.login);
        insert = (Button) mView.findViewById(R.id.insert);
        quit = (Button) mView.findViewById(R.id.quit);
    }

    public void cleanEdit(){
        username.setText("");
        password.setText("");
    }
}
