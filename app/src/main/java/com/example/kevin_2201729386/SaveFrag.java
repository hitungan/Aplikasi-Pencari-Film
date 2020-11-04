package com.example.kevin_2201729386;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.android.volley.RequestQueue;

import java.util.ArrayList;


public class SaveFrag extends Fragment {

    public static final String TEXT01 = "com.example.kevin_2201729386.EXTRA_TEXT0";
    public static final String TEXT02 = "com.example.kevin_2201729386.EXTRA_TEXT1";
    public static final String TEXT03 = "com.example.kevin_2201729386.EXTRA_TEXT2";
    public static final String TEXT04 = "com.example.kevin_2201729386.EXTRA_TEXT3";
    public static final String TEXT05 = "com.example.kevin_2201729386.EXTRA_TEXT4";

    ArrayList<ItemMovie> arrayListsaved = new ArrayList<>();
    private SQLiteDatabase nDatabase;
    RecyclerView mRecyclerView;
    String Judul1;
    String Year1;
    String [] a = new String[1];
    int delsave = 0;
    String IDmov1;
    String Linkgmbr1;
    AdapterSearchMov AdapterS;
    RecyclerView.LayoutManager mLayoutManager;


    public SaveFrag() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View  view =  inflater.inflate(R.layout.fragment_save, container, false);

        mRecyclerView = view.findViewById(R.id.rcsave);
        mLayoutManager = new GridLayoutManager(getContext(),2);

        AdapterS = new AdapterSearchMov(arrayListsaved , getContext());

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(AdapterS);

        AdapterS.notifyDataSetChanged();
        DatabaseSavedHelper SavedHelper = new DatabaseSavedHelper(view.getContext());
        nDatabase = SavedHelper.getWritableDatabase();


        AdapterS.setOnItemLongClickListener(new AdapterSearchMov.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(int position,View v) {
                Cursor cursor = ambilSaved();
                String ID12 = arrayListsaved.get(position).getID();
                while (cursor.moveToNext()){
                    String IDmov5 = cursor.getString(cursor.getColumnIndex(DatabaseSaved.databaseSaved.COLOUMN_IDMOV));
                    if(ID12.equals(IDmov5)){

                        Log.d("alim13",IDmov5);
                        Log.d("alim23",ID12);
                        a[0] = ID12;
                        delsave = 1;
                        break;
                    }
                }
                PopupMenu popup = new PopupMenu(getContext(),v);
                if(delsave==0){
                    popup.inflate(R.menu.save);
                }
                else if(delsave==1){
                    popup.inflate(R.menu.del);
                }
                Judul1 = arrayListsaved.get(position).getJudul();
                Year1 = arrayListsaved.get(position).getYear();
                IDmov1 = arrayListsaved.get(position).getID();
                Linkgmbr1 = arrayListsaved.get(position).getLinkGambar();

                popup.show();
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.menu_save:
                                saveMov();
                                updateData();
                                break;
                            case R.id.delmenu:
                                delMov();
                                updateData();
                                break;
                        }
                        updateData();
                        return false;
                    }
                });

                AdapterS.notifyDataSetChanged();
                delsave=0;
            }
        });



        AdapterS.setOnItemClickListener(new AdapterSearchMov.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getContext(),DetailMovie.class);
//                arrayList.get(position).changeText1("Clicked");

                String judul = arrayListsaved.get(position).getJudul();
                String Year = arrayListsaved.get(position).getYear();
                String ID = arrayListsaved.get(position).getID();
                String Linkgmbr = arrayListsaved.get(position).getLinkGambar();
                Log.d("alim",judul);
                intent.putExtra(TEXT01, judul);
                intent.putExtra(TEXT02, Year);
                intent.putExtra(TEXT03, ID);
                intent.putExtra(TEXT04, Linkgmbr);
                intent.putExtra(TEXT05, "Save");
                startActivity(intent);
                AdapterS.notifyDataSetChanged();
            }


        });



        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateData();



    }
    public void updateData() {
        AdapterS.notifyDataSetChanged();
        arrayListsaved.clear();
        Cursor cursor = ambilSaved();
        while (cursor.moveToNext()){
            String Judul = cursor.getString(cursor.getColumnIndex(DatabaseSaved.databaseSaved.COLOUMN_JUDUL));
            String Year = cursor.getString(cursor.getColumnIndex(DatabaseSaved.databaseSaved.COLOUMN_YEAR));
            String IDmov = cursor.getString(cursor.getColumnIndex(DatabaseSaved.databaseSaved.COLOUMN_IDMOV));
            String LinkGambar = cursor.getString(cursor.getColumnIndex(DatabaseSaved.databaseSaved.COLOUMN_LINKGAM));
            arrayListsaved.add(new ItemMovie(Judul, IDmov , Year , LinkGambar));
            Log.d("alim1",Judul);
            Log.d("alim1",Year);
            Log.d("alim1",IDmov);
            Log.d("alim1",LinkGambar);
        }
        AdapterS.notifyDataSetChanged();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private Cursor ambilSaved(){
        Cursor cursor = nDatabase.rawQuery("SELECT * FROM "+ DatabaseSaved.databaseSaved.TABLE_NAME,
                null);
        return cursor;
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
        AdapterS.notifyDataSetChanged();
    }
}
