package com.steamybeans.beanbook;

import com.steamybeans.beanbook.Remote.IGoogleAPIService;
import com.steamybeans.beanbook.Remote.RetrofitClient;

public class Common {

    private static final String GOOGLE_API_URL = "https://maps.googleapis.com/";

    public static IGoogleAPIService getGoogleAPIService() {
        return RetrofitClient.getClient(GOOGLE_API_URL).create(IGoogleAPIService.class);
    }
}

