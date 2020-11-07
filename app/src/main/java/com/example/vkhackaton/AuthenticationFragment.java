package com.example.vkhackaton;

        import androidx.appcompat.app.AppCompatActivity;
        import androidx.appcompat.widget.Toolbar;

        import android.app.Fragment;

        import android.content.Context;
        import android.content.SharedPreferences;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Toast;

public class AuthenticationFragment extends Fragment {

    EditText mailET, passwordET;
    Button regBTN;
    View v;
    Button loginBTN;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_authentication, container, false);
        Toolbar toolbar = v.findViewById(R.id.toolbar);
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);
        //((MainActivity) getActivity()).getSupportActionBar().setTitle(R.string.Registration);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("");
        //((MainActivity) getActivity()).getSupportActionBar().setIcon(R.drawable.logo_main);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        mailET = v.findViewById(R.id.mailET);
        passwordET = v.findViewById(R.id.passwordET);
        loginBTN = v.findViewById(R.id.loginBTN);
        loginBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginBTN.setEnabled(false);
                loginBTN.setFocusable(false);
                loginBTN.setClickable(false);
                login();
            }
        });
        return v;
    }

    public void login(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final String[] message = APIConnector.authorize(mailET.getText().toString(), passwordET.getText().toString());
                    if (message[0].equals("token")){

                        String token = message[1];
                        SharedPreferences prefs = getActivity().getSharedPreferences(ApplicationConstants.PREFERENCES, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString(ApplicationConstants.TOKEN, token);
                        editor.commit();
                        v.post(new Runnable() {
                            @Override
                            public void run() {
                                loginBTN.setEnabled(true);
                                loginBTN.setFocusable(true);
                                loginBTN.setClickable(true);
                                ((MainActivity)getActivity()).login();
                            }
                        });
                    } else{
                        v.post(new Runnable() {
                            @Override
                            public void run() {
                                loginBTN.setEnabled(true);
                                loginBTN.setFocusable(true);
                                loginBTN.setClickable(true);
                                Toast.makeText(getActivity().getApplicationContext(), message[1], Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

}