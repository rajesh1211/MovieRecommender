package in.pipecast.movierecommender;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class UserInfoActivity extends AppCompatActivity {

    private EditText user_name;
    private EditText age;
    private RadioGroup gender_radio_group;
    private RadioButton gender_radio;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        spinner = (Spinner) findViewById(R.id.occupation_spinner);

        List<String> occupations = new ArrayList<String>();
        occupations.add("Select");
        occupations.add("Engineer");
        occupations.add("Artist");
        occupations.add("Doctor");

        ArrayAdapter<String> spinnerDataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, occupations);
        spinnerDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerDataAdapter);

    }

    public void getRecommendations(View v) {

        if (validateUserData()) {

            //Later main recommendation engine logic goes here
            System.out.println(user_name.getText());
            System.out.println(age.getText());
            System.out.println(gender_radio.getText());
            System.out.println(spinner.getSelectedItem().toString());
            //Temp ProgressBar
            showProgress();

        }
    }

    public void showProgress() {
        final ProgressDialog progress=new ProgressDialog(this);
        progress.setMessage("Hang on! Crunching data for you..");
        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progress.setIndeterminate(false);
        progress.setProgress(0);
        progress.show();
        final int totalProgressTime = 100;
        final Thread t = new Thread() {
            @Override
            public void run() {
                int jumpTime = 0;

                while(jumpTime < totalProgressTime) {
                    try {
                        sleep(2000);
                        jumpTime += 20;
                        progress.setProgress(jumpTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                progress.dismiss();
            }
        };
        t.start();
    }

    public boolean validateUserData() {
        user_name = (EditText) findViewById(R.id.user_name);
        age = (EditText) findViewById(R.id.user_age);
        gender_radio_group = (RadioGroup)findViewById(R.id.gender_radio_group);
        gender_radio = (RadioButton) findViewById( gender_radio_group.getCheckedRadioButtonId());

        if (user_name.getText().length() == 0) {
            user_name.setError("Please enter username");
            return false;
        }
        if (age.getText().length() == 0) {
            age.setError("Please enter age");
            return false;
        }
        if (gender_radio == null) {
            Toast.makeText(this, "Please select your gender", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (spinner.getSelectedItem().toString() == "Select" ){
            Toast.makeText(this, "Please select your occupation", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}
