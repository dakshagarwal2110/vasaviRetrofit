package com.daksh.vasavievents;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EventAdapter eventAdapter;
    private static final String BASE_URL =
            "https://app.ticketmaster.com/discovery/v2/";

    //rkQJzVGFiW0OdtVmbCkqbFfTKUoold27
    private static final String API_KEY = "rkQJzVGFiW0OdtVmbCkqbFfTKUoold27"; // Replace with your actual API key

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerViewEvents);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        eventAdapter = new EventAdapter();
        recyclerView.setAdapter(eventAdapter);

        // Create Retrofit instance
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Create API service
        TicketmasterAPI ticketmasterAPI = retrofit.create(TicketmasterAPI.class);

        // Make network call
        Call<EventsResponse> call = ticketmasterAPI.getEvents(API_KEY, "us");
        call.enqueue(new Callback<EventsResponse>() {
            @Override
            public void onResponse(Call<EventsResponse> call, Response<EventsResponse> response) {
                if (response.isSuccessful()) {
                    EventsResponse eventsResponse = response.body();
                    if (eventsResponse != null && eventsResponse.getEmbedded() != null) {
                        List<Event> events = eventsResponse.getEmbedded().getEvents();
                        eventAdapter.setEvents(events);

                        // Log the events
                        for (Event event : events) {
                            Log.d("EventName", event.getName());
                            // Log each image URL if needed
                            for (Image image : event.getImages()) {
                                Log.d("ImageUrl", image.toString());
                            }
                        }
                    }
                } else {
                    // Handle error
                    Log.e("APIError", "Failed to retrieve events: " + response.message());
                }
            }


            @Override
            public void onFailure(Call<EventsResponse> call, Throwable t) {
                // Handle failure
                Toast.makeText(MainActivity.this, "Bhai galat hai!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Adapter for RecyclerView
    private class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

        private List<Event> events;

        public void setEvents(List<Event> events) {
            this.events = events;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, parent, false);
            return new EventViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
            Event event = events.get(position);
            holder.textViewEventName.setText(event.getName());
            //Log.d("blah" , event.getImageUrl()+"daksh");
            Glide.with(MainActivity.this)
                    .load(event.getImages().get(0).getUrl())
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(holder.imageViewEvent);
            //Picasso.get().load(event.getImageUrl()).placeholder(R.drawable.ic_launcher_background).into(holder.imageViewEvent);
        }

        @Override
        public int getItemCount() {
            return events != null ? events.size() : 0;
        }

        // ViewHolder class
        class EventViewHolder extends RecyclerView.ViewHolder {
            ImageView imageViewEvent;
            TextView textViewEventName;

            public EventViewHolder(@NonNull View itemView) {
                super(itemView);
                imageViewEvent = itemView.findViewById(R.id.imageViewEvent);
                textViewEventName = itemView.findViewById(R.id.textViewEventName);
            }
        }
    }
}
