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

public class CartActivity extends ListActivity {
    ListView listView;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private Cursor c;
    private SimpleCursorAdapter dbAdapter;
    private String[] allColumns;
    TextView total;
    TextView showCredit;
    Button pay;
    TextView showUser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        showCredit = (TextView)findViewById(R.id.credit);
        pay = (Button)findViewById(R.id.button3);
        showUser = (TextView)findViewById(R.id.username);

        allColumns = new String[]{
                DatabaseHelper.COL_CARTITEMID,
                DatabaseHelper.COL_ITEM,
                DatabaseHelper.COL_AMOUNT,
                DatabaseHelper.COL_MOVIEPRICE,
        };

        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getReadableDatabase();
        c = db.rawQuery("SELECT cartItemID as _id, item, amount, price FROM cart WHERE user = '" + dbHelper.getLoginUser() + "'", null);
        //c = db.query(DatabaseHelper.TABLE_MOVIE, allColumns, null, null, null, null, null);

        String[] fromColumns = {
                DatabaseHelper.COL_ITEM,
                DatabaseHelper.COL_AMOUNT,
                DatabaseHelper.COL_MOVIEPRICE,
        };

        int[] toViews = {
                R.id.movieName,
                R.id.amount,
                R.id.moviePrice,
        };

        dbAdapter = new SimpleCursorAdapter(this,
                R.layout.row_cart,
                c,
                fromColumns,
                toViews,
                0);

        setListAdapter(dbAdapter);

        total = findViewById(R.id.total);
        c = db.rawQuery("SELECT rowid _id, SUM(price) FROM cart WHERE user = '" + dbHelper.getLoginUser() + "'", null);
        if (c.getCount() != 0) {
            c.moveToFirst();
            int totalCost = c.getInt(c.getColumnIndex("SUM(price)"));
            total.setText("Total: $" + totalCost);
        }
        else
            total.setText("Total: $" + 0);

        c = db.rawQuery("SELECT credit FROM registeruser WHERE username = '" + dbHelper.getLoginUser() + "'", null);
        c.moveToFirst();
        int credit = c.getInt(c.getColumnIndex("credit"));
        showCredit.setText("credit: " + credit);

        c = db.rawQuery("SELECT username FROM registeruser WHERE username = '" + dbHelper.getLoginUser() + "'", null);
        c.moveToFirst();
        String username = c.getString(c.getColumnIndex("username"));
        showUser.setText("user: " + username);

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int totalCost = 0;
                Cursor c = db.rawQuery("SELECT rowid _id, SUM(price) FROM cart WHERE user = '" + dbHelper.getLoginUser() + "'", null);
                if (c.getCount() != 0) {
                    c.moveToFirst();
                    totalCost = c.getInt(c.getColumnIndex("SUM(price)"));
                }
                dbHelper.decreaseCredit(totalCost);
                db.execSQL("DELETE FROM cart WHERE user ='" + dbHelper.getLoginUser() + "'");
                Intent intent = new Intent();
                intent.setClass(CartActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });
    }

    public void backHome(View v) {
        Intent registerIntent = new Intent(CartActivity.this,HomeActivity.class);
        startActivity(registerIntent);
    }

    public void clearCart(View v) {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        db = dbHelper.getReadableDatabase();
        db.execSQL("DELETE FROM cart WHERE user ='" + dbHelper.getLoginUser() + "'");
        Intent intent = new Intent();
        intent.setClass(CartActivity.this, CartActivity.class);
        startActivity(intent);
    }

    public void onListItemClick(ListView parent, View v, int position, long id) {


        Intent intent = new Intent();
        intent.setClass(CartActivity.this, MDescription.class);

        Bundle bundle = new Bundle();
        bundle.putLong("movieID", id);
        intent.putExtras(bundle);

        startActivity(intent);
    }
}
