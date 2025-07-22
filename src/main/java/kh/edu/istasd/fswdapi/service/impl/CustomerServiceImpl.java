package kh.edu.istasd.fswdapi.service.impl;

import kh.edu.istasd.fswdapi.domain.Customer;
import kh.edu.istasd.fswdapi.domain.CustomerSegment;
import kh.edu.istasd.fswdapi.domain.KYC;
import kh.edu.istasd.fswdapi.dto.CreateCustomerRequest;
import kh.edu.istasd.fswdapi.dto.CustomerResponse;
import kh.edu.istasd.fswdapi.dto.UpdateCustomerRequest;
import kh.edu.istasd.fswdapi.mapper.CustomerMapper;
import kh.edu.istasd.fswdapi.mapper.CustomerMapperImpl;
import kh.edu.istasd.fswdapi.repository.CustomerRepository;
import kh.edu.istasd.fswdapi.repository.CustomerSegmentRepository;
import kh.edu.istasd.fswdapi.repository.KYCRepository;
import kh.edu.istasd.fswdapi.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.swing.text.Segment;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final CustomerSegmentRepository customerSegmentRepository;
    private final KYCRepository kycRepository;

    @Transactional
    @Override
    public void disableByPhoneNumber(String phoneNumber) {
        if (!customerRepository.existsByPhoneNumber(phoneNumber)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "customer phone number does not exist");
        }
        customerRepository.disableByPhoneNumber(phoneNumber);
    }

    @Override
    public CustomerResponse createCustomer(CreateCustomerRequest createCustomerRequest) {

        // Check uniqueness
        if (customerRepository.existsByEmail(createCustomerRequest.email())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        }

        if (customerRepository.existsByPhoneNumber(createCustomerRequest.phoneNumber())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Phone number already exists");
        }
        if (customerRepository.existsByNationalCardId(createCustomerRequest.nationalCardId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "National card id already exists");
        }

        log.info("Creating new customer with: {}", createCustomerRequest);

        // Load segment
        CustomerSegment customerSegment = customerSegmentRepository
                .findByCustomerSegmentId(createCustomerRequest.segmentId());

        // Construct Customer
        Customer customer = new Customer();
        customer.setFullName(createCustomerRequest.fullName());
        customer.setSegment(customerSegment);
        customer.setNationalCardId(createCustomerRequest.nationalCardId());
        customer.setGender(createCustomerRequest.gender());
        customer.setEmail(createCustomerRequest.email());
        customer.setPhoneNumber(createCustomerRequest.phoneNumber());
        customer.setRemark(createCustomerRequest.remark());
        customer.setIsDeleted(false);

        // Create KYC
        KYC kyc = new KYC();
        kyc.setIsVerified(false);
        kyc.setIsDeleted(false);
        kyc.setNationalCardId(createCustomerRequest.nationalCardId());
        // Set both sides of relation
        kyc.setCustomer(customer);
        customer.setKyc(kyc);

        // Save customer (cascade saves KYC)
        Customer savedCustomer = customerRepository.save(customer);

        log.info("Saved customer: {}", savedCustomer);

        return customerMapper.fromCustomer(savedCustomer);
    }


    @Override
    public List<CustomerResponse> getAllCustomers() {
        List<Customer> customers = customerRepository.findAllByIsDeletedFalse();
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
        return customerRepository.findByPhoneNumberAndIsDeletedFalse(phoneNumber)
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
                .findByPhoneNumberAndIsDeletedFalse(phoneNumber)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer with this numberPhone do not exist to update"));
        customerMapper.toCustomerPartially(updateCustomerRequest, customer);
        customer = customerRepository.save(customer);
        return customerMapper.fromCustomer(customer);
    }

    @Override
    public void deleteCustomerByPhoneNumber(String phoneNumber) {
        Customer customer = customerRepository
                .findByPhoneNumberAndIsDeletedFalse(phoneNumber)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer with this numberPhone do not exist to update"));
        customerRepository.delete(customer);
    }

    @Override
    public boolean exitsByNationalCardId(String nationalCardId) {
        if (!customerRepository.existsByNationalCardId(nationalCardId)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer with do not exist to update");
        }
        return true;
    }

    @Override
    public boolean exitsById(Integer id) {
        if (!customerRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer with do not exist to update");
        }
        return true;
    }

}
