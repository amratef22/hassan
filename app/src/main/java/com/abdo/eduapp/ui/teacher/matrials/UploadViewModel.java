package com.abdo.eduapp.ui.teacher.matrials;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.abdo.eduapp.utils.Const;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class UploadViewModel extends ViewModel {


    private DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    private StorageReference storage = FirebaseStorage.getInstance().getReference();
    private StorageReference sRef = FirebaseStorage.getInstance().getReference();


    MutableLiveData<String> uploadFile = new MutableLiveData<>();
    MutableLiveData<Integer> start = new MutableLiveData<>();


    public void uploadFile(Uri filePath, String courseId , String filename) {

        if (filePath != null) {
            start.setValue(1);
            sRef = storage.child("file/" + courseId +"/" +System.currentTimeMillis());

            sRef.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                    final StorageReference filePath = sRef;


                    filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            String finalpath = uri.toString();

                            ref.child(Const.REF_FILES).child(courseId).child(filename).setValue(finalpath);
                            uploadFile.setValue("Uploaded");
                        }
                    });
                }
            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            uploadFile.setValue(e.getLocalizedMessage());
                        }
                    });


        }
    }
}
