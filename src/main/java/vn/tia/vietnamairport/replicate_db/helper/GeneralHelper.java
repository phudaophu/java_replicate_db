package vn.tia.vietnamairport.replicate_db.helper;

import com.google.gson.Gson;
import org.springframework.stereotype.Component;
import vn.tia.vietnamairport.replicate_db.entity.primary.FlightEntity;


public class GeneralHelper {
    Gson gson = new Gson();
    public boolean flightEntityIsChanged(FlightEntity primaryFlight, vn.tia.vietnamairport.replicate_db.entity.secondary.FlightEntity secondaryFlight){
        String primaryFlightString = gson.toJson(primaryFlight);
        String secondaryFlightString = gson.toJson(secondaryFlight);
        return !primaryFlightString.equals(secondaryFlightString);
    }

}
