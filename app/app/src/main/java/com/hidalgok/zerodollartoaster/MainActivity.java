package com.hidalgok.zerodollartoaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_COOK = "com.hidalgok.zerodollartoaster.COOK";
    SeekBar cookMeter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize seek bar
        cookMeter=(SeekBar)findViewById(R.id.cookMeter);
    }

    //On button press, get value from seek bar and pass it to new activity
    public void makeToast(View view){
        Intent intent = new Intent(this, MakeToastActivity.class);
        int seekBarValue= cookMeter.getProgress();
        intent.putExtra(EXTRA_COOK, seekBarValue);
        startActivity(intent);
    }
}
