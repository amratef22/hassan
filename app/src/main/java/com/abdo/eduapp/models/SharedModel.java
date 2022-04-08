package com.abdo.eduapp.models;

import androidx.annotation.NonNull;

import com.abdo.eduapp.local.MyRoomDatabase;
import com.abdo.eduapp.utils.Const;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SharedModel {

    public static int id =1;
    public static int type_id =0;
    public static int wrong_counter =0;
    public static int added_counter =0;

    public static boolean cache =false;

    public static String userId = "";

    private static String Selected_Student = "";
    private static String Selected_Student_name = "";


    private static String Selected_pdf = "";

    private static String Selected_pdf_Url = "";

    public static String getSelected_pdf_Url() {
        return Selected_pdf_Url;
    }

    public static void setSelected_pdf_Url(String selected_pdf_Url) {
        Selected_pdf_Url = selected_pdf_Url;
    }

    public static String getSelected_pdf() {
        return Selected_pdf;
    }

    public static void setSelected_pdf(String selected_pdf) {
        Selected_pdf = selected_pdf;
    }


    public static String getSelected_Student_name() {
        return Selected_Student_name;
    }

    public static void setSelected_Student_name(String selected_Student_name) {
        Selected_Student_name = selected_Student_name;
    }

    public static String getSelected_Student() {
        return Selected_Student;
    }

    public static void setSelected_Student(String selected_Student) {
        Selected_Student = selected_Student;
    }

    public static void set_id(int input){
        id = input;
    }
    public static int get_id(){
        return id;
    }

    public static ArrayList<String> codes = new ArrayList<>();

    public static ArrayList<String> answers = new ArrayList<>();
    public static ArrayList<String> all_answers = new ArrayList<>();

    public static ArrayList<String> getAll_answers() {
        return all_answers;
    }

    public static void setAll_answers(ArrayList<String> all_answers) {
        SharedModel.all_answers = all_answers;
    }

    public static ArrayList<String> getAnswers() {
        return answers;
    }

    public static void setAnswers(ArrayList<String> answers) {
        SharedModel.answers = answers;
    }

    public static String CourseId = "";
    public static String CourseName = "";
    public static String UserName = "";

    public static int Q_NO = 0;
    public static String QUIZName = "";

    public static String getQUIZName() {
        return QUIZName;
    }

    public static void setQUIZName(String QUIZName) {
        SharedModel.QUIZName = QUIZName;
    }

    public static void setCodes(ArrayList<String> codes) {
        SharedModel.codes = codes;
    }

    public static int getqNo() {
        return Q_NO;
    }

    public static void setqNo(int qNo) {
        Q_NO = qNo;
    }

    public static String getCourseName() {
        return CourseName;
    }

    public static void setCourseName(String courseName) {
        CourseName = courseName;
    }

    public static String getUserName() {
        return UserName;
    }

    public static void setUserName(String userName) {
        UserName = userName;
    }

    public static String getCourseId() {
        return CourseId;
    }

    public static void setCourseId(String courseId) {
        CourseId = courseId;
    }

    public static ArrayList<String> ReturnCodes(){
        return codes;

    }

    public static void getCodes(){


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        ref.child(Const.STD_COURCES).child(SharedModel.getUserId())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        SharedModel.codes.clear();
                        for (DataSnapshot snap: snapshot.getChildren()) {
                            SharedModel.codes .add(snap.getKey());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });

    }



    public static int getType_id() {
        return type_id;
    }

    public static void setType_id(int type_id) {
        SharedModel.type_id = type_id;
    }

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        SharedModel.id = id;
    }

    public static String getUserId() {
        return userId;
    }

    public static void setUserId(String userId) {
        SharedModel.userId = userId;
    }

    public static void cache(List<ModelAuthCache> list){

        MyRoomDatabase.getInstance().getDao().insert(list).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onError(@NonNull Throwable e) {

            }
        });

    }

    public static void delete(ModelAuthCache model){

        MyRoomDatabase.getInstance().getDao().delete(model).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });

    }



}





