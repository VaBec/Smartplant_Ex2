package de.htwg.smartplant.presenter;

import android.util.Log;
import android.widget.Toast;

import de.htwg.smartplant.data.UserModel;
import de.htwg.smartplant.interfaces.HttpNotifier;
import de.htwg.smartplant.rest.RequestHandler;

public class LoginPresenter implements HttpNotifier {

    private final ILoginView view;
    private UserModel userModel;

    private int retryCounter = 0;

    public LoginPresenter(ILoginView view) {
        this.view = view;
    }

    public void updateUser(String name, String password) {
        userModel = new UserModel(name, password, this);

        userModel.login();
        view.updateToLoggedIn();
    }

    @Override
    public void showRetry() {
        view.showToast("Can't login - trying again.", Toast.LENGTH_LONG);
        retryCounter++;
    }

    @Override
    public void showFailure() {
        String errorMessage = "HTTP Error.";

        if(retryCounter == 5) {
            errorMessage = "Cant connect to '" + RequestHandler.BASE_URL + UserModel.LOGIN_ENDPOINT + "'.";
            retryCounter = 0;
        }

        view.showToast(errorMessage, Toast.LENGTH_LONG);
        view.showStandardButton();
    }

    @Override
    public void showSuccess() {

    }

    @Override
    public void showStart() {
        view.startButtonAnimation();
    }


    public interface ILoginView{

        void updateToLoggedIn();
        void startButtonAnimation();
        void showStandardButton();
        void showToast(String text, int length);
    }
}
