package com.abdo.eduapp.ui.student;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.abdo.eduapp.R;
import com.abdo.eduapp.adapters.SolveQuizzesAdapter;
import com.abdo.eduapp.databinding.FragmentSolveQuizBinding;
import com.abdo.eduapp.models.ModelDrQuiz;
import com.abdo.eduapp.models.SharedModel;
import com.abdo.eduapp.utils.Const;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SolveQuizFragment extends Fragment {

    FragmentSolveQuizBinding binding ;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    ArrayList<ModelDrQuiz> list = new ArrayList<>();
    SolveQuizzesAdapter adapter = new SolveQuizzesAdapter();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_solve_quiz, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentSolveQuizBinding.bind(view);
        binding.toolbar.setTitle(SharedModel.getQUIZName());
        load();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void load(){
        list.clear();
        SharedModel.all_answers.clear();
        ref.child(Const.REF_Quizzes).child(SharedModel.getCourseId()).child(SharedModel.getQUIZName()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    list.add(snapshot1.getValue(ModelDrQuiz.class));
                }
                SharedModel.answers.clear();
                for (int i = 0  ; i< list.size() ; i++) {
                    SharedModel.answers.add(list.get(i).getCorrectAnswer());
                    SharedModel.all_answers.add("");

                }

                adapter.setList(list);
                binding.recyclerViewSolveQuiz.setAdapter(adapter);
                onclicks();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void onclicks(){

        binding.createQuizBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check();
            }
        });
    }
    private void check(){
        int x =1;
        int grade = 0;
        for (int i = 0  ; i< SharedModel.all_answers.size() ; i++){
            if (SharedModel.all_answers.get(i).isEmpty()){
                x=0;
                break;
            }
        }
        if (x==0){
            Toast.makeText(getContext(), R.string.ans_all , Toast.LENGTH_SHORT).show();
        }
        else{
            for (int i = 0  ; i< SharedModel.answers.size() ; i++){
                if(list.get(i).getCorrectAnswer().equals(SharedModel.answers.get(i))){
                    grade++;
                }
            }
            String grade_msg = grade +" / "+list.size();
            sendData(grade_msg);

        }
    }
    private void sendData(String grade_msg){
        ref.child(Const.REF_Quizzes_solvers).child(SharedModel.getCourseId()).child(SharedModel.getUserId()).child(SharedModel.getQUIZName()).setValue(grade_msg)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getContext(), R.string.suc, Toast.LENGTH_SHORT).show();
                requireActivity().onBackPressed();
            }
        });
    }

}