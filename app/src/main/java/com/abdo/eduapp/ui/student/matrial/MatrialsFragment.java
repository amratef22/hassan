package com.abdo.eduapp.ui.student.matrial;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.abdo.eduapp.R;
import com.abdo.eduapp.adapters.StdpdfRecyclerAdapter;
import com.abdo.eduapp.databinding.FragmentMatrialsBinding;
import com.abdo.eduapp.models.ModelPdf;

import java.util.ArrayList;


public class MatrialsFragment extends Fragment {


    FragmentMatrialsBinding binding;
    MviewModel viewModel;
    ArrayList<ModelPdf> list = new ArrayList<>();

    StdpdfRecyclerAdapter adapter = new StdpdfRecyclerAdapter();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_matrials, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding  = FragmentMatrialsBinding.bind(view);
        viewModel = new ViewModelProvider(this).get(MviewModel.class);

        load();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void load(){
        viewModel.load_data();
        viewModel.matrial.observe(getViewLifecycleOwner(), new Observer<ArrayList<ModelPdf>>() {
            @Override
            public void onChanged(ArrayList<ModelPdf> modelPdfs) {
                list = modelPdfs;
                show();
            }
        });

    }
    private void show(){
        adapter.setList(list);
        binding.recyclerViewStdMatrials.setAdapter(adapter);
        binding.recyclerViewStdMatrials.smoothScrollToPosition(adapter.getItemCount());
        onadabterclicks();
    }
    private void onadabterclicks(){

        adapter.setOnItemClick(new StdpdfRecyclerAdapter.OnItemClick() {
            @Override
            public void OnClick(String PdfName) {
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame,new ViewerFragment() ,"details")
                        .addToBackStack("details")
                        .commit();
            }
        });

    }
}