package com.twinetree.juice.api;

public class ApiResponse {

    private static String questionsResponse;
    private static String myQuestionsResponse;

    //  GETTERS
    public static String getMyQuestionsResponse() {
        return myQuestionsResponse;
    }

    public static String getQuestionsResponse() {
        return questionsResponse;
    }

    //  SETTERS
    public static void setMyQuestionsResponse(String mMyQuestionsResponse) {
        myQuestionsResponse = mMyQuestionsResponse;
    }

    public static void setQuestionsResponse(String mQuestionsResponse) {
        questionsResponse = mQuestionsResponse;
    }
}
