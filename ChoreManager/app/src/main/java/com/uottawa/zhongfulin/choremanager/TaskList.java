package com.uottawa.zhongfulin.choremanager;

/**
 * Created by zhongfulin on 2017-12-02.
 */

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class TaskList extends ArrayAdapter<Task> {
    private Activity context;
    List<Task> Tasks;

    public TaskList(Activity context, List<Task> tasks) {
        super(context, R.layout.layout_product_list, tasks);
        this.context = context;
        this.Tasks = tasks;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_product_list, null, true);

        TextView editTaskName = (TextView) listViewItem.findViewById(R.id.editTaskName);
        TextView editStarted = (TextView) listViewItem.findViewById(R.id.editStarted);

        Task task = Tasks.get(position);
        editTaskName.setText(task.getTaskName());
        editStarted.setText(String.valueOf(task.getStartDate()));
        return listViewItem;
    }
}