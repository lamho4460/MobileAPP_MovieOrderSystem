package com.cityu.ast.movieordersystem;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.MenuItem;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;



public class MDescription extends AppCompatActivity {

    private DatabaseHelper sqlHelper;
    private SQLiteDatabase mydb;
    private String[] allColumns;
    private Cursor myCursor;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showmovie);





        Bundle bundle = this.getIntent().getExtras();
        long movieID = bundle.getLong("movieID");


        allColumns = new String[] {
                DatabaseHelper.COL_MOVIEID,
                DatabaseHelper.COL_MOVIENAME,
                DatabaseHelper.COL_MOVIEPRICE,
                DatabaseHelper.COL_MOVIEDISCRIPTION,
                DatabaseHelper.COL_MOVIERATING,
                DatabaseHelper.COL_POSTER,
                DatabaseHelper.COL_ACTOR,
                DatabaseHelper.COL_DIRECTOR ,
                DatabaseHelper.COL_GENRE
        };
        sqlHelper = new DatabaseHelper(this);
        mydb = sqlHelper.getReadableDatabase();

        myCursor = mydb.query(DatabaseHelper.TABLE_MOVIE,
                allColumns, "movieID="+String.valueOf(movieID), null, null, null, null);


        ImageView imageViewM = (ImageView) findViewById(R.id.imageViewM);
        TextView movieTitle = (TextView) findViewById(R.id.textViewTitle);
        TextView DesTitle = (TextView) findViewById(R.id.textViewDe);

        TextView price = (TextView) findViewById(R.id.textViewPrice);
        TextView rating = (TextView) findViewById(R.id.textViewRating);

        TextView actor = (TextView) findViewById(R.id.textViewActor);
        TextView director = (TextView) findViewById(R.id.textViewDirector);
        TextView genre = (TextView) findViewById(R.id.textViewGenre);


        myCursor.moveToFirst();
        if (!myCursor.isAfterLast()) {
            setTitle("MOVIEDESCRIPTION");
            imageViewM.setImageResource(myCursor.getInt(myCursor.getColumnIndex(DatabaseHelper.COL_POSTER)));
            movieTitle.setText(myCursor.getString(myCursor.getColumnIndex(DatabaseHelper.COL_MOVIENAME)));
            DesTitle.setText(myCursor.getString(myCursor.getColumnIndex(DatabaseHelper.COL_MOVIEDISCRIPTION)));

            price.setText(myCursor.getString(myCursor.getColumnIndex(DatabaseHelper.COL_MOVIEPRICE)));
            rating.setText(myCursor.getString(myCursor.getColumnIndex(DatabaseHelper.COL_MOVIERATING)));
            actor.setText(myCursor.getString(myCursor.getColumnIndex(DatabaseHelper.COL_ACTOR)));
            director.setText(myCursor.getString(myCursor.getColumnIndex(DatabaseHelper.COL_DIRECTOR)));
            genre.setText(myCursor.getString(myCursor.getColumnIndex(DatabaseHelper.COL_GENRE)));
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void addItem(View v) {
        Bundle bundle = this.getIntent().getExtras();
        int movieID = (int)bundle.getLong("movieID");
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        dbHelper.addToCart(movieID, 1);
        Toast.makeText(MDescription.this,"item added",Toast.LENGTH_SHORT).show();
    }

    public void back(View V)
    {
        // Bundle bundleB = this.getIntent().getExtras();
        //int position = bundleB.getInt("position");
        Bundle bundleB = this.getIntent().getExtras();
        long movieID = bundleB.getLong("movieID");
        if(movieID <= 01 ) {
            Intent myIntent = new Intent();
            myIntent.setClass(MDescription.this, HomeActivity.class);
            startActivity(myIntent);
        }else if (movieID >= 02) {


            Intent intent = new Intent();
            intent.setClass(MDescription.this, MDescription.class);

            movieID--;
            Bundle bundle = new Bundle();
            bundle.putLong("movieID", movieID);
            intent.putExtras(bundle);

            startActivity(intent);

        }

    }
    public void next(View V)
    {

        Bundle bundleB = this.getIntent().getExtras();
        long movieID = bundleB.getLong("movieID");
        if(movieID < 5){
            Intent intent = new Intent();
            intent.setClass(MDescription.this, MDescription.class);

            movieID++;
            Bundle bundle = new Bundle();
            bundle.putLong("movieID", movieID);
            intent.putExtras(bundle);

            startActivity(intent);
        }
    }













}