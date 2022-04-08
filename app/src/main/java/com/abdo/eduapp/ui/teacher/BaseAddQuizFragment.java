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
import com.abdo.eduapp.databinding.FragmentBaseAddQuizBinding;
import com.abdo.eduapp.models.SharedModel;
import com.abdo.eduapp.utils.Const;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class BaseAddQuizFragment extends Fragment {

    FragmentBaseAddQuizBinding binding ;

    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_base_add_quiz, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentBaseAddQuizBinding.bind(view);
        onclicks();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void onclicks(){

        binding.createqBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkempty();
            }
        });
    }

    private void checkempty(){
        String name = binding.quizNameEdit.getText().toString().trim();
        String n = binding.qnoEdit.getText().toString().trim();

        if(name.isEmpty()){
            binding.quizNameEdit.setError(getString(R.string.Required_msg));
        }
        else if (n.isEmpty()){
            binding.qnoEdit.setError(getString(R.string.Required_msg));
        }
        else{
            check(name , n);

        }
    }
    private void check(String name , String n){
        ref.child(Const.REF_Quizzes).child(SharedModel.getCourseId()).child(name).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null) {
                    Toast.makeText(requireContext(), R.string.exist_quiz  , Toast.LENGTH_SHORT).show();
                }else {
                    SharedModel.setQUIZName(name);
                    SharedModel.setqNo(Integer.parseInt(n));
                    requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame,new CreateQuizFragment(),"create").addToBackStack("create").commit();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}