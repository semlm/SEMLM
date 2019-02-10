package multilevel.multilevelmarkitning.Admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import multilevel.multilevelmarkitning.R;

public class Admin_Login extends AppCompatActivity {

    EditText userid;
    EditText password;
    Button loginbtn;
    TextView signup;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__login);
        userid=(EditText)findViewById(R.id.admin_id);
        password=(EditText)findViewById(R.id.admin_password);
        loginbtn=(Button) findViewById(R.id.admin_submit);
        signup=(TextView)findViewById(R.id.admin_sigup);

      requestQueue= Volley.newRequestQueue(Admin_Login.this);


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Admin_Login.this,Admin_Register.class));

            }
        });

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String UserID=userid.getText().toString().trim();
                String Password=password.getText().toString().trim();
                if(TextUtils.isEmpty(UserID) || TextUtils.isEmpty(Password))
                {
                    userid.setError("Please fill the UserId");
                    password.setError("Please fill the Password");
                }
                else
                {
                    if(Password.length()<6)
                    {
                        password.setError("Minimum 6 lengths required");
                    }
                    else
                    {

                      AdminLogin adminLogin=new AdminLogin(UserID,Password,new Response.Listener<String>(){
                          @Override

                          public void onResponse(String response) {
                              Log.i("Response", response);

                              try
                              {
                                  if (new JSONObject(response).getBoolean("success"))
                                  {

                                      Intent intent=new Intent(getApplicationContext(), Admin_Panel.class);
                                      startActivity(intent);
                                      //Toast.makeText(Admin_Login.this, "Account Successfully Created", Toast.LENGTH_SHORT).show();
                                      //finish();
                                  }
                                  else
                                      Toast.makeText(Admin_Login.this, "Something Has Happened. Please Try Again!", Toast.LENGTH_SHORT).show();
                              }
                              catch (JSONException e)
                              {
                                  e.printStackTrace();
                              }
                          }


                      });

                        requestQueue.add(adminLogin);
                    }

                }
            }
        });

    }

    public class AdminLogin extends StringRequest {

        private static final String REGISTER_URL = "https://veiled-heat.000webhostapp.com/MLM/Admin/Login.php ";
        private Map<String, String> parameters;

        public AdminLogin(String Id, String password, Response.Listener<String> listener) {
            super(Method.POST, REGISTER_URL, listener, null);
            parameters = new HashMap<>();
            parameters.put("adminid",Id);
            parameters.put("password", password);


        }

        @Override
        protected Map<String, String> getParams() throws AuthFailureError
        {
            return parameters;
        }
    }

}





