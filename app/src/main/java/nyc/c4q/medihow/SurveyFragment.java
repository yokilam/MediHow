package nyc.c4q.medihow;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import nyc.c4q.medihow.model.SurveyQuestions;


/**
 * A simple {@link Fragment} subclass.
 */
public class SurveyFragment extends Fragment {

    private RadioButton yes,no;
    private TextView question;
    private Button next;
    int indexOfArray;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_survey, container, false);
        yes= view.findViewById(R.id.yes_radio_button);
        no= view.findViewById(R.id.no_radio_button);
        question= view.findViewById(R.id.question);
        next= view.findViewById(R.id.next);

        indexOfArray=0;
        question.setText(SurveyQuestions.questions[indexOfArray]);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n= indexOfArray+1;
                question.setText(SurveyQuestions.questions[indexOfArray+1]);
                indexOfArray=n;
            }
        });

        return view;
    }

}
