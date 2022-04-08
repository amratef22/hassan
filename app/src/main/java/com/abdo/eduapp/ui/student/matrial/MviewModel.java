package com.abdo.eduapp.ui.student.matrial;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.abdo.eduapp.models.ModelPdf;
import com.abdo.eduapp.models.SharedModel;
import com.abdo.eduapp.utils.Const;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MviewModel  extends ViewModel {

    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

    ArrayList<ModelPdf> list = new ArrayList<>();
    MutableLiveData<ArrayList<ModelPdf>> matrial = new MutableLiveData<>();

    public void load_data(){
        list.clear();
        ref.child(Const.REF_FILES).child(SharedModel.getCourseId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                if(snapshot != null){
                    for(DataSnapshot snapshot1 : snapshot.getChildren()){
                        list.add(new ModelPdf(snapshot1.getKey() , (String) snapshot1.getValue()));
                    }
                    matrial.setValue(list);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }



}
