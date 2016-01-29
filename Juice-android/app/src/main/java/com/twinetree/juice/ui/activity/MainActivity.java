package com.twinetree.juice.ui.activity;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import com.twinetree.juice.R;
import com.twinetree.juice.external.swipeablecard.model.CardModel;
import com.twinetree.juice.external.swipeablecard.view.CardContainer;
import com.twinetree.juice.external.swipeablecard.view.SimpleCardStackAdapter;

public class MainActivity extends AppCompatActivity {

    private CardContainer mCardContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Resources r = getResources();

        SimpleCardStackAdapter adapter = new SimpleCardStackAdapter(this);

        mCardContainer = (CardContainer) findViewById(R.id.layoutview);

        adapter.add(new CardModel("Title1", "Description goes here", r.getDrawable(R.drawable.picture1)));
        adapter.add(new CardModel("Title2", "Description goes here", r.getDrawable(R.drawable.picture2)));
        adapter.add(new CardModel("Title3", "Description goes here", r.getDrawable(R.drawable.picture3)));
        adapter.add(new CardModel("Title4", "Description goes here", r.getDrawable(R.drawable.picture1)));
        adapter.add(new CardModel("Title5", "Description goes here", r.getDrawable(R.drawable.picture2)));
        adapter.add(new CardModel("Title6", "Description goes here", r.getDrawable(R.drawable.picture3)));
        adapter.add(new CardModel("Title1", "Description goes here", r.getDrawable(R.drawable.picture1)));
        adapter.add(new CardModel("Title2", "Description goes here", r.getDrawable(R.drawable.picture2)));
        adapter.add(new CardModel("Title3", "Description goes here", r.getDrawable(R.drawable.picture3)));
        adapter.add(new CardModel("Title4", "Description goes here", r.getDrawable(R.drawable.picture1)));
        adapter.add(new CardModel("Title5", "Description goes here", r.getDrawable(R.drawable.picture2)));
        adapter.add(new CardModel("Title6", "Description goes here", r.getDrawable(R.drawable.picture3)));
        adapter.add(new CardModel("Title1", "Description goes here", r.getDrawable(R.drawable.picture1)));
        adapter.add(new CardModel("Title2", "Description goes here", r.getDrawable(R.drawable.picture2)));
        adapter.add(new CardModel("Title3", "Description goes here", r.getDrawable(R.drawable.picture3)));
        adapter.add(new CardModel("Title4", "Description goes here", r.getDrawable(R.drawable.picture1)));
        adapter.add(new CardModel("Title5", "Description goes here", r.getDrawable(R.drawable.picture2)));
        adapter.add(new CardModel("Title6", "Description goes here", r.getDrawable(R.drawable.picture3)));
        adapter.add(new CardModel("Title1", "Description goes here", r.getDrawable(R.drawable.picture1)));
        adapter.add(new CardModel("Title2", "Description goes here", r.getDrawable(R.drawable.picture2)));
        adapter.add(new CardModel("Title3", "Description goes here", r.getDrawable(R.drawable.picture3)));
        adapter.add(new CardModel("Title4", "Description goes here", r.getDrawable(R.drawable.picture1)));
        adapter.add(new CardModel("Title5", "Description goes here", r.getDrawable(R.drawable.picture2)));
        adapter.add(new CardModel("Title6", "Description goes here", r.getDrawable(R.drawable.picture3)));
        adapter.add(new CardModel("Title1", "Description goes here", r.getDrawable(R.drawable.picture1)));
        adapter.add(new CardModel("Title2", "Description goes here", r.getDrawable(R.drawable.picture2)));
        adapter.add(new CardModel("Title3", "Description goes here", r.getDrawable(R.drawable.picture3)));
        adapter.add(new CardModel("Title4", "Description goes here", r.getDrawable(R.drawable.picture1)));
        adapter.add(new CardModel("Title5", "Description goes here", r.getDrawable(R.drawable.picture2)));

        CardModel cardModel = new CardModel("Title1", "Description goes here", r.getDrawable(R.drawable.picture1));
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

        mCardContainer.setAdapter(adapter);
    }
}
