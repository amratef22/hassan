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
import com.abdo.eduapp.adapters.StdQuizzesRecyclerAdapter;
import com.abdo.eduapp.databinding.FragmentQuizzesBinding;
import com.abdo.eduapp.models.SharedModel;
import com.abdo.eduapp.utils.Const;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class QuizzesFragment extends Fragment {

    FragmentQuizzesBinding binding;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    ArrayList<String> list = new ArrayList<>();
    StdQuizzesRecyclerAdapter adapter = new StdQuizzesRecyclerAdapter();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quizzes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentQuizzesBinding.bind(view);
        load();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void load(){
        list.clear();
        ref.child(Const.REF_Quizzes).child(SharedModel.getCourseId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    list.add(snapshot1.getKey());
                }
                adapter.setList(list);
                binding.recyclerViewStdQuizzes.setAdapter(adapter);
                onadabterclicks();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void onadabterclicks(){


        adapter.setOnItemClick(new StdQuizzesRecyclerAdapter.OnItemClick() {
            @Override
            public void OnClick(String QUIZName) {
                check(QUIZName);

            }
        });
    }
    private void check(String QUIZName){
        ref.child(Const.REF_Quizzes_solvers).child(SharedModel.getCourseId()).child(SharedModel.getUserId()).child(QUIZName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null) {
                    Toast.makeText(requireContext(), R.string.solve   , Toast.LENGTH_SHORT).show();
                }else {
                    requireActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frame,new SolveQuizFragment(),"details")
                            .addToBackStack("details")
                            .commit();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}