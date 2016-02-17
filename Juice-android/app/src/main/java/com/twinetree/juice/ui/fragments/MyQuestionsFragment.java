package com.twinetree.juice.ui.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.github.clans.fab.FloatingActionButton;
import com.twinetree.juice.MyApplication;
import com.twinetree.juice.R;
import com.twinetree.juice.api.Url;
import com.twinetree.juice.util.JsonBearerRequest;

import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyQuestionsFragment extends Fragment implements View.OnClickListener {

    private RecyclerView questionList;
    private FloatingActionButton text;

    public MyQuestionsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_questions, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        questionList = (RecyclerView) getActivity().findViewById(R.id.fragment_my_questions_list);
        text = (FloatingActionButton) getActivity().findViewById(R.id.menu_item_text);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        questionList.setLayoutManager(layoutManager);

        text.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menu_item_text:
                new QuestionInputDialogFragment().show(getFragmentManager(), "Dialog");
                break;
        }
    }
}
