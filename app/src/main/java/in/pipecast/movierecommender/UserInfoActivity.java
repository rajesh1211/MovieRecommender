package in.pipecast.movierecommender;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;


public class UserInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        Spinner spinner = (Spinner) findViewById(R.id.occupation_spinner);

        List<String> occupations = new ArrayList<String>();
        occupations.add("Engineer");
        occupations.add("Artist");
        occupations.add("Doctor");

        ArrayAdapter<String> spinnerDataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, occupations);
        spinnerDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerDataAdapter);

    }

    public void getRecommendations(View v) {

        if (validateUserData()) {
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
    }

    public boolean validateUserData() {

        return true;
    }

}
