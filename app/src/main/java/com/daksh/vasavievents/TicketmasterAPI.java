package com.daksh.vasavievents;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TicketmasterAPI {
    @GET("events.json")
    Call<EventsResponse> getEvents(
            @Query("apikey") String apiKey,
            @Query("countryCode") String countryCode
    );
}
