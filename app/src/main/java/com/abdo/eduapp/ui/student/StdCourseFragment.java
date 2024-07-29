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
import com.abdo.eduapp.databinding.FragmentStdCourseBinding;
import com.abdo.eduapp.models.SharedModel;
import com.abdo.eduapp.ui.chat.ChatFragment;
import com.abdo.eduapp.ui.student.attendance.AttendFragment;
import com.abdo.eduapp.ui.student.grades.StudentGradesFragment;
import com.abdo.eduapp.ui.student.matrial.MatrialsFragment;
import com.abdo.eduapp.utils.Const;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class StdCourseFragment extends Fragment {

    FragmentStdCourseBinding binding;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_std_course, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentStdCourseBinding.bind(view);
        onClicks();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void onClicks(){

        binding.stdCourseName.setText(SharedModel.getCourseName());
        binding.stdName.setText(SharedModel.getUserName());

        binding.stdChatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame,new ChatFragment(), "chat").addToBackStack("chat").commit();
            }
        });

        binding.stdAttendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame,new AttendFragment(), "att")
                        .addToBackStack("att").commit();
            }
        });
        binding.stdSolveQuizBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame,new QuizzesFragment(), "quiz")
                        .addToBackStack("quiz").commit();
            }
        });

        binding.stdGradesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedModel.setSelected_Student_name(SharedModel.getUserName());
                SharedModel.setSelected_Student(SharedModel.getUserId());
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame,new StudentGradesFragment(), "grades").addToBackStack("grades").commit();
            }
        });

        binding.stdMaterialBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame,new MatrialsFragment(), "mat")
                        .addToBackStack("mat").commit();

            }
        });

    }

    private void check(String code){
        ref.child(Const.REF_ATTENDANCE).child(SharedModel.getCourseId()).child(code).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null) {
                    attend(code);
                }else {
                    Toast.makeText(requireContext(), R.string.notvalid_code , Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void attend(String code){

        ref.child(Const.REF_ATTENDANCE).child(SharedModel.getCourseId()).child(code).child(SharedModel.getUserId())
                .setValue(SharedModel.getUserId())
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(requireContext(), R.string.fal , Toast.LENGTH_SHORT).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                Toast.makeText(requireContext(), R.string.suc , Toast.LENGTH_SHORT).show();
            }
        });

    }

}