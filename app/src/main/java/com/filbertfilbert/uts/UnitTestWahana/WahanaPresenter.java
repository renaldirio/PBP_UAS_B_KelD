package com.filbertfilbert.uts.UnitTestWahana;

public class WahanaPresenter {

    private WahanaView view;
    private WahanaService service;
    private WahanaCallback callback;
    public WahanaPresenter(WahanaView view, WahanaService service) {
        this.view = view;
        this.service = service;
    }
    public void onLoginClicked() {
        if (view.getNamaWahana().isEmpty()) {
            view.showNamaWahanaError("Nama Wahana Tidak Boleh Kosong");
            return;
        }else if (view.getLokasi().isEmpty()) {
            view.showLokasiError("Lokasi Tidak Boleh Kosong");
            return;
        }else if (view.getRating().isEmpty()) {
            view.showRatingError("Rating Tidak Boleh Kosong");
            return;
        }else if (view.getDeskripsi().isEmpty()) {
            view.showDeskripsiError("Deskripsi Tidak Boleh Kosong");
            return;
        }
        else{
            service.login(view, view.getNamaWahana(), view.getLokasi(),view.getRating(),view.getDeskripsi(),new WahanaCallback() {
                @Override
                public void onSuccess(boolean value) {
                    view.startMainActivity();
                }

                @Override
                public void onError() {

                }
            });
            return;
        }
    }

}
