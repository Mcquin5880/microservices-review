package com.mcq.accounts.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerDto {

    @NotEmpty(message = "Name can not be null or empty")
    @Size(max = 30, message = "Length of customer name should not exceed 30 characters")
    private String name;

    @NotEmpty(message = "Email address can not be null or empty")
    @Email(message = "Invalid email format")
    private String email;

    @Pattern(regexp = "($|[0-9]{10})", message = "Mobile number must be 10 digits")
    private String mobileNumber;

    private AccountDto accountDTO;
}
