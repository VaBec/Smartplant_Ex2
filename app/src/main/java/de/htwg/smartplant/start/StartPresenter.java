package de.htwg.smartplant.start;

import android.content.Context;

import org.json.JSONObject;

import de.htwg.smartplant.rest.jsonmodels.User;
import de.htwg.smartplant.rest.HttpNotifier;

public class StartPresenter implements HttpNotifier {

    private final ILoginView view;
    private final Context context;
    private StartModel startModel;
    private User user;

    public StartPresenter(ILoginView view, Context context) {
        this.view = view;
        this.context = context;
    }

    public void register() {
        view.showRegisterView();
    }

    public void login() {
        view.showLoginView();
    }

    public void registerUser(String name, String password1, String password2) {
        if(!password1.equals(password2)) {
            view.showToast("Die Passwörter stimmen nicht überein.");
        } else {
            this.startModel = new StartModel(new User(name, password1), this, context);
            this.startModel.sendRegisterRequest();
            view.hideKeyboard();
        }
    }

    public void loginUser(String name, String password) {
        if(name.equals("") || password.equals("")) {
            view.showToast("Name und Passwort angeben.");
        } else {
            this.user = new User(name, password);
             this.startModel = new StartModel(this.user, this, context);
             this.startModel.sendLoginRequest();
             view.hideKeyboard();
        }
    }

    public void showException(Exception e) {
        view.showToast(e.getMessage());
    }

    @Override
    public void showRetry() {
        view.showToast("Fehler - Versuche es erneut.");
    }

    @Override
    public void showFailure(String errorMessage) {
        view.showToast(errorMessage);

        if (this.startModel.isLogin()) {
            view.showLoginView();
            view.showStandardLoginButton();
        } else {
            view.showRegisterView();
            view.showStandardRegisterButton();
        }

    }

    @Override
    public void showSuccess(JSONObject response) {
        String toastMessage;
        try {
            toastMessage = response.getString("payload");
        }catch(Exception e) {
            toastMessage = e.getMessage();
        }

        view.showToast(toastMessage);

        if(this.startModel.isLogin()) {
            view.startMainActivity(user);
        } else {
            view.showStandardRegisterButton();
            view.showLoginView();
            view.showStandardLoginButton();
        }
    }

    @Override
    public void showStart() {
        if(this.startModel.isLogin()) {
            view.startLoggingIn();
        } else {
            view.startRegister();
        }
    }


    public interface ILoginView{
        void showLoginView();
        void showRegisterView();

        void hideKeyboard();

        void startLoggingIn();
        void startRegister();

        void showStandardLoginButton();
        void showStandardRegisterButton();

        void showToast(String text);

        void startMainActivity(User user);
    }
}
