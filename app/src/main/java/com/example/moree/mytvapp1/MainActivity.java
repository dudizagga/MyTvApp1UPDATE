package com.example.moree.mytvapp1;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessException;
import com.backendless.exceptions.BackendlessFault;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import weborb.client.Fault;

public class MainActivity extends AppCompatActivity {
    Context context;
    EditText userId, usePass;
    Button Login, register;
    Fragmentcontainer fragmentcontainer;
    TextInputLayout input_Email, input_pass;

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
        input_Email = (TextInputLayout) findViewById(R.id.Input_Email);
        input_pass = (TextInputLayout) findViewById(R.id.Input_pass);
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
        String Myuser = userId.getText().toString();
        String Mypass = usePass.getText().toString();

        if (userId.getText().toString().length() < 1 && usePass.getText().toString().length() < 1) {
            input_Email.setErrorTextAppearance(R.style.error_appearance);
            input_Email.setError("Enter you Email");
            return;
        }
        try {


            Backendless.UserService.login(Myuser, Mypass, new AsyncCallback<BackendlessUser>() {
                @Override
                public void handleResponse(BackendlessUser response) {
                    Intent First = new Intent(context, Fragmentcontainer.class);
                    startActivity(First);
                }

                @Override
                public void handleFault(BackendlessFault fault) {


                    if (fault.getCode().equals("3003")) {
                        input_Email.setErrorTextAppearance(R.style.error_appearance);
                        input_Email.setError("Email Not registed");
                    } else {
                        Toast.makeText(context, "Network Error ", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (Exception e) {
            Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }


    private void Register() {
        final AlertDialog myRegister = new AlertDialog.Builder(context).create();
        //final AlertDialog optionAlert =myRegister.create();
        View inflateRegister = LayoutInflater.from(context).inflate(R.layout.register, null, false);
        myRegister.setCanceledOnTouchOutside(false);
        final TextInputLayout Email = (TextInputLayout) inflateRegister.findViewById(R.id.Email);
        final TextInputLayout pass1 = (TextInputLayout) inflateRegister.findViewById(R.id.layout_Pass1);
        final TextInputLayout pass2 = (TextInputLayout) inflateRegister.findViewById(R.id.layout_Pass2);
        final EditText username = (EditText) inflateRegister.findViewById(R.id.RegisterName);
        final EditText userpass1 = (EditText) inflateRegister.findViewById(R.id.userPass1);
        final EditText userpass2 = (EditText) inflateRegister.findViewById(R.id.userPass2);
        //userpass1.setTransformationMethod(new PasswordTransformationMethod());
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
                    Email.setErrorTextAppearance(R.style.error_appearance);
                    Email.setError("Enter Email");

                }

                if (userpass1.getText().toString().length() < 6 || userpass1.getText().toString().isEmpty()) {
                    pass1.setError("Not a valid Password");
                    pass1.setErrorTextAppearance(R.style.error_appearance);

                }

                if (!userpass1.getText().toString().equals(userpass2.getText().toString())) {
                    pass2.setError("Password dont match");
                    pass2.setErrorTextAppearance(R.style.error_appearance);

                }

                BackendlessUser RegisterUser = new BackendlessUser();
                RegisterUser.setProperty("email", username.getText().toString());
                RegisterUser.setProperty("password", userpass1.getText().toString());
                try {


                    Backendless.UserService.register(RegisterUser, new AsyncCallback<BackendlessUser>() {
                        @Override
                        public void handleResponse(BackendlessUser response) {

                            Toast.makeText(context, "User Registed", Toast.LENGTH_SHORT).show();
                            myRegister.dismiss();
                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            if (fault.getCode().equals("3033")) {
                                Email.setErrorTextAppearance(R.style.error_appearance);
                                Email.setError("Email Already Exists");
                            }
                            if (fault.getCode().equals("3040")) {
                                Email.setErrorTextAppearance(R.style.error_appearance);
                                Email.setError("Not Valid Email");
                            }
                        }
                    });

                } catch (Exception e) {
                    Toast.makeText(context, "Please Enter the Following!", Toast.LENGTH_SHORT).show();
                }
            }


        });

        myRegister.setView(inflateRegister);
        myRegister.show();

    }


}
