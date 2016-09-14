package gwacela.sgcino.fusemobilecodingtest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

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
        FuseConnection.disconnect();
        return Result;
        //this class will receive URL and read the JSON response jh
    }

    public Bitmap DownloadIMG (String ImgURL)
    {
        Bitmap result= null;
        InputStream IMGStream;
        try
        {
            HttpURLConnection FuseConnection2;
            URL ConvertToURl = new URL(ImgURL);
            //open connecition
            FuseConnection2 = (HttpURLConnection) ConvertToURl.openConnection();
            FuseConnection2.setConnectTimeout(HTTP_TIMEOUT);
            FuseConnection2.setRequestMethod("GET");
            FuseConnection2.setReadTimeout(HTTP_TIMEOUT);
            FuseConnection2.connect();
            int response = FuseConnection2.getResponseCode();
            if (response == 200)
            {
                Log.i("Image download: ","Image downloaded successfully");
                IMGStream = FuseConnection2.getInputStream();
                result = BitmapFactory.decodeStream(IMGStream);
            }
            else
            {
                Log.i("Image download:","Image download fail");
            }
        }
        catch (Exception E)
        {
            Log.e("Error Downloading","There seems to be a problem: "+E.getMessage());
        }
        return result;
    }
}
