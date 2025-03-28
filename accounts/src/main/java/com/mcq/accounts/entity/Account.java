package com.mcq.accounts.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account extends BaseEntity {

    @Id
    private String accountNumber;

    private Long customerId;
    private String accountType;
    private String branchAddress;
}
