package com.uottawa.zhongfulin.choremanager;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhongfulin on 2017-12-03.
 */

public class Resouces extends AppCompatActivity {
    public DatabaseReference databaseTasks;
    ListView listViewRecources;
    List<Task> Tasks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_resources);
        listViewRecources=(ListView)findViewById(R.id.listViewRecources);
        Tasks = new ArrayList<>();
        databaseTasks= FirebaseDatabase.getInstance().getReference("Tasks");
    }
    protected void onStart() {
        super.onStart();
        //attaching value event listener
        databaseTasks.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //clearing the previous artist list
                Tasks.clear();

                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting product
                    Task product = postSnapshot.getValue(Task.class);
                    //adding product to the list
                    Tasks.add(product);
                }

                ResourcesList resourcesAdapter;
                resourcesAdapter = new ResourcesList(Resouces.this,Tasks);
                listViewRecources.setAdapter(resourcesAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
