package sg.edu.np.week_6_whackamole_3_0;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class CustomScoreAdaptor extends RecyclerView.Adapter<CustomScoreViewHolder> {
    /* Hint:
        1. This is the custom adaptor for the recyclerView list @ levels selection page

     */
    private static final String FILENAME = "CustomScoreAdaptor.java";
    private static final String TAG = "Whack-A-Mole3.0!";

    private Context con;
    private Integer level, score;


    private UserData data = new UserData();

    public UserData getData() {
        return data;
    }

    public CustomScoreAdaptor(UserData userdata){
        data = userdata;
    }

    public CustomScoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.level_select, parent, false);
        CustomScoreViewHolder holder = new CustomScoreViewHolder(view);
        return holder;
    }

    public void onBindViewHolder(CustomScoreViewHolder holder, final int position){

        level = data.getLevels().get(position);
        score = data.getScores().get(position);
        holder.levelText.setText("Level: " + level);
        holder.scoreText.setText("Highest Score: " + score);

        Log.v(TAG, FILENAME + " Showing level " + level + " with highest score: " + score);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(con, Main4Activity.class);
                intent.putExtra("Level", level);
                intent.putExtra("Username", data.getMyUserName());
                Log.v(TAG, FILENAME+ ": Load level " + position +" for: " + data.getMyUserName());
                con.startActivity(intent);
            }
        });
    }

    public int getItemCount(){
        /* Hint:
        This method returns the the size of the overall data.
         */

        return data.getLevels().size();

    }
}