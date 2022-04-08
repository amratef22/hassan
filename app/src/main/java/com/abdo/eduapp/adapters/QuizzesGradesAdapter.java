package com.abdo.eduapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abdo.eduapp.R;
import com.abdo.eduapp.models.ModelQuizGrades;

import java.util.ArrayList;

public class QuizzesGradesAdapter extends RecyclerView.Adapter<QuizzesGradesAdapter.Holder> {


    ArrayList<ModelQuizGrades> list = new ArrayList<>();

    public void setList(ArrayList<ModelQuizGrades> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quiz_grade , parent , false);

        return new QuizzesGradesAdapter.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.quizname.setText(list.get(position).getQuizName());
        holder.grade.setText(list.get(position).getGrade());

    }

    @Override
    public int getItemCount() {
        return list == null ? 0:list.size();
    }

    class Holder extends RecyclerView.ViewHolder{

        TextView quizname;
        TextView grade;


        public Holder(@NonNull View itemView) {
            super(itemView);

            quizname = itemView.findViewById(R.id.quiz_name);
            grade = itemView.findViewById(R.id.quiz_grades);
        }
    }
}
