package com.filbertfilbert.uts.API;


import com.filbertfilbert.uts.Service;

public class UtilApi {

    public static final String BASE_URL_API ="https://uaspbp.000webhostapp.com";

    //deklarasi apiinterface
    public static Service getApiSerivce() {
        return APIClient.getClient(BASE_URL_API).create(Service.class);

    }

}
