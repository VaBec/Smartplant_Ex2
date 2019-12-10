package de.htwg.smartplant.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import de.htwg.smartplant.R;
import de.htwg.smartplant.Utils;
import de.htwg.smartplant.main.MainActivity;

public class LoginView extends AppCompatActivity implements LoginPresenter.ILoginView {

    private LoginPresenter loginPresenter;

    private Button loginButton;
    private Button registerButton;

    private ProgressBar loginSpinner;
    private ProgressBar registerSpinner;

    private TextView registerLink;
    private TextView registerText;
    private TextView loginText;
    private TextView loginLink;

    private EditText loginNameEditText;
    private EditText passwordEditText;

    private EditText registerNameText;
    private EditText registerPassword1;
    private EditText registerPassword2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getSupportActionBar().hide();

        setContentView(R.layout.login);

        initGuiFields();
        setUpLoginGui();
        setListeners();
        loginPresenter = new LoginPresenter(this, this.getApplicationContext());
    }

    private void setListeners() {
        loginButton.setOnClickListener(v ->
                loginPresenter.loginUser(loginNameEditText.getText().toString(), passwordEditText.getText().toString()));

        registerButton.setOnClickListener(v ->
                loginPresenter.registerUser
                        (registerNameText.getText().toString(), registerPassword1.getText().toString(), registerPassword2.getText().toString()));

        registerLink.setOnClickListener(v ->
                loginPresenter.register());

        loginLink.setOnClickListener(v ->
                loginPresenter.login());
    }

    private void initGuiFields() {
        loginButton = findViewById(R.id.loginButton);
        loginSpinner = findViewById(R.id.loginSpinner);
        registerSpinner = findViewById(R.id.registerSpinner);
        loginNameEditText = findViewById(R.id.loginNameEditText);
        passwordEditText = findViewById(R.id.passwordText);
        registerLink = findViewById(R.id.registerLink);
        registerText = findViewById(R.id.registerText);
        registerNameText = findViewById(R.id.registerNameText);
        registerPassword1 = findViewById(R.id.registerPassword1);
        registerPassword2 = findViewById(R.id.registerPassword2);
        registerButton = findViewById(R.id.registerButton);
        loginText = findViewById(R.id.loginText);
        loginLink = findViewById(R.id.loginLink);
    }

    private void setUpLoginGui() {
        loginSpinner.setVisibility(View.INVISIBLE);
        registerSpinner.setVisibility(View.INVISIBLE);
        registerNameText.setVisibility(View.INVISIBLE);
        registerPassword1.setVisibility(View.INVISIBLE);
        registerPassword2.setVisibility(View.INVISIBLE);
        registerButton.setVisibility(View.INVISIBLE);
        loginText.setVisibility(View.INVISIBLE);
        loginLink.setVisibility(View.INVISIBLE);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Hide keyboard on any touch on the activity for better ux
        hideKeyboard();

        return true;
    }

    @Override
    public void startMainActivity(String name, String password) {
        Intent intent = new Intent(this, MainActivity.class);
        Utils.user = name;
        Utils.password = password;
        startActivity(intent);
    }

        @Override
    public void showLoginView() {
        registerPassword1.setVisibility(View.INVISIBLE);
        registerPassword2.setVisibility(View.INVISIBLE);
        registerNameText.setVisibility(View.INVISIBLE);
        registerButton.setVisibility(View.INVISIBLE);
        loginLink.setVisibility(View.INVISIBLE);
        loginText.setVisibility(View.INVISIBLE);
        registerSpinner.setVisibility(View.INVISIBLE);

        loginButton.setVisibility(View.VISIBLE);
        loginNameEditText.setVisibility(View.VISIBLE);
        passwordEditText.setVisibility(View.VISIBLE);
        registerLink.setVisibility(View.VISIBLE);
        registerText.setVisibility(View.VISIBLE);
    }

    @Override
    public void showRegisterView() {
        loginButton.setVisibility(View.INVISIBLE);
        loginNameEditText.setVisibility(View.INVISIBLE);
        passwordEditText.setVisibility(View.INVISIBLE);
        registerLink.setVisibility(View.INVISIBLE);
        registerText.setVisibility(View.INVISIBLE);

        registerNameText.setVisibility(View.VISIBLE);
        registerPassword1.setVisibility(View.VISIBLE);
        registerPassword2.setVisibility(View.VISIBLE);
        registerButton.setVisibility(View.VISIBLE);
        loginText.setVisibility(View.VISIBLE);
        loginLink.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideKeyboard() {
        // Hide keyboard for better UX
        InputMethodManager imm = (InputMethodManager)
                this.getSystemService(Context.INPUT_METHOD_SERVICE);

        View view = findViewById(android.R.id.content);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void startLoggingIn() {
        loginButton.setText("");
        loginButton.setEnabled(false);

        loginSpinner.setVisibility(View.VISIBLE);
        registerText.setVisibility(View.INVISIBLE);
        registerLink.setVisibility(View.INVISIBLE);
    }

    @Override
    public void startRegister() {
        registerButton.setText("");
        registerButton.setEnabled(false);

        registerSpinner.setVisibility(View.VISIBLE);
        loginText.setVisibility(View.INVISIBLE);
        loginLink.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showStandardLoginButton() {
        loginButton.setText("Login");
        loginButton.setEnabled(true);
        loginSpinner.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showStandardRegisterButton() {
        registerButton.setText("Registrieren");
        registerButton.setEnabled(true);
        registerSpinner.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showToast(String text, int toastLength) {
        Toast toast = Toast.makeText(getApplicationContext(), text, toastLength);

        toast.show();
    }
}
