package com.vijayantsoni.directorytask;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements SaveResponse
{
    Button login;
    LinearLayout login_ll;
    EditText mobile_number;

    boolean hidden = true;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);

        login = (Button)findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(hidden)
                {
                    login_ll = (LinearLayout)findViewById(R.id.login_ll);
                    login_ll.setVisibility(View.VISIBLE);
                    hidden = false;
                }
                else
                {
                    mobile_number = (EditText)findViewById(R.id.mobile_number);
                    String m = mobile_number.getText().toString();

                    if(check(m))
                    {
                        LoginTask task = new LoginTask(LoginActivity.this);
                        task.setParameters(m);
                        task.delegate = LoginActivity.this; //A data member of LoginTask AsyncTask.(Type SaveResponse)
                        task.execute();
                    }

                    else
                    {
                        Toast.makeText(LoginActivity.this,"Enter 10 digit number",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    //Checking the input
    protected boolean check(String m)
    {
        if(m.length()<10 || m.length()>10)
        {
            return false;
        }
        return true;
    }

    //Reading response given by AsyncTask.
    //Method of SaveResponse interface. Called by LoginTask AsyncTask when the task is completed.
    //using a Interface as a medium to transfer response data from AsynTask to main thread.
    @Override
    public void save(String param)
    {
        if(param.equals("no"))
        {
            Toast.makeText(this,"Wrong mobile number",Toast.LENGTH_LONG).show();
            mobile_number.setText("");
        }
        else if(param.equals("yes"))
        {
            Toast.makeText(this,"Welcome",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(LoginActivity.this,DirectoryActivity.class);
            startActivity(intent);
        }
    }
}