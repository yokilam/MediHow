package nyc.c4q.medihow.activites;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.Map;

import nyc.c4q.medihow.R;
import nyc.c4q.medihow.fragments.SurveyFragment;
import nyc.c4q.medihow.model.SurveyQuestions;

/**
 * Created by c4q on 3/3/18.
 */

public class SurveyActivity extends AppCompatActivity implements SurveyFragment.SurveryCallBack {
   // Context context;
    public static final String RETRIEVE = "retrieving questions";
    SharedPreferences prefs;


   @Override
    public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_survey);
       prefs = getApplicationContext().getSharedPreferences(
               "app", Context.MODE_PRIVATE);

        getSurveyQuestions();
//        question.setText(SurveyQuestions.questions[0]);

       getSupportFragmentManager().beginTransaction().
               replace(R.id.survey_fragment_container, new SurveyFragment()).
               addToBackStack("survey").commit();
   }

    public SurveyQuestions getSurveyQuestions() {
        SurveyQuestions surveyQuestions= new SurveyQuestions();
        surveyQuestions.questions[0] = getString(R.string.prove_identity);
        surveyQuestions.questions[1] = getString(R.string.prove_income);
        surveyQuestions.questions[2] = getString(R.string.prove_address);
        surveyQuestions.questions[3] = getString(R.string.prove_dependent_care);
        surveyQuestions.questions[4] = getString(R.string.prove_previous_insurance);
        surveyQuestions.questions[5] = getString(R.string.prove_immigrant_status);

        Log.d(RETRIEVE, "getSurveyQuestions: ");
        return surveyQuestions;
    }



    @Override
    public void startMapActivity() {
        int count=0;
        Map<String,?> keys = prefs.getAll();

        for(Map.Entry<String,?> entry : keys.entrySet()){
            if(entry.getValue().equals("yes")){
                count++;
            }
        }
        if(count>2) {
            Intent intent = new Intent(SurveyActivity.this, MapsActivity.class);
            startActivity(intent);
        }
        else{
            String url = "http://www1.nyc.gov/site/ochia/index.page";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);        }
    }
}
