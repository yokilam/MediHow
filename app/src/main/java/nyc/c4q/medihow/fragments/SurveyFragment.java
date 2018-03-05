package nyc.c4q.medihow.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import nyc.c4q.medihow.R;
import nyc.c4q.medihow.model.SurveyQuestions;

import static nyc.c4q.medihow.model.SurveyQuestions.questions;


/**
 * A simple {@link Fragment} subclass.
 */
public class SurveyFragment extends Fragment {
SurveryCallBack surveryCallBack;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            surveryCallBack = (SurveryCallBack) context;
        }catch (Exception e){

        }
    }

    private RadioButton yes,no;
    private TextView question;
    private Button next;
    int indexOfArray;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view= inflater.inflate(R.layout.fragment_survey, container, false);
        yes= view.findViewById(R.id.yes_radio_button);
        yes.setText("yes");

        no= view.findViewById(R.id.no_radio_button);
        no.setText("No");

        question= view.findViewById(R.id.question);
        next= view.findViewById(R.id.next);
        SharedPreferences prefs = getActivity().getApplicationContext().getSharedPreferences(
                "app", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = prefs.edit();

        indexOfArray=0;
        question.setText(questions[indexOfArray]);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(yes.isChecked()) {
                    editor.putString(questions[indexOfArray],yes.getText().toString());
                    Log.d("ddd", "onClick:"+ yes.getText().toString());
                    editor.apply();
                    yes.setChecked(false);
                    no.setChecked(false);

                }
                else if(no.isChecked()){
                    editor.putString(questions[indexOfArray],no.getText().toString());
                    yes.toggle();
                    editor.apply();
                    yes.setChecked(false);
                    no.setChecked(false);
                }
                int n = indexOfArray+1;

                if(n==6){
                    Toast.makeText(view.getContext(), "You are eligible for medicare/medicaid", Toast.LENGTH_LONG).show();
                    surveryCallBack.startMapActivity();
                }
                else {
                    Toast.makeText(view.getContext(), "You are not eligible for medicare/medicaid", Toast.LENGTH_LONG).show();
                    question.setText(questions[indexOfArray+1]);
                    indexOfArray=n;
                    yes.setSelected(false);
                    no.setSelected(false);
                }
            }
        });
        return view;
    }

    public interface SurveryCallBack{
        void startMapActivity();
    }
}
