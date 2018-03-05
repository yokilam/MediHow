package nyc.c4q.medihow.fragments;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import nyc.c4q.medihow.MainActivity;
import nyc.c4q.medihow.R;
import nyc.c4q.medihow.activites.SurveyActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment implements
        GoogleApiClient.OnConnectionFailedListener {

    View view;
    EditText email, password, userName;
    Button regButton;
    private FirebaseAuth mFirebaseAuth;
    private static final String TAG = "FragMent";

    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_register_, container, false);

        mFirebaseAuth = FirebaseAuth.getInstance();

        regButton = view.findViewById(R.id.reg_in_button);
        email = view.findViewById(R.id.emai_frag);
        password = view.findViewById(R.id.password_frag);
        userName = view.findViewById(R.id.user_name);

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!email.getText().toString().isEmpty() && !password.getText().toString().isEmpty() && !userName.getText().toString().isEmpty()) {
                    creatuser(email.getText().toString(), password.getText().toString());
                    hideSoftKeyboard();
                }
            }
        });
        return view;
    }

    private void creatuser(String email, String password) {
        mFirebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mFirebaseAuth.getCurrentUser();
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(userName.getText().toString()).build();
                            assert user != null;
                            user.updateProfile(profileUpdates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.e(TAG, "User profile updated..."+userName.getText().toString());
                                                new AlertDialog.Builder(getActivity())
                                                        .setTitle("Eligibility")
                                                        .setMessage("Are You Eligible for Medicare/Medicaid?")
                                                        .setPositiveButton("No / I don't know ", new DialogInterface.OnClickListener() {
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                //  deleteSuggestions(position);
                                                                getActivity().startActivity(new Intent(view.getContext(), SurveyActivity.class));
                                                                getActivity().finish();
                                                            }
                                                        })
                                                        .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                // do nothing
                                                                getActivity().startActivity(new Intent(view.getContext(), MainActivity.class));
                                                                getActivity().finish();
                                                            }
                                                        })
                                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                                        .show();
//                                                getActivity().startActivity(new Intent(view.getContext(), MainActivity.class));
//                                                getActivity().finish();
                                            }
                                        }
                                    });
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(view.getContext(), task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    public void hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(view.getContext().INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(password.getWindowToken(), 0);
    }
}
