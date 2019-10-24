package de.htwg.smartplant.login;

public class LoginPresenter {

    private final ILoginView view;
    private final UserModel user;

    public LoginPresenter(ILoginView view) {
        this.view = view;
        this.user = new UserModel();
    }

    public void updateUser(String name, String password) {
        user.setName(name);
        user.setPassword(password);

        new RESTModel().login(name, password);

        view.login();
    }


    public interface ILoginView{

        void login();

    }
}
