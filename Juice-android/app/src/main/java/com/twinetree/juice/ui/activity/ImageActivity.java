package com.twinetree.juice.ui.activity;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

public class ImageActivity extends AppCompatActivity implements View.OnClickListener,
            QuestionInputDialogFragment.Callback {

    private final String QUESTION_TEXT_TAG = "QUESTION-TEXT";
    private final String POSITIVE_TEXT_TAG = "POSITIVE-TEXT";
    private final String NEGATIVE_TEXT_TAG = "NEGATIVE-TEXT";
    private final String IMAGE_URL_TAG = "IMAGE-URL";
    private final String VIDEO_URL_TAG = "VIDEO-URL";

    private ImageView image;
    private ImageButton cancel;
    private ImageButton done;
    private Bundle extras;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        extras = getIntent().getExtras();

        bitmap = (Bitmap) extras.get(getResources().getString(R.string.get_image_tag));
        image = (ImageView) findViewById(R.id.activity_image_image);
        cancel = (ImageButton) findViewById(R.id.activity_image_cancel);
        done = (ImageButton) findViewById(R.id.activity_image_done);

        image.setImageBitmap(bitmap);

        cancel.setOnClickListener(this);
        done.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_image_cancel:
                finish();
                break;
            case R.id.activity_image_done:
                QuestionInputDialogFragment dialogFragment = QuestionInputDialogFragment.newInstance(
                        getResources().getString(R.string.get_image_tag), "", extras, null);
                dialogFragment.show(getSupportFragmentManager(),
                        getResources().getString(R.string.questions_input_tag));
                break;
        }
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

            if (imageUrl.equals(getResources().getString(R.string.get_image_tag))) {
                Bundle args = new Bundle();
                args.putString(QUESTION_TEXT_TAG, questionText);
                args.putString(POSITIVE_TEXT_TAG, positiveText);
                args.putString(NEGATIVE_TEXT_TAG, negativeText);
                args.putString(IMAGE_URL_TAG, imageUrl);
                args.putString(VIDEO_URL_TAG, videoUrl);

                getSassUrl(bitmap, args);
            }
            else {
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
                                Toast.makeText(ImageActivity.this, "Success", Toast.LENGTH_SHORT).show();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
                MyApplication.queue.add(request);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getSassUrl(final Bitmap bitmap, final Bundle args) {
        final String blobName = UUID.randomUUID().toString() + ".png";
        Log.i("hvqwhj", blobName);
        args.putString(IMAGE_URL_TAG, "https://twinetree.blob.core.windows.net/joos/" + blobName);

        BearerRequest request = new BearerRequest(Request.Method.GET, Url.sassUrl(blobName),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            String result = object.getString("result");

                            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bytes);
                            File file = new File(Environment.getExternalStorageDirectory() +
                                    File.separator + blobName);
                            file.createNewFile();
                            FileOutputStream fo = new FileOutputStream(file);
                            fo.write(bytes.toByteArray());
                            fo.close();

                            uploadBlob(args, result, file);
                        }
                        catch (Exception e) {
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

    private void uploadBlob(final Bundle args,
                            String result,
                            final File image) {

        Ion.with(this)
                .load("PUT", result)
                .setHeader("x-ms-blob-type", "BlockBlob")
                .setFileBody(image)
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        Toast.makeText(ImageActivity.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
                        image.delete();
                        postQuestion(args);
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
                            Toast.makeText(ImageActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            });
            MyApplication.queue.add(request);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
