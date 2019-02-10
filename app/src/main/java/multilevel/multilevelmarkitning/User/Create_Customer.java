package multilevel.multilevelmarkitning.User;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

import multilevel.multilevelmarkitning.Admin.Admin_Register;
import multilevel.multilevelmarkitning.Admin.RegisterRequest;
import multilevel.multilevelmarkitning.Customer.Customer_Home_Page;
import multilevel.multilevelmarkitning.R;
import multilevel.multilevelmarkitning.Validation;

public class Create_Customer extends AppCompatActivity {
    Spinner spinner;
    String[] level={"Level 1","Level 2","Level 3","Level 4","Level 5"};
    EditText name;

    EditText location;
    EditText profession;
    EditText mobile;
    Button createbtn;
    EditText password;
    EditText email;
    private RadioGroup radioSexGroup;
    private RadioButton radioSexButton;
    RequestQueue requestQueue;

    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create__customer);
        name=(EditText)findViewById(R.id.cus_user_name);
        builder=new AlertDialog.Builder(this);
        location=(EditText)findViewById(R.id.cus_user_address);
        profession=(EditText)findViewById(R.id.cus_user_profession);
        mobile=(EditText)findViewById(R.id.cus_user_mobile);
        spinner=(Spinner)findViewById(R.id.level);
        radioSexGroup=(RadioGroup)findViewById(R.id.cus_radiogrp);
        password=(EditText)findViewById(R.id.cus_user_password);
        email=(EditText)findViewById(R.id.cus_user_email);
        createbtn=(Button)findViewById(R.id.create_cus_btn);
        ArrayAdapter<String>arrayAdapter=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,level);
        spinner.setAdapter(arrayAdapter);
        requestQueue= Volley.newRequestQueue(Create_Customer.this);


        createbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name=name.getText().toString().trim();
                String Location=location.getText().toString().trim();
                String Profession=profession.getText().toString().trim();
                String Mobile=mobile.getText().toString().trim();
                String Level=spinner.getSelectedItem().toString().trim();
                String Password=password.getText().toString().trim();
                String Email=email.getText().toString().trim();




                if(TextUtils.isEmpty(Name)  || TextUtils.isEmpty(Location) || TextUtils.isEmpty(Profession) || TextUtils.isEmpty(Mobile) || TextUtils.isEmpty(Password) || TextUtils.isEmpty(Email))
                {
                   Toast.makeText(getApplicationContext(),"please fill the fields",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(Password.length()<6)
                    {
                        //Toast.makeText(getApplicationContext(),"password must be atleast 6 characters",Toast.LENGTH_SHORT).show();
                        password.setError("password must be atleast 6 characters");
                    }
                    else if(!Validation.emailValidation(Email))
                    {
                      //  Toast.makeText(getApplicationContext(),"please enter a valid email address",Toast.LENGTH_SHORT).show();
                        email.setError("please enter a valid email address");
                    }
                    else if(!Validation.mobileValidation(Mobile))
                    {
                        mobile.setError("please enter a valid mobile number");
                    }
                    else if(radioSexGroup.getCheckedRadioButtonId()==-1)
                    {
                        Toast.makeText(getApplicationContext(),"Please select your gender",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                       // String idgenerated=java.time.LocalDate.now().toString()+java.time.LocalTime.now().toString()+name.getText().toString().trim();
                        int selectedId=radioSexGroup.getCheckedRadioButtonId();
                        radioSexButton=(RadioButton)findViewById(selectedId);
                        String sex=radioSexButton.getText().toString().trim();
                        final String idgenerated = UUID.randomUUID().toString();



                        RegisterRequest registerRequest = new RegisterRequest(idgenerated,Name,,Profession,Password,Email,Mobile,sex,Location ,new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.i("Response", response);

                                try
                                {
                                    if (new JSONObject(response).getString("status").equals("register"))
                                    {

                                        //  Toast.makeText(Admin_Register.this, "Account Successfully Created", Toast.LENGTH_LONG).show();

                                        builder.setMessage("Your ID is "+idgenerated);
                                        builder.setCancelable(true);
                                        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                                Intent intent=new Intent(getApplicationContext(),User_Login.class);
                                                intent.putExtra("userid",idgenerated);
                                                startActivity(intent);
                                            }
                                        });

                                        AlertDialog alertDialog = builder.create();
                                        alertDialog.setTitle("Succesfully register");
                                        alertDialog.show();


                                        //finish();
                                    }
                                    else
                                        Toast.makeText(Admin_Register.this, "Something Has Happened. Please Try Again!", Toast.LENGTH_SHORT).show();
                                }
                                catch (JSONException e)
                                {
                                    e.printStackTrace();
                                }
                            }
                        });

                        requestQueue.add(registerRequest);





//                        String idgenerated = UUID.randomUUID().toString();
//                        builder.setMessage("YOUR ID IS "+idgenerated);
//                        builder.setCancelable(true);
//                        builder.setPositiveButton("ok", new DialogInterface.OnClickListener(){
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.cancel();

                        }
                    });
                        AlertDialog alert=builder.create();
                        alert.setTitle("Successfully created");
                        alert.show();
                     //   builder.create();
                    }

                }
            }
        });


    }
}
