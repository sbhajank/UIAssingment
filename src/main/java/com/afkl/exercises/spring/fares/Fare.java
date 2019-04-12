package com.afkl.exercises.spring.fares;

import com.afkl.exercises.spring.locations.Location;
import lombok.Value;

@Value
public class Fare {

    double amount;
    Currency currency;
    String origin, destination;

    private Location originLocation;
    private Location destinationLocation;

}
