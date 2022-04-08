package com.abdo.eduapp.ui.student.hmoe;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.abdo.eduapp.models.ModelCourse;
import com.abdo.eduapp.models.RegModel;
import com.abdo.eduapp.models.SharedModel;
import com.abdo.eduapp.utils.Const;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StdHomeViewModel extends ViewModel {

    DatabaseReference ref ;
    MutableLiveData<RegModel> userdata = new MutableLiveData<>();
    MutableLiveData<Integer> wrongid = new MutableLiveData<>();
    MutableLiveData<Integer> added = new MutableLiveData<>();

    ArrayList<String> codes ;
    MutableLiveData<ArrayList<ModelCourse>> Mcources = new MutableLiveData<>();
    ArrayList<ModelCourse> courses ;

    public StdHomeViewModel() {
        ref = FirebaseDatabase.getInstance().getReference();
        codes = new ArrayList<>();
        courses = new ArrayList<>();
    }

    public void checktype(){
        ref.child(Const.REG_STD).child(SharedModel.getUserId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userdata.setValue(snapshot.getValue(RegModel.class));
                getCodes();

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void getCodes(){

        ref.child(Const.STD_COURCES).child(SharedModel.getUserId())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        codes.clear();
                        for (DataSnapshot snap: snapshot.getChildren()) {
                            codes.add(snap.getKey());
                        }
                        getCourses();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });

    }
    private void getCourses(){


        if (codes.size() != 0){
            courses.clear();
            getCourse();

        }

    }
    private void getCourse(){



        for(String CourseId : codes){
            ref.child(Const.COURSES).child(CourseId)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            courses.add(snapshot.getValue(ModelCourse.class));

                            Mcources.setValue(courses);



                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {


                        }
                    });
        }

    }


    public void CheckCourse(String courseid){

        ref.child(Const.COURSES).child(courseid).child("courseName").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null) {
                    addcourse(courseid);
                    if(SharedModel.added_counter >0 ){
                        added.setValue(1+SharedModel.added_counter);
                        SharedModel.added_counter += 1;
                    }
                    else{
                        added.setValue(1);
                        SharedModel.added_counter += 1;
                    }

                }
                else {
                    if(SharedModel.wrong_counter>0){

                        wrongid.setValue(1+SharedModel.wrong_counter);
                        SharedModel.wrong_counter += 1;
                    }
                    else{
                        wrongid.setValue(1);
                        SharedModel.wrong_counter += 1;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void addcourse(String courseid){
        ref.child(Const.STD_COURCES).child(SharedModel.getUserId()).child(courseid).setValue("joined").addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                getCodes();


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

}
