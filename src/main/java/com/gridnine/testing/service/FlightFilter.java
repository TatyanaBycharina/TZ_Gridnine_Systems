package com.gridnine.testing.service;

import java.util.List;

import com.gridnine.testing.model.Flight;

public interface FlightFilter {

    List<Flight> departureBeforeCurrentTimeFilter();

    List<Flight> arrivalBeforeDepartureFilter();

    List<Flight> commonTimeOnGroundMoreThanTwoHoursFilter();

}
