package com.vijayantsoni.directorytask;

import android.app.ListActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Vijayant Soni on 11/12/2015.
 */
public class DirectoryActivity extends ListActivity implements SaveUser
{

    //To store all the person objects which are returned by the AsyncTask.
   ArrayList<Person> persons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance_history);


        //Executing AsyncTask to fetch data from the server.
        DirectoryTask directoryTask = new DirectoryTask(this);
        //passes current context to the reference variable of StoreUser interface so that it could call the saveUser method which
        // is implemented in this Activity..
        directoryTask.saveUser = this;
        directoryTask.execute();

        //Filtering list.
        final EditText editText = (EditText)findViewById(R.id.filter);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                //Creating a new List of Persons based on the filtering.
                ArrayList<Person> personArrayList = new ArrayList<Person>();

                if(editText.getText().equals(""))
                {
                    //Set the original list if no characters are entered to filter.
                    setListAdapter(new MyAdapter(persons));
                }
                else {

                    //From the list of all persons, choose those which satisfy the filter condition and put them in the new list
                    //created above (personArrayList) and pass this list to MyAdapter.
                    for (int p = 0; p < persons.size(); p++) {
                        Person person = persons.get(p);

                        String name = person.getfName() + " " + person.getlName();

                        if (name.toLowerCase().contains(charSequence.toString().toLowerCase())) {
                            personArrayList.add(person);
                        }
                    }
                    setListAdapter(new MyAdapter(personArrayList));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void  saveUser(ArrayList<Person> persons)
    {
        //Storing the response from the AsyncTask and also passing it to MyAdapter.
        this.persons = persons;
        setListAdapter(new MyAdapter(persons));
    }

    class MyAdapter extends BaseAdapter
    {
        ArrayList<Person> persons;

        MyAdapter(ArrayList<Person> persons)
        {
            this.persons = persons;
        }

        @Override
        public int getCount() {
            return persons.size();
        }

        @Override
        public Object getItem(int i) {
            return persons.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup)
        {
            LayoutInflater inflater = getLayoutInflater();
            view = inflater.inflate(R.layout.custom_maintenance,viewGroup,false);

            //Filling values in the View.
            Person person = persons.get(i);

            TextView fname =(TextView)view.findViewById(R.id.name);
            fname.setText(person.getfName() + " " + person.getlName());

            return view;
        }
    }
}