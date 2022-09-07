package com.cityu.ast.movieordersystem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DatabaseHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME ="movie.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME ="registeruser";
    public static final String COL_1 ="ID";
    public static final String COL_2 ="username";
    public static final String COL_3 ="password";
    public static final String COL_4 ="status";
    public static final String COL_5 ="credit";

    public static final String TABLE_CART ="cart";
    public static final String COL_CARTITEMID ="cartItemID";
    public static final String COL_USER ="user";
    public static final String COL_ITEM ="item";
    public static final String COL_AMOUNT = "amount";

    public static final String TABLE_MOVIE ="movielist";
    public static final String COL_MOVIEID ="movieID";
    public static final String COL_MOVIENAME ="moviename";
    public static final String COL_MOVIEPRICE ="price";
    public static final String COL_MOVIEDISCRIPTION ="moviediscribtion";
    public static final String COL_MOVIERATING ="rating";
    public static final String COL_POSTER ="poster";
    public static final String COL_ACTOR ="actor";
    public static final String COL_DIRECTOR ="director";
    public static final String COL_GENRE = "genre";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_MOVIE);

        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_CART);

        sqLiteDatabase.execSQL("CREATE TABLE registeruser (ID INTEGER PRIMARY  KEY AUTOINCREMENT, username TEXT, password TEXT, status TEXT, credit INTEGER)");

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_CART + "(cartItemID INTEGER PRIMARY KEY AUTOINCREMENT, user TEXT, item TEXT, amount INTEGER, price INTEGER)");

        sqLiteDatabase.execSQL("CREATE TABLE movielist (movieID INTEGER, moviename TEXT, price INTEGER, moviediscribtion TEXT, rating INTEGER, poster INTEGER, actor TEXT, director TEXT, genre TEXT)");

        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_MOVIE + " VALUES ("
                + "01, "
                + "'Aquaman', "
                + "100, "
                + "'“Aquaman” reveals the origin story of half-human, half-Atlantean Arthur Curry and takes him on the journey of his lifetime-to discover if he is worthy of who he was born to be…a king.', "
                + "6.9, "
                + R.drawable.poster1 + ", "
                + "'Jason Momoa, Amber Heard, Willem Dafoe', "
                + "'James Wan', "
                + "'Adventure, Action, Science Fiction, Fantasy' )"
        );

        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_MOVIE + " VALUES ("
                + "02, "
                + "'TOY STORY 4', "
                + "120, "
                + "'When a new toy called \"Forky\" joins Woody and the gang, a road trip alongside old and new friends reveals how big the world can be for a toy.', "
                + "7.9, "
                + R.drawable.poster2 + ", "
                + "'Tom Hanks, Tim Allen, Annie Potts, Tony Hale, Keegan-Michael Key', "
                + "'Josh Cooley', "
                + "'Kids, Comedy, Animation' )"
        );

        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_MOVIE + " VALUES ("
                + "03, "
                + "'Frozen', "
                + "80, "
                + "'In \"Frozen,\" fearless optimist Anna teams up with rugged mountain man Kristoff and his loyal reindeer Sven in an epic journey.', "
                + "7.5, "
                + R.drawable.poster3 + ", "
                + "'Kristen Bell, Idina Menzel, Idina Menzel', "
                + "'Chris Buck, Jennifer Lee', "
                + "'Animation, Kids' )"
        );

        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_MOVIE + " VALUES ("
                + "04, "
                + "'Avengers: Endgame', "
                + "150, "
                + "'The fourth installment in the Avengers saga is the culmination of 22 interconnected films and the climax of an epic journey. Earth’s heroes will finally understand how fragile our reality is—and the sacrifices that must be made to uphold it—in a story of friendship, teamwork and setting aside differences to overcome an impossible obstacle.', "
                + "8.5, "
                + R.drawable.poster4 + ", "
                + "'Robert Downey Jr., Chris Evans, Mark Ruffalo', "
                + "'Anthony Russo, Joe Russo', "
                + "'Action, Adventure, Science Fiction' )"
        );

        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_MOVIE + " VALUES ("
                + "05, "
                + "'Alita: Battle Angel', "
                + "55, "
                + "'In \"Frozen,\" fearless optimist Anna teams up with rugged mountain man Kristoff and his loyal reindeer Sven in an epic journey.', "
                + "7.4, "
                + R.drawable.poster5 + ", "
                + "'Rosa Salazar, Christph Waltz, Keenan Johnson', "
                + "'Robert Rodriguez', "
                + "'Action, Adventure, Science Fiction' )"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_MOVIE);
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_CART);
        onCreate(sqLiteDatabase);
    }

    public long addUser(String user, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username",user);
        contentValues.put("password",password);
        contentValues.put("status", "logout");
        contentValues.put("credit", 10000);
        long res = db.insert("registeruser",null,contentValues);
        db.close();
        return  res;
    }

    // login系統,check用戶輸入的name,password 是否正確的地方    光
    public boolean checkUser(String username, String password){
        String[] columns = { COL_1 };
        SQLiteDatabase db = getReadableDatabase();
        String selection = COL_2 + "=?" + " and " + COL_3 + "=?";
        String[] selectionArgs = { username, password };
        Cursor cursor = db.query(TABLE_NAME,columns,selection,selectionArgs,null,null,null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        if(count>0)
            return  true;
        else
            return  false;
    }

    public String getLoginUser() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT rowid _id, username FROM registeruser WHERE status = 'login'", null);
        if (c.getCount() != 0) {
            c.moveToFirst();
            String target = c.getString(c.getColumnIndex("username"));
            return target;
        }
        else return "";
    }

    public String getMovieNameByID(int id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT rowid _id, moviename FROM movielist WHERE movieID = " + id, null);
        if (c.getCount() != 0) {
            c.moveToFirst();
            String target = c.getString(c.getColumnIndex("moviename"));
            return target;
        }
        else return "";
    }

    public String getMoviePriceByID(int id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT rowid _id, price FROM movielist WHERE movieID = " + id, null);
        if (c.getCount() != 0) {
            c.moveToFirst();
            String target = c.getString(c.getColumnIndex("price"));
            return target;
        }
        else return "";
    }

    public void addToCart(int id, int amount) {
        SQLiteDatabase db = getReadableDatabase();
        //Cursor c = db.rawQuery("SELECT * FROM cart WHERE item = '" + id + "' AND user = '" + getLoginUser() + "'", null);
        //if (c.getCount() == 0) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("user",getLoginUser());
        contentValues.put("item",getMovieNameByID(id));
        contentValues.put("amount", amount);
        contentValues.put("price",getMoviePriceByID(id));
        long res = db.insert("cart",null,contentValues);
        db.close();
        //}
        //else db.execSQL("UPDATE " + TABLE_CART + " SET " + COL_AMOUNT + "=" + COL_AMOUNT + amount + " WHERE " + COL_ITEM + "=" + id);
    }

    public void decreaseCredit(int total) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT credit FROM registeruser WHERE username = '" + getLoginUser() + "'", null);
        c.moveToFirst();
        int credit = c.getInt(c.getColumnIndex("credit"));
        int result = credit - total;
        db.execSQL("UPDATE registeruser SET credit = " + result + " WHERE username = '" + getLoginUser() + "'");
    }
}