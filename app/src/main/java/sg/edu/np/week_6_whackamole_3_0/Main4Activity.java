package sg.edu.np.week_6_whackamole_3_0;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class Main4Activity extends AppCompatActivity {

    /* Hint:
        1. This creates the Whack-A-Mole layout and starts a countdown to ready the user
        2. The game difficulty is based on the selected level
        3. The levels are with the following difficulties.
            a. Level 1 will have a new mole at each 10000ms.
                - i.e. level 1 - 10000ms
                       level 2 - 9000ms
                       level 3 - 8000ms
                       ...
                       level 10 - 1000ms
            b. Each level up will shorten the time to next mole by 100ms with level 10 as 1000 second per mole.
            c. For level 1 ~ 5, there is only 1 mole.
            d. For level 6 ~ 10, there are 2 moles.
            e. Each location of the mole is randomised.
        4. There is an option return to the login page.
     */
    private static final String FILENAME = "Main4Activity.java";
    private static final String TAG = "Whack-A-Mole3.0!";
    private CountDownTimer myCountDown;
    private int advancedScore = 0;
    private int level;
    private int time;
    private String username;
    private TextView scoreTrack;
    private Button backButton;

    CountDownTimer readyTimer;
    CountDownTimer newMolePlaceTimer;

    private void readyTimer(){
        /*  HINT:
            The "Get Ready" Timer.
            Log.v(TAG, "Ready CountDown!" + millisUntilFinished/ 1000);
            Toast message -"Get Ready In X seconds"
            Log.v(TAG, "Ready CountDown Complete!");
            Toast message - "GO!"
            belongs here.
            This timer countdown from 10 seconds to 0 seconds and stops after "GO!" is shown.
         */

        readyTimer = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.v(TAG, "Ready CountDown!" + millisUntilFinished/1000);
                Toast.makeText(getApplicationContext(), "Get Ready in " + millisUntilFinished/1000 + " seconds", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFinish() {
                Log.v(TAG, "Ready CountDown Complete!");
                Toast.makeText(getApplicationContext(), "GO!", Toast.LENGTH_SHORT).show();
                setNewMole(level);
                placeMoleTimer();
            }
        };
        readyTimer.start();
    }
    private void placeMoleTimer(){
        /* HINT:
           Creates new mole location each second.
           Log.v(TAG, "New Mole Location!");
           setNewMole();
           belongs here.
           This is an infinite countdown timer.
         */
        time = 11000 - (level * 1000);
        newMolePlaceTimer = new CountDownTimer(time, time) {
            @Override
            public void onTick(long l) {
                Log.v(TAG, "New Mole Location");
            }
            @Override
            public void onFinish() {
                setNewMole(level);
            }
        };
        newMolePlaceTimer.start();
    }
    private static final int[] BUTTON_IDS = {
            R.id.Button1, R.id.Button2, R.id.Button3,
            R.id.Button4, R.id.Button5, R.id.Button6,
            R.id.Button7, R.id.Button8, R.id.Button9
            /* HINT:
                Stores the 9 buttons IDs here for those who wishes to use array to create all 9 buttons.
                You may use if you wish to change or remove to suit your codes.*/

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        /*Hint:
            This starts the countdown timers one at a time and prepares the user.
            This also prepares level difficulty.
            It also prepares the button listeners to each button.
            You may wish to use the for loop to populate all 9 buttons with listeners.
            It also prepares the back button and updates the user score to the database
            if the back button is selected.
         */
        backButton = findViewById(R.id.backButton);
        scoreTrack =findViewById(R.id.score);
        scoreTrack.setText(String.valueOf(advancedScore));
        Intent receivingEnd = getIntent();
        username = receivingEnd.getStringExtra("Username");
        level = receivingEnd.getIntExtra("Level", 0);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserScore();
                Intent intent = new Intent(Main4Activity.this, Main3Activity.class);
                intent.putExtra("Username", username);
                startActivity(intent);
            }
        });

        for(final int id : BUTTON_IDS){
            /*  HINT:
            This creates a for loop to populate all 9 buttons with listeners.
            You may use if you wish to remove or change to suit your codes.
            */
            Button userButton = (Button)findViewById(id);
            userButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int id = view.getId();
                    Button clickedButton = (Button) findViewById(id);
                    doCheck(clickedButton);
                }
            });
        }
    }
    @Override
    protected void onStart(){
        super.onStart();
        readyTimer();
    }

    private void doCheck(Button checkButton)
    {
        /* Hint:
            Checks for hit or miss
            Log.v(TAG, FILENAME + ": Hit, score added!");
            Log.v(TAG, FILENAME + ": Missed, point deducted!");
            belongs here.
        */
        String buttonText = checkButton.getText().toString();
        switch (buttonText){
            case "0":
                Log.v(TAG, "Missed point deducted!");
                advancedScore = advancedScore - 1;
                if (advancedScore < 0){
                    advancedScore = 0;
                    Log.v(TAG, "Come on! Try harder!");
                }
                setNewMole(level);
                break;
            case "*":
                Log.v(TAG, "Hit, score added!");
                advancedScore = advancedScore + 1;
                setNewMole(level);
                break;
        }
        String count = String.valueOf(advancedScore);
        scoreTrack.setText(count);

    }

    public void setNewMole(Integer level)
    {
        /* Hint:
            Clears the previous mole location and gets a new random location of the next mole location.
            Sets the new location of the mole. Adds additional mole if the level difficulty is from 6 to 10.
         */
        for (int id: BUTTON_IDS)
        {
            Button buttonID = findViewById(id);
            buttonID.setText("0");
        }
        if (level < 6)
        {
            Random ran = new Random();
            int randomLocation = ran.nextInt(9);
            Button newButton = findViewById(BUTTON_IDS[randomLocation]);
            newButton.setText("*");
        }
        else
        {
            Random ran = new Random();
            int randomLocation = ran.nextInt(9);
            Button newButton = findViewById(BUTTON_IDS[randomLocation]);
            newButton.setText("*");
            int randomLocation2 = ran.nextInt(9);
            Button newButton2 = findViewById(BUTTON_IDS[randomLocation2]);
            newButton2.setText("*");
        }
        placeMoleTimer();
    }


    private void updateUserScore()
    {

     /* Hint:
        This updates the user score to the database if needed. Also stops the timers.
        Log.v(TAG, FILENAME + ": Update User Score...");
      */
        readyTimer.cancel();

        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        UserData userData = dbHandler.findUser(username);
        Log.v(TAG, FILENAME + ": Update User Score...");
        dbHandler.deleteAccount(username);
        userData.getScores().set(level - 1, advancedScore);
        dbHandler.addUser(userData);
        Log.v(TAG, FILENAME + ": Update User Score...");
    }

}
