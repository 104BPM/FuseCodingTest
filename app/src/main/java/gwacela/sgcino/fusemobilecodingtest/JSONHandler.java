package gwacela.sgcino.fusemobilecodingtest;

import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Steve.Gwacela on 2016/09/13.
 */
public class JSONHandler {

    private static final int HTTP_TIMEOUT = 30 * 1000; // milliseconds
    public String Get(String StrURL)
    {
        String Result="";
        try
        {
            java.net.URL ConvertedUrl = new URL(StrURL);
            HttpsURLConnection FuseConnection = (HttpsURLConnection) ConvertedUrl.openConnection();
            FuseConnection.setReadTimeout(HTTP_TIMEOUT);
            FuseConnection.setConnectTimeout(HTTP_TIMEOUT);
            FuseConnection.setRequestMethod("GET");
            FuseConnection.connect();

            int response = FuseConnection.getResponseCode();
            if (response==200)
            {
                Result = "success";
                Log.i("Success","Success: "+ response);
            }
            else
            {
                Result=  "fail";
                Log.i("Fail","Failure: "+ response);
            }
        }

        catch (Exception E)
        {
            Log.e("Error","Error with JSON: "+ E.getMessage());
        }
        return Result;
        //this class will receive URL and read the JSON response
    }
}
