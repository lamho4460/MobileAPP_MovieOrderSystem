package com.cityu.ast.movieordersystem;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;


public class HomeActivity extends ListActivity{
    ListView listView;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private Cursor c;
    private SimpleCursorAdapter dbAdapter;
    private String[] allColumns;
    Button toCart;
    TextView showCredit;
    TextView showUser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toCart = (Button)findViewById(R.id.cart);
        showCredit = (TextView)findViewById(R.id.credit);
        showUser = (TextView)findViewById(R.id.username);

        allColumns = new String[]{
                DatabaseHelper.COL_POSTER,
                DatabaseHelper.COL_MOVIENAME,
                DatabaseHelper.COL_MOVIEPRICE,
                DatabaseHelper.COL_MOVIERATING,
        };

        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getReadableDatabase();
        c = db.rawQuery("SELECT rowid _id, poster, moviename, price, rating FROM movielist", null);
        //c = db.query(DatabaseHelper.TABLE_MOVIE, allColumns, null, null, null, null, null);

        String[] fromColumns = {
                DatabaseHelper.COL_POSTER,
                DatabaseHelper.COL_MOVIENAME,
                DatabaseHelper.COL_MOVIEPRICE,
                DatabaseHelper.COL_MOVIERATING,
        };

        int[] toViews = {
                R.id.poster,
                R.id.movieName,
                R.id.moviePrice,
                R.id.movieRate
        };

        dbAdapter = new SimpleCursorAdapter(this,
                R.layout.row,
                c,
                fromColumns,
                toViews,
                0);

        setListAdapter(dbAdapter);

        c = db.rawQuery("SELECT rowid _id, SUM(amount) FROM cart WHERE user = '" + dbHelper.getLoginUser() + "'", null);
        if (c.getCount() != 0) {
            c.moveToFirst();
            int cartAmount = c.getInt(c.getColumnIndex("SUM(amount)"));
            toCart.setText("cart: " + cartAmount);
        }
        else
            toCart.setText("cart: " + 0);

        c = db.rawQuery("SELECT credit FROM registeruser WHERE username = '" + dbHelper.getLoginUser() + "'", null);
        c.moveToFirst();
        int credit = c.getInt(c.getColumnIndex("credit"));
        showCredit.setText("credit: " + credit);

        c = db.rawQuery("SELECT username FROM registeruser WHERE username = '" + dbHelper.getLoginUser() + "'", null);
        c.moveToFirst();
        String username = c.getString(c.getColumnIndex("username"));
        showUser.setText("user: " + username);
    }

    public void viewCart(View v) {
        Intent registerIntent = new Intent(HomeActivity.this,CartActivity.class);
        startActivity(registerIntent);
    }

    public void backHome(View v) {
        Intent registerIntent = new Intent(HomeActivity.this,HomeActivity.class);
        startActivity(registerIntent);
    }


    public void clearCart(View v) {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        db.execSQL("DELETE FROM cart WHERE user ='" + dbHelper.getLoginUser() + "'");
        Intent intent = new Intent();
        intent.setClass(HomeActivity.this, CartActivity.class);
        startActivity(intent);
    }

    public void logout(View v) {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        db.execSQL("UPDATE registeruser SET status = 'logout' WHERE status = 'login'");
        Intent registerIntent = new Intent(HomeActivity.this,LoginActivity.class);
        startActivity(registerIntent);
    }

    public void onListItemClick(ListView parent, View v, int position, long id) {


        Intent intent = new Intent();
        intent.setClass(HomeActivity.this, MDescription.class);

        Bundle bundle = new Bundle();
        bundle.putLong("movieID", id);
        intent.putExtras(bundle);

        startActivity(intent);
    }

    public void find(View v){
        Intent intent = new Intent();
        intent.setClass(HomeActivity.this, zFindMain.class);
        startActivity(intent);
    }


}

