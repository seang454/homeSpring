package kh.edu.istasd.fswdapi.service.impl;

import kh.edu.istasd.fswdapi.domain.Customer;
import kh.edu.istasd.fswdapi.domain.KYC;
import kh.edu.istasd.fswdapi.repository.CustomerRepository;
import kh.edu.istasd.fswdapi.repository.KYCRepository;
import kh.edu.istasd.fswdapi.service.CustomerService;
import kh.edu.istasd.fswdapi.service.KYCService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@RequiredArgsConstructor
public class KYCServiceImpl implements KYCService {
    private final CustomerService customerService;
    private final CustomerRepository customerRepository;
    private final KYCRepository kycRepository;
    @Override
    public void verityKYC(Integer customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!customerService.exitsByNationalCardId(customer.getNationalCardId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "National Card Id is not valid");
        }
        KYC kyc = kycRepository.findById(customer.getId()).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!customer.getNationalCardId().equals(kyc.getNationalCardId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "customer national card id does not match");
        }

        kyc.setIsVerified(true);
        kycRepository.save(kyc);
    }
}
