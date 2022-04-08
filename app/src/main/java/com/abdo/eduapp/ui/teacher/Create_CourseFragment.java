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
import com.abdo.eduapp.databinding.FragmentCreateCourseBinding;
import com.abdo.eduapp.models.ModelChat;
import com.abdo.eduapp.models.ModelCourse;
import com.abdo.eduapp.models.SharedModel;
import com.abdo.eduapp.utils.Const;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Create_CourseFragment extends Fragment {

    FragmentCreateCourseBinding binding;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    ModelCourse modelCourse = new ModelCourse();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create__course, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentCreateCourseBinding.bind(view);
        on_clicks();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void on_clicks(){

        binding.createCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validation();

            }
        });
    }
    private void validation(){
        String coursename = binding.cousreNameEdit.getText().toString().trim();
        String attgrade = binding.attendEdit.getText().toString().trim();
        String projgrade = binding.projectEdit.getText().toString().trim();
        String quzgrade = binding.quizEdit.getText().toString().trim();

        if(coursename.isEmpty()){
            binding.cousreNameEdit.setError(getString(R.string.Required_msg));
        }
        else if(attgrade.isEmpty()){
            binding.attendEdit.setError(getString(R.string.Required_msg));
        }
        else if(projgrade.isEmpty()){
            binding.projectEdit.setError(getString(R.string.Required_msg));
        }
        else if(quzgrade.isEmpty()){
            binding.quizEdit.setError(getString(R.string.Required_msg));
        }
        else{

            modelCourse.setCourseName(coursename );
            modelCourse.setAssignmentGrade(Double.parseDouble(quzgrade));
            modelCourse.setAttendanceGrade(Double.parseDouble(attgrade));
            modelCourse.setProjectsGrade(Double.parseDouble(projgrade));
            modelCourse.setInstructorId(SharedModel.getUserId());
            modelCourse.setCourseId(ref.push().getKey());
            SendData(modelCourse);

        }


    }

    private void SendData(ModelCourse model){

        ref.child(Const.COURSES).child(model.getCourseId()).setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                ref.child(Const.CHATS).child(model.getCourseId()).push().setValue(new ModelChat("System","Hello Message"));
                Toast.makeText(requireActivity(), "Course Created Successfully", Toast.LENGTH_SHORT).show();
                requireActivity().onBackPressed();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(requireActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}