package com.driver.services.impl;

import com.driver.model.Cab;
import com.driver.model.Driver;
import com.driver.repository.CabRepository;
import com.driver.repository.DriverRepository;
import com.driver.services.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DriverServiceImpl implements DriverService {
    @Autowired
    DriverRepository driverRepository3;

    @Autowired
    CabRepository cabRepository3;

    @Override
    public void register(String mobile, String password){
        //Save a driver in the database having given details and a cab with ratePerKm as 10 and availability as True by default.
        Driver newDriver = new Driver();
        newDriver.setMobileNo(mobile);
        newDriver.setPassword(password);

        Cab newCab = new Cab();
        newCab.setPerKmRate(10);
        newCab.setAvailable(true);

        // Mapping by updating values of these parameters in respective model ;
        newDriver.setCab(newCab);
        newCab.setDriver(newDriver);

        // Here we do not have to save in the cabRepository3 as
        driverRepository3.save(newDriver);
    }

    @Override
    public void removeDriver(int driverId){
        // Delete driver without using deleteById function
        driverRepository3.deleteById(driverId);
    }

    @Override
    public void updateStatus(int driverId){
        //Set the status of respective car to unavailable
        Driver driver = driverRepository3.findById(driverId).get();
        driver.getCab().setAvailable(false);
    }
}
