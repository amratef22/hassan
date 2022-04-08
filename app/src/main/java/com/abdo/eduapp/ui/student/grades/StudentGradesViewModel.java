package com.abdo.eduapp.ui.student.grades;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.abdo.eduapp.models.ModelQuizGrades;
import com.abdo.eduapp.models.SharedModel;
import com.abdo.eduapp.utils.Const;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StudentGradesViewModel extends ViewModel {


    DatabaseReference ref ;

    int i =0;

    int j =0;

    MutableLiveData<Integer> attendance = new MutableLiveData<>();
    MutableLiveData<ArrayList<ModelQuizGrades>> grades = new MutableLiveData<>();

    ArrayList<String> qnames = new ArrayList<>();

    ArrayList<ModelQuizGrades> list = new ArrayList<>();

    public StudentGradesViewModel() {
        ref = FirebaseDatabase.getInstance().getReference();

    }


    public void getAttendance(){

        ref.child(Const.REF_ATTENDANCE).child(SharedModel.getCourseId()).orderByChild(SharedModel.getSelected_Student()).equalTo(SharedModel.getSelected_Student())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.getValue()!= null){

                            for (DataSnapshot snapshot1 : snapshot.getChildren()){
                                i++;
                            }

                        }
                        attendance.setValue(i);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }

    public void getGrades(){

        ref.child(Const.REF_Quizzes_solvers).child(SharedModel.getCourseId()).child(SharedModel.getSelected_Student()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren() ) {

                    list.add(new ModelQuizGrades(snapshot1.getKey(), (String) snapshot1.getValue()));
                }
                grades.setValue(list);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


}
