package gwacela.sgcino.fusemobilecodingtest;


import android.graphics.Bitmap;

import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.JsonReader;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.*;

import org.json.JSONObject;
public class MainPage extends AppCompatActivity {
    //global variable
    private EditText edtCompanySearch;
    public HTTPSConnectionClass ManipulateJSON;
    private ImageView ImgToDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        FindViews();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void FindViews() {
        try {
            ImgToDisplay = (ImageView) findViewById(R.id.imgDownloaded);
            edtCompanySearch = (EditText) findViewById(R.id.edtCompany);
            edtCompanySearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    edtCompanySearch.setBackgroundColor(Color.WHITE);
                    ImgToDisplay.setImageBitmap(null);
                }
            });

            edtCompanySearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView Main, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_GO) {
                        //Perform String manipulation
                        //Call AsyncTask
                        //Url https://[COMPANY NAME].fusion-universal.com/api/v1/company.json
                        //check if network is enabled
                        ConnectivityManager ConnectMngr = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                        NetworkInfo CurrNetworkInfo = ConnectMngr.getActiveNetworkInfo();
                        if (CurrNetworkInfo!=null)
                        {
                            if (edtCompanySearch.getText().toString().length() > 1)
                            {
                                String URL = edtCompanySearch.getText().toString().replaceAll(" ", "");
                                new Query().execute("https://"+URL+".fusion-universal.com/api/v1/company.json");
                                Toast.makeText(getBaseContext(),"Processing request,please wait...",Toast.LENGTH_LONG).show();
                                //replace whitespace
                            } else {
                                Toast.makeText(getBaseContext(), "Text is too short", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(getBaseContext(), "Please ensure your device has internet connection", Toast.LENGTH_SHORT).show();
                        }
                    }
                    return false;
                }
            });
        } catch (Exception E) {
            Log.e("Error", "Error loading views: " + E.getMessage());
        }

    }

    private class Query extends AsyncTask<String, Void, String>
    {
        Bitmap ImgBitmap = null;
        String Result="";
        JSONObject CurrJSONObject;
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            try {

                //when url has been received, go fetch results here
                ManipulateJSON = new HTTPSConnectionClass();
                Result = ManipulateJSON.ReadJSON(params[0]);
                JsonReader JReader;
                Log.i("Success",Result);
                params[0]= "";
                if(!(Result.equals("Failed"))) {
                    CurrJSONObject = new JSONObject(Result);
                    Log.i("JSON Success: ", CurrJSONObject.getString("name"));
                   ImgBitmap = ManipulateJSON.DownloadIMG(CurrJSONObject.getString("logo"));
                    Log.i("BItmap: ", CurrJSONObject.getString("logo"));
                }
            } catch (Exception E) {
                Log.e("GET JSON", "Error executing the following URL" + params[0]);
            }
            return params[0];
        }

        @Override
        protected void onPostExecute(String s) {

            try {
                if(!(Result.equals("Failed"))) {
                    edtCompanySearch.clearComposingText();
                    edtCompanySearch.setText(CurrJSONObject.getString("name"));
                    edtCompanySearch.setBackgroundColor(Color.GREEN);
                    ImgToDisplay.setImageBitmap(ImgBitmap);
                    ImgToDisplay.setMaxWidth(100);
                    ImgToDisplay.setMaxHeight(100);
                }
                else
                {
                    edtCompanySearch.setBackgroundColor(Color.RED);

                }
                super.onPostExecute(s);
            }
            catch(Exception E)
            {
                Log.e("GET JSON", "Error");
            }
        }

    }

}