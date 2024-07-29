package com.abdo.eduapp.ui.student.attendance;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.abdo.eduapp.R;
import com.abdo.eduapp.databinding.FragmentAttendBinding;
import com.abdo.eduapp.models.SharedModel;
import com.abdo.eduapp.utils.Const;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.Result;

public class AttendFragment extends Fragment {
    private CodeScanner mCodeScanner;
    String code;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

    FragmentAttendBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_attend, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentAttendBinding.bind(view);
        mCodeScanner = new CodeScanner(getContext(), binding.scannerView);
        decode();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void decode(){
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        code = result.getText();
                        code = code.replaceAll("[^a-zA-Z0-9&_ ا-ي]","");
                        check(code);
                    }
                });
            }
        });
        binding.scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    public void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }
    private void check(String code){
        ref.child(Const.REF_ATTENDANCE).child(SharedModel.getCourseId()).child(code).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null) {
                    attend(code);
                }else {
                    Toast.makeText(requireContext(), R.string.notvalid_code , Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void attend(String code){

        ref.child(Const.REF_ATTENDANCE).child(SharedModel.getCourseId()).child(code).child(SharedModel.getUserId())
                .setValue(SharedModel.getUserId())
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(requireContext(), R.string.fal , Toast.LENGTH_SHORT).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                Toast.makeText(requireContext(), R.string.suc , Toast.LENGTH_SHORT).show();
            }
        });

    }
}