package com.gridnine.testing;

import com.gridnine.testing.model.Flight;
import com.gridnine.testing.model.Segment;
import com.gridnine.testing.service.FlightFilterImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FlightFilterTest {

    private List<Flight> testFlights;
    private List<Flight> departureBeforeNowFlights;
    private List<Flight> arrivalBeforeDepartureFlights;
    private List<Flight> timeOnGroundMoreThanTwoHoursFlights;

    public void initDepartureBeforeNow() {
        //сегмент с вылетом до текущего момента времени
        List<Segment> departureBeforeNow = new ArrayList<>();
        departureBeforeNow.add(new Segment(LocalDateTime.now().minusDays(3), LocalDateTime.now()));
        departureBeforeNowFlights = new ArrayList<>();
        departureBeforeNowFlights.add(new Flight(departureBeforeNow));
    }
    public void initArrivalBeforeDeparture() {
        List<Segment> arrivalBeforeDeparture = new ArrayList<>();
        arrivalBeforeDeparture.add(new Segment(LocalDateTime.now().plusDays(3), LocalDateTime.now().plusDays(3).minusHours(1)));
        arrivalBeforeDepartureFlights = new ArrayList<>();
        arrivalBeforeDepartureFlights.add(new Flight(arrivalBeforeDeparture));
    }
    public void initTimeOnGroundMoreThanTwoHours() {
        List<Segment> timeOnGroundMoreThanTwoHours = new ArrayList<>();
        timeOnGroundMoreThanTwoHours.add(new Segment(LocalDateTime.now().plusDays(3), LocalDateTime.now().plusDays(3).plusHours(1)));
        timeOnGroundMoreThanTwoHours.add(new Segment(LocalDateTime.now().plusDays(3).plusHours(4), LocalDateTime.now().plusDays(3).plusHours(5)));
        timeOnGroundMoreThanTwoHoursFlights = new ArrayList<>();
        timeOnGroundMoreThanTwoHoursFlights.add(new Flight(timeOnGroundMoreThanTwoHours));
    }
    @BeforeEach
    public void initFlights() {
        initDepartureBeforeNow();
        initArrivalBeforeDeparture();
        initTimeOnGroundMoreThanTwoHours();

        testFlights = new ArrayList<>();
        testFlights.addAll(departureBeforeNowFlights);
        testFlights.addAll(arrivalBeforeDepartureFlights);
        testFlights.addAll(timeOnGroundMoreThanTwoHoursFlights);
    }

    @Test
    public void departureBeforeCurrentTimeFilterTest() {
        List<Flight> expected = new ArrayList<>(testFlights);
        expected.removeAll(departureBeforeNowFlights);
        List<Flight> filtered = new FlightFilterImpl(testFlights).departureBeforeCurrentTimeFilter();
        assertEquals(expected, filtered);
    }
    @Test
    public void arrivalBeforeDepartureFilterTest() {
        List<Flight> expected = new ArrayList<>(testFlights);
        expected.removeAll(arrivalBeforeDepartureFlights);
        List<Flight> filtered = new FlightFilterImpl(testFlights).arrivalBeforeDepartureFilter();
        assertEquals(expected, filtered);
    }

    @Test
    public void commonTimeOnGroundMoreThanTwoHoursFilterTest() {
        List<Flight> expected = new ArrayList<>(testFlights);
        expected.removeAll(timeOnGroundMoreThanTwoHoursFlights);
        List<Flight> filtered = new FlightFilterImpl(testFlights).commonTimeOnGroundMoreThanTwoHoursFilter();
        assertEquals(expected, filtered);
    }
}
