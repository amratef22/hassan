package com.abdo.eduapp.ui.teacher.matrials;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.abdo.eduapp.R;
import com.abdo.eduapp.databinding.FragmentMatrialBinding;
import com.abdo.eduapp.models.SharedModel;


public class MatrialFragment extends Fragment {

    FragmentMatrialBinding binding;
    UploadViewModel viewModel;
    Uri uri ;
    String filename = "";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_matrial, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentMatrialBinding.bind(view);
        viewModel = new ViewModelProvider(this).get(UploadViewModel.class);
        onClicks();
        observer();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    private void onClicks() {
        binding.pdfView.setVisibility(View.GONE);
        binding.btnAddPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("application/pdf");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent , 0 );
            }
        });


        binding.upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (uri == null){
                    Toast.makeText(getContext(), "Please Add Pdf", Toast.LENGTH_SHORT).show();
                }else {
                    viewModel.uploadFile(uri , SharedModel.getCourseId() ,filename);
                }
            }
        });
    }

    private void observer() {

        viewModel.uploadFile.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(requireContext(), s, Toast.LENGTH_SHORT).show();
                requireActivity().onBackPressed();
            }
        });

        viewModel.start.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.upload.setClickable(false);
                binding.btnAddPdf.setClickable(false);
                binding.bar.setVisibility(View.VISIBLE);

            }
        });

    }








    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ( data != null && data.getData() != null && requestCode == 0 ){

            uri  = data.getData();
            binding.pdfView.setVisibility(View.VISIBLE);
            binding.pdfView.fromUri(uri)
                    .enableSwipe(true)
                    .enableDoubletap(true)
                    .defaultPage(0)
                    .enableAnnotationRendering(true)
                    .scrollHandle(null)
                    .enableDoubletap(true)
                    .load();

            filename = getFileName(uri);
        }

    }

    @SuppressLint("Range")
    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContext(). getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }

        result =  result.replaceAll(".pdf","");
        result = result.replaceAll("[^a-zA-Z0-9&_ ا-ي]","");



        return result;
    }


}