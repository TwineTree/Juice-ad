package com.twinetree.juice.api;

public class Url {

    public static String accessToken = "P0s3t7jTLKq7_zvyRVWPN0Ky94SG96trRMWh9snHqzGjIF9kWvx2YMTgy88IzzTMSLdpsLS3Wh2pe4qeC1w8_M2p0EBKyjSHPiZBnaBLguEka-pzOiW_TRmassbL9Cm_Z-eF4EDj9vwu9fBnKPAIleJhtP8Aow6AAK8-bHYL0zWnj0hM19FPrBrCDSF4c2WaAusM32v_ex7aUVdj8NHORbXl3kx-OggVhmDw9Pp9OO100OTgGMQl2zD0jFoe3IgnqX0XVnJ0kSxahyx_05gE2UXy4KJsuBda_4RMDypbp-1mIBdPjwhWMVXaowm0gIzk_KPjduYZ-qliHzmCk6VjKYYetFZCdA2INDOm1JobBsy3E7vSbMlhw1-wR5CU5Bjt3vMFjuVTVyIle7X0NJdxYe-vDPaBNNLEOMuXnzz_yWpkbql3VfzSh12jN-LFKmrf";

    public static String getQuestions(int pageIndex, int pageSize) {
        return "http://joos.azurewebsites.net/api/question/get?pageIndex=" + Integer.toString(pageIndex)
        + "&pageSize=" + Integer.toString(pageSize);
    }

    public static String addQuestion = "http://joos.azurewebsites.net/api/question/add";

    public static String sassUrl(String blobName) {
        String url = "http://joos.azurewebsites.net/api/azure/getsass?blobUri=https://twinetree.blob.core.windows.net/joos/"+
                blobName + "&containerName=joos&method=PUT";
        return url;
    }
}
