package com.twinetree.juice.api;

public class Url {

    public static String accessToken = "r_Md2G2JE7OptO0DRokaxVlanzV6arLPUmfZwdFC-dUYq4ngaUK6A5re9uXmSFtj9HQ44TlM5cI1M3GFz3poC6ibxl6ksgwBX9WzO2Y7izcRBNaKa55ucF0Ofp3PAiIoZt5bqtrvqp3EjnQI6ZPSTDQiIWsPIWiJwQNxvRJXf5l8CIa5jURpdcmpWhhte3eY40I8VtASoP2UH-QDgPD7uqZMvUkthh86cMg94YliulWdhK9sGTY-QYAxuoZZ9cUNjFOdPpVvLKcPhAgIb3LypJ6dV28o97806EJ_OqZbjjL4R-Te5U-d7TolXbwKNuH1RbAhNvoEBjsyTd0jZB6f1He_VZzMQ1v0udlRUbQ16AyeUcfOWGq725ExumhlhInh8bgPjxyfsWu3m0evPshKjCei9I7U-Aiq5u6PteTPd-kam5hurT7a-8V5_sApUmzS";

    public static String getQuestions(int pageIndex, int pageSize) {
        return "http://joos.azurewebsites.net/api/question/get?pageIndex=" + Integer.toString(pageIndex)
        + "&pageSize=" + Integer.toString(pageSize);
    }
}
