package fsms.my1stproject.com.financialstatement;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import constants.ConstantStrings;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Logininfo = getPreferences(Context.MODE_PRIVATE);
        editor = Logininfo.edit();

        fragmentContainer = (RelativeLayout) findViewById(R.id.fragment_container);

        fragmentTransaction = getSupportFragmentManager().beginTransaction();

        //load suitable fragment based on registration status
        if(!Logininfo.contains(ConstantStrings.IS_REGISTERED)){    //means app is running for the first time
            editor.putBoolean(ConstantStrings.IS_REGISTERED, false);
            editor.commit();
            fragmentTransaction.add(R.id.fragment_container, register);
            Log.d("oncreate ","isregistered key is not present");
        }
        else{                   //app is not running for the first time
            if(!Logininfo.getBoolean(ConstantStrings.IS_REGISTERED, false)){  //user is NOT registered
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
        Log.d("Before putting info ", String.valueOf(Logininfo.contains(ConstantStrings.USERNAME)));
        editor.putString(ConstantStrings.FIRSTNAME, data.get(ConstantStrings.FIRSTNAME));
        editor.putString(ConstantStrings.LASTNAME, data.get(ConstantStrings.LASTNAME));
        editor.putString(ConstantStrings.EMAIL_ID, data.get(ConstantStrings.EMAIL_ID));
        editor.putString(ConstantStrings.USERNAME, data.get(ConstantStrings.USERNAME));
        editor.putString(ConstantStrings.PASSWORD, data.get(ConstantStrings.PASSWORD));
        editor.putString(ConstantStrings.COMPANY_NAME, data.get(ConstantStrings.COMPANY_NAME));
        editor.putBoolean(ConstantStrings.IS_REGISTERED, true);
        editor.commit();
        Log.d("After Reg,username ", data.get(ConstantStrings.USERNAME).toString());
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
        Object value = map.get(ConstantStrings.USERNAME);
        Log.d("MainAct Username", value.toString());
        if(Logininfo.contains(ConstantStrings.USERNAME) && Logininfo.contains(ConstantStrings.PASSWORD)){
            if((data.get(ConstantStrings.USERNAME).equals(Logininfo.getString(ConstantStrings.USERNAME, "Not found"))) &&
                    (data.get(ConstantStrings.PASSWORD).equals(Logininfo.getString(ConstantStrings.PASSWORD, "Not found")))){
                fragmentTransaction.remove(login);
                fragmentTransaction.commit();
                fragmentContainer.setVisibility(View.GONE);
                Intent gototabs = new Intent(MainActivity.this, TabActivity.class);
                startActivity(gototabs);
            }
            else{
                Toast.makeText(getApplicationContext(),"Invalid Username or Password!",Toast.LENGTH_LONG).show();
                return;
            }
        }
        else{
            Log.d("Getting login data ", "Username or password not found in preferences");
        }
    }
}
