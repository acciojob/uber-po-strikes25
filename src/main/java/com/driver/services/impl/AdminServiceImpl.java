package com.driver.services.impl;

import com.driver.model.Admin;
import com.driver.model.Customer;
import com.driver.model.Driver;
import com.driver.repository.AdminRepository;
import com.driver.repository.CustomerRepository;
import com.driver.repository.DriverRepository;
import com.driver.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminRepository adminRepository1;

    @Autowired
    DriverRepository driverRepository1;

    @Autowired
    CustomerRepository customerRepository1;

    @Override
    public void adminRegister(Admin admin) {
        //Save the admin in the database
        adminRepository1.save(admin);
    }

    @Override
    public Admin updatePassword(Integer adminId, String password) {
        //Update the password of admin with given id
        // Whenever Optional<> error is shown then .get() ;
        Admin udpatedAdmin = adminRepository1.findById(adminId).get();
        udpatedAdmin.setPassword(password);
        adminRepository1.save(udpatedAdmin);

        return udpatedAdmin;
    }

    @Override
    public void deleteAdmin(int adminId){
        // Delete admin without using deleteById function
        adminRepository1.deleteById(adminId);
    }

    @Override
    public List<Driver> getListOfDrivers() {
        //Find the list of all drivers
        List<Driver> driverList = driverRepository1.findAll();
        return driverList;
    }

    @Override
    public List<Customer> getListOfCustomers() {
        //Find the list of all customers
        List<Customer> customerList = customerRepository1.findAll();
        return customerList;

    }
}
