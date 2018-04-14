package com.example.example.example.example.example.example.ekart.ekart.login.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.example.example.example.example.example.ekart.ekart.HomeActivity;
import com.example.example.example.example.example.example.ekart.ekart.R;
import com.example.example.example.example.example.example.ekart.ekart.customerDatabase.DatabaseHelperCustomer;
import com.example.example.example.example.example.example.ekart.ekart.helper.SharedPrefs;
import com.example.example.example.example.example.example.ekart.ekart.signup.view.SignUpActivity;


public class LoginActivity extends AppCompatActivity {

    private EditText inputLoginName,inputLoginPassword;
    private TextInputLayout inputLayoutLoginName,inputLayoutLoginPassword;
    private Button btn_login,btn_backToSignUp;
    private SharedPrefs sharedPrefs;
    private String name,password, valPassword, valName;
    DatabaseHelperCustomer helper = new DatabaseHelperCustomer(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedPrefs = new SharedPrefs(this);
        inputLayoutLoginName = (TextInputLayout) findViewById(R.id.input_layout_login_name);
        inputLayoutLoginPassword = (TextInputLayout) findViewById(R.id.input_layout_login_password);
        inputLoginName = (EditText) findViewById(R.id.input_login_name);
        inputLoginPassword = (EditText) findViewById(R.id.input_login_password);
        btn_login = (Button) findViewById(R.id.btn_confirm_login);
        btn_backToSignUp = (Button) findViewById(R.id.btn_back_sign);

        btn_backToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(i);
                finish();
            }
        });

        inputLoginName.addTextChangedListener(new MyTextWatcher(inputLoginName));
        inputLoginPassword.addTextChangedListener(new MyTextWatcher(inputLoginPassword));

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });
    }


    public void submit(){
        if (!validateName()){
            return;
        }
        if (!validatePassword()){
            return;
        }
        valPassword = helper.searchPass(name);
        if (password.equals(valPassword)){
            Toast.makeText(this,"Login Successfull",Toast.LENGTH_LONG).show();
            sharedPrefs.setLogin(true);
            Intent ii = new Intent(LoginActivity.this,HomeActivity.class);
            startActivity(ii);
            finish();
        }
        else{
            Toast.makeText(this,"Credentials doest not match",Toast.LENGTH_LONG).show();
        }


    }

    private boolean validateName(){
        name = inputLoginName.getText().toString().trim();
        if (name.isEmpty()){
            inputLayoutLoginName.setError(getString(R.string.err_msg_name));
            requestFocus(inputLoginName);
            return false;
        }else {
            inputLayoutLoginName.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validatePassword(){
        password = inputLoginPassword.getText().toString().trim();
        if (password.isEmpty()){
            inputLayoutLoginPassword.setError(getString(R.string.err_msg_password));
            requestFocus(inputLoginPassword);
            return false;
        }else {
            inputLayoutLoginPassword.setErrorEnabled(false);
        }
        return true;
    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }


    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.input_login_name:
                    validateName();
                    break;
                case R.id.input_login_password:
                    validatePassword();
                    break;
            }
        }
    }
}
