package com.driver.services;

import org.springframework.stereotype.Service;

public interface DriverService {

    public void register(String mobile, String password);
    public void removeDriver(int driverId);
    public void updateStatus(int driverId);
}
