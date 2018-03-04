package nyc.c4q.medihow;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import nyc.c4q.medihow.model.MedicareOffice;
import nyc.c4q.medihow.model.SurveyQuestions;
import nyc.c4q.medihow.retrofit.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by c4q on 3/3/18.
 */

public class SurveyActivity extends AppCompatActivity {
   // Context context;
    public static final String RETRIEVE = "retrieving questions";

   @Override
    public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       getSurveyQuestions();
   }
    SurveyQuestions surveyQuestions= new SurveyQuestions();
    public SurveyQuestions getSurveyQuestions() {
        surveyQuestions.questions[0] = getString(R.string.prove_identity);
        Log.d(RETRIEVE, "getSurveyQuestions: ");
        return surveyQuestions;
    }






}
