package com.example.kevin_2201729386;

import android.content.ClipData;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.ui.layout.Arrangement;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class SearchFrag extends Fragment {

    public static final String TEXT01 = "com.example.kevin_2201729386.EXTRA_TEXT0";
    public static final String TEXT02 = "com.example.kevin_2201729386.EXTRA_TEXT1";
    public static final String TEXT03 = "com.example.kevin_2201729386.EXTRA_TEXT2";
    public static final String TEXT04 = "com.example.kevin_2201729386.EXTRA_TEXT3";
    public static final String TEXT05 = "com.example.kevin_2201729386.EXTRA_TEXT4";

    ArrayList<ItemMovie> arrayList = new ArrayList<>();
    RecyclerView mRecyclerView;
    AdapterSearchMov AdapterS;
    String [] a = new String[1];
    private SQLiteDatabase nDatabase;
    RecyclerView.LayoutManager mLayoutManager;
    TextView namamov;
    String Judul1;
    String Year1;
    String IDmov1;
    String Linkgmbr1;
    RequestQueue queue;
    EditText search;
    String x;
    int delsave = 0;

    public SearchFrag() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // mLayoutManager = new LinearLayoutManager(this);
        // mAdapter = new AdapterSearchMov(Item,this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View  view =  inflater.inflate(R.layout.fragment_search, container, false);
        AdapterS = new AdapterSearchMov(arrayList , view.getContext());
        mLayoutManager = new GridLayoutManager(view.getContext(),2);
        mRecyclerView = view.findViewById(R.id.recyclerview1);
        mRecyclerView.setHasFixedSize(true);
        DatabaseSavedHelper SavedHelper = new DatabaseSavedHelper(view.getContext());
        nDatabase = SavedHelper.getWritableDatabase();

        search = view.findViewById(R.id.search);

        final Button buttonsearch = view.findViewById(R.id.butoncari);

        queue = Volley.newRequestQueue(view.getContext());
        buttonsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList.clear();
                x=search.getText().toString();
                jsonParse();
                AdapterS.notifyDataSetChanged();

//                Toast.makeText(view.getContext(), namamov.getText().toString(), Toast.LENGTH_SHORT);
            }
        });
        mRecyclerView.setLayoutManager(mLayoutManager);
        AdapterS.notifyDataSetChanged();
        mRecyclerView.setAdapter(AdapterS);


        AdapterS.setOnItemLongClickListener(new AdapterSearchMov.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(int position,View v) {
                Cursor cursor = ambilSaved();
                String ID12 = arrayList.get(position).getID();
                while (cursor.moveToNext()){
                    String IDmov5 = cursor.getString(cursor.getColumnIndex(DatabaseSaved.databaseSaved.COLOUMN_IDMOV));
                    if(ID12.equals(IDmov5)){

                        //Log.d("alim13",IDmov5);
                      //  Log.d("alim23",ID12);
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
                Judul1 = arrayList.get(position).getJudul();
                Year1 = arrayList.get(position).getYear();
                IDmov1 = arrayList.get(position).getID();
                Linkgmbr1 = arrayList.get(position).getLinkGambar();

                popup.show();
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                       switch (item.getItemId()){
                           case R.id.menu_save:
                               saveMov();
                               break;
                           case R.id.delmenu:
                               delMov();
                               break;
                       }
                        return false;
                    }
                });


                delsave=0;
            }
        });

        AdapterS.setOnItemClickListener(new AdapterSearchMov.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getContext(),DetailMovie.class);
//                arrayList.get(position).changeText1("Clicked");

                String judul = arrayList.get(position).getJudul();
                String Year = arrayList.get(position).getYear();
                String ID = arrayList.get(position).getID();
                String Linkgmbr = arrayList.get(position).getLinkGambar();
                Log.d("alim",judul);
                intent.putExtra(TEXT01, judul);
                intent.putExtra(TEXT02, Year);
                intent.putExtra(TEXT03, ID);
                intent.putExtra(TEXT04, Linkgmbr);
                intent.putExtra(TEXT05, "Search");
                startActivity(intent);
                AdapterS.notifyDataSetChanged();
            }


        });
        AdapterS.notifyDataSetChanged();
        return view;

    }



    private void jsonParse() {

        String url = "https://www.omdbapi.com/?s="+x+"&apikey=961a57f1";
        Log.d("kevin2",x);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override

                    public void onResponse(JSONObject response) {
                        Log.d("kevin",response.toString());
                        try {
                            JSONArray jsonArray = response.getJSONArray("Search");
//                                    response.getJSONArray("Search");
                            Log.d("kevin",jsonArray.length()+"");
                            for (int i=0;i<jsonArray.length();i++){

                                JSONObject search = jsonArray.getJSONObject(i);

                                String judul = search.getString("Title");
                                String tahun = search.getString("Year");
                                String imbdID = search.getString("imdbID");
                                String linkgam = search.getString("Poster");

                                arrayList.add(new ItemMovie(judul, imbdID , tahun , linkgam));
                                AdapterS.notifyDataSetChanged();
                                Log.d("kevin",judul+tahun+imbdID+linkgam);
                            }
                        } catch (JSONException e) {
                            Log.d("kevin","error 1");
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("kevin","error 2");
                error.printStackTrace();
            }
        });
        queue.add(request);
    }


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

    }
}
