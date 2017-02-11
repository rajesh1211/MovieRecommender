package in.pipecast.movierecommender;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        final MainActivity temp_obj = this;
        //Redirecting after a second
        new Thread(new Runnable() {
            @Override
            public void run() {
                long seconds_later = System.currentTimeMillis() + 2000;
                while (System.currentTimeMillis() < seconds_later) {
                    System.out.println("Redirecting");
                    synchronized (this) {
                        try{
                            wait(seconds_later - System.currentTimeMillis());
                        }catch (Exception e) {}

                        Intent i = new Intent(temp_obj , UserInfoActivity.class);
                        startActivity(i);
                    }
                }
            }
        }).start();
    }

    public void goToMainform(View v) {
        Intent i = new Intent(this, UserInfoActivity.class);
        startActivity(i);
    }
}
