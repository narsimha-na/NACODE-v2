package in.nacode.nacode;

import android.app.Fragment;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FrameLayout frameLayout;

    private HomeFragment homeFragment;
    private PlayGroundFragment playGroundFragment;
    private ProgrammingLangFragment programmingLangFragment;
    private ProfileFragment profileFragment;
    private QAFragemnt qaFragemnt;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        frameLayout = (FrameLayout) findViewById(R.id.main_frame);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.main_nav);

        homeFragment = new HomeFragment();
        playGroundFragment = new PlayGroundFragment();
        programmingLangFragment = new ProgrammingLangFragment();
        profileFragment = new ProfileFragment();
        qaFragemnt = new QAFragemnt();

        setFragment(homeFragment);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.nav_id_pg :
                        setFragment(playGroundFragment);

                        return true;

                    case R.id.nav_id_lang :
                        setFragment(programmingLangFragment);
                        return true;

                    case R.id.nav_id_home :
                        setFragment(homeFragment);
                        return true;

                    case R.id.nav_id_qa :
                        setFragment(qaFragemnt);
                        return true;

                    case R.id.nav_id_profile :
                        setFragment(profileFragment);
                        return  true;

                        default:
                            return false;


                }
            }
        });



    }
    private  void setFragment(android.support.v4.app.Fragment fragment){

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame,fragment);
        fragmentTransaction.commit();
    }
}
