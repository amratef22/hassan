package com.abdo.eduapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.abdo.eduapp.databinding.ActivityMainBinding;
import com.abdo.eduapp.local.MyRoomDatabase;
import com.abdo.eduapp.models.ModelAuthCache;
import com.abdo.eduapp.models.SharedModel;
import com.abdo.eduapp.ui.TypeFragment;
import com.abdo.eduapp.ui.student.hmoe.StdHomeFragment;
import com.abdo.eduapp.ui.teacher.DrHomeFragment;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getcache();

    }

    private void splash(){

        if(SharedModel.cache == false){
            getSupportFragmentManager().beginTransaction().replace(binding.frame.getId(),new TypeFragment()).commit();
        }
        else{
            if (SharedModel.getType_id() == 1){
                SharedModel.getCodes();
                getSupportFragmentManager().beginTransaction().replace(binding.frame.getId(),new StdHomeFragment()).commit();
            }
            else if (SharedModel.getType_id() == 2){
                getSupportFragmentManager().beginTransaction().replace(binding.frame.getId(),new DrHomeFragment()).commit();
            }
            else{
                getSupportFragmentManager().beginTransaction().replace(binding.frame.getId(),new TypeFragment()).commit();
            }
        }

    }
    private   void getcache(){
        MyRoomDatabase.getInstance().getDao().getall()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<ModelAuthCache>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<ModelAuthCache> modelAuthCaches) {


                        if(modelAuthCaches.size()>0){
                            SharedModel.cache = true;
                            SharedModel.setUserId(modelAuthCaches.get(0).getUser_id());
                            SharedModel.setUserName(modelAuthCaches.get(0).getUser_name());
                            SharedModel.setType_id(modelAuthCaches.get(0).getType_id());
                            splash();
                        }
                        else{
                            splash();
                        }


                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        splash();
                    }
                });
    }










}