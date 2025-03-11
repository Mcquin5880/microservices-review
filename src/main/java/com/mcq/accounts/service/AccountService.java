package com.mcq.accounts.service;

import com.mcq.accounts.constants.AccountConstants;
import com.mcq.accounts.dto.CustomerDTO;
import com.mcq.accounts.entity.Account;
import com.mcq.accounts.entity.Customer;
import com.mcq.accounts.exception.CustomerAlreadyExistsException;
import com.mcq.accounts.mapper.CustomerMapper;
import com.mcq.accounts.repository.AccountRepository;
import com.mcq.accounts.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
@AllArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;

    @Transactional
    public void createAccount(CustomerDTO customerDTO) {
        Optional<Customer> existingCustomer = customerRepository.findByMobileNumber(customerDTO.getMobileNumber());
        if (existingCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer already registered with mobile number: " + customerDTO.getMobileNumber());
        }

        Customer customer = CustomerMapper.mapToCustomer(customerDTO, new Customer());
        Customer savedCustomer = customerRepository.save(customer);

        Account newAccount = buildNewAccount(savedCustomer);
        accountRepository.save(newAccount);
    }

    private Account buildNewAccount(Customer customer) {
        String accountNumber = generateCustomAccountNumber();
        return Account.builder()
                .accountNumber(accountNumber)
                .customerId(customer.getCustomerId())
                .accountType(AccountConstants.SAVINGS)
                .branchAddress(AccountConstants.ADDRESS)
                .build();
    }

    private String generateCustomAccountNumber() {
        long number = ThreadLocalRandom.current().nextLong(1000000000L, 10000000000L);
        return "ACCT" + number;
    }

}
