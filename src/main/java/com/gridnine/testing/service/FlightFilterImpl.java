package com.gridnine.testing.service;

import com.gridnine.testing.model.Flight;
import com.gridnine.testing.model.Segment;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class FlightFilterImpl implements FlightFilter {

    private List<Flight> flights;

    public FlightFilterImpl(List<Flight> flights) {
        this.flights = flights;
    }
    @Override
    public List<Flight> departureBeforeCurrentTimeFilter() {
        List<Flight> departureBeforeCurrentTime = flights.stream().filter(flight ->
                flight.getSegments()
                        .stream()
                        .allMatch(segment -> segment.getDepartureDate().isAfter(LocalDateTime.now())))
                .collect(Collectors.toList());
        return departureBeforeCurrentTime;
    }

    @Override
    public List<Flight> arrivalBeforeDepartureFilter() {
        List<Flight> arrivalBeforeDeparture = flights.stream().filter(flight ->
                flight.getSegments()
                        .stream()
                        .allMatch(segment -> segment.getArrivalDate().isAfter(segment.getDepartureDate())))
                        .collect(Collectors.toList());
        return arrivalBeforeDeparture;
    }

    @Override
    public List<Flight> commonTimeOnGroundMoreThanTwoHoursFilter() {
        List<Flight> commonTimeOnGroundMoreThanTwoHours = flights.stream().filter(flight -> {
            List<Segment> segments = flight.getSegments();
            LocalDateTime departure;
            LocalDateTime lastArrival;
            Duration duration = Duration.ZERO;
            if (segments.size() == 1) {
                return true;
            } else {
                for (int i = 1; i < segments.size(); i++) {
                    departure = segments.get(i).getDepartureDate();
                    lastArrival = segments.get(i - 1).getArrivalDate();
                    duration = duration.plus(Duration.between(departure, lastArrival).abs());
                }
                return duration.toHours() <= 2;
            }
        })
                .collect(Collectors.toList());
        return commonTimeOnGroundMoreThanTwoHours;
    }
}
