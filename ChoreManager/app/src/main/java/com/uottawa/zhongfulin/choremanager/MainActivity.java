package com.uottawa.zhongfulin.choremanager;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    Button buttonAddTask;
    ListView listViewTask;
    ListView listViewRecources;

    private EditText editTaskName;
    private EditText editCreator;
    private EditText editReciptent;
    private EditText editStarted;
    private EditText editDeadline;
    private EditText editNote;
    private EditText editPoints;
    private EditText editEquip;

    private DatabaseReference n;

    public String mmm;

    List<Task> Tasks;

    public DatabaseReference databaseTasks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseTasks = FirebaseDatabase.getInstance().getReference("Tasks");
        listViewTask = (ListView) findViewById(R.id.listViewTask);
        buttonAddTask = (Button) findViewById(R.id.button_add);

        Tasks = new ArrayList<>();

        //adding an onclicklistener to button
        buttonAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTask();
            }
        });

        listViewTask.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Task task = Tasks.get(i);
                showUpdateDeleteDialog(task);
                return true;
            }
        });
    }



    @Override
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

                //creating adapter
                TaskList productsAdapter = new TaskList(MainActivity.this, Tasks);
                //attaching adapter to the listview
                listViewTask.setAdapter(productsAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void addTask(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.layout_add_new_task, null);
        dialogBuilder.setView(dialogView);



        dialogBuilder.setTitle("Add New Task");
        final AlertDialog b = dialogBuilder.create();
        b.show();

        final EditText editTaskName = (EditText) dialogView.findViewById(R.id.editTaskName);
        final EditText editCreator  = (EditText) dialogView.findViewById(R.id.editCreator);
        final EditText editReciptent = (EditText) dialogView.findViewById(R.id.editReciptent);
        final EditText editStarted = (EditText) dialogView.findViewById(R.id.editStarted);
        final EditText editDeadline = (EditText) dialogView.findViewById(R.id.editDeadline);
        final EditText editNote = (EditText) dialogView.findViewById(R.id.editNote);
        final EditText editComplete = (EditText) dialogView.findViewById(R.id.editCopmlete);
        final EditText editEquip = (EditText) dialogView.findViewById(R.id.editEquip);
        final Button addTask = (Button) dialogView.findViewById(R.id.addTask);
        final Button cancle = (Button) dialogView.findViewById(R.id.cancel);

        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTaskName.getText().toString().trim();
                String creator = editCreator.getText().toString().trim();
                String reciptent = editReciptent.getText().toString().trim();
                String start = editStarted.getText().toString().trim();
                String deadline = editDeadline.getText().toString().trim();
                String note = editNote.getText().toString().trim();
                String equip = editEquip.getText().toString().trim();
                String complete="Not Complete";

                //checking if the value is provided
                if (!TextUtils.isEmpty(name)) {

                    //getting a unique id using push().getKey() method//it will create a unique id and we will use it as the Primary Key for our Product
                    String id = databaseTasks.push().getKey();

                    //creating an Task Object
                    Task task = new Task(id, name, creator, reciptent,start,deadline,note,equip,complete);

                    //Saving the Task
                    databaseTasks.child(id).setValue(task);


                    //displaying a success toast
                    Toast.makeText(MainActivity.this, "Product added", Toast.LENGTH_LONG).show();
                    b.dismiss();

                } else {
                    //if the value is not given displaying a toast
                    Toast.makeText(MainActivity.this, "Please enter a task name", Toast.LENGTH_LONG).show();
                }

            }
        });
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b.dismiss();
            }
        });

    }


    private void showUpdateDeleteDialog(final Task task) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.layout_show_info_task, null);
        dialogBuilder.setView(dialogView);



        dialogBuilder.setTitle(task.getTaskName());
        final AlertDialog b = dialogBuilder.create();
        b.show();

        final EditText editTaskName = (EditText) dialogView.findViewById(R.id.editTaskName);
        final EditText editCreator  = (EditText) dialogView.findViewById(R.id.editCreator);
        final EditText editReciptent = (EditText) dialogView.findViewById(R.id.editReciptent);
        final EditText editStarted = (EditText) dialogView.findViewById(R.id.editStarted);
        final EditText editDeadline = (EditText) dialogView.findViewById(R.id.editDeadline);
        final EditText editNote = (EditText) dialogView.findViewById(R.id.editNote);
        final EditText editCopmlete = (EditText) dialogView.findViewById(R.id.editCopmlete);
        final EditText editEquip = (EditText) dialogView.findViewById(R.id.editEquip);
        final Button buttonUpdateTask = (Button) dialogView.findViewById(R.id.buttonUpdateTask);
        final Button buttonDeleteTask = (Button) dialogView.findViewById(R.id.buttonDeleteTask);
        final Button complete = (Button) dialogView.findViewById(R.id.complete);

        editTaskName.setText(task.getTaskName());
        editCreator.setText(task.getCreator());
        editReciptent.setText(task.getRecipent());
        editStarted.setText(task.getStartDate());
        editDeadline.setText(task.getDeadline());
        editNote.setText(task.getNote());
        editEquip.setText(task.getEquipment());
        editCopmlete.setText(task.getComplete());



        buttonUpdateTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTaskName.getText().toString().trim();
                String creator = editCreator.getText().toString().trim();
                String reciptent = editReciptent.getText().toString().trim();
                String start = editStarted.getText().toString().trim();
                String deadline = editDeadline.getText().toString().trim();
                String note = editNote.getText().toString().trim();
                String equip = editEquip.getText().toString().trim();
                String complete=task.getComplete().toString();
                if (!TextUtils.isEmpty(name)) {
                    updateProduct(task.getId(), name, creator, reciptent,start,deadline,note,equip, complete);
                    Toast.makeText(getApplicationContext(), "Task Updated!", Toast.LENGTH_LONG).show();
                    b.dismiss();
                }
            }
        });


        buttonDeleteTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteProduct(task.getId());
                b.dismiss();
            }
        });
        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task.setComplete("Completed!!");
                String name = editTaskName.getText().toString().trim();
                String creator = editCreator.getText().toString().trim();
                String reciptent = editReciptent.getText().toString().trim();
                String start = editStarted.getText().toString().trim();
                String deadline = editDeadline.getText().toString().trim();
                String note = editNote.getText().toString().trim();
                String equip = editEquip.getText().toString().trim();
                String complete=task.getComplete().toString();
                task.increasePoints();
                updateProduct(task.getId(), name, creator, reciptent,start,deadline,note,equip, complete);
                final FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
                n =FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid());
//                n.child("point").setValue()
                n.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        n =FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid());
                        Users users = dataSnapshot.getValue(Users.class);
                        String name1=users.getName();
                        String id=user.getUid();
                        String Email =user.getEmail();
                        String point=users.increasePoint();
                        Users newusers= new Users(id,name1,Email,point);
                        n.setValue(newusers);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                Toast.makeText(getApplicationContext(), "Task Completed", Toast.LENGTH_LONG).show();
                b.dismiss();
            }
        });
    }

    private void updateProduct(String id, String name, String creator, String reciptent,String start,String deadline,String note,String equip,String complete) {

        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Tasks").child(id);
        //updating
        Task product = new Task(id, name, creator, reciptent,start,deadline,note,equip, complete);
        dR.setValue(product);

    }

    private boolean deleteProduct(String id) {
        //getting the specified  reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Tasks").child(id);
        //removing
        dR.removeValue();
        Toast.makeText(getApplicationContext(), "Task Deleted", Toast.LENGTH_LONG).show();
        return true;
    }


}