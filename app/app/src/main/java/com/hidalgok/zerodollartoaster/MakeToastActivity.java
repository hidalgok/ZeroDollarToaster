package com.hidalgok.zerodollartoaster;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MakeToastActivity extends AppCompatActivity {
    private static long MAX_COOKTIME = 60000;
    TextView timerText;
    AlertDialog toastDialog;
    AlertDialog noToastDialog;
    CountDownTimer toastTimer;
    boolean Toasted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_toast);

        //Get Intent from Activity who called
        Intent intent = getIntent();
        int cook = intent.getIntExtra(MainActivity.EXTRA_COOK,0);
        long cookTime = MAX_COOKTIME * cook/100;

        //Set toast flag to false
        Toasted = false;

        //Create alert dialogs
        toastDialog = toastDialogBuilder();
        noToastDialog = finishToastDialogBuilder();

        //Initialize textview
        timerText = findViewById(R.id.timerText);

        //create and start timer
        toastTimer = new CountDownTimer(cookTime, 1000) {

            public void onTick(long millisUntilFinished) {
                timerText.setText("Seconds Remaining: " + millisUntilFinished / 1000);
            }

            //on countdown finish, "pop" the toast for the user
            public void onFinish() {
                timerText.setText("Done!");
                toastDialog.show();
                Toasted = true;
            }
        };
        toastTimer.start();
    }

    /**
     * "Up" Button functionality
     * Close this activity and send the user back up to the main activity.
     * I realize that just calling "finish" here isn't good practice.
     * It just makes things simple for this simple app.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Method to create an alert dialog for when the toast "pops".
     * Only used once but moved out of the onCreate method for visual clarity.
     * @return AlertDialog
     */
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

    /**
     * Method to create an alert dialog for when the cancel button is pushed but
     * the toast has already popped.
     * Only used once but moved out of the onCreate method for visual clarity.
     * @return AlertDialog
     */
    private AlertDialog finishToastDialogBuilder(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MakeToastActivity.this);
        builder.setTitle(R.string.finishToastTitle);
        builder.setMessage(R.string.finishToastMessage);
        builder.setPositiveButton(R.string.finishToastBack, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });
        builder.setNegativeButton(R.string.finishToastStay, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });
        return builder.create();
    }

    /**
     * Cancel Button Method
     * Allows the user to cancel toasting early. Finished toast dialog will show.
     * If toasting has already finished earlier (either by normal completion or cancellation),
     * will show a new dialog box allowing the user to go back to the main screen.
     * @param view
     */
    public void cancelToast(View view){
        if (!Toasted){
            toastTimer.cancel();
            toastTimer.onFinish();
        }else{
            noToastDialog.show();
        }
    }
}
