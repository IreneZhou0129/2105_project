package com.uottawa.zhongfulin.choremanager;

/**
 * Created by zhongfulin on 2017-12-03.
 */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainPageActivity extends AppCompatActivity {
    private Button Rbutton;
    private Button Tbutton;
    private Button calendar;
    private Button logout;

    private String Email;
    private DatabaseReference p;
    private DatabaseReference n;
    private TextView Tpoint;
    private TextView Tname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Rbutton = (Button)findViewById(R.id.Resources_Botton);
        Tbutton = (Button)findViewById(R.id.Task_Botton);
        calendar =(Button) findViewById(R.id.calendar);
        logout = (Button) findViewById(R.id.logout);
        Tpoint =(TextView)findViewById(R.id.point);
        Tname = (TextView) findViewById(R.id.Name);



//        n = FirebaseDatabase.getInstance().getReference("name");




        Tbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent =new Intent(MainPageActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        Rbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent =new Intent(MainPageActivity.this,Resouces.class);
                startActivity(intent);
            }
        });
        calendar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent =new Intent(MainPageActivity.this,Calendar.class);
                startActivity(intent);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent m= new Intent(MainPageActivity.this,Profile.class);
                startActivity(m);
            }
        });



    }
    @Override
    protected void onStart() {
        super.onStart();
        //attaching value event listener
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        n =FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid()).child("name");
        n.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String Name= dataSnapshot.getValue(String.class);
                Tname.setText(Name);

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        p =FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid()).child("point");
        p.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String Point= dataSnapshot.getValue(String.class);
                Tpoint.setText(Point);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}