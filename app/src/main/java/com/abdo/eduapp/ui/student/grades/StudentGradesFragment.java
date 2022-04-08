package com.abdo.eduapp.ui.student.grades;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.abdo.eduapp.R;
import com.abdo.eduapp.adapters.QuizzesGradesAdapter;
import com.abdo.eduapp.databinding.FragmentStudentGradesBinding;
import com.abdo.eduapp.models.ModelQuizGrades;
import com.abdo.eduapp.models.SharedModel;

import java.util.ArrayList;


public class StudentGradesFragment extends Fragment {


    FragmentStudentGradesBinding binding;
    StudentGradesViewModel viewModel;
    ArrayList<ModelQuizGrades> list = new ArrayList<>();

    QuizzesGradesAdapter adapter = new QuizzesGradesAdapter();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student_grades, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentStudentGradesBinding.bind(view);
        viewModel =new ViewModelProvider(this).get(StudentGradesViewModel.class);
        binding.nameTxt.setText(SharedModel.getSelected_Student_name());

        load();
        getQuizzes();



    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    private void load(){
        viewModel.getAttendance();
        viewModel.attendance.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.attendTxt.setText(integer+"");
            }
        });


    }


    private void getQuizzes(){
        viewModel.getGrades();
        viewModel.grades.observe(getViewLifecycleOwner(), new Observer<ArrayList<ModelQuizGrades>>() {
            @Override
            public void onChanged(ArrayList<ModelQuizGrades> modelQuizGrades) {

                list = modelQuizGrades ;
                show();
            }
        });
    }
    private void show(){
        binding.quizzesTxt.setText(list.size()+"");
        adapter.setList(list);
        binding.recyclerViewQuizzesGrades.setAdapter(adapter);
        binding.recyclerViewQuizzesGrades.smoothScrollToPosition(adapter.getItemCount());
    }

}