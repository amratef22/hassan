package com.abdo.eduapp.ui.teacher;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.abdo.eduapp.R;
import com.abdo.eduapp.adapters.CreateQuizzesAdapter;
import com.abdo.eduapp.databinding.FragmentCreateQuizBinding;
import com.abdo.eduapp.models.ModelDrQuiz;
import com.abdo.eduapp.models.SharedModel;
import com.abdo.eduapp.utils.Const;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class CreateQuizFragment extends Fragment {

    FragmentCreateQuizBinding binding;
    ArrayList<ModelDrQuiz> list = new ArrayList<>();
    CreateQuizzesAdapter adapter = new CreateQuizzesAdapter();
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_quiz, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentCreateQuizBinding.bind(view);
        binding.toolbar.setTitle(SharedModel.getQUIZName());
        createlist();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void createlist(){

        for (int i = 0; i< SharedModel.getqNo(); i++){
            list.add(new ModelDrQuiz("Q"+(i+1) , "", "","","","",""));
        }
        onclicks();
    }


    private void onclicks(){
        adapter.setList(list);
        binding.recyclerViewCreateQuiz.setAdapter(adapter);

        binding.createQuizBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkempty();

            }
        });
    }

    private void checkempty(){
        int x =1;
        for (int i = 0; i< SharedModel.getqNo(); i++){
            if(list.get(i).getQuestion().isEmpty()){
                x=0;
                break;
            }
            if(list.get(i).getAnswer1().isEmpty()){
                x=0;
                break;
            }
            if(list.get(i).getAnswer2().isEmpty()){
                x=0;
                break;
            }
            if(list.get(i).getAnswer3().isEmpty()){
                x=0;
                break;
            }
            if(list.get(i).getAnswer4().isEmpty()){
                x=0;
                break;
            }
            if(list.get(i).getCorrectAnswer().isEmpty()){

                x=0;
                break;
            }
        }
        if(x==0){
            Toast.makeText(getContext(), R.string.full_all , Toast.LENGTH_SHORT).show();

        }
        else{
            SendData();
        }


    }

    private void SendData(){


        ref.child(Const.REF_Quizzes).child(SharedModel.getCourseId()).child(SharedModel.getQUIZName()).setValue(list).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getContext(), R.string.suc, Toast.LENGTH_SHORT).show();
                requireActivity().onBackPressed();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), R.string.fal, Toast.LENGTH_SHORT).show();
            }
        });

    }




}