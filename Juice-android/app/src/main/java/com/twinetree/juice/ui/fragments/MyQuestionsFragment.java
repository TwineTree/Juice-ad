package com.twinetree.juice.ui.fragments;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.clans.fab.FloatingActionButton;
import com.twinetree.juice.R;
import com.twinetree.juice.ui.activity.ImageActivity;
import com.twinetree.juice.ui.activity.VideoActivity;
import com.twinetree.juice.ui.adapter.MyQuestionsListAdapter;

import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyQuestionsFragment extends Fragment implements View.OnClickListener {

    private final int IMAGE_CAPTURE_ID = 0;
    private final int VIDEO_CAPTURE_ID = 1;

    private RecyclerView questionList;
    private FloatingActionButton text, image, video;

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

        Activity a = getActivity();

        questionList = (RecyclerView) a.findViewById(R.id.fragment_my_questions_list);
        text = (FloatingActionButton) a.findViewById(R.id.menu_item_text);
        image = (FloatingActionButton) a.findViewById(R.id.menu_item_image);
        video = (FloatingActionButton) a.findViewById(R.id.menu_item_video);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        questionList.setLayoutManager(layoutManager);

        questionList.setAdapter(new MyQuestionsListAdapter(getContext()));

        text.setOnClickListener(this);
        image.setOnClickListener(this);
        video.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menu_item_text:
                new QuestionInputDialogFragment().show(getFragmentManager(), "Dialog");
                break;
            case R.id.menu_item_image:
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, IMAGE_CAPTURE_ID);
                }
                break;
            case R.id.menu_item_video:
                Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                if (takeVideoIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(takeVideoIntent, VIDEO_CAPTURE_ID);
                }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bundle extras;
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case IMAGE_CAPTURE_ID:
                    extras = data.getExtras();

                    Bitmap bitmap = (Bitmap) extras.get("data");

                    Intent imageIntent = new Intent(getActivity().getApplicationContext(), ImageActivity.class);
                    imageIntent.putExtra(getResources().getString(R.string.get_image_tag), bitmap);
                    startActivity(imageIntent);
                    break;
                case VIDEO_CAPTURE_ID:
                    Uri videoUri = data.getData();

                    Intent videoIntent = new Intent(getActivity().getApplicationContext(), VideoActivity.class);
                    videoIntent.putExtra(getResources().getString(R.string.get_video_tag), videoUri);
                    startActivity(videoIntent);
                    break;
            }

        }
    }
}
