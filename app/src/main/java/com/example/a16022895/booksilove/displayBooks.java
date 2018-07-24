package com.example.a16022895.booksilove;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class displayBooks extends AppCompatActivity {
    private static final String TAG = "DisplayMenuItems" ;
    private ListView lvItems;
    private ArrayList<book> alItems;
    private ArrayAdapter<book> aaItems;
    private String loginId;
    private String apikey;
    private BookCategory cat;
    private int catId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_books);

        lvItems = (ListView)findViewById(R.id.lvItems);
        alItems = new ArrayList<book>();
        aaItems= new ArrayAdapter<book>(this, android.R.layout.simple_list_item_1, alItems);
        lvItems.setAdapter(aaItems);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        loginId = prefs.getString("loginid", "");
        apikey = prefs.getString("apikey", "");
        Intent i = getIntent();
        cat = (BookCategory) i.getSerializableExtra("bookcat");
        catId = cat.getCategoryId();


        HttpRequest request = new HttpRequest
                ("http://10.0.2.2/Books/getBookByCategory.php");
        request.setOnHttpResponseListener(mHttpResponseListener);
        request.setMethod("POST");
        request.addData("loginid",loginId);
        request.addData("apikey",apikey);
        request.addData("categoryId", String.valueOf(catId));
        request.execute();

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                book selectedItem = alItems.get(position);

                book c = alItems.get(position);
                int bookId = c.getBook_id();
                String bookName = c.getBook_name();
                String bookAuthor = c.getBook_author();
                String bookSummary = c.getBook_summary();

                Intent i = new Intent(displayBooks.this,
                        BookDetails.class);
                i.putExtra("id", bookId);
                i.putExtra("bookName", bookName);
                i.putExtra("apikey", apikey);
                i.putExtra("loginId", loginId);
                i.putExtra("author", bookAuthor);
                i.putExtra("summary", bookSummary);
                Log.i("info", bookId + "");
                startActivity(i);
            }
        });

    }

    private HttpRequest.OnHttpResponseListener mHttpResponseListener =
            new HttpRequest.OnHttpResponseListener() {
                @Override
                public void onResponse(String response){
                    // process response here
                    Log.d(TAG, "onResponse: " + response);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i=0; i<jsonArray.length(); i++){
                            JSONObject jsonObj = jsonArray.getJSONObject(i);
                            String name = jsonObj.getString("book_name");
                            String author = jsonObj.getString("book_author");
                            String summary = jsonObj.getString("book_summary");
                            int id2 = jsonObj.getInt("book_id");
                            alItems.add(new book(id2,catId,name,author,summary));
                        }
                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }
                    aaItems.notifyDataSetChanged();
                }
            };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.addItem) {

            // TODO (8) Create an Intent to Create Contact
            // Put the following into intent:- loginId, apikey

            Intent intent = new Intent(getBaseContext(), AddBookItem.class);
            intent.putExtra("apikey", apikey);
            intent.putExtra("loginid", loginId);
            intent.putExtra("categoryId",String.valueOf(catId));
            startActivity(intent);
            return true;
        }else if(id == R.id.logOut) {
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