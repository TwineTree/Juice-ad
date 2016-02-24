package com.twinetree.juice.ui.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.twinetree.juice.R;
import com.twinetree.juice.util.StringUtil;

public class QuestionInputDialogFragment extends DialogFragment {

    private static final String IMAGE_URL_TAG = "IMAGE-URL";
    private static final String VIDEO_URL_TAG = "VIDEO-URL";
    private static final String BUNDLE_TAG = "BUNDLE";
    private static final String VIDEO_URI_TAG = "VIDEO-URI";

    private static boolean closeDialog = true;

    public static QuestionInputDialogFragment newInstance(String imageUrl,
                                                          String videoUrl,
                                                          Bundle extras,
                                                          Uri videoUri) {

        QuestionInputDialogFragment dialog = new QuestionInputDialogFragment();
        Bundle args = new Bundle();

        args.putString(IMAGE_URL_TAG, imageUrl);
        args.putString(VIDEO_URL_TAG, videoUrl);
        args.putBundle(BUNDLE_TAG, extras);
        args.putString(VIDEO_URI_TAG, videoUri.toString());

        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.question_input_dialog_layout, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(view);

        final TextView questionInput = (TextView) view.findViewById(R.id.question_input_dialog_question_text);
        final TextView positiveInput = (TextView) view.findViewById(R.id.question_input_dialog_question_pos_text);
        final TextView negativeInput = (TextView) view.findViewById(R.id.question_input_dialog_question_neg_text);
        final Button ok = (Button) view.findViewById(R.id.question_input_dialog_ok);
        final Button cancel = (Button) view.findViewById(R.id.question_input_dialog_cancel);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = getArguments();

                String questionText = questionInput.getText().toString();
                String positiveText = positiveInput.getText().toString();
                String negativeText = negativeInput.getText().toString();
                String imageUrl = args.getString(IMAGE_URL_TAG, "");
                String videoUrl = args.getString(VIDEO_URL_TAG, "");
                Bundle extras = args.getBundle(BUNDLE_TAG);
                Uri videoUri = Uri.parse(args.getString(VIDEO_URI_TAG));
                Bitmap bitmap = null;

                if (extras != null) {
                    bitmap = (Bitmap) extras.get(getResources().getString(R.string.get_image_tag));
                }

                if (StringUtil.isNullOrEmpty(questionText)) {
                    closeDialog = false;
                    questionInput.setError("Cannot be Empty");
                }
                if (StringUtil.isNullOrEmpty(positiveText)) {
                    positiveText = "Haan";
                }
                if (StringUtil.isNullOrEmpty(negativeText)) {
                    negativeText = "Naa";
                }
                if (closeDialog) {
                    if (getActivity() instanceof Callback) {
                        ((Callback)getActivity()).onSuccessfulInput(questionText, positiveText,
                                negativeText, imageUrl, videoUrl, bitmap, videoUri);
                        getDialog().cancel();
                    }
                }
            }
        });

        return alertDialogBuilder.create();
    }

    public interface Callback {
        void onSuccessfulInput(String questionText, String positiveText, String negativeText,
                               String imageUrl, String videoUrl, Bitmap bitmap, Uri videoUri);
    }
}
