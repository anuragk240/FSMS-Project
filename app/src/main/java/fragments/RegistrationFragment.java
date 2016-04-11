package fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import constants.RegistrationConst;
import fsms.my1stproject.com.financialstatement.R;

public class RegistrationFragment extends Fragment implements View.OnClickListener {

    private EditText firstname, lastname, emailid, username, password, confirmpassword, companyname;
    private TextView passworderror, gotologin;
    private Button registerButton;

    private HashMap<String, String> data = new HashMap<>();

    public RegistrationFragment(){

    }

    public OnFragmentInteractionListener mcallback;

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.registerbuttonid:
                if (test()) {
                    data.put(RegistrationConst.FIRSTNAME, firstname.getText().toString());
                    data.put(RegistrationConst.LASTNAME, lastname.getText().toString());
                    data.put(RegistrationConst.EMAIL_ID, emailid.getText().toString());
                    data.put(RegistrationConst.USERNAME, username.getText().toString());
                    data.put(RegistrationConst.COMPANY_NAME, companyname.getText().toString());

                    Log.d("Regfragment usernme", data.get(RegistrationConst.USERNAME));
                    data.put(RegistrationConst.PASSWORD, password.getText().toString());
                    mcallback.getdata(data);
                }
                break;
            case R.id.passworderror:
                v.setVisibility(View.INVISIBLE);
                break;
            case R.id.gotologinid:
                //Not set
                break;
        }
    }

    public interface OnFragmentInteractionListener{
        public void getdata(HashMap<String, String> data);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try{
            mcallback = (OnFragmentInteractionListener) context;
        }
        catch(ClassCastException e){
            throw new ClassCastException(context.toString() +
                    " must implement OnFragmentInteractionListener");
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //inflate layout for this fragment
        View v = inflater.inflate(R.layout.register, container, false);

        firstname = (EditText) v.findViewById(R.id.firstnameid);
        lastname = (EditText) v.findViewById(R.id.lastnameid);
        emailid = (EditText) v.findViewById(R.id.emailid);
        username = (EditText) v.findViewById(R.id.regusernameid);
        password = (EditText) v.findViewById(R.id.regpasswordid);
        confirmpassword = (EditText) v.findViewById(R.id.confirmpasswrdid);
        registerButton = (Button) v.findViewById(R.id.registerbuttonid);
        passworderror = (TextView) v.findViewById(R.id.passworderror);
        gotologin = (TextView) v.findViewById(R.id.gotologinid);
        companyname = (EditText) v.findViewById(R.id.companynameid);

        registerButton.setOnClickListener(this);
        passworderror.setOnClickListener(this);
        gotologin.setOnClickListener(this);

        return v;
    }

    private boolean test(){
        boolean result = true;
        if(firstname.getText().toString().isEmpty()){
            firstname.setHint("*Required Field");
            result = false;
        }
        if(emailid.getText().toString().isEmpty()){
            emailid.setHint("*Required Field");
            result = false;
        }
        if(username.getText().toString().isEmpty()){
            username.setHint("*Required Field");
            result = false;
        }
        String s = password.getText().toString();
        if(s.length()<8||s.contains(" ")){
            passworderror.setVisibility(View.VISIBLE);
            result = false;
        }
        if(!s.equals(confirmpassword.getText().toString())){
            Toast.makeText(getContext(),"*Passwords did not match",Toast.LENGTH_LONG).show();
            result = false;
        }
        if(companyname.getText().toString().isEmpty()){
            companyname.setHint("*Required Field");
            result = false;
        }
        return result;
    }


}
