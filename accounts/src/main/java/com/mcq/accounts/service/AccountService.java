package com.mcq.accounts.service;

import com.mcq.accounts.dto.AccountDto;
import com.mcq.accounts.dto.CustomerDto;
import com.mcq.accounts.entity.Account;
import com.mcq.accounts.entity.Customer;
import com.mcq.accounts.exception.CustomerAlreadyExistsException;
import com.mcq.accounts.exception.ResourceNotFoundException;
import com.mcq.accounts.mapper.AccountMapper;
import com.mcq.accounts.mapper.CustomerMapper;
import com.mcq.accounts.repository.AccountRepository;
import com.mcq.accounts.repository.CustomerRepository;
import com.mcq.accounts.util.constants.AccountConstants;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
@AllArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;

    public void createAccount(CustomerDto customerDTO) {
        Optional<Customer> existingCustomer = customerRepository.findByMobileNumber(customerDTO.getMobileNumber());
        if (existingCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer already registered with mobile number: " + customerDTO.getMobileNumber());
        }

        Customer customer = CustomerMapper.mapToCustomer(customerDTO, new Customer());
        Customer savedCustomer = customerRepository.save(customer);

        Account newAccount = buildNewAccount(savedCustomer);
        accountRepository.save(newAccount);
    }

    public CustomerDto getAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));

        Account account = accountRepository.findByCustomerId(customer.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString()));

        CustomerDto customerDTO = CustomerMapper.mapToCustomerDTO(customer, new CustomerDto());
        customerDTO.setAccountDTO(AccountMapper.mapToAccountDto(account, new AccountDto()));
        return customerDTO;
    }

    public boolean updateAccount(CustomerDto customerDTO) {
        AccountDto accountDTO = customerDTO.getAccountDTO();
        if (accountDTO == null) {
            return false;
        }

        Account account = accountRepository.findById(accountDTO.getAccountNumber())
                .orElseThrow(() -> new ResourceNotFoundException("Account", "accountNumber", accountDTO.getAccountNumber()));
        AccountMapper.mapToAccount(accountDTO, account);
        accountRepository.save(account);

        Customer customer = customerRepository.findById(account.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "customerId", String.valueOf(account.getCustomerId())));
        CustomerMapper.mapToCustomer(customerDTO, customer);
        customerRepository.save(customer);

        return true;
    }

    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "customerId", mobileNumber));
        accountRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return true;
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
