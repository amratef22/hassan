package com.abdo.eduapp.ui.auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.abdo.eduapp.R;
import com.abdo.eduapp.databinding.FragmentDrRegBinding;
import com.abdo.eduapp.models.RegModel;
import com.abdo.eduapp.models.SharedModel;
import com.abdo.eduapp.ui.teacher.DrHomeFragment;
import com.abdo.eduapp.utils.Const;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class DrRegFragment extends Fragment {


    FragmentDrRegBinding binding ;
    DatabaseReference drreg_ref = FirebaseDatabase.getInstance().getReference();
    FirebaseAuth drreg_auth = FirebaseAuth.getInstance();

    private RegModel model ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dr_reg, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentDrRegBinding.bind(view);
        on_clicks();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void on_clicks(){


        binding.drregBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Validation();

            }
        });
    }
    private void Validation(){

        String username = binding.drregUsrEdit.getText().toString().trim();
        String email = binding.drregMailEdit.getText().toString().trim();
        String phone = binding.drregPhoneEdit.getText().toString().trim();
        String password = binding.drregPassEdit.getText().toString().trim();


        if (username.isEmpty()){
            binding.drregUsrEdit.setError("required");
        }
        else if (email.isEmpty()){
            binding.drregMailEdit.setError("required");
        }
        else if (phone.isEmpty()){
            binding.drregPhoneEdit.setError("required");
        }
        else if (password.isEmpty()){
            binding.drregPassEdit.setError("required");
        }
        else{
            Sign(username,email,phone , password);
        }
    }
    private void Sign( String username , String email ,String phone, String password){
        drreg_auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                SendData(username,email,phone , password);
                Toast.makeText(requireActivity(), "Success", Toast.LENGTH_SHORT).show();
                login(email,password);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(requireActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void SendData(String username , String email ,String phone, String password){
        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        if (id != null){
            SharedModel.setUserId(id);
            SharedModel.setUserName(username);
            drreg_ref.child(Const.REG_DR).child(id).setValue(new RegModel(username,email,phone , password));
        }


    }

    private void login(String email ,String password ){
        drreg_auth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame , new DrHomeFragment()).commit();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(requireActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

}