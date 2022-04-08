package com.abdo.eduapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abdo.eduapp.R;
import com.abdo.eduapp.models.SharedModel;

import java.util.ArrayList;

public class StdQuizzesRecyclerAdapter extends RecyclerView.Adapter<StdQuizzesRecyclerAdapter.Holder> {


    ArrayList<String> list = new ArrayList<>();

    public void setList(ArrayList<String> list) {
        this.list = list;
    }

    private StdQuizzesRecyclerAdapter.OnItemClick onItemClick ;

    public void setOnItemClick(StdQuizzesRecyclerAdapter.OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }




    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_stdquiz,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

        holder.QuizName.setText(list.get(position));

    }

    @Override
    public int getItemCount() {
        return list == null ? 0: list.size() ;
    }

    class Holder extends RecyclerView.ViewHolder{

        TextView QuizName;


        public Holder(@NonNull View itemView) {
            super(itemView);

            QuizName = itemView.findViewById(R.id.std_quiz_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(onItemClick != null){



                        SharedModel.setQUIZName(list.get(getLayoutPosition()));
                        onItemClick.OnClick(SharedModel.getQUIZName());
                    }
                }
            });

        }
    }

    public interface OnItemClick{

        void OnClick(String QUIZName );

    }





}
