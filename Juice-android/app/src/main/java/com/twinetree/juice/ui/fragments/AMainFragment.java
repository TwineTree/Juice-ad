package com.twinetree.juice.ui.fragments;


import android.content.res.Resources;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.twinetree.juice.MyApplication;
import com.twinetree.juice.R;
import com.twinetree.juice.api.ApiResponse;
import com.twinetree.juice.api.Url;
import com.twinetree.juice.external.swipeablecard.model.CardModel;
import com.twinetree.juice.external.swipeablecard.view.CardContainer;
import com.twinetree.juice.external.swipeablecard.view.SimpleCardStackAdapter;
import com.twinetree.juice.util.BearerRequest;
import com.twinetree.juice.util.NetworkUtil;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class AMainFragment extends Fragment {

    private CardContainer mCardContainer;
    private ProgressBar progressBar;

    public AMainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_amain, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Resources r = getResources();

        final SimpleCardStackAdapter adapter = new SimpleCardStackAdapter(getActivity().getApplicationContext());

        mCardContainer = (CardContainer) getActivity().findViewById(R.id.fragment_amain_layout);
        progressBar = (ProgressBar) getActivity().findViewById(R.id.fragment_amain_loading);

        if (NetworkUtil.isConnected(getActivity().getApplicationContext())) {
            progressBar.setVisibility(View.VISIBLE);
            BearerRequest request = new BearerRequest(Request.Method.GET, Url.getQuestions(0, 10),
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressBar.setVisibility(View.GONE);
                            ApiResponse.setQuestionsResponse(response);
                            loadAdapter(adapter);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            });
            MyApplication.queue.add(request);
        }

        /*CardModel cardModel = new CardModel("Title1", "Description goes here", r.getDrawable(R.drawable.picture1));
        cardModel.setOnClickListener(new CardModel.OnClickListener() {
            @Override
            public void OnClickListener() {
                Log.i("Swipeable Cards", "I am pressing the card");
            }
        });

        cardModel.setOnCardDismissedListener(new CardModel.OnCardDismissedListener() {
            @Override
            public void onLike() {
                Log.i("Swipeable Cards", "I like the card");
            }

            @Override
            public void onDislike() {
                Log.i("Swipeable Cards", "I dislike the card");
            }
        });
        adapter.add(cardModel);
*/
    }

    private void loadAdapter(SimpleCardStackAdapter adapter) {
        try {
            JSONObject object = new JSONObject(ApiResponse.getQuestionsResponse());
            JSONArray result = object.getJSONArray("result");

            for (int i=0; i<result.length(); i++) {
                JSONObject item = result.getJSONObject(i);

                String questionText = item.getString("questionText");
                adapter.add(new CardModel(questionText, "", ""));
            }
            mCardContainer.setAdapter(adapter);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
