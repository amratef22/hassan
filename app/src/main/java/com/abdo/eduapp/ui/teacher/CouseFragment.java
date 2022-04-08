package com.abdo.eduapp.ui.teacher;

import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.abdo.eduapp.R;
import com.abdo.eduapp.databinding.FragmentCouseBinding;
import com.abdo.eduapp.models.SharedModel;
import com.abdo.eduapp.ui.chat.ChatFragment;
import com.abdo.eduapp.ui.teacher.grades.BaseGradesFragment;
import com.abdo.eduapp.ui.teacher.matrials.MatrialFragment;


public class CouseFragment extends Fragment {


    FragmentCouseBinding binding ;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_couse, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentCouseBinding.bind(view);
        binding.courseCodeTxt.setText(SharedModel.getCourseId());

        onClicks();



    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null ;
    }


    private void onClicks() {


        binding.copyCourseCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager cp =(ClipboardManager) view.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                cp.setText(binding.courseCodeTxt.getText());
                Toast.makeText(getContext(), R.string.copied , Toast.LENGTH_SHORT).show();



            }
        });

        binding.drOpenChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame,new ChatFragment(), "chat").addToBackStack("chat").commit();
            }
        });

        binding.drUploadMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame,new MatrialFragment(), "upload").addToBackStack("upload").commit();
            }
        });

        binding.btnOpenTakeAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame,new AttendanceFragment(), "att").addToBackStack("att").commit();
            }
        });
        binding.drMakeQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame,new BaseAddQuizFragment(), "qz").addToBackStack("qz").commit();
            }
        });

        binding.drShowAllGrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame,new BaseGradesFragment(),"b").addToBackStack("b").commit();
            }
        });
    }

    }