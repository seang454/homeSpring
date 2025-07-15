package kh.edu.istasd.fswdapi.service;

import kh.edu.istasd.fswdapi.domain.Customer;
import kh.edu.istasd.fswdapi.dto.CreateCustomerRequest;
import kh.edu.istasd.fswdapi.dto.CustomerResponse;
import kh.edu.istasd.fswdapi.dto.UpdateCustomerRequest;

import java.util.List;

public interface CustomerService {
    void disableByPhoneNumber(String phoneNumber);
    public CustomerResponse createCustomer(CreateCustomerRequest createCustomerRequest);
    public List<CustomerResponse> getAllCustomers();
    public CustomerResponse getCustomerById(int id);
    public void deleteCustomerById(int id);
    public CustomerResponse updateCustomerById(Integer id,CreateCustomerRequest createCustomerRequest);
    public CustomerResponse getCustomerByPhoneNumber(String phoneNumber);
    public CustomerResponse updateCustomerByPhoneNumber(String phoneNumber, UpdateCustomerRequest updateCustomerRequest);
    void deleteCustomerByPhoneNumber(String phoneNumber);
    boolean exitsByNationalCardId(String nationalCardId);
    boolean exitsById(Integer id);
}
