package de.htwg.smartplant.rest;

public interface HttpNotifier {

    void showRetry();
    void showFailure();
    void showSuccess();
    void showStart();

}
