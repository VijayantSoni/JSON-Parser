package com.vijayantsoni.directorytask;

import android.os.AsyncTask;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Vijayant Soni on 10/12/2015.
 */
public class LoginTask extends AsyncTask<Void,Void,String>
{
    Context context;
    ProgressDialog p;

    String _URL = "http://thirpur.netii.net/thirpur.php?method=login";

    String parameter;

    SaveResponse delegate; //Reference of SaveResponse.

    public LoginTask(Context context)
    {
        this.context = context;
    }

    protected void setParameters(String parameters)
    {
        parameter = parameters;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        p.dismiss();
        Log.i("RESPONSE:", s);
        delegate.save(s);
        delegate = null;
    }

    @Override
    protected String doInBackground(Void... voids)
    {
        try
        {
            //Preparing connection.
            URL HttpPost = new URL(_URL);
            HttpURLConnection client = (HttpURLConnection) HttpPost.openConnection();

            //This ensures that we will write some data to the server which makes this request a POST request.
            //Hence no need to explicitly specify.
            // client.setRequestMethod("POST");
            client.setDoOutput(true);

            //Writing POST parameter.
            //OutputStreamWriter outputStream = new OutputStreamWriter(client.getOutputStream()); //This operates on byte streams.
            //Either can be used.
            PrintWriter outputStream = new PrintWriter(client.getOutputStream()); //This operates on character streams.
            String s = "mobile_no="+parameter;
            outputStream.write(s);
            outputStream.close();
            //Writing done.

            client.connect();

            //Process the response stream.
            StringBuilder stringBuilder = null;

            BufferedReader inputStream = new BufferedReader(new InputStreamReader(client.getInputStream()));

            stringBuilder = new StringBuilder();
            String line = "";

            while ((line = inputStream.readLine()) != null)
            {
                stringBuilder.append(line + "\n");
            }

            inputStream.close();
            client.disconnect();

            JSONObject jsonObject = new JSONObject(stringBuilder.toString());
            String message = jsonObject.getString("success");

            return message;
        }
        catch (Exception e)
        {
            return e.getMessage();
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        p = new ProgressDialog(context);
        p.setMessage("Please Wait...");
        p.setCancelable(false);
        p.show();
    }
}
