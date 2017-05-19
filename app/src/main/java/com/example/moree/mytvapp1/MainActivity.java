package com.example.moree.mytvapp1;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentContainer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.async.callback.BackendlessCallback;
import com.backendless.exceptions.BackendlessException;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;
import com.backendless.property.UserProperty;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    Context context;
    EditText userId, usePass;
    Button Login, register;
    Fragmentcontainer fragmentcontainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Backendless.initApp(this, "B69DDA46-E458-378B-FF0E-F5F182F4A800", "B506595D-64F7-320B-FF90-227125992900", "v1");
        setPointer();

    }

    private void setPointer() {
        this.context = this;
        userId = (EditText) findViewById(R.id.userName);
        usePass = (EditText) findViewById(R.id.usePass);
        Login = (Button) findViewById(R.id.btnLogin);
        register = (Button) findViewById(R.id.InRegister);
        fragmentcontainer = new Fragmentcontainer();

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register();
            }
        });

    }

    private void Login() {
        final String Myuser = userId.getText().toString();
        final String MyPass = usePass.getText().toString();

        /*Intent First = new Intent(context, Fragmentcontainer.class);
        startActivity(First);*/


//        try
//        {
//            BackendlessUser user=Backendless.UserService.login(Myuser,MyPass);
//        }
//        catch( BackendlessException exception )
//        {
//            // login failed, to get the error code, call exception.getFault().getCode()
//            Toast.makeText(context, "Exception: "+exception, Toast.LENGTH_SHORT).show();
//        }



        Backendless.UserService.login(Myuser , MyPass, new AsyncCallback<BackendlessUser>() {
            @Override
            public void handleResponse(BackendlessUser response) {
                Toast.makeText(context, "Logged"+Myuser, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(context,Fragmentcontainer.class));
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(context, "Error: " + fault.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
/*
        if (userId.getText().toString().length()<1 && usePass.getText().toString().length()<1)
        {
            Toast.makeText(context, "Please enter Email or Password", Toast.LENGTH_SHORT).show();
        return;
        }

        Toast.makeText(context, "my id" + userId.getText().toString() + usePass.getText().toString(), Toast.LENGTH_SHORT).show();
        Backendless.UserService.login(Myuser, Mypass, new AsyncCallback<BackendlessUser>() {
            @Override
            public void handleResponse(BackendlessUser response) {

            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(context, "Error" + fault.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

*/
    }

    private void Register() {
        final AlertDialog myRegister = new AlertDialog.Builder(context).create();
        //final AlertDialog optionAlert =myRegister.create();
        View inflateRegister = LayoutInflater.from(context).inflate(R.layout.register, null, false);
        myRegister.setCanceledOnTouchOutside(false);
        final EditText username = (EditText) inflateRegister.findViewById(R.id.RegisterName);
        final EditText userpass1 = (EditText) inflateRegister.findViewById(R.id.userPass1);
        final EditText userpass2 = (EditText) inflateRegister.findViewById(R.id.userPass2);
        Button btnRegister = (Button) inflateRegister.findViewById(R.id.btnRegister);
        Button btnCancel = (Button) inflateRegister.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRegister.dismiss();
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().toString().isEmpty() || username.getText().toString().length() < 4) {
                    username.setError("Enter your E-mail please");
                } else if (userpass1.getText().toString().length() < 6 || userpass1.getText().toString().isEmpty()) {
                    userpass1.setError("Password needs 6 characters and more");
                } else if (!userpass1.getText().toString().equals(userpass2.getText().toString())) {
                    userpass2.setError("Password dont match");
                } else {

                    BackendlessUser RegisterUser = new BackendlessUser();
                    RegisterUser.setEmail(username.getText().toString());
                    RegisterUser.setPassword(userpass1.getText().toString());
                    Backendless.UserService.register(RegisterUser, new AsyncCallback<BackendlessUser>() {
                        @Override
                        public void handleResponse(BackendlessUser response) {

                            Toast.makeText(context, "User Registed", Toast.LENGTH_SHORT).show();
                            myRegister.dismiss();
                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            Toast.makeText(context, "Error" + fault.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }


        });

        myRegister.setView(inflateRegister);
        myRegister.show();

    }


}
