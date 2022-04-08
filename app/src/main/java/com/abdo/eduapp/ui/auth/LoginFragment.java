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
import com.abdo.eduapp.databinding.FragmentLoginBinding;
import com.abdo.eduapp.models.SharedModel;
import com.abdo.eduapp.ui.student.hmoe.StdHomeFragment;
import com.abdo.eduapp.ui.teacher.DrHomeFragment;
import com.abdo.eduapp.utils.Const;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LoginFragment extends Fragment {

    FragmentLoginBinding binding;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    //DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference();
    FirebaseAuth log_auth = FirebaseAuth.getInstance();

    ArrayList<String> students = new ArrayList();
    ArrayList<String> doctors = new ArrayList();





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentLoginBinding.bind(view);
        int id = SharedModel.get_id();
        checktype1();
        checktype2();
        on_clicks(id);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void on_clicks(int id ){
        binding.barLogin.setVisibility(View.GONE);
        binding.reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (id == 1){
                    requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame,new StudRegFragment() , "stdreg").commit();
                }
                else{
                    requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame,new DrRegFragment() , "stdreg").commit();
                }
            }
        });

        binding.loginBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                binding.barLogin.setVisibility(View.VISIBLE);
                Validation();



            }
        });
    }
    private void Validation(){

        String email = binding.emailEdit.getText().toString().trim();
        String password = binding.passEdit.getText().toString().trim();


        if (email.isEmpty()){
            binding.emailEdit.setError("required");
        }
        else if (password.isEmpty()){
            binding.passEdit.setError("required");
        }
        else{
            login(email , password);
        }
    }

    private void login(String email ,String password ){

        log_auth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {

                binding.barLogin.setVisibility(View.GONE);

                String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                SharedModel.setUserId(user_id);

                for(int i =0 ; i <students.size();i++){
                    if(user_id .equals(students.get(i)) ){
                        SharedModel.setType_id(1);
                    }
                }

                for(int i =0 ; i <doctors.size();i++){
                    if(user_id .equals(doctors.get(i)) ){
                        SharedModel.setType_id(2);
                    }
                }

                if(SharedModel.get_id() == 1 && SharedModel.getType_id() ==1 ){
                    requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame , new StdHomeFragment()).commit();

                }

                else if(SharedModel.get_id() == 2 && SharedModel.getType_id() ==2 ){
                    requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame , new DrHomeFragment()).commit();
                }
                else{
                    Toast.makeText(requireActivity(), "Wrong account Type", Toast.LENGTH_LONG).show();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                binding.barLogin.setVisibility(View.GONE);
                Toast.makeText(requireActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void checktype1(){

        ref.child(Const.REG_STD).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for( DataSnapshot snapshot1 : snapshot.getChildren()){
                    students.add(snapshot1.getKey());
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });



    }

   private void checktype2(){
        ref.child(Const.REG_DR).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for( DataSnapshot snapshot2 : snapshot.getChildren()){
                    doctors.add(snapshot2.getKey());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


    }





    }

