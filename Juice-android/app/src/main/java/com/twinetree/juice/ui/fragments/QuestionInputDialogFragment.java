package com.twinetree.juice.ui.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.twinetree.juice.R;
import com.twinetree.juice.util.StringUtil;

public class QuestionInputDialogFragment extends DialogFragment {

    private static final String QUESTION_TEXT_TAG = "QUESTION-TEXT";
    private static final String POSITIVE_TEXT_TAG = "POSITIVE-TEXT";
    private static final String NEGATIVE_TEXT_TAG = "NEGATIVE-TEXT";

    private static boolean closeDialog = true;

    public static QuestionInputDialogFragment newInstance(String questionText,
                                                          String positiveText,
                                                          String negativeText) {

        QuestionInputDialogFragment dialog = new QuestionInputDialogFragment();
        Bundle args = new Bundle();

        args.putString(QUESTION_TEXT_TAG, questionText);
        args.putString(POSITIVE_TEXT_TAG, positiveText);
        args.putString(NEGATIVE_TEXT_TAG, negativeText);

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
                String questionText = questionInput.getText().toString();
                String positiveText = positiveInput.getText().toString();
                String negativeText = negativeInput.getText().toString();


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
                        ((Callback)getActivity()).onSuccessfulInput(questionText, positiveText, negativeText);
                        getDialog().cancel();
                    }
                }
            }
        });

        return alertDialogBuilder.create();
    }

    public interface Callback {
        void onSuccessfulInput(String questionText, String positiveText, String negativeText);
    }
}
