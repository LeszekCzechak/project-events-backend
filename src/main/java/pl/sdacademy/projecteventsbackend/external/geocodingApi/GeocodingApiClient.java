package pl.sdacademy.projecteventsbackend.external.geocodingApi;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.sdacademy.projecteventsbackend.external.geocodingApi.model.LocationCoordinates;

import java.io.IOException;

@Component
public class GeocodingApiClient {

    @Value("${geocodingApiKey}")
    private String key;

    public LocationCoordinates getLocationCoordinates(String location) {

        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(key)
                .build();
        GeocodingResult[] results = new GeocodingResult[0];
        try {
            results = GeocodingApi.geocode(context,
                    location)
                    .await();

            LocationCoordinates locationCoordinates = new LocationCoordinates();
            locationCoordinates.setLat(results[0].geometry.location.lat);
            locationCoordinates.setLng(results[0].geometry.location.lng);
            locationCoordinates.setFormattedAddress(results[0].formattedAddress);

            return locationCoordinates;

        } catch (ApiException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Connection to Api fail");
    }
}
