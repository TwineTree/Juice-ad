package com.twinetree.juice.api;

public class Url {

    public static String accessToken = "QwDqaFKVLKMlxbWnUg6xMmWQYQT_KhFm9lsBM6aRfDqawtNZXtPm7pyQ5S0Z-4dDDc3O3uZjtnn2qu_9jQ077GHg--YWZYytPO8n1gOGmbXicB_5L04xaxBXqTE6HcdKfIviXYHCDya4W2FFOzUkG5i14oIYcdAbYNEjzNMfXsoiLGrpNe1Fw81Yy7ZWo4ZzPben2dLaETJmPXrmTiwBxYXTqycVTYBygIQvJnirOWj-1LKhyBxw7ey2p6R12za5v8Q83Q00QNH9IV1qpmQT1-83a0QPUnHr5SDDa8wmg2gQov1tII5tyWjl2i7eF1PngDbUm1I4g-itsF1Y69JObbRvJf2O6n5X2naSUBsPmLNFqon4t0ze5KQudp808MKT6wXfOdsrlLVgMZzEjAZUVcdjT2804zIcxTC24JNFC8JF4XZilzJdeX5OY1hci2XY";

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
