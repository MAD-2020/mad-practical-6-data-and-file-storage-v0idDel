package sg.edu.np.week_6_whackamole_3_0;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class CustomScoreViewHolder extends RecyclerView.ViewHolder {
    /* Hint:
        1. This is a customised view holder for the recyclerView list @ levels selection page
     */
    private static final String FILENAME = "CustomScoreViewHolder.java";
    private static final String TAG = "Whack-A-Mole3.0!";

    TextView level, score;

    public CustomScoreViewHolder(final View itemView, final CustomScoreAdaptor.OnItemClickListener listener){
        super(itemView);
        /* Hint:
        This method dictates the viewholder contents and links the widget to the objects for manipulation.
         */
        level = itemView.findViewById(R.id.levelText);
        score = itemView.findViewById(R.id.scoreText);

        itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (listener != null) { //need the listener to call the interface method
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION){ //make sure the position is valid
                        listener.onItemClick(position);
                    }
                }
            }
        });
    }
}
