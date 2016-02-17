package com.twinetree.juice.ui.activity;

import android.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.twinetree.juice.MyApplication;
import com.twinetree.juice.R;
import com.twinetree.juice.api.Url;
import com.twinetree.juice.ui.adapter.AMainNavigationListAdapter;
import com.twinetree.juice.ui.fragments.AMainFragment;
import com.twinetree.juice.ui.fragments.MyQuestionsFragment;
import com.twinetree.juice.ui.fragments.QuestionInputDialogFragment;
import com.twinetree.juice.util.JsonBearerRequest;

import org.json.JSONObject;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ListView.OnItemClickListener,
        QuestionInputDialogFragment.Callback {

    private String[] options;
    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private FragmentManager fragmentManager;
    private ActionBarDrawerToggle drawerToggle;

    private final String MAIN_FRAGMENT_TAG = "MAIN-FRAGMENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        options = getResources().getStringArray(R.array.drawer_list);
        fragmentManager = getFragmentManager();

        drawerLayout = (DrawerLayout) findViewById(R.id.activity_main_drawer);
        drawerList = (ListView) findViewById(R.id.activity_main_drawer_list);

        fragmentManager.beginTransaction()
                .add(R.id.activity_main_frame, new AMainFragment(), MAIN_FRAGMENT_TAG)
                .commit();

        drawerList.setAdapter(new AMainNavigationListAdapter(this));

        drawerList.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 2:
                fragmentManager.beginTransaction()
                        .add(R.id.activity_main_frame, new MyQuestionsFragment(), MAIN_FRAGMENT_TAG)
                        .addToBackStack(null)
                        .commit();
                drawerLayout.closeDrawer(Gravity.LEFT);
                break;
        }
    }

    @Override
    public void onSuccessfulInput(String questionText, String positiveText, String negativeText) {
        try {
            JSONObject object = new JSONObject();
            object.put("QuestionText", questionText);
            object.put("PositiveValue", positiveText);
            object.put("NegativeValue", negativeText);
            object.put("ImageUrl", "");
            object.put("VideoUrl", "");

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
