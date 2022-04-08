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
import com.abdo.eduapp.adapters.CourcesRecyclerAdapter;
import com.abdo.eduapp.databinding.FragmentDrHomeBinding;
import com.abdo.eduapp.models.ModelAuthCache;
import com.abdo.eduapp.models.ModelCourse;
import com.abdo.eduapp.models.RegModel;
import com.abdo.eduapp.models.SharedModel;
import com.abdo.eduapp.ui.TypeFragment;
import com.abdo.eduapp.utils.Const;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class DrHomeFragment extends Fragment {

    FragmentDrHomeBinding binding;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    ArrayList<RegModel> list = new ArrayList();

    ArrayList<ModelCourse> courses = new ArrayList<>();
    CourcesRecyclerAdapter adapter = new CourcesRecyclerAdapter();

    ArrayList<ModelAuthCache> caches = new ArrayList<>();




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dr_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentDrHomeBinding.bind(view);
        caches.add(new ModelAuthCache(SharedModel.getUserName(),SharedModel.getUserId(),2));
        SharedModel.cache(caches);
        getCourses();
        checktype();



    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private void checktype(){

        ref.child(Const.REG_DR).child(SharedModel.getUserId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.add(snapshot.getValue(RegModel.class));
                onclicks();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void onclicks(){
        String tittle = list.get(0).getUsername();
        SharedModel.setUserName(tittle);
        onadabterclicks();
        binding.userTxt.setText(""+tittle);
        binding.drSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                SharedModel.delete(caches.get(0));
                if (SharedModel.cache==true){
                   requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame,new TypeFragment()).commit();
                }
                else{
                    requireActivity().onBackPressed();
                }

            }
        });

        binding.createCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame,new Create_CourseFragment(),"create").addToBackStack("create").commit();
            }
        });

    }

    public void getCourses(){

        courses.clear();

        ref.child(Const.COURSES).orderByChild(Const.REF_INSTRUCTOR_ID).equalTo(SharedModel.getUserId())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot snap: snapshot.getChildren()) {
                            courses .add(snap.getValue(ModelCourse.class));
                        }

                        if(courses.size() != 0){

                            adapter.setList(courses);
                            binding.recyclerViewHomeDoctor.setAdapter(adapter);
                        }

                        binding.createCourseButton.setVisibility(View.VISIBLE);
                        binding.drSignout.setVisibility(View.VISIBLE);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(requireContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


    }

    private void onadabterclicks(){


        adapter.setOnItemClick(new CourcesRecyclerAdapter.OnItemClick() {
            @Override
            public void OnClick(String CourseId) {

                SharedModel.setCourseId(CourseId);

                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame,new CouseFragment(),"details")
                        .addToBackStack("details")
                        .commit();

            }
        });
    }



}