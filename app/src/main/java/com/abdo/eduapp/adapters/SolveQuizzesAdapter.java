package com.abdo.eduapp.adapters;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abdo.eduapp.R;
import com.abdo.eduapp.models.ModelDrQuiz;
import com.abdo.eduapp.models.SharedModel;

import java.util.ArrayList;

public class SolveQuizzesAdapter extends RecyclerView.Adapter<SolveQuizzesAdapter.Holder> {

    ArrayList<ModelDrQuiz> list = new ArrayList<>();


    public void setList(ArrayList<ModelDrQuiz> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_solve_quiz,parent,false);
        return new SolveQuizzesAdapter.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, @SuppressLint("RecyclerView")  int position) {
        holder.q.setText(list.get(position).getTxt());
        holder.question.setText(list.get(position).getQuestion());
        holder.ans1.setText(list.get(position).getAnswer1());
        holder.ans2.setText(list.get(position).getAnswer2());
        holder.ans3.setText(list.get(position).getAnswer3());
        holder.ans4.setText(list.get(position).getAnswer4());


        holder.ans1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.ans1.setBackgroundColor(Color.GREEN);
                holder.ans2.setBackgroundColor(Color.WHITE);
                holder.ans3.setBackgroundColor(Color.WHITE);
                holder.ans4.setBackgroundColor(Color.WHITE);
                list.get(position).setCorrectAnswer("1");
                SharedModel.all_answers.set(position,"1") ;
            }
        });
        holder.ans2.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                holder.ans2.setBackgroundColor(Color.GREEN);
                holder.ans1.setBackgroundColor(Color.WHITE);
                holder.ans3.setBackgroundColor(Color.WHITE);
                holder.ans4.setBackgroundColor(Color.WHITE);
                list.get(position).setCorrectAnswer("2");
                SharedModel.all_answers.set(position,"3") ;
            }
        });
        holder.ans3.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                holder.ans3.setBackgroundColor(Color.GREEN);
                holder.ans2.setBackgroundColor(Color.WHITE);
                holder.ans1.setBackgroundColor(Color.WHITE);
                holder.ans4.setBackgroundColor(Color.WHITE);
                list.get(position).setCorrectAnswer("3");
                SharedModel.all_answers.set(position,"3") ;

            }
        });
        holder.ans4.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                holder.ans4.setBackgroundColor(Color.GREEN);
                holder.ans2.setBackgroundColor(Color.WHITE);
                holder.ans3.setBackgroundColor(Color.WHITE);
                holder.ans1.setBackgroundColor(Color.WHITE);
                list.get(position).setCorrectAnswer("4");
                SharedModel.all_answers.set(position,"4") ;
            }
        });






    }

    @Override
    public int getItemCount() {
        return list== null ? 0:list.size();
    }

    class Holder extends RecyclerView.ViewHolder{

        TextView q;
        TextView question , ans1 , ans2 , ans3 , ans4 ;

        public Holder(@NonNull View itemView) {
            super(itemView);
            q= itemView.findViewById(R.id.txt_unused);
            question =itemView.findViewById(R.id.question_edit);
            ans1 = itemView.findViewById(R.id.ans1_edit);
            ans2 = itemView.findViewById(R.id.ans2_edit);
            ans3 = itemView.findViewById(R.id.ans3_edit);
            ans4 = itemView.findViewById(R.id.ans4_edit);

        }
    }
}
