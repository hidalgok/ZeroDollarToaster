package com.hidalgok.zerodollartoaster;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MenuItem;
import android.widget.TextView;

public class MakeToastActivity extends AppCompatActivity {
    private static long MAX_COOKTIME = 60000;
    TextView timerText;
    AlertDialog cookedDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_toast);

        //Get Intent from Activity who called
        Intent intent = getIntent();
        int cook = intent.getIntExtra(MainActivity.EXTRA_COOK,0);
        long cookTime = MAX_COOKTIME * cook/100;

        cookedDialog = toastDialogBuilder();

        //Initialize textview
        timerText = findViewById(R.id.timerText);
        new CountDownTimer(cookTime, 1000) {

            public void onTick(long millisUntilFinished) {
                timerText.setText("Seconds Remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                timerText.setText("Done!");
                cookedDialog.show();
            }
        }.start();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private AlertDialog toastDialogBuilder(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MakeToastActivity.this);
        builder.setTitle(R.string.toastTitle);
        builder.setMessage(R.string.toastMessage);
        builder.setPositiveButton(R.string.eatToast, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
            }
        });
        builder.setNegativeButton(R.string.wasteToast, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });
        return builder.create();
    }
}
