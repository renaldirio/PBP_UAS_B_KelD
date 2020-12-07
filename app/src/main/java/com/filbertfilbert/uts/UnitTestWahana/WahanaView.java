package com.filbertfilbert.uts.UnitTestWahana;

public interface WahanaView {


    String getNamaWahana();
    String getLokasi();
    String getRating();
    String getDeskripsi();
    void showNamaWahanaError(String message);
    void showLokasiError(String message);
    void showRatingError(String message);
    void showDeskripsiError(String message);

    void startMainActivity();
    void showLoginError(String message);
    void showErrorResponse(String message);


}
