package com.twinetree.juice.api;

public class Url {

    public static String accessToken = "SV6xS7NFsukjSxY2KntDCOsQBNwc3A6TzOMv1g-ZgNG3AoF-UC8k0t8nR1IF5IrzvsIvgiKCN6TAqmU4HXdADnQt-P1qS9L-np3yAAWC6IUq7-1Ke4PNYwAUopzYBwk_ki-ozBaBNYExUMa0I7xreNDfYiVkuw_55M_8jj6HwZlZxZM9i4LQsr-p4jzFX8FcAbhfVWNu6O380Gbp4z-L-6PFkM_a3GF__2LGB2qY2xsZDbyVUipaSiSx1mlcFSMxskrP8DBDZZYviLN41SaBKgXzrkXzDneG0mgmMf8LigG2gApTVlyiLHAkyU8a7N-5KsuRQQZsAW9kxVD6SSLTFnU6FAe0Jx8ETpz3pzSu0pppEO1kR-TM6HfdOwNBNta_IRLOZn2KdwmhQkBiuojZKFTZABlkMWGKDpWAlGUSo86FI_1JdsavZqc6Usdv-qws";
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
