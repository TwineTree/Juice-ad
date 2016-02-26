package com.twinetree.juice.ui.activity;

import android.app.FragmentManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.twinetree.juice.MyApplication;
import com.twinetree.juice.R;
import com.twinetree.juice.api.ApiResponse;
import com.twinetree.juice.api.Url;
import com.twinetree.juice.datasets.User;
import com.twinetree.juice.ui.adapter.AMainNavigationListAdapter;
import com.twinetree.juice.ui.fragments.AMainFragment;
import com.twinetree.juice.ui.fragments.AMainNavigationFragment;
import com.twinetree.juice.ui.fragments.MyQuestionsFragment;
import com.twinetree.juice.ui.fragments.QuestionInputDialogFragment;
import com.twinetree.juice.util.BearerRequest;
import com.twinetree.juice.util.JsonBearerRequest;

import org.apache.http.HttpVersion;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements ListView.OnItemClickListener,
        QuestionInputDialogFragment.Callback {

    private final String QUESTION_TEXT_TAG = "QUESTION-TEXT";
    private final String POSITIVE_TEXT_TAG = "POSITIVE-TEXT";
    private final String NEGATIVE_TEXT_TAG = "NEGATIVE-TEXT";
    private final String IMAGE_URL_TAG = "IMAGE-URL";
    private final String VIDEO_URL_TAG = "VIDEO-URL";

    private static DrawerLayout drawerLayout;
    private static android.support.v4.app.FragmentManager fragmentManager;

    private String[] options;
    private ListView drawerList;
    private ActionBarDrawerToggle drawerToggle;
    private Toolbar toolbar;
    private ProgressBar loading;

    private final String MAIN_FRAGMENT_TAG = "MAIN-FRAGMENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        options = getResources().getStringArray(R.array.drawer_list);
        fragmentManager = getSupportFragmentManager();

        loading = (ProgressBar) findViewById(R.id.activity_main_loading);

        fragmentManager.beginTransaction()
                .add(R.id.activity_main_frame, new AMainFragment(), MAIN_FRAGMENT_TAG)
                .commit();

        initToolbar();
        initDrawerFragment();
    }

    private void initDrawerFragment() {
        AMainNavigationFragment drawerFragment = (AMainNavigationFragment)
                getSupportFragmentManager().findFragmentById(R.id.activity_main_navigation_fragment);
        drawerFragment.setUp(R.id.activity_main_navigation_fragment,
                (DrawerLayout) findViewById(R.id.activity_main_drawer),
                toolbar,
                "Rishabh Makhija",
                "rishabh.makhija07@gmail.com",
                "",
                this);
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
    }

    public static void addFragment(Fragment fragment, String tag) {
        fragmentManager.beginTransaction()
                .add(R.id.activity_main_frame, fragment, tag)
                .addToBackStack(null)
                .commit();
        //drawerLayout.closeDrawer(Gravity.LEFT);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 2:
                loading.setVisibility(View.VISIBLE);
                getMyQuestions();
                break;
        }
    }

    private void getMyQuestions() {
        User user = new User(MainActivity.this);
        BearerRequest request = new BearerRequest(Request.Method.GET, Url.getQuestions(0, 10, user.getId()),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loading.setVisibility(View.GONE);

                        ApiResponse.setMyQuestionsResponse(response);

                        fragmentManager.beginTransaction()
                                .add(R.id.activity_main_frame, new MyQuestionsFragment(), MAIN_FRAGMENT_TAG)
                                .addToBackStack(null)
                                .commit();
                        drawerLayout.closeDrawer(Gravity.LEFT);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        MyApplication.queue.add(request);
    }

    @Override
    public void onSuccessfulInput(String questionText, String positiveText,
                                  String negativeText, String imageUrl, String videoUrl, Bitmap bitmap,
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
                                Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
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
                .load(result)
                .setMultipartFile("", "image/png", image)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        Toast.makeText(MainActivity.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
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
