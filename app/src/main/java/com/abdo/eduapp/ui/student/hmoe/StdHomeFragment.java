package com.abdo.eduapp.ui.student.hmoe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.abdo.eduapp.R;
import com.abdo.eduapp.adapters.CourcesRecyclerAdapter;
import com.abdo.eduapp.databinding.FragmentStdHomeBinding;
import com.abdo.eduapp.models.ModelAuthCache;
import com.abdo.eduapp.models.ModelCourse;
import com.abdo.eduapp.models.RegModel;
import com.abdo.eduapp.models.SharedModel;
import com.abdo.eduapp.ui.TypeFragment;
import com.abdo.eduapp.ui.student.StdCourseFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class StdHomeFragment extends Fragment {

    FragmentStdHomeBinding binding;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    RegModel userdata ;

    int added =0;
    int wrongid =0;

    ArrayList<ModelCourse> courses = new ArrayList<>();

    CourcesRecyclerAdapter adapter = new CourcesRecyclerAdapter();
    ArrayList<ModelAuthCache> caches = new ArrayList<>();

    StdHomeViewModel viewModel ;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_std_home, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentStdHomeBinding.bind(view);

        viewModel = new ViewModelProvider(this).get(StdHomeViewModel.class);


        checktype();




    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private void checktype(){



        viewModel.checktype();
        viewModel.userdata.observe(getViewLifecycleOwner(), new Observer<RegModel>() {
            @Override
            public void onChanged(RegModel regModel) {
                userdata = regModel;

                caches.add(new ModelAuthCache(userdata.getUsername(), SharedModel.getUserId(), 1));
                SharedModel.cache(caches);

                viewModel.Mcources.observe(getViewLifecycleOwner(), new Observer<ArrayList<ModelCourse>>() {
                    @Override
                    public void onChanged(ArrayList<ModelCourse> modelCourses) {
                        courses = modelCourses;
                        show_data();

                    }
                });
                onclicks();
            }
        });
    }

    private void onclicks(){


        String tittle = userdata.getUsername();
        onadabterclicks();
        SharedModel.setUserName(tittle);



        binding.userTxt.setText(""+tittle);
        binding.stdSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                SharedModel.delete(caches.get(0));

                if (SharedModel.cache == true ){
                    requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame,new TypeFragment()).commit();
                }
                else{
                    requireActivity().onBackPressed();
                }

            }
        });

        binding.addCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validation();
            }
        });
    }

    private void validation(){
        String courseid = binding.courseIdEdit.getText().toString().trim();

        if ((courseid.isEmpty())){
            binding.courseIdEdit.setError(getString(R.string.Required_msg));
        }
        else{
            viewModel.CheckCourse(courseid);
            viewModel.wrongid.observe(getViewLifecycleOwner(), new Observer<Integer>() {
                @Override
                public void onChanged(Integer integer) {
                    if(wrongid != integer){
                        wrongid = integer;
                        Toast.makeText(requireContext(), R.string.course_not_found, Toast.LENGTH_SHORT).show();
                    }
                }
            });
            viewModel.added.observe(getViewLifecycleOwner(), new Observer<Integer>() {
                @Override
                public void onChanged(Integer integer) {
                    if(added != integer){
                        added=integer;
                        Toast.makeText(requireContext(), R.string.Course_Added_Successfully, Toast.LENGTH_SHORT).show();
                        binding.courseIdEdit.setText("");

                    }
                }
            });
        }
    }


    private void show_data(){
        adapter.setList(courses);
        binding.recyclerViewHomeStudent.setAdapter(adapter);
    }


    private void onadabterclicks(){


        adapter.setOnItemClick(new CourcesRecyclerAdapter.OnItemClick() {
            @Override
            public void OnClick(String CourseId) {

                SharedModel.setCourseId(CourseId);

                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame,new StdCourseFragment(),"details")
                        .addToBackStack("details")
                        .commit();

            }
        });
    }





}