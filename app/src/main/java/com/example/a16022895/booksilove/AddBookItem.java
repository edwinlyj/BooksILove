package com.example.a16022895.booksilove;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

public class AddBookItem extends AppCompatActivity {


    private EditText etName,etAuthor, etSummary;
    private String loginID, apikey, category_id;
    private Button btnAdd;
    private int catId;
    private BookCategory cat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book_item);
        etName = findViewById(R.id.etName);
        etAuthor = findViewById(R.id.etAuthor);
        etSummary = findViewById(R.id.etSummary);
        btnAdd = findViewById(R.id.btnAdd);

        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //loginID = prefs.getString("loginid", "");
        //apikey = prefs.getString("apikey", "");
        //Intent i = getIntent();
        //cat = (BookCategory) i.getSerializableExtra("bookcat");
        //catId = cat.getCategoryId();

        Intent i = getIntent();//get
        loginID = i.getStringExtra("loginid");
        apikey = i.getStringExtra("apikey");
        category_id = i.getStringExtra("categoryId");
        catId  = Integer.parseInt(category_id);


        Toast.makeText(getApplicationContext(),loginID, Toast.LENGTH_SHORT).show();
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpRequest request = new HttpRequest("http://10.0.2.2/Books/addBook.php");
                request.setOnHttpResponseListener(mHttpResponseListener);
                request.setMethod("POST");
                request.addData("loginid",loginID);
                request.addData("apikey",apikey);
                request.addData("categoryId", String.valueOf(catId));
                request.addData("bookName",etName.getText().toString().trim());
                request.addData("author",etAuthor.getText().toString().trim());
                request.addData("summary",etSummary.getText().toString().trim()+"");
                request.execute();
                finish();
            }
        });

    }

    // Code for step 2 start
    private HttpRequest.OnHttpResponseListener mHttpResponseListener =
            new HttpRequest.OnHttpResponseListener() {
                @Override
                public void onResponse(String response) {

                    // process response here
                    try {
                        Log.i("JSON Results: ", response);
                        JSONObject jsonObj = new JSONObject(response);
                        String au = jsonObj.getBoolean("authorized")+"";

                        if(au.equalsIgnoreCase("true")){
                            Toast.makeText(getApplicationContext(),"Book item is inserted successfully", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(), "Create new Book item is unsuccessful", Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            };
}
