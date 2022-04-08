package com.abdo.eduapp.ui.teacher.grades;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.abdo.eduapp.models.RegModel;
import com.abdo.eduapp.models.SharedModel;
import com.abdo.eduapp.utils.Const;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GradesViewModel extends ViewModel {

    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

    ArrayList<String> list = new ArrayList<>();
    ArrayList<String> names = new ArrayList<>();

    MutableLiveData<ArrayList<String>> students = new MutableLiveData<>();
    MutableLiveData<ArrayList<String>> studentscodes = new MutableLiveData<>();



    public void getStudents(){

        list.clear();
        names.clear();
        ref.child(Const.STD_COURCES).orderByChild(SharedModel.getCourseId()).equalTo("joined").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    list.add(snapshot1.getKey());
                }
                studentscodes.setValue(list);
                getnames();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void getnames(){

        for(String code : list){

            ref.child(Const.REG_STD).child(code).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    RegModel R = new RegModel();
                    R = snapshot.getValue(RegModel.class);
                    names.add(R.getUsername());

                    if (names.size() == list.size()){
                        students.setValue(names);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }



    }



}
