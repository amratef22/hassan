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
import com.abdo.eduapp.databinding.FragmentAttendanceBinding;
import com.abdo.eduapp.models.SharedModel;
import com.abdo.eduapp.utils.Const;
import com.abdo.eduapp.utils.Helper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AttendanceFragment extends Fragment {

    FragmentAttendanceBinding binding;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    String code;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_attendance, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding =FragmentAttendanceBinding.bind(view);
        onclicks();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void onclicks(){
        binding.bar.setVisibility(View.GONE);
        binding.genBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                code = Helper.generateCode()+"";
                binding.genCodeEdit.setText(code);
                binding.attendCodeConfirmBtn.setEnabled(true);

            }
        });
        binding.attendCodeConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.bar.setVisibility(View.VISIBLE);
                SendData(code);
            }
        });
    }
    private void SendData(String code){
        ref.child(Const.REF_ATTENDANCE).child(SharedModel.getCourseId()).child(code).setValue("success")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        binding.bar.setVisibility(View.GONE);
                        Toast.makeText(getContext(), R.string.suc, Toast.LENGTH_SHORT).show();
                        requireActivity().onBackPressed();

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), R.string.fal , Toast.LENGTH_SHORT).show();
            }
        }) ;
    }

}