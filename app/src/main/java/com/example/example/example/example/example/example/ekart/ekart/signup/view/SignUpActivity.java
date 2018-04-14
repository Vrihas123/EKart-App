package com.example.example.example.example.example.example.ekart.ekart.signup.view;

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
import com.example.example.example.example.example.example.ekart.ekart.customerDatabase.CustomerContact;
import com.example.example.example.example.example.example.ekart.ekart.customerDatabase.DatabaseHelperCustomer;
import com.example.example.example.example.example.example.ekart.ekart.helper.SharedPrefs;
import com.example.example.example.example.example.example.ekart.ekart.login.view.LoginActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {

    private EditText inputSignName, inputSignMobile,inputSignEmail,inputSignPassword,inputSignRetypePassword;
    private TextInputLayout inputLayoutSignName, inputLayoutSignMobile,inputLayoutSignEmail,inputLayoutSignPassword,inputLayoutSignRetypePassword;
    private Button btn_login,btn_signUp;
    private String name,mobile,email,password,retypePassword;
    private SharedPrefs sharedPrefs;
    DatabaseHelperCustomer helperCustomer = new DatabaseHelperCustomer(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        sharedPrefs = new SharedPrefs(this);
        inputLayoutSignName = (TextInputLayout) findViewById(R.id.input_layout_signup_name);
        inputLayoutSignMobile = (TextInputLayout) findViewById(R.id.input_layout_signup_mobile);
        inputLayoutSignEmail = (TextInputLayout) findViewById(R.id.input_layout_signup_email);
        inputLayoutSignPassword = (TextInputLayout) findViewById(R.id.input_layout_signup_password);
        inputLayoutSignRetypePassword = (TextInputLayout) findViewById(R.id.input_layout_signup_retypePassword);
        inputSignName = (EditText) findViewById(R.id.input_signup_name);
        inputSignEmail = (EditText) findViewById(R.id.input_signup_email);
        inputSignMobile = (EditText) findViewById(R.id.input_signup_mobile);
        inputSignPassword = (EditText) findViewById(R.id.input_signup_password);
        inputSignRetypePassword = (EditText) findViewById(R.id.input_signup_retypePassword);
        btn_signUp = (Button) findViewById(R.id.btn_signup);
        btn_login = (Button) findViewById(R.id.proceed_login_btn);

        inputSignName.addTextChangedListener(new MyTextWatcher(inputSignName));
        inputSignMobile.addTextChangedListener(new MyTextWatcher(inputSignMobile));
        inputSignEmail.addTextChangedListener(new MyTextWatcher(inputSignEmail));
        inputSignPassword.addTextChangedListener(new MyTextWatcher(inputSignPassword));

        btn_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (sharedPrefs.isLoggedIn()){
                    Toast.makeText(SignUpActivity.this,"Already Logged In !!Welcome!!",Toast.LENGTH_LONG).show();
                    Intent i =new Intent(SignUpActivity.this, HomeActivity.class);
                    startActivity(i);
                    finish();
                }else{
                    Intent i = new Intent(SignUpActivity.this,LoginActivity.class);
                    startActivity(i);
                    finish();
                }

            }
        });
    }

    public void submit(){
        if (!validateName()){
            return;
        }
        if (!validateMobile()){
            return;
        }
        if (!validateEmail()){
            return;
        }
        if (!validatePassword()){
            return;
        }
        if (!validateRetypePassword()){
            return;
        }

        CustomerContact c = new CustomerContact();
        c.setName(name);
        c.setMobile(mobile);
        c.setEmail(email);
        c.setPassword(retypePassword);

        helperCustomer.insertContact(c);
        Toast.makeText(this,"Registration Successfull, proceeding to login page",Toast.LENGTH_LONG).show();
        Intent i = new Intent(SignUpActivity.this,LoginActivity.class);
        startActivity(i);
        finish();
    }

    private boolean validateName(){
        name = inputSignName.getText().toString().trim();
        if (name.isEmpty()){
            inputLayoutSignName.setError(getString(R.string.err_msg_name));
            requestFocus(inputSignName);
            return false;
        }else {
            inputLayoutSignName.setErrorEnabled(false);
        }
        return true;
    }
    private boolean validateMobile(){
        mobile = inputSignMobile.getText().toString().trim();
        if (mobile.isEmpty()){
            inputLayoutSignMobile.setError(getString(R.string.err_msg_mobile));
            requestFocus(inputSignMobile);
            return false;
        }else if (mobile.length()!=10){
            inputLayoutSignMobile.setError(getString(R.string.err_msg_mobile_digits));
            requestFocus(inputSignMobile);
            return false;
        }else {
            inputLayoutSignMobile.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateEmail(){
        email = inputSignEmail.getText().toString().trim();
        if (email.isEmpty()){
            inputLayoutSignEmail.setError(getString(R.string.err_msg_email));
            requestFocus(inputSignEmail);
            return false;
        }else if(emailInvalid(email)) {
            inputLayoutSignEmail.setError("Entered email is not a valid email");
            requestFocus(inputSignEmail);
            return false;
        }else {
            inputLayoutSignEmail.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validatePassword(){
        password = inputSignPassword.getText().toString().trim();
        if (password.isEmpty()){
            inputLayoutSignPassword.setError(getString(R.string.err_msg_password));
            requestFocus(inputSignPassword);
            return false;
        }else {
            inputLayoutSignPassword.setErrorEnabled(false);
        }
        return true;
    }
    private boolean validateRetypePassword(){
        retypePassword = inputSignRetypePassword.getText().toString().trim();
        if (retypePassword.isEmpty()){
            inputLayoutSignRetypePassword.setError(getString(R.string.err_msg_retype_password));
            requestFocus(inputSignRetypePassword);
            return false;
        }else if(!password.equals(retypePassword)){
            inputLayoutSignRetypePassword.setError(getString(R.string.err_msg_confirm_password));
            requestFocus(inputSignRetypePassword);
            return false;
        }
        else {
            inputLayoutSignPassword.setErrorEnabled(false);
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

    public boolean emailInvalid(String email) {
        Pattern pattern;
        Matcher matcher;

        final String EMAIL_PATTERN =
                "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        boolean a = matcher.matches();
        return !a;
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
                case R.id.input_signup_name:
                    validateName();
                    break;
                case R.id.input_signup_mobile:
                    validateMobile();
                    break;
                case R.id.input_signup_email:
                    validateEmail();
                    break;
                case R.id.input_signup_password:
                    validatePassword();
                    break;
            }
        }
    }
}
