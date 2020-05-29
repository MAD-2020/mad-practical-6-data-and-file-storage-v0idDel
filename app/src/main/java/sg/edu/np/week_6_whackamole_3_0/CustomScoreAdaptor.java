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

    private OnItemClickListener mListener;
    private UserData user;
    private Context con;
    ArrayList<Integer> levelList = new ArrayList<>();
    ArrayList<Integer> scoreList = new ArrayList<>();


    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }


    public CustomScoreAdaptor(UserData userdata, Context context){
        this.user = userdata;
        this.levelList = userdata.getLevels();
        this.scoreList = userdata.getScores();
        this.con = context;
        /* Hint:
        This method takes in the data and readies it for processing.
         */
    }

    public CustomScoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.level_select, parent, false);
        CustomScoreViewHolder csvh = new CustomScoreViewHolder(v, mListener);
        return csvh;
        /* Hint:
        This method dictates how the viewholder layuout is to be once the viewholder is created.
         */
    }

    public void onBindViewHolder(CustomScoreViewHolder holder, final int position){

        holder.level.setText(levelList.get(position).toString());
        holder.score.setText(scoreList.get(position).toString());

        Log.v(TAG, FILENAME + " Showing level " + levelList.get(position) + " with highest score: " + scoreList.get(position));
        Log.v(TAG, FILENAME+ ": Load level " + position +" for: " + user.getMyUserName());
        /* Hint:
        This method passes the data to the viewholder upon bounded to the viewholder.
        It may also be used to do an onclick listener here to activate upon user level selections.

        Log.v(TAG, FILENAME + " Showing level " + level_list.get(position) + " with highest score: " + score_list.get(position));
        Log.v(TAG, FILENAME+ ": Load level " + position +" for: " + list_members.getMyUserName());
         */
    }

    public int getItemCount(){
        /* Hint:
        This method returns the the size of the overall data.
         */

        return scoreList.size();

    }
}