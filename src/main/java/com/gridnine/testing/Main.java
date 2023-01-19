package com.gridnine.testing;

import com.gridnine.testing.model.Flight;
import com.gridnine.testing.service.FlightFilterImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Flight> flights = FlightBuilder.createFlights();

        List<Flight> departureBeforeCurrentTimeFlights = new FlightFilterImpl(flights)
                .departureBeforeCurrentTimeFilter();
        List<Flight> arrivalBeforeDepartureFlights = new FlightFilterImpl(flights)
                .arrivalBeforeDepartureFilter();
        List<Flight> commonTimeOnGroundMoreThanTwoHoursFlights = new FlightFilterImpl(flights).commonTimeOnGroundMoreThanTwoHoursFilter();
        System.out.println(departureBeforeCurrentTimeFlights);
        System.out.println(arrivalBeforeDepartureFlights);
        System.out.println(commonTimeOnGroundMoreThanTwoHoursFlights);

    }
}