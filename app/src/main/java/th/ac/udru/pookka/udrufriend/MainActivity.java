package th.ac.udru.pookka.udrufriend;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        add fragment to activity
        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().add(R.id.layoutMainFragment, new MainFragment()).commit();
        }

    }   // main method
}   //main class
