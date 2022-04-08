package com.abdo.eduapp.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.abdo.eduapp.R;
import com.abdo.eduapp.databinding.FragmentTypeBinding;
import com.abdo.eduapp.models.SharedModel;
import com.abdo.eduapp.ui.auth.LoginFragment;


public class TypeFragment extends Fragment {

    FragmentTypeBinding binding ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_type, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentTypeBinding.bind(view);
        SharedModel.setUserId("");
        SharedModel.setType_id(0);
        on_clicks();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void on_clicks(){
        binding.stdCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedModel.set_id(1);
                binding.drCheckbox.setChecked(false);
            }
        });
        binding.drCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedModel.set_id(2);
                binding.stdCheckbox.setChecked(false);
            }
        });

        binding.nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.stdCheckbox.isChecked() || binding.drCheckbox.isChecked() ){
                    requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame,new LoginFragment(),"login").addToBackStack("login").commit();
                }
                else{
                    Toast.makeText(requireActivity(), "choose your account type", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}