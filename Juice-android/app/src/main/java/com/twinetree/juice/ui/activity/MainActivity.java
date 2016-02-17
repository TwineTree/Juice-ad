package com.twinetree.juice.ui.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.twinetree.juice.R;
import com.twinetree.juice.ui.adapter.AMainNavigationListAdapter;
import com.twinetree.juice.ui.fragments.AMainFragment;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ListView.OnItemClickListener {

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
        fragmentManager = getSupportFragmentManager();

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
        Toast.makeText(this, options[position-1], Toast.LENGTH_SHORT).show();
    }
}
