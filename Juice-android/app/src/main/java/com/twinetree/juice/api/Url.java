package com.twinetree.juice.api;

public class Url {

    public static String accessToken = "Sc7r2qS-On4K-vP1D4ZatGj8yNHDj7S33E33yDS51eW9osyl7ztxU9KM_p7meZE4mSQFjdmbPZLaumH-WhcOoBxcV7mfXZ8dMeXU41gL1dyJeP67y10z4LEwhx8W2EtL_pSJulmCfbKwRrstd9hkHRbQXA_xuHiA7feV5mQqN5vKBcSmIWv7cSFUxoZA8_gBS-5mNfbLSUdr1TaVs6EhkrTqBpSSOsagYDAtxGXit9f2fWt3Bg9-8FxniKRzCShrIwon2yCF2L8sqzhpNgTjozNkPL-uOE0WW1yfGkBKGJnP5SrtLfN-X7ZQ3_8FWXaY97B5WmWzVK6Z_qvTZzpY31nK809_fLLVWlVSl_CMjpgC2lY9OuLHOYvEApvF3kjV6o-nwIx-z9ktaoKSn_DsvgikXjik89sO26u05tC5U-7DaRE4dCSxlboPPJ7CALMm";
    public static String getQuestions(int pageIndex, int pageSize) {
        return "http://joos.azurewebsites.net/api/question/get?pageIndex=" + Integer.toString(pageIndex)
        + "&pageSize=" + Integer.toString(pageSize);
    }
}
