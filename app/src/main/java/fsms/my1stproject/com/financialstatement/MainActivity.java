package fsms.my1stproject.com.financialstatement;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import constants.RegistrationConst;
import fragments.LoginFragment;
import fragments.RegistrationFragment;

public class MainActivity extends AppCompatActivity implements RegistrationFragment.OnFragmentInteractionListener, LoginFragment.OnFragmentInteractionListener {

    private HashMap<String, String> RegistrationDetails = null;
    private SharedPreferences Logininfo;
    private SharedPreferences.Editor editor;

    private FragmentTransaction fragmentTransaction;

    private RegistrationFragment register = new RegistrationFragment();
    private LoginFragment login = new LoginFragment();

    private RelativeLayout fragmentContainer;

    public static String company_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Logininfo = getPreferences(Context.MODE_PRIVATE);
        editor = Logininfo.edit();

        fragmentContainer = (RelativeLayout) findViewById(R.id.fragment_container);

        fragmentTransaction = getSupportFragmentManager().beginTransaction();

        //load suitable fragment based on registration status
        if(!Logininfo.contains(RegistrationConst.IS_REGISTERED)){    //means app is running for the first time
            editor.putBoolean(RegistrationConst.IS_REGISTERED, false);
            editor.commit();
            fragmentTransaction.add(R.id.fragment_container, register);
            Log.d("oncreate ","isregistered key is not present");
        }
        else{                   //app is not running for the first time
            if(!Logininfo.getBoolean(RegistrationConst.IS_REGISTERED, false)){  //user is NOT registered
                fragmentTransaction.add(R.id.fragment_container, register);
                Log.d("Not registered ", "RegistrationFragment added");
            }
            else{           //user is registered
                fragmentTransaction.add(R.id.fragment_container, login);
                Log.d("Registered ", "LoginFragment added");
            }
        }
        fragmentContainer.setVisibility(View.VISIBLE);
        fragmentTransaction.commit();

    }

    //---------to handle login and registration--------------------------------------------------------------------------------------
    //--------------------------------------------------------------------------------------------------------------------------------

    @Override
    public void getdata(HashMap<String, String> data) {
        //this callback indicates that registration has been successful
        RegistrationDetails = data;
        Logininfo = getPreferences(Context.MODE_PRIVATE);
        editor = Logininfo.edit();

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Log.d("Before putting info ", String.valueOf(Logininfo.contains(RegistrationConst.USERNAME)));
        editor.putString(RegistrationConst.FIRSTNAME, data.get(RegistrationConst.FIRSTNAME));
        editor.putString(RegistrationConst.LASTNAME, data.get(RegistrationConst.LASTNAME));
        editor.putString(RegistrationConst.EMAIL_ID, data.get(RegistrationConst.EMAIL_ID));
        editor.putString(RegistrationConst.USERNAME, data.get(RegistrationConst.USERNAME));
        editor.putString(RegistrationConst.PASSWORD, data.get(RegistrationConst.PASSWORD));
        editor.putString(RegistrationConst.COMPANY_NAME, data.get(RegistrationConst.COMPANY_NAME));
        editor.putBoolean(RegistrationConst.IS_REGISTERED, true);
        editor.commit();
        Log.d("After Reg,username ", data.get(RegistrationConst.USERNAME).toString());
        //navigate to login page
        LoginFragment login = new LoginFragment();
        fragmentTransaction.replace(R.id.fragment_container, login);
        fragmentTransaction.commit();
    }

    @Override
    public void getlogindata(HashMap<String, String> data) {
        Logininfo = getPreferences(Context.MODE_PRIVATE);
        editor = Logininfo.edit();

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Map<String, ?> map =  Logininfo.getAll();
        Object value = map.get(RegistrationConst.USERNAME);
        Log.d("MainAct Username", value.toString());
        if(Logininfo.contains(RegistrationConst.USERNAME) && Logininfo.contains(RegistrationConst.PASSWORD)){
            if((data.get(RegistrationConst.USERNAME).equals(Logininfo.getString(RegistrationConst.USERNAME, "Not found"))) &&
                    (data.get(RegistrationConst.PASSWORD).equals(Logininfo.getString(RegistrationConst.PASSWORD, "Not found")))){
                fragmentTransaction.remove(login);
                fragmentTransaction.commit();
                fragmentContainer.setVisibility(View.GONE);
                Intent gototabs = new Intent(MainActivity.this, TabActivity.class);
                startActivity(gototabs);
                finish();
            }
            else{
                Toast.makeText(getApplicationContext(),"Invalid Username or Password!",Toast.LENGTH_LONG).show();
                return;
            }
        }
        else{
            Log.d("Getting login data ", "Username or password not found in preferences");
        }

        company_name = Logininfo.getString(RegistrationConst.COMPANY_NAME, "Not found");
    }
}
