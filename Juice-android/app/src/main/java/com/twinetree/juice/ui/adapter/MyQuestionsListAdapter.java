package com.twinetree.juice.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.twinetree.juice.R;
import com.twinetree.juice.api.ApiResponse;

import org.json.JSONArray;
import org.json.JSONObject;

public class MyQuestionsListAdapter extends RecyclerView.Adapter<MyQuestionsListAdapter.Holder> {

    private Context context;
    private JSONArray questionsList;
    private LayoutInflater inflater;

    public MyQuestionsListAdapter(Context context) {
        try {
            this.context = context;
            inflater = LayoutInflater.from(context);
            questionsList = new JSONObject(ApiResponse.getMyQuestionsResponse()).getJSONArray("result");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public MyQuestionsListAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.my_question_list_item, null);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(MyQuestionsListAdapter.Holder holder, int position) {
        try {
            JSONObject item = questionsList.getJSONObject(position);

            holder.text.setText(item.getString("questionText"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return questionsList.length();
    }

    public class Holder extends RecyclerView.ViewHolder {

        TextView text;

        public Holder(View itemView) {
            super(itemView);

            text = (TextView) itemView.findViewById(R.id.my_question_list_item_text);
        }
    }
}
