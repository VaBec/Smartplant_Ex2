package de.htwg.smartplant.view.login;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import de.htwg.smartplant.R;
import de.htwg.smartplant.presenter.LoginPresenter;

public class LoginView extends AppCompatActivity implements LoginPresenter.ILoginView {

    private final LoginPresenter loginPresenter = new LoginPresenter(this);

    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getSupportActionBar().hide();

        setContentView(R.layout.login);

        loginButton = findViewById(R.id.loginButton);
        EditText nameEditText = findViewById(R.id.nameText);
        EditText passwordEditText = findViewById(R.id.passwordText);

        loginButton.setOnClickListener(v ->
                loginPresenter.updateUser(nameEditText.getText().toString(), passwordEditText.getText().toString()));
    }

    @Override
    public void updateToLoggedIn() {
        // Hide keyboard
        InputMethodManager imm = (InputMethodManager)
                this.getSystemService(Context.INPUT_METHOD_SERVICE);

        imm.hideSoftInputFromWindow(findViewById(android.R.id.content), 0);
    }

    @Override
    public void startButtonAnimation() {
        //loginButton.setText("");
        //loginButton.setAnimation(buttonAnimation);
        loginButton.setVisibility(View.INVISIBLE);

    }

    @Override
    public void showStandardButton() {
        findViewById(R.id.loginButton).setEnabled(true);
    }

    @Override
    public void showToast(String text, int toastLength) {
        Toast toast = Toast.makeText(getApplicationContext(), text, toastLength);

        toast.show();
    }
}
