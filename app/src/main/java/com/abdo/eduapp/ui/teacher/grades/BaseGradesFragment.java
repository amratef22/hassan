package com.abdo.eduapp.ui.teacher.grades;

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
import com.abdo.eduapp.adapters.CourseStudentsAdapter;
import com.abdo.eduapp.databinding.FragmentBaseGradesBinding;
import com.abdo.eduapp.models.SharedModel;
import com.abdo.eduapp.ui.student.grades.StudentGradesFragment;

import java.util.ArrayList;


public class BaseGradesFragment extends Fragment {


    FragmentBaseGradesBinding binding;
    GradesViewModel gradesViewModel;
    ArrayList<String> names = new ArrayList<>();
    ArrayList<String> codes = new ArrayList<>();

    CourseStudentsAdapter adapter = new CourseStudentsAdapter();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_base_grades, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentBaseGradesBinding.bind(view);
        gradesViewModel = new ViewModelProvider(this).get(GradesViewModel.class);
        load();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private void load(){
        binding.gradesBar.setTitle(SharedModel.getCourseName());
        gradesViewModel.getStudents();

        gradesViewModel.studentscodes.observe(getViewLifecycleOwner(), new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> strings) {
                codes = strings;
            }
        });

        gradesViewModel.students.observe(getViewLifecycleOwner(), new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> strings) {
                names  = strings;
                showData();
            }
        });
    }
    private void showData(){
        adapter.setList(names);
        binding.recyclerStudents.setAdapter(adapter);
        binding.recyclerStudents.smoothScrollToPosition(adapter.getItemCount());
        onadabterclicks();
    }


    private void onadabterclicks(){


        adapter.setOnItemClick(new CourseStudentsAdapter.OnItemClick() {
            @Override
            public void OnClick(String Name) {

                SharedModel.setSelected_Student_name(Name);

                for (int i =0 ; i<names.size();i++){

                    if(Name.equals(names.get(i))){
                        SharedModel.setSelected_Student(codes.get(i));
                        break;
                    }

                }

                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame,new StudentGradesFragment(),"details")
                        .addToBackStack("details")
                        .commit();
            }
        });
    }

}