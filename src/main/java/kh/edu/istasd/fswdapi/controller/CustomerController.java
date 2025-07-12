package kh.edu.istasd.fswdapi.controller;


import kh.edu.istasd.fswdapi.domain.Customer;
import kh.edu.istasd.fswdapi.dto.CreateCustomerRequest;
import kh.edu.istasd.fswdapi.dto.CustomerResponse;
import kh.edu.istasd.fswdapi.dto.UpdateCustomerRequest;
import kh.edu.istasd.fswdapi.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CustomerResponse createCustomer(@RequestBody CreateCustomerRequest createCustomerRequest) {
        return customerService.createCustomer(createCustomerRequest);
    }
    @GetMapping
    public List<CustomerResponse> getAllCustomers() {
        return customerService.getAllCustomers();
    }
    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/id")
    public CustomerResponse getCustomerById(@RequestParam Integer id) {
        return customerService.getCustomerById(id);
    }

    @DeleteMapping
    public void deleteCustomerById(@RequestParam Integer id) {
        customerService.deleteCustomerById(id);
    }
//    @PatchMapping("/{id}")
//    public CustomerResponse updateCustomerById( @PathVariable Integer id, @RequestBody CreateCustomerRequest createCustomerRequest) {
//        return customerService.updateCustomerById(id, createCustomerRequest);
//    }
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{phoneNumber}")
    public CustomerResponse updateCustomerByPhoneNumber(@PathVariable String phoneNumber, @RequestBody UpdateCustomerRequest updateCustomerRequest) {
        return customerService.updateCustomerByPhoneNumber(phoneNumber, updateCustomerRequest);
    }

    @GetMapping("/{phoneNumber}")
    public CustomerResponse getCustomerByPhoneNumber(@PathVariable String phoneNumber) {
        return customerService.getCustomerByPhoneNumber(phoneNumber);
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{phoneNumber}")
    public void deleteCustomerByPhoneNumber(@PathVariable String phoneNumber) {
        customerService.deleteCustomerByPhoneNumber(phoneNumber);

    }
}
