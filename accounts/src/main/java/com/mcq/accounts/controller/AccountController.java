package com.mcq.accounts.controller;

import com.mcq.accounts.dto.CustomerDto;
import com.mcq.accounts.dto.ResponseDto;
import com.mcq.accounts.service.AccountService;
import com.mcq.accounts.util.constants.AccountConstants;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDTO) {
        accountService.createAccount(customerDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountConstants.STATUS_201, AccountConstants.MESSAGE_201));
    }

    @GetMapping
    public ResponseEntity<CustomerDto> getAccountDetails(
            @RequestParam @Pattern(regexp = "($|[0-9]{10})", message = "Mobile number must be 10 digits") String mobileNumber) {

        CustomerDto customerDTO = accountService.fetchAccount(mobileNumber);
        return ResponseEntity.ok(customerDTO);
    }

    @PutMapping
    public ResponseEntity<ResponseDto> updateAccount(@Valid @RequestBody CustomerDto customerDTO) {
        boolean updated = accountService.updateAccount(customerDTO);
        if (updated) {
            return ResponseEntity.ok(new ResponseDto(AccountConstants.STATUS_200, AccountConstants.MESSAGE_200));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(AccountConstants.STATUS_500, AccountConstants.MESSAGE_500));
        }
    }

    @DeleteMapping
    public ResponseEntity<ResponseDto> deleteAccount(
            @RequestParam @Pattern(regexp = "($|[0-9]{10})", message = "Mobile number must be 10 digits") String mobileNumber) {

        boolean deleted = accountService.deleteAccount(mobileNumber);
        if (deleted) {
            return ResponseEntity.ok(new ResponseDto(AccountConstants.STATUS_200, AccountConstants.MESSAGE_200));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(AccountConstants.STATUS_500, AccountConstants.MESSAGE_500));
        }
    }
}

