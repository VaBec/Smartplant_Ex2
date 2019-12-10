package de.htwg.smartplant.login;

import android.content.Context;
import android.widget.Toast;

import org.json.JSONObject;

import de.htwg.smartplant.rest.HttpNotifier;

public class LoginPresenter implements HttpNotifier {

    private final ILoginView view;
    private final Context context;
    private LoginModel userModel;

    public LoginPresenter(ILoginView view, Context context) {
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
            view.showToast("Passwords do not match.", Toast.LENGTH_LONG);
        } else {
            userModel = new LoginModel(name, password1, this, context);

            userModel.sendRegisterRequest();
            view.hideKeyboard();
        }
    }

    public void loginUser(String name, String password) {
        if(name.equals("") || password.equals("")) {
            view.showToast("Enter username and password.", Toast.LENGTH_LONG);
        } else {
            userModel = new LoginModel(name, password, this, context);

            userModel.sendLoginRequest();
            view.hideKeyboard();
        }
    }

    public void showException(Exception e) {
        view.showToast(e.getMessage(), Toast.LENGTH_LONG);
    }

    @Override
    public void showRetry() {
        String retryMessage = userModel.getRequestType().getRetryMessage();

        view.showToast(retryMessage, Toast.LENGTH_LONG);
    }

    @Override
    public void showFailure(JSONObject response) {
        String errorMessage = "Server unavailable.";

        if(response != null) {
            try {
                errorMessage = (String) response.get("payload");
            } catch (Exception e) {
                showException(e);
            }
        }

        view.showToast(errorMessage, Toast.LENGTH_LONG);

        if (userModel.getRequestType() == LoginModel.RequestType.LOGIN) {
            view.showLoginView();
            view.showStandardLoginButton();
        } else if (userModel.getRequestType() == LoginModel.RequestType.REGISTER) {
            view.showRegisterView();
            view.showStandardRegisterButton();
        }

    }

    @Override
    public void showSuccess(JSONObject response) {
        try {
            String message = (String) response.get("payload");
            view.showToast(message, Toast.LENGTH_LONG);

            if(userModel.getRequestType() == LoginModel.RequestType.REGISTER) {
                view.showStandardRegisterButton();
                view.showLoginView();
                view.showStandardLoginButton();
            }
            else
            {
                view.startMainActivity(userModel.getName(), userModel.getPassword());
            }

        } catch(Exception e){
            showException(e);
        }
    }

    @Override
    public void showStart() {
        LoginModel.RequestType requestType = userModel.getRequestType();

        if(requestType == LoginModel.RequestType.LOGIN) {
            view.startLoggingIn();
        } else if(requestType == LoginModel.RequestType.REGISTER) {
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

        void showToast(String text, int length);

        void startMainActivity(String userName, String password);
    }
}
