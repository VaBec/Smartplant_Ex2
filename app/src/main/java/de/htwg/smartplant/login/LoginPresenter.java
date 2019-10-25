package de.htwg.smartplant.login;

import android.widget.Toast;

import de.htwg.smartplant.rest.HttpNotifier;

public class LoginPresenter implements HttpNotifier {

    private final ILoginView view;
    private UserModel userModel;

    public LoginPresenter(ILoginView view) {
        this.view = view;
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
            userModel = new UserModel(name, password1, this);

            userModel.sendRegisterRequest();
            view.hideKeyboard();
        }
    }

    public void loginUser(String name, String password) {
        if(name.equals("") || password.equals("")) {
            view.showToast("Enter username and password.", Toast.LENGTH_LONG);
        } else {
            userModel = new UserModel(name, password, this);

            userModel.sendLoginRequest();
            view.hideKeyboard();
        }
    }

    @Override
    public void showRetry() {
        String retryMessage = userModel.getRequestType().getRetryMessage();

        view.showToast(retryMessage, Toast.LENGTH_LONG);
    }

    @Override
    public void showFailure() {
        String errorMessage = userModel.getRequestType().getErrorMessage();

        view.showToast(errorMessage, Toast.LENGTH_LONG);

        if(userModel.getRequestType() == UserModel.RequestType.LOGIN) {
            view.showStandardLoginButton();
            view.showLoginView();
        } else if(userModel.getRequestType() == UserModel.RequestType.REGISTER) {
            view.showStandardRegisterButton();
            view.showRegisterView();
        }
    }

    @Override
    public void showSuccess() {

    }

    @Override
    public void showStart() {
        UserModel.RequestType requestType = userModel.getRequestType();

        if(requestType == UserModel.RequestType.LOGIN) {
            view.startLoggingIn();
        } else if(requestType == UserModel.RequestType.REGISTER) {
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
    }
}
