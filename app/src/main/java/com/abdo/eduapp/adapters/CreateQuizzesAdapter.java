package com.abdo.eduapp.adapters;

import android.annotation.SuppressLint;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abdo.eduapp.R;
import com.abdo.eduapp.models.ModelDrQuiz;

import java.util.ArrayList;

public class CreateQuizzesAdapter extends RecyclerView.Adapter<CreateQuizzesAdapter.Holder> {

    ArrayList<ModelDrQuiz> list = new ArrayList<>();

    public void setList(ArrayList<ModelDrQuiz> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_create_quiz,parent,false);
        return new CreateQuizzesAdapter.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, @SuppressLint("RecyclerView")  int position) {
        holder.q.setText(list.get(position).getTxt());
        holder.question.setText(list.get(position).getQuestion());
        holder.ans1.setText(list.get(position).getAnswer1());
        holder.ans2.setText(list.get(position).getAnswer2());
        holder.ans3.setText(list.get(position).getAnswer3());
        holder.ans4.setText(list.get(position).getAnswer4());
        holder.corr.setText(list.get(position).getCorrectAnswer());

        holder.question.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                list.get(position).setQuestion(""+s);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        holder.ans1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                list.get(position).setAnswer1(""+s);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        holder.ans2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                list.get(position).setAnswer2(""+s);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        holder.ans3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                list.get(position).setAnswer3(""+s);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        holder.ans4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                list.get(position).setAnswer4(""+s);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        holder.corr.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                list.get(position).setCorrectAnswer(""+s);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



    }

    @Override
    public int getItemCount() {
        return list== null ? 0:list.size();
    }

    class Holder extends RecyclerView.ViewHolder{

        TextView q;
        TextView question , ans1 , ans2 , ans3 , ans4 , corr;

        public Holder(@NonNull View itemView) {
            super(itemView);
            q= itemView.findViewById(R.id.txt_unused);
            question =itemView.findViewById(R.id.question_edit);
            ans1 = itemView.findViewById(R.id.ans1_edit);
            ans2 = itemView.findViewById(R.id.ans2_edit);
            ans3 = itemView.findViewById(R.id.ans3_edit);
            ans4 = itemView.findViewById(R.id.ans4_edit);
            corr = itemView.findViewById(R.id.correct_ans_edit);

        }
    }
}
