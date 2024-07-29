package com.abdo.eduapp.ui.teacher;

import static android.content.Context.WINDOW_SERVICE;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.abdo.eduapp.R;
import com.abdo.eduapp.databinding.FragmentAttendanceBinding;
import com.abdo.eduapp.models.SharedModel;
import com.abdo.eduapp.utils.Const;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.WriterException;

import java.text.SimpleDateFormat;
import java.util.Date;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class AttendanceFragment extends Fragment {

    FragmentAttendanceBinding binding;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    String code ;
    QRGEncoder qrgEncoder;
    Bitmap bitmap;

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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        String currentDateandTime = sdf.format(new Date());


        code = currentDateandTime+SharedModel.getUserId();


        showQr();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void showQr(){
        WindowManager manager = (WindowManager) getContext().getSystemService(WINDOW_SERVICE);

        Display display = manager.getDefaultDisplay();

        Point point = new Point();
        display.getSize(point);

        int width = point.x;
        int height = point.y;

        // generating dimension from width and height.
        int dimen = width < height ? width : height;
        dimen = dimen * 3 / 4;



        qrgEncoder = new QRGEncoder(code, null, QRGContents.Type.TEXT, dimen);
        try {
            bitmap = qrgEncoder.encodeAsBitmap();
            binding.idIVQrcode.setImageBitmap(bitmap);
            code = code.replaceAll("[^a-zA-Z0-9&_ ا-ي]","");
            SendData(code);
        } catch (WriterException e) {

            Log.e("Tag", e.toString());
        }

    }
    private void SendData(String code){
        ref.child(Const.REF_ATTENDANCE).child(SharedModel.getCourseId()).child(code).child("success").setValue("success")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getContext(), R.string.suc, Toast.LENGTH_SHORT).show();


                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), R.string.fal , Toast.LENGTH_SHORT).show();
            }
        }) ;
    }


}