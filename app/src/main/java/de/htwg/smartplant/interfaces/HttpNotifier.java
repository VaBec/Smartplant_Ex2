package de.htwg.smartplant.interfaces;

public interface HttpNotifier {

    void showRetry();
    void showFailure();
    void showSuccess();
    void showStart();

}
