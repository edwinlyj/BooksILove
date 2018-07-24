package com.example.a16022895.booksilove;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private ListView lvCategories;
    private ArrayList<BookCategory> alCategories;
    private ArrayAdapter<BookCategory> aaCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvCategories = (ListView)findViewById(R.id.ListViewCategories);
        alCategories = new ArrayList<BookCategory>();
        aaCategories = new ArrayAdapter<BookCategory>(this, android.R.layout.simple_list_item_1, alCategories);
        lvCategories.setAdapter(aaCategories);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String loginID = prefs.getString("loginid", "");
        String apikey = prefs.getString("apikey", "");

        HttpRequest request = new HttpRequest
                ("http://10.0.2.2/Books/getBookCategories.php");
        request.setOnHttpResponseListener(mHttpResponseListener);
        request.setMethod("POST");
        request.addData("loginid",loginID);
        request.addData("apikey",apikey);
        request.execute();

        lvCategories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BookCategory cat = alCategories.get(position);
                Intent intent = new Intent(MainActivity.this, displayBooks.class);
                intent.putExtra("bookcat", cat);
                startActivityForResult(intent, 1);
            }
        });


    }

    private HttpRequest.OnHttpResponseListener mHttpResponseListener =
            new HttpRequest.OnHttpResponseListener() {
                @Override
                public void onResponse(String response){
                    // process response here
                    try {
                        Log.d(TAG, "onResponse: " + response);
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i=0; i<jsonArray.length(); i++){
                            JSONObject jsonObj = jsonArray.getJSONObject(i);
                            String category = jsonObj.getString("book_category");
                            int id2 = jsonObj.getInt("book_category_id");
                            alCategories.add(new BookCategory(id2, category));
                        }
                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }
                    aaCategories.notifyDataSetChanged();
                }
            };

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.logOut) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = prefs.edit();
            editor.clear();
            editor.commit();

            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
