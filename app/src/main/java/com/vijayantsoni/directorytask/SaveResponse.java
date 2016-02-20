package com.vijayantsoni.directorytask;

/**
 * Created by Vijayant Soni on 10/12/2015.
 */

//Interface for transferring data between AsyncTask and Main UI thread.

public interface SaveResponse
{
    //This method is called by AsyncTask on the reference variable of this interface which is made to hold the object of MainActivity Class.
    //MainActivity implements this interface. A reference of this is created in AsyncTask (LoginTask).
    void save(String param);
}
