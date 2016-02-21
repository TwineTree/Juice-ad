package com.twinetree.juice.ui.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.twinetree.juice.R;
import com.twinetree.juice.util.FontsUtil;

public class AMainNavigationListAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private String[] options;
    private String name;
    private String email;
    private String profileUrl;
    private Context context;

    public AMainNavigationListAdapter(String options[], String name, String email, String profileUrl, Context context) {
        this.options = options;
        this.name = name;
        this.email = email;
        this.profileUrl = profileUrl;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (position == 0) {
            return inflater.inflate(R.layout.header_layout, null);
        }
        View view = inflater.inflate(R.layout.amain_navigation_list_item, null);
        Holder holder = new Holder();

        holder.image = (ImageView) view.findViewById(R.id.amain_navigation_list_item_image);
        holder.text = (TextView) view.findViewById(R.id.amain_navigation_list_item_text);

        Typeface robotoMedium = Typeface.createFromAsset(context.getAssets(), FontsUtil.RobotoMedium);
        holder.text.setTypeface(robotoMedium);

        switch (position) {
            case 1:
                holder.text.setText(options[0]);
                holder.image.setImageResource(R.mipmap.ic_account_circle_black_24dp);
                break;
            case 2:
                holder.text.setText(options[1]);
                holder.image.setImageResource(R.mipmap.ic_question_answer_black_24dp);
                break;
            case 3:
                holder.text.setText(options[0]);
                holder.image.setImageResource(R.mipmap.ic_exit_to_app_black_24dp);
                break;
        }
        return view;
    }

    private class Holder {
        ImageView image;
        TextView text;
    }
}
