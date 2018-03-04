package nyc.c4q.medihow;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import nyc.c4q.medihow.model.SurveyQuestions;

/**
 * Created by c4q on 3/3/18.
 */

public class SurveyActivity extends AppCompatActivity {
   // Context context;
    public static final String RETRIEVE = "retrieving questions";

   @Override
    public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       //setContentView(R.id.);

       getSurveyQuestions();
   }

    public SurveyQuestions getSurveyQuestions() {
        SurveyQuestions surveyQuestions= new SurveyQuestions();
        surveyQuestions.questions[0] = getString(R.string.prove_identity);
        surveyQuestions.questions[1] = getString(R.string.prove_income);
        surveyQuestions.questions[2] = getString(R.string.prove_address);
        surveyQuestions.questions[3] = getString(R.string.prove_dependent_care);
        surveyQuestions.questions[4] = getString(R.string.prove_previous_insurance);
        surveyQuestions.questions[5] = getString(R.string.prove_pregnancy);
        surveyQuestions.questions[6] = getString(R.string.prove_immigrant_status);

        Log.d(RETRIEVE, "getSurveyQuestions: ");
        return surveyQuestions;
    }



}
