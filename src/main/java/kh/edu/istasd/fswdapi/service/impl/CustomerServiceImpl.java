package kh.edu.istasd.fswdapi.service.impl;

import kh.edu.istasd.fswdapi.domain.Customer;
import kh.edu.istasd.fswdapi.dto.CreateCustomerRequest;
import kh.edu.istasd.fswdapi.dto.CustomerResponse;
import kh.edu.istasd.fswdapi.dto.UpdateCustomerRequest;
import kh.edu.istasd.fswdapi.mapper.CustomerMapper;
import kh.edu.istasd.fswdapi.mapper.CustomerMapperImpl;
import kh.edu.istasd.fswdapi.repository.CustomerRepository;
import kh.edu.istasd.fswdapi.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public CustomerResponse createCustomer(CreateCustomerRequest createCustomerRequest) {


        //validate email
        if (customerRepository.existsByEmail(createCustomerRequest.email())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        }
        if (customerRepository.existsByPhoneNumber(createCustomerRequest.phoneNumber())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Phone number already exists");
        }

        Customer customer = new Customer();
        customer.setFullName(createCustomerRequest.fullName());
        customer.setGender(createCustomerRequest.gender());
        customer.setEmail(createCustomerRequest.email());
        customer.setPhoneNumber(createCustomerRequest.phoneNumber());
        customer.setRemark(createCustomerRequest.remark());
        customer.setIsDeleted(false);

        log.info("Creating Customer : {}", customer);
        Customer customer1 = customerRepository.save(customer);
        log.info("Customer : {}", customer1);

        return CustomerResponse.builder()
                .fullName(customer.getFullName())
                .email(customer.getEmail())
                .phoneNumber(String.valueOf(customer.getPhoneNumber()))
                .build();
    }

    @Override
    public List<CustomerResponse> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        if (customers.isEmpty()){
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND, "No customers found");
        }

        return customers.stream().map(customer -> CustomerResponse.builder()
                .fullName(customer.getFullName())
                .email(customer.getEmail())
                .phoneNumber(String.valueOf(customer.getPhoneNumber()))
                .build()).toList();
    }

    @Override
    public CustomerResponse getCustomerById(int id) {
        if(!customerRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found");
        }
        Customer customer = customerRepository.findById(id).orElse(null);
        assert customer != null;
        return CustomerResponse.builder()
                .fullName(customer.getFullName())
                .email(customer.getEmail())
                .phoneNumber(String.valueOf(customer.getPhoneNumber()))
                .build();
    }

    @Override
    public void deleteCustomerById(int id) {
        if(!customerRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer with do not exist");
        }else {
            customerRepository.deleteById(id);
            throw new ResponseStatusException(HttpStatus.OK, "Customer deleted successfully");
        }
    }

    @Override
    public CustomerResponse updateCustomerById(Integer id, CreateCustomerRequest createCustomerRequest) {
        if(!customerRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer with do not exist to update");
        }else {
            Customer customer = customerRepository.findById(id).orElse(null);
            assert customer != null;
            customer.setFullName(createCustomerRequest.fullName());
            customer.setGender(createCustomerRequest.gender());
            customer.setPhoneNumber(createCustomerRequest.phoneNumber());
            customer.setRemark(createCustomerRequest.remark());
            customer.setIsDeleted(false);
            Customer customer1 = customerRepository.save(customer);
            return CustomerResponse.builder()
                    .fullName(customer1.getFullName())
                    .email(customer1.getEmail())
                    .phoneNumber(String.valueOf(customer1.getPhoneNumber()))
                    .build();

        }

    }

    @Override
    public CustomerResponse getCustomerByPhoneNumber(String phoneNumber) {
        return customerRepository.findByPhoneNumber(phoneNumber)
                .map(customer ->
                        CustomerResponse.builder()
                                .fullName(customer.getFullName())
                                .email(customer.getEmail())
                                .phoneNumber(String.valueOf(customer.getPhoneNumber()))
                                .build())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer with do not exist to update"));
    }

    @Override
    public CustomerResponse updateCustomerByPhoneNumber(String phoneNumber, UpdateCustomerRequest updateCustomerRequest) {
        Customer customer = customerRepository
                .findByPhoneNumber(phoneNumber)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer with this numberPhone do not exist to update"));
        customerMapper.toCustomerPartially(updateCustomerRequest, customer);
        customer = customerRepository.save(customer);
        return customerMapper.fromCustomer(customer);
    }

    @Override
    public void deleteCustomerByPhoneNumber(String phoneNumber) {
        Customer customer = customerRepository
                .findByPhoneNumber(phoneNumber)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer with this numberPhone do not exist to update"));
        customerRepository.delete(customer);
    }

}
