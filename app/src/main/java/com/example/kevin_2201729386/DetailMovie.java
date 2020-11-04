package com.example.kevin_2201729386;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class DetailMovie extends AppCompatActivity {

    int delsave = 0;
    ArrayList<ItemMovie> arrayListsaved1 = new ArrayList<>();
    String [] a = new String[1];
    private SQLiteDatabase nDatabase;
    int sen = 0;
    String Judul1;
    String Year1;
    String IDmov1;
    String Linkgmbr1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);



        Intent intent = getIntent();
        String judul = intent.getStringExtra(SearchFrag.TEXT01);
        String Year = intent.getStringExtra(SearchFrag.TEXT02);
        String ID = intent.getStringExtra(SearchFrag.TEXT03);
        String Linkgmbr = intent.getStringExtra(SearchFrag.TEXT04);
//        String SaveSearch = intent.getStringExtra(SearchFrag.TEXT05);

//        if(SaveSearch.equals("Search")){
//            sen = 1;
//        }
//        else if(SaveSearch.equals("Save")){
//            sen = 2;
//        }

        DatabaseSavedHelper SavedHelper = new DatabaseSavedHelper(this);
        nDatabase = SavedHelper.getWritableDatabase();


        TextView detailJudul = (TextView) findViewById(R.id.DetailJudul);
        TextView detailTahun = (TextView) findViewById(R.id.DetailTahun);
        TextView detailID = (TextView) findViewById(R.id.DetailID);
        ImageView detailLinkGambar = (ImageView) findViewById(R.id.DetailGambar);
        Button saveDel = (Button) findViewById(R.id.btnsavedel);
        Cursor cursor = ambilSaved();
        arrayListsaved1.clear();
        while (cursor.moveToNext()){
            String IDmov5 = cursor.getString(cursor.getColumnIndex(DatabaseSaved.databaseSaved.COLOUMN_IDMOV));
            if(ID.equals(IDmov5)){
                saveDel.setText("Delete");
                Log.d("alim1",IDmov5);
                Log.d("alim2",ID);
                a[0] = ID;
                delsave = 1;
                break;
            }


        }

        saveDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(delsave==0){
                    saveMov();

                }
                else if(delsave==1){
                    delMov();

                }
                finish();
            }
        });
        Judul1 = judul;
        Year1 = Year;
        IDmov1 = ID;
        Linkgmbr1 = Linkgmbr;

        detailJudul.setText(judul);
        detailTahun.setText(Year);
        detailID.setText(ID);
        Glide.with(this).load(Linkgmbr).into(detailLinkGambar);
    }

    private void saveMov(){
        ContentValues cv = new ContentValues();

        cv.put(DatabaseSaved.databaseSaved.COLOUMN_JUDUL, Judul1);
        cv.put(DatabaseSaved.databaseSaved.COLOUMN_YEAR, Year1);
        cv.put(DatabaseSaved.databaseSaved.COLOUMN_IDMOV, IDmov1);
        cv.put(DatabaseSaved.databaseSaved.COLOUMN_LINKGAM, Linkgmbr1);

        nDatabase.insert(DatabaseSaved.databaseSaved.TABLE_NAME, null,cv);


    }
    private void delMov()
    {
        nDatabase.rawQuery("DELETE FROM "+ DatabaseSaved.databaseSaved.TABLE_NAME + " WHERE "+ DatabaseSaved.databaseSaved.COLOUMN_IDMOV + " = ?", a).moveToFirst();

    }

    private Cursor ambilSaved(){
        Cursor cursor = nDatabase.rawQuery("SELECT * FROM "+ DatabaseSaved.databaseSaved.TABLE_NAME,
                null);
        return cursor;
    }

}
