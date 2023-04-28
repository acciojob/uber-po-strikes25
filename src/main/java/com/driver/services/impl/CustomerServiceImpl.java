package com.driver.services.impl;

import com.driver.model.Customer;
import com.driver.model.Driver;
import com.driver.model.TripBooking;
import com.driver.model.TripStatus;
import com.driver.repository.CustomerRepository;
import com.driver.repository.DriverRepository;
import com.driver.repository.TripBookingRepository;
import com.driver.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    CustomerRepository customerRepository2;

    @Autowired
    DriverRepository driverRepository2;

    @Autowired
    TripBookingRepository tripBookingRepository2;

    @Override
    public void register(Customer customer) {
        //Save the customer in database
        customerRepository2.save(customer);
    }

    @Override
    public void deleteCustomer(Integer customerId) {
        // Delete customer without using deleteById function
        customerRepository2.deleteById(customerId);
    }

    @Override
    public TripBooking bookTrip(int customerId, String fromLocation, String toLocation, int distanceInKm) throws Exception{
        // Book the driver with lowest driverId who is free (cab available variable is Boolean.TRUE).
        // Avoid using SQL query

        // First fetch all the drivers for traversal :
        List<Driver> driverList = driverRepository2.findAll();
        for(Driver driver : driverList) {
            if(driver.getCab().isAvailable()) {
                driver.getCab().setAvailable(false);

                TripBooking newTrip = new TripBooking();
                newTrip.setDriver(driver);

                Customer customer = customerRepository2.findById(customerId).get();

                newTrip.setCustomer(customer);
                newTrip.setFromLocation(fromLocation);
                newTrip.setToLocation(toLocation);
                newTrip.setDistanceInKm(distanceInKm);

                // Calculating the fare by fetching variables from respective model classes ;
                int fare = distanceInKm * driver.getCab().getPerKmRate();
                newTrip.setBill(fare);

                newTrip.setTripStatus(TripStatus.CONFIRMED);

                driver.getTripBookingList().add(newTrip);
                customer.getTripBookingList().add(newTrip);

                driverRepository2.save(driver);
                customerRepository2.save(customer);

                return newTrip;
            }
        }

        // If no driver is available, throw "No cab available!" exception
        throw new RuntimeException("No cab available!");
    }

    @Override
    public void cancelTrip(Integer tripId){
        //Cancel the trip having given trip Id and update TripBooking attributes accordingly ;
        TripBooking canceledTrip = tripBookingRepository2.findById(tripId).get();
        Customer customer = canceledTrip.getCustomer();
        customer.getTripBookingList().remove(canceledTrip);

        Driver driver = canceledTrip.getDriver();
        driver.getCab().setAvailable(true);
        driver.getTripBookingList().remove(canceledTrip);

        customerRepository2.save(customer);
        driverRepository2.save(driver);

        tripBookingRepository2.deleteById(tripId);
    }

    @Override
    public void completeTrip(Integer tripId){
        //Complete the trip having given trip Id and update TripBooking attributes accordingly
        TripBooking completeTrip = tripBookingRepository2.findById(tripId).get();

        Driver driver = completeTrip.getDriver();
        driver.getCab().setAvailable(true);
        driverRepository2.save(driver);

        completeTrip.setTripStatus(TripStatus.COMPLETED);
        tripBookingRepository2.save(completeTrip);
    }
}
