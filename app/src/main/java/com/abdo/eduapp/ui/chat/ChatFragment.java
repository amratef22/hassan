package com.abdo.eduapp.ui.chat;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.abdo.eduapp.R;
import com.abdo.eduapp.adapters.AdapterRecyclerChat;
import com.abdo.eduapp.databinding.FragmentChatBinding;
import com.abdo.eduapp.models.ModelChat;
import com.abdo.eduapp.models.SharedModel;
import com.abdo.eduapp.utils.Const;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ChatFragment extends Fragment {

    FragmentChatBinding binding;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    private ArrayList<ModelChat> list = new ArrayList<>();

    //ChatRecyclerAdapter adapter = new ChatRecyclerAdapter();

    AdapterRecyclerChat adapter = new AdapterRecyclerChat();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentChatBinding.bind(view);
        onClicks();
        getMassages();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void onClicks(){
        binding.chatBar.setTitle(SharedModel.getCourseName());

        binding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendmsg();
            }
        });
    }

    private void sendmsg(){
        String msg = binding.msgEdit.getText().toString().trim();
        String courseId = SharedModel.getCourseId();

        if(msg.isEmpty()){
            binding.msgEdit.setError(getString(R.string.Required_msg));
        }
        else{
            ref.child(Const.CHATS).child(courseId).push().setValue(new ModelChat(SharedModel.getUserName(),msg))
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    binding.msgEdit.setText("");

                }
            });
        }


    }


    public void getMassages (){

        String courseId = SharedModel.getCourseId();

        ref.child(Const.CHATS).child(courseId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.e("TAG", "onDataChange: "+snapshot );
                list.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    list.add(snapshot1.getValue(ModelChat.class));
                }

                adapter.setmMessageList(list);
                binding.recyclerChat.setAdapter(adapter);
                binding.recyclerChat.smoothScrollToPosition(adapter.getItemCount());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}