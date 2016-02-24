package com.twinetree.juice.ui.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.twinetree.juice.R;
import com.twinetree.juice.ui.adapter.AMainNavigationListAdapter;
import com.twinetree.juice.util.DimensionUtil;

public class AMainNavigationFragment extends Fragment {

    private static final String PREF_FILE_NAME_TAG = "PREF-FILE-NAME";
    public static final String KEY_USER_LEARNED_DRAWER_TAG = "KEY-USER-LEARNED-DRAWER";

    private boolean userLearnedDrawer;
    private boolean fromSavedInstanceState;
    private View view;
    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout drawerLayout;
    private ListView listView;
    private AMainNavigationListAdapter adapter;

    public AMainNavigationFragment() {
        // Required empty public constructor
    }

    public static void saveToPreferences(Context context, String preferenceName, String preferenceValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME_TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(preferenceName, preferenceValue);
        editor.apply();
    }

    public static String readFromPreferences(Context context, String preferenceName, String defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME_TAG, Context.MODE_PRIVATE);
        return sharedPreferences.getString(preferenceName, defaultValue);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userLearnedDrawer = Boolean.valueOf(readFromPreferences(getActivity(), KEY_USER_LEARNED_DRAWER_TAG, "false"));
        if (savedInstanceState != null) {
            fromSavedInstanceState = true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_amain_navigation, container, false);
        ListView list = (ListView) view.findViewById(R.id.activity_main_drawer_list);

        return view;
    }

    public void setUp(int fragmentId, final DrawerLayout drawerLayout, Toolbar toolbar, String name, String email,
                      String profileURL, Context context) {

        view = getActivity().findViewById(fragmentId);

        String mNavigationItems[] = getResources().getStringArray(R.array.drawer_list);
        listView = (ListView) view.findViewById(R.id.activity_main_drawer_list);
        adapter = new AMainNavigationListAdapter(mNavigationItems, name, email, profileURL, context);
        listView.setAdapter(adapter);

        this.drawerLayout = drawerLayout;
        drawerToggle = new ActionBarDrawerToggle(getActivity(), this.drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (userLearnedDrawer) {
                    userLearnedDrawer = true;
                    saveToPreferences(getActivity(), KEY_USER_LEARNED_DRAWER_TAG, userLearnedDrawer + "");
                }
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }
        };

        /*if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
            mDrawerLayout.openDrawer(containterView);
        }
        mDrawerLayout.setDrawerListener(mDrawerToggle);*/

        this.drawerLayout.post(new Runnable() {
            @Override
            public void run() {
                drawerToggle.syncState();
            }
        });
    }
}
