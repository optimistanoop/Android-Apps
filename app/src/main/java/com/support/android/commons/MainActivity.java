package com.support.android.commons;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.support.android.admin.PushNotificationFragment;
import com.support.android.admin.UserResistration;
import com.support.android.user.EmployeeListFragment;
import com.support.android.user.PersonalDetailRecyclerFrag;
import com.support.android.user.R;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    Employees employee;
    TextView actionBarName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        employee = (Employees) getApplication();

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        ab.setCustomView(R.layout.actionbar_layout);
        ab.setDisplayOptions(ab.DISPLAY_SHOW_CUSTOM);
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);
        //drawerlayout
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }



         actionBarName = (TextView) findViewById(R.id.textViewActionBarTitle);

        // check for user role and render fragment based on role

        if (employee.getUserRoleForView().equals("Admin") && viewPager != null){
            actionBarName.setText("User Resitration");
            setupViewPagerForAdmin(viewPager);
        }else   if(employee.getUserRoleForView().length()>0 && viewPager != null){
            actionBarName.setText("Personal Detail");
            setupViewPagerForUser(viewPager);
            // remove push notification menu from nav drawer
            final Menu menu = navigationView.getMenu();
            menu.removeItem(R.id.push_notification);
        }
        // setting name on nav drawer for logged in user
        TextView nav_username = (TextView) findViewById(R.id.nav_username);
        nav_username.setText(employee.getFullName());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //  getMenuInflater().inflate(R.menu.sample_actions, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            //noinspection SimplifiableIfStatement

        }
        return super.onOptionsItemSelected(item);
    }


    private void setupViewPagerForUser(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new PersonalDetailRecyclerFrag(), "abc");

        viewPager.setAdapter(adapter);
    };
    private void setupViewPagerForAdmin(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new UserResistration(), "abc");
        viewPager.setAdapter(adapter);
    };


    public void validateUser(View view){
       EditText employeeId=(EditText) findViewById(R.id.empId);
       String id =  employeeId.getText().toString();
        System.out.print("test Function");
        new UserResistration().validateUser(view);
    };
    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        if (menuItem.getTitle().equals("All Employees")) {

                            actionBarName.setText("All Employees");
                            RelativeLayout layout = (RelativeLayout) findViewById(R.id.switching);
                            layout.removeAllViewsInLayout();
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            EmployeeListFragment employeeListFragment = new EmployeeListFragment();
                            fragmentTransaction.add(R.id.switching, employeeListFragment, "HELLO");
                            fragmentTransaction.commit();
                        }
                        if (menuItem.getTitle().equals("Home") && employee.getUserRole().equals("Admin") && employee.getUserRoleForView().equals("Admin")) {

                            actionBarName.setText("User Resitration");
                            RelativeLayout layout = (RelativeLayout) findViewById(R.id.switching);
                            layout.removeAllViewsInLayout();
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            UserResistration userResistration = new UserResistration();
                            fragmentTransaction.add(R.id.switching, userResistration, "HELLO");
                            fragmentTransaction.commit();
                        } else if (menuItem.getTitle().equals("Home") && employee.getUserRole().length() > 0) {

                            employee.setEmpIdForPersonalDetail(employee.getEmpId());
                            actionBarName.setText("Personal Detail");
                            RelativeLayout layout = (RelativeLayout) findViewById(R.id.switching);
                            layout.removeAllViewsInLayout();
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            PersonalDetailRecyclerFrag personalDetailRecyclerFrag = new PersonalDetailRecyclerFrag();
                            fragmentTransaction.add(R.id.switching, personalDetailRecyclerFrag, "HELLO");
                            fragmentTransaction.commit();
                        } else if (menuItem.getTitle().equals("Push notification") && employee.getUserRole().equals("Admin")) {

                            actionBarName.setText("Add Notification");
                            RelativeLayout layout = (RelativeLayout) findViewById(R.id.switching);
                            layout.removeAllViewsInLayout();
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            PushNotificationFragment pushNotificationFragment = new PushNotificationFragment();
                            fragmentTransaction.add(R.id.switching, pushNotificationFragment, "HELLO");
                            fragmentTransaction.commit();
                        } else if (menuItem.getTitle().equals("About Us")) {
                            loadAboutUs();
                        } else if (menuItem.getTitle().equals("Contact Us")) {
                            loadContactUs();
                        }
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    };

    public void loadAboutUs(){
        actionBarName.setText("About Us");
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.switching);
        layout.removeAllViewsInLayout();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        AboutUsFragment aboutUs = new AboutUsFragment();
        fragmentTransaction.add(R.id.switching, aboutUs, "About Us");
        fragmentTransaction.commit();
    }
    public void loadContactUs(){
        actionBarName.setText("Conatct Us");
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.switching);
        layout.removeAllViewsInLayout();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ContactUsFragment contactUsFragment = new ContactUsFragment();
        fragmentTransaction.add(R.id.switching, contactUsFragment, "About Us");
        fragmentTransaction.commit();
    }
    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }

}
