package fragments;


import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;

import constants.MiscConst;
import constants.RegistrationConst;
import fsms.my1stproject.com.financialstatement.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {

    HashMap<String, String> data = new HashMap<>();
    private EditText loginusername;
    private EditText loginpassword;
    private Button submit;
    private TextView forgotpassword, app_title;

    public LoginFragment() {
        // Required empty public constructor
    }

    OnFragmentInteractionListener mcallback;

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.submitid:
                if(test()){
                    data.put(RegistrationConst.USERNAME, loginusername.getText().toString());
                    data.put(RegistrationConst.PASSWORD, loginpassword.getText().toString());
                    mcallback.getlogindata(data);
                }
                break;
            case R.id.forgotpasswordid:

                break;
        }
    }

    public interface OnFragmentInteractionListener{
        public void getlogindata(HashMap<String, String> data);
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
        View v = inflater.inflate(R.layout.login, container, false);
        loginusername = (EditText) v.findViewById(R.id.usernameid);
        loginpassword = (EditText) v.findViewById(R.id.passwordid);
        submit = (Button) v.findViewById(R.id.submitid);
        forgotpassword = (TextView) v.findViewById(R.id.forgotpasswordid);
        app_title = (TextView) v.findViewById(R.id.login_app_title);

        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), MiscConst.FONT_TIMESNEWROMAN_BOLD);
        app_title.setTypeface(font);

        submit.setOnClickListener(this);
        forgotpassword.setOnClickListener(this);

        return v;
    }

    private boolean test(){
        boolean result = true;
        if(loginusername.getText().toString().isEmpty()){
            loginusername.setHint("*Required Field");
            result = false;
        }
        if(loginpassword.getText().toString().isEmpty()){
            loginpassword.setHint("*Required Field");
            result = false;
        }
        return result;
    }

}
