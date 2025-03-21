package com.mcq.accounts.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class AccountDto {

    @NotEmpty(message = "Account number can not be null / empty")
    private String accountNumber;

    @NotEmpty(message = "Account type can not be null / empty")
    private String accountType;

    @NotEmpty(message = "Branch address can not be null / empty")
    private String branchAddress;
}
