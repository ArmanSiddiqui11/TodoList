package com.example.todolist.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.Addnewtask;
import com.example.todolist.MainActivity;
import com.example.todolist.Modal.ToDoModel;
import com.example.todolist.R;
import com.example.todolist.Utils.DatabaseHelper;

import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.MyViewHolder> {
    private List<ToDoModel> list;
    private MainActivity activity;
    private DatabaseHelper myDB;

    public ToDoAdapter(DatabaseHelper myDB,MainActivity activity){
        this.activity=activity;
        this.myDB=myDB;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
final ToDoModel item=list.get(i);
myViewHolder.checkBox.setText(item.getTask());
myViewHolder.checkBox.setChecked(toBoolean(item.getStatus()));
myViewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked){
            myDB.updateStatus(item.getId(),1);
        }else{
            myDB.updateStatus(item.getId(),0);
        }
    }
});
    }
    public boolean toBoolean(int num){
        return num!=0;
    }
    public Context getContext(){
        return activity;
    }
    public void setTask(List<ToDoModel> list){
        this.list=list;
        notifyDataSetChanged();
    }
    public void deleteTask(int position){
        ToDoModel item=list.get(position);
        myDB.deleteTask(item.getId());
        list.remove(position);
        notifyItemRemoved(position);
    }
    public void editItem(int position){
        ToDoModel item=list.get(position);

        Bundle bundle=new Bundle();
        bundle.putInt("id",item.getId());
        bundle.putString("task",item.getTask());

        Addnewtask task=new Addnewtask();
        task.setArguments(bundle);
        task.show(activity.getSupportFragmentManager(),task.getTag());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
CheckBox checkBox;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox=itemView.findViewById(R.id.checkbox);
        }
    }

}
