package com.vijayantsoni.directorytask;

import android.os.AsyncTask;
import android.app.ProgressDialog;
import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Vijayant Soni on 10/12/2015.
 */
public class DirectoryTask extends AsyncTask<Void,Void,String>
{
    Context context;
    ProgressDialog p;

    //Reference variable of SaveUser interface, implemented by DirectoryActivity.
    SaveUser saveUser;

    //List to store detail of each person.
    ArrayList<Person> persons = new ArrayList<Person>();

    String _URL = "http://thirpur.netii.net/thirpur.php?method=directory";

    public DirectoryTask(Context context)
    {
        this.context = context;
    }

    @Override
    protected void onPostExecute(String s)
    {
        super.onPostExecute(s);
        p.dismiss();

        //Passing back the list to the DirectoryActivity.
        saveUser.saveUser(persons);
        saveUser = null;
    }

    @Override
    protected String doInBackground(Void... voids)
    {
        try
        {
            //Preparing connection.
            URL HttpPost = new URL(_URL);
            HttpURLConnection client = (HttpURLConnection) HttpPost.openConnection();

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

            //creating JSON object and parsing it.
            JSONArray jsonArray = new JSONArray(stringBuilder.toString());

            for(int i=0;i<jsonArray.length();i++)
            {
                //Fetching each object.
                JSONObject j = jsonArray.getJSONObject(i);

                //Mapping each JSON object to the Person Class' object. (person is a POJO class).
                Person person = new Person(j.getString("First_Name"),j.getString("Surname"),j.getString("Middle_Name"),
                        j.getString("Address1"),j.getString("Address2"),j.getString("Profile_Pic"),j.getString("City"),
                        j.getString("Blood"),j.getString("Mobile_No"),j.getString("Home_No"),j.getString("Birth_Date"));

                //Adding each POJO(Plain Old Java Object) to the list.
                persons.add(person);

            }

            return "Success";
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
