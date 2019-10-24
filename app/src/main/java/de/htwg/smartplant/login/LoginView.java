package de.htwg.smartplant.login;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import de.htwg.smartplant.R;

public class LoginView extends AppCompatActivity implements LoginPresenter.ILoginView {

    private final LoginPresenter loginPresenter = new LoginPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getSupportActionBar().hide();

        setContentView(R.layout.login);

        Button loginButton = findViewById(R.id.loginButton);
        EditText nameEditText = findViewById(R.id.nameText);
        EditText passwordEditText = findViewById(R.id.passwordText);

        loginButton.setOnClickListener(v ->
                loginPresenter.updateUser(nameEditText.getText().toString(), passwordEditText.getText().toString()));
    }

    @Override
    public void login() {
        // Hide keyboard
        InputMethodManager imm = (InputMethodManager)
                this.getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = findViewById(android.R.id.content);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
