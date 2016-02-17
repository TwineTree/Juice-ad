package com.twinetree.juice.api;

public class ApiResponse {

    private static String questionsResponse;

    public static String getQuestionsResponse() {
        return questionsResponse;
    }

    public static void setQuestionsResponse(String mQuestionsResponse) {
        questionsResponse = mQuestionsResponse;
    }
}
