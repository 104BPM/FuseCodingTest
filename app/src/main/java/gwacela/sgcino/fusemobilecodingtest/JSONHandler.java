package gwacela.sgcino.fusemobilecodingtest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Steve.Gwacela on 2016/09/13.
 */
public class JSONHandler {

    private static final int HTTP_TIMEOUT = 30 * 1000; // milliseconds
    public HttpsURLConnection FuseConnection;
    public String ReadJSON(String StrURL)
    {
        String Result = "default";
        try
        {
            java.net.URL ConvertedUrl = new URL(StrURL);
            FuseConnection = (HttpsURLConnection) ConvertedUrl.openConnection();
            FuseConnection.setReadTimeout(HTTP_TIMEOUT);
            FuseConnection.setConnectTimeout(HTTP_TIMEOUT);
            FuseConnection.setRequestMethod("GET");
            FuseConnection.connect();

            int response = FuseConnection.getResponseCode();
            if (response==200)
            {
                InputStream Temp;
                Temp = FuseConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(Temp, "iso-8859-1"), 8);
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null)
                {
                    sb.append(line );
                }
                Temp.close();
                Result =  sb.toString();
                Log.i("Success","Success: "+ response);
            }
            else
            {
                Result = "Failed";
            }
        }

        catch (Exception E)
        {
            Log.e("Error","Error with JSON: "+ E.getMessage());
        }
        return Result;
        //this class will receive URL and read the JSON response jh
    }

    public Bitmap DownloadIMG (String ImgURL)
    {
        Bitmap result= null;
        InputStream IMGStream;
        try
        {
            URL ConvertToURl = new URL(ImgURL);
            //open connecition
            FuseConnection = (HttpsURLConnection) ConvertToURl.openConnection();
            FuseConnection.setConnectTimeout(HTTP_TIMEOUT);
            FuseConnection.setRequestMethod("GET");
            FuseConnection.setReadTimeout(HTTP_TIMEOUT);
            FuseConnection.connect();

            if (FuseConnection.getResponseCode()== 200)
            {
                Log.i("Image download: ","Image downloaded successfully");
                IMGStream= FuseConnection.getInputStream();
                result= BitmapFactory.decodeStream(IMGStream);
            }
            else
            {
                Log.i("Image download:","Image download fail");
            }
        }
        catch (Exception E)
        {

        }
        return result;
    }
}
