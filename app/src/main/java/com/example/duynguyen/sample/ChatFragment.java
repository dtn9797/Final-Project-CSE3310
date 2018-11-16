package com.example.duynguyen.sample;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.duynguyen.sample.model.User;
import com.google.gson.Gson;

public class ChatFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getInfo();

        return inflater.inflate(R.layout.fragment_chat, container, false);
    }

    private void getInfo() {
        final SharedPreferences mPrefs = getActivity().getPreferences(getActivity().MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString("MyObject", "");
        User obj = gson.fromJson(json, User.class);
    }

}
