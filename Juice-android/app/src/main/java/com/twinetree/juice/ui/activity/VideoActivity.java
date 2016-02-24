package com.twinetree.juice.ui.activity;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.twinetree.juice.MyApplication;
import com.twinetree.juice.R;
import com.twinetree.juice.api.Url;
import com.twinetree.juice.ui.fragments.QuestionInputDialogFragment;
import com.twinetree.juice.util.BearerRequest;
import com.twinetree.juice.util.JsonBearerRequest;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.UUID;

public class VideoActivity extends AppCompatActivity implements View.OnClickListener,
        QuestionInputDialogFragment.Callback {

    private final String QUESTION_TEXT_TAG = "QUESTION-TEXT";
    private final String POSITIVE_TEXT_TAG = "POSITIVE-TEXT";
    private final String NEGATIVE_TEXT_TAG = "NEGATIVE-TEXT";
    private final String IMAGE_URL_TAG = "IMAGE-URL";
    private final String VIDEO_URL_TAG = "VIDEO-URL";

    private VideoView video;
    private ImageButton cancel;
    private ImageButton done;
    private Bundle extras;
    private Uri videoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        extras = getIntent().getExtras();

        videoUri = (Uri) extras.get(getResources().getString(R.string.get_video_tag));
        cancel = (ImageButton) findViewById(R.id.activity_video_cancel);
        done = (ImageButton) findViewById(R.id.activity_video_done);
        video = (VideoView) findViewById(R.id.activity_video_video);

        video.setVideoURI(videoUri);

        cancel.setOnClickListener(this);
        done.setOnClickListener(this);
    }

    @Override
    public void onSuccessfulInput(String questionText,
                                  String positiveText,
                                  String negativeText,
                                  String imageUrl,
                                  String videoUrl,
                                  Bitmap bitmap,
                                  Uri uri) {


        try {

            if (videoUrl.equals(getResources().getString(R.string.get_video_tag))) {
                Bundle args = new Bundle();
                args.putString(QUESTION_TEXT_TAG, questionText);
                args.putString(POSITIVE_TEXT_TAG, positiveText);
                args.putString(NEGATIVE_TEXT_TAG, negativeText);
                args.putString(IMAGE_URL_TAG, imageUrl);
                args.putString(VIDEO_URL_TAG, videoUrl);

                getSassUrl(uri, args);
            } else {
                JSONObject object = new JSONObject();
                object.put("QuestionText", questionText);
                object.put("PositiveValue", positiveText);
                object.put("NegativeValue", negativeText);
                object.put("ImageUrl", imageUrl);
                object.put("VideoUrl", videoUrl);

                JsonBearerRequest request = new JsonBearerRequest(Request.Method.POST, Url.addQuestion, object,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Toast.makeText(VideoActivity.this, "Success", Toast.LENGTH_SHORT).show();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
                MyApplication.queue.add(request);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getSassUrl(final Uri uri, final Bundle args) {
        final String blobName = UUID.randomUUID().toString() + ".mp4";
        Log.i("hvqwhj", blobName);
        args.putString(VIDEO_URL_TAG, "https://twinetree.blob.core.windows.net/joos/" + blobName);

        BearerRequest request = new BearerRequest(Request.Method.GET, Url.sassUrl(blobName),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            String result = object.getString("result");

                            File file = new File(getRealPathFromURI(uri));

                            uploadBlob(args, result, file);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        MyApplication.queue.add(request);
    }

    private File saveFile(Uri uri, String blobName) {
        String sourceFilename = uri.getPath();
        String destinationFilename = Environment.getExternalStorageDirectory().getPath() + File.separatorChar + blobName;

        try {
            File source = new File(uri.getPath());
            FileChannel src = new FileInputStream(source).getChannel();
            FileChannel dst = new FileOutputStream(destinationFilename).getChannel();
            dst.transferFrom(src, 0, src.size());
            src.close();
            dst.close();
            return  source;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void uploadBlob(final Bundle args,
                            String result,
                            final File video) {

        Ion.with(this)
                .load("PUT", result)
                .setHeader("x-ms-blob-type", "BlockBlob")
                .setFileBody(video)
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        if (e == null) {
                            Toast.makeText(VideoActivity.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
                            video.delete();
                            postQuestion(args);
                        } else {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void postQuestion(Bundle args) {
        try {
            JSONObject object = new JSONObject();
            object.put("QuestionText", args.getString(QUESTION_TEXT_TAG));
            object.put("PositiveValue", args.getString(POSITIVE_TEXT_TAG));
            object.put("NegativeValue", args.getString(NEGATIVE_TEXT_TAG));
            object.put("ImageUrl", args.getString(IMAGE_URL_TAG));
            object.put("VideoUrl", args.getString(VIDEO_URL_TAG));

            JsonBearerRequest request = new JsonBearerRequest(Request.Method.POST, Url.addQuestion, object,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Toast.makeText(VideoActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            });
            MyApplication.queue.add(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getRealPathFromURI(Uri contentUri) {
        Context context = VideoActivity.this;
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_video_cancel:
                finish();
                break;
            case R.id.activity_video_done:
                QuestionInputDialogFragment dialogFragment = QuestionInputDialogFragment.newInstance("",
                        getResources().getString(R.string.get_video_tag), extras, videoUri);
                dialogFragment.show(getSupportFragmentManager(),
                        getResources().getString(R.string.questions_input_tag));
                break;
        }
    }
}
