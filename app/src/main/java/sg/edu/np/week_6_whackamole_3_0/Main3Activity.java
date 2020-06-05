package sg.edu.np.week_6_whackamole_3_0;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class Main3Activity extends AppCompatActivity {
    /* Hint:
        1. This displays the available levels from 1 to 10 to the user.
        2. The different levels makes use of the recyclerView and displays the highest score
           that corresponds to the different levels.
        3. Selection of the levels will load relevant Whack-A-Mole game.
        4. The levels are with the following difficulties.
            a. Level 1 will have a new mole at each 10000ms.
            b. Each level up will shorten the time to next mole by 100ms with level 10 as 1000 second per mole.
            c. For level 1 ~ 5, there is only 1 mole.
            d. For level 6 ~ 10, there are 2 moles.
            e. Each location of the mole is randomised.
        5. There is an option return to the login page.
     */
    private static final String FILENAME = "Main3Activity.java";
    private static final String TAG = "Whack-A-Mole3.0!";
    MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
    Button returnButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        returnButton = findViewById(R.id.returnButton);

        Intent receivingEnd = getIntent();
        String userName = receivingEnd.getStringExtra("Username");

        UserData user = dbHandler.findUser(userName);
        RecyclerView recyclerView = findViewById(R.id.levelRecyclerView);
        CustomScoreAdaptor mAdaptor = new CustomScoreAdaptor(user);
        LinearLayoutManager mlayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mlayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdaptor);
        Log.v(TAG, FILENAME + ": Show level for user: " + userName);

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main3Activity.this, MainActivity.class);
                Log.v(TAG, FILENAME + ": Returning to Login...");
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }}

