package gwacela.sgcino.fusemobilecodingtest;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.*;

import java.net.HttpURLConnection;

public class MainPage extends AppCompatActivity {
    //global variable
    private EditText edtCompanySearch;
    public JSONHandler ManipulateJSON;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        FindViews();
    }

    public void FindViews() {
        try {
            edtCompanySearch = (EditText) findViewById(R.id.edtCompany);
            edtCompanySearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView Main, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_GO) {
                        //Perform String manipulation

                        //Call AsyncTask

                        //Url https://[COMPANY NAME].fusion-universal.com/api/v1/company.json

                        if (edtCompanySearch.getText().toString().length() > 1)
                        {
                            String URL = edtCompanySearch.getText().toString().replaceAll(" ", "");
                            new Query().execute("https://fuse.fusion-universal.com/api/v1/company.json");
                            //Toast.makeText(getBaseContext(),"https://"+Temp+".fusion-universal.com/api/v1/company.json",Toast.LENGTH_SHORT).show();
                            //replace whitespace
                        } else {
                            Toast.makeText(getBaseContext(), "Text is too short", Toast.LENGTH_SHORT).show();
                        }
                    }
                    return false;
                }
            });
        } catch (Exception E) {
            Log.e("Error", "Error loading views: " + E.getMessage());
        }

    }

    public void Processing(String URL) {

    }

    private class Query extends AsyncTask<String, Void, String>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                //when url has been received, go fetch results here
                ManipulateJSON = new JSONHandler();
                ManipulateJSON.Get(params[0]);
            } catch (Exception E) {
                Log.e("GET JSON", "Error executing the following URL" + params[0]);
            }
            return params[0];
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(getBaseContext(), s, Toast.LENGTH_SHORT).show();
            super.onPostExecute(s);
        }
    }


}