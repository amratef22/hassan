package com.abdo.eduapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abdo.eduapp.R;
import com.abdo.eduapp.models.ModelCourse;
import com.abdo.eduapp.models.SharedModel;

import java.util.ArrayList;

public class CourcesRecyclerAdapter extends RecyclerView.Adapter<CourcesRecyclerAdapter.Holder> {


    ArrayList<ModelCourse> list = new ArrayList<>();

    public void setList(ArrayList<ModelCourse> list) {
        this.list = list;
    }


    private OnItemClick onItemClick ;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }


    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.course_item,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

        holder.courseName.setText(list.get(position).getCourseName());

    }

    @Override
    public int getItemCount() {
        return list == null ? 0: list.size() ;
    }

    class Holder extends RecyclerView.ViewHolder{

        TextView courseName;


        public Holder(@NonNull View itemView) {
            super(itemView);

            courseName = itemView.findViewById(R.id.course_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(onItemClick != null){
                        SharedModel.setCourseName(list.get(getLayoutPosition()).getCourseName());
                        onItemClick.OnClick(list.get(getLayoutPosition()).getCourseId());
                    }
                }
            });

        }
    }

    public interface OnItemClick{

        void OnClick(String CourseId );

    }



}
