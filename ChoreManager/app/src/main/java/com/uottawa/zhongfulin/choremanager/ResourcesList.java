package com.uottawa.zhongfulin.choremanager;

/**
 * Created by zhongfulin on 2017-12-03.
 */

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ResourcesList extends ArrayAdapter<Task> {
    private Activity context;
    List<Task> Tasks;

    public ResourcesList(Activity context, List<Task> tasks) {
        super(context, R.layout.equip, tasks);
        this.context = context;
        this.Tasks = tasks;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.equip, null, true);

        TextView editTaskName = (TextView) listViewItem.findViewById(R.id.editTaskName);
        TextView info = (TextView) listViewItem.findViewById(R.id.info);

        Task task = Tasks.get(position);
        editTaskName.setText(task.getTaskName());
        info.setText(String.valueOf(task.getEquipment()));
        return listViewItem;
    }
}