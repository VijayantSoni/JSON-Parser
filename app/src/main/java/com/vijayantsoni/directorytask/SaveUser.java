package com.vijayantsoni.directorytask;

import java.util.ArrayList;

/**
 * Created by Vijayant Soni on 13/12/2015.
 */

//Interface for transferring data between AsyncTask and Main UI thread.

public interface SaveUser
{
    void saveUser(ArrayList<Person> persons);
}
