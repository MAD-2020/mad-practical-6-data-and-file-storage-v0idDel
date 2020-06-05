package sg.edu.np.week_6_whackamole_3_0;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class MyDBHandler extends SQLiteOpenHelper {
    /*
        The Database has the following properties:
        1. Database name is WhackAMole.db
        2. The Columns consist of
            a. Username
            b. Password
            c. Level
            d. Score
        3. Add user method for adding user into the Database.
        4. Find user method that finds the current position of the user and his corresponding
           data information - username, password, level highest score for each level
        5. Delete user method that deletes based on the username
        6. To replace the data in the database, we would make use of find user, delete user and add user

        The database shall look like the following:

        Username | Password | Level | Score
        --------------------------------------
        User A   | XXX      | 1     |    0
        User A   | XXX      | 2     |    0
        User A   | XXX      | 3     |    0
        User A   | XXX      | 4     |    0
        User A   | XXX      | 5     |    0
        User A   | XXX      | 6     |    0
        User A   | XXX      | 7     |    0
        User A   | XXX      | 8     |    0
        User A   | XXX      | 9     |    0
        User A   | XXX      | 10    |    0
        User B   | YYY      | 1     |    0
        User B   | YYY      | 2     |    0

     */

    private static final String FILENAME = "MyDBHandler.java";
    private static final String TAG = "Whack-A-Mole3.0!";

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME ="WhackThis.db";
    public static final String TABLE_DATA = "Accounts";
    public static final String COLUMN_USERNAME = "Username";
    public static final String COLUMN_PASSWORD = "Password";
    public static final String COLUMN_LEVEL = "Level";
    public static final String COLUMN_SCORE = "Score";



    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        /* HINT:
            This is used to init the database.
         */
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        /* HINT:
            This is triggered on DB creation.
            Log.v(TAG, "DB Created: " + CREATE_ACCOUNTS_TABLE);
         */
        String CREATE_ACCOUNTS_TABLE = "CREATE TABLE " + TABLE_DATA + "(" +
                COLUMN_USERNAME + " TEXT," +
                COLUMN_PASSWORD + " TEXT," +
                COLUMN_LEVEL + " INTEGER," +
                COLUMN_SCORE + " INTEGER" + ")";
        db.execSQL(CREATE_ACCOUNTS_TABLE);
        Log.v(TAG, "DB Created: " + CREATE_ACCOUNTS_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        /* HINT:
            This is triggered if there is a new version found. ALL DATA are replaced and irreversible.
         */
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DATA);
        onCreate(db);
    }

    public void addUser(UserData userData)
    {
            /* HINT:
                This adds the user to the database based on the information given.
                Log.v(TAG, FILENAME + ": Adding data for Database: " + values.toString());
             */
            ContentValues values = new ContentValues();
            values.put(COLUMN_USERNAME, userData.getMyUserName());
            values.put(COLUMN_PASSWORD, userData.getMyPassword());
            SQLiteDatabase db = this.getWritableDatabase();
            for (int i = 0; i < 10; i++)
            {
                values.put(COLUMN_LEVEL, userData.getLevels().get(i));
                values.put(COLUMN_SCORE, userData.getScores().get(i));
                Log.v(TAG, FILENAME + ": Adding data for Database: " + values.toString());
                db.insert(TABLE_DATA, null, values);
            }
            db.close();
    }

    public UserData findUser(String username)
    {
        String query = "SELECT * FROM " + TABLE_DATA + " WHERE "
                + COLUMN_USERNAME+ " = \"" + username + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        UserData queryData = new UserData();


        if (cursor.moveToFirst())
        {
            queryData.setMyUserName(cursor.getString(0));
            queryData.setMyPassword(cursor.getString(1));
            ArrayList<Integer> levelList = new ArrayList<>();
            ArrayList<Integer> scoreList = new ArrayList<>();

            do{
                levelList.add(Integer.parseInt(cursor.getString(2)));
                scoreList.add(Integer.parseInt(cursor.getString(3)));
            }while(cursor.moveToNext());

            queryData.setLevels(levelList);
            queryData.setScores(scoreList);

            Log.v(TAG, FILENAME + ": QueryData: " + queryData.getLevels().toString() + queryData.getScores().toString());
        }
        else{
            queryData = null;
            Log.v(TAG, FILENAME+ ": No data found!");
        }

        return queryData;
    }

    public boolean deleteAccount(String username) {
        /* HINT:
            This finds and delete the user data in the database.
            This is not reversible.
            Log.v(TAG, FILENAME + ": Database delete user: " + query);
         */
        boolean result = false;

        String query = "SELECT * FROM " + TABLE_DATA + " WHERE "
                + COLUMN_USERNAME + "= \"" + username + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        UserData delData = new UserData();

        if (cursor.moveToFirst())
        {
            do {
                delData.setMyUserName(cursor.getString(0));
                db.delete(TABLE_DATA, COLUMN_USERNAME + " = ?", new String[]{String.valueOf(delData.getMyUserName())});
                result = true;
                Log.v(TAG, FILENAME + ": Database delete user: " + query);
            }while(cursor.moveToNext());
        }
        else
        {
            Log.v(TAG, FILENAME+ ": No data found!");
            cursor.close();
        }
        db.close();;
        return result;
    }
}
