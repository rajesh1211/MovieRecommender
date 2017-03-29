package in.pipecast.movierecommender;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class UserInfoActivity extends AppCompatActivity {

    private EditText user_name;
    private EditText age;
    private RadioGroup gender_radio_group;
    private RadioButton gender_radio;
    private Spinner spinner;

    private static String TAG = MainActivity.class.getSimpleName();

    ListView mDrawerList;
    RelativeLayout mDrawerPane;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;

    ArrayList<NavItem> mNavItems = new ArrayList<NavItem>();

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

        mNavItems.add(new NavItem("Home", "Meetup destination", R.drawable.icon));
        mNavItems.add(new NavItem("Preferences", "Change your preferences", R.drawable.icon));
        mNavItems.add(new NavItem("About", "Get to know about us", R.drawable.icon));

        // DrawerLayout
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        // Populate the Navigtion Drawer with options
        mDrawerPane = (RelativeLayout) findViewById(R.id.drawerPane);
        mDrawerList = (ListView) findViewById(R.id.navList);
        DrawerListAdapter adapter = new DrawerListAdapter(this, mNavItems);
        mDrawerList.setAdapter(adapter);

        // Drawer Item click listeners
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItemFromDrawer(position);
            }

            private void selectItemFromDrawer(int position) {
                Fragment fragment = new PreferencesFragment();

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.activity_user_info, fragment)
                        .commit();

                mDrawerList.setItemChecked(position, true);
                setTitle(mNavItems.get(position).mTitle);

                // Close the drawer
                mDrawerLayout.closeDrawer(mDrawerPane);
            }
        });

        /*
* Called when a particular item from the navigation drawer
* is selected.
* */


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

    class NavItem {
        String mTitle;
        String mSubtitle;
        int mIcon;

        public NavItem(String title, String subtitle, int icon) {
            mTitle = title;
            mSubtitle = subtitle;
            mIcon = icon;
        }
    }

    class DrawerListAdapter extends BaseAdapter {

        Context mContext;
        ArrayList<NavItem> mNavItems;

        public DrawerListAdapter(Context context, ArrayList<NavItem> navItems) {
            mContext = context;
            mNavItems = navItems;
        }

        @Override
        public int getCount() {
            return mNavItems.size();
        }

        @Override
        public Object getItem(int position) {
            return mNavItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.drawer_item, null);
            }
            else {
                view = convertView;
            }

            TextView titleView = (TextView) view.findViewById(R.id.title);
            TextView subtitleView = (TextView) view.findViewById(R.id.subTitle);
            ImageView iconView = (ImageView) view.findViewById(R.id.icon);

            titleView.setText( mNavItems.get(position).mTitle );
            subtitleView.setText( mNavItems.get(position).mSubtitle );
            iconView.setImageResource(mNavItems.get(position).mIcon);

            return view;
        }
    }

}
