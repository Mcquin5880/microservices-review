package com.mcq.accounts.mapper;

import com.mcq.accounts.dto.AccountDto;
import com.mcq.accounts.entity.Account;

public class AccountMapper {

    public static AccountDto mapToAccountDTO(Account account, AccountDto accountDTO) {
        accountDTO.setAccountNumber(account.getAccountNumber());
        accountDTO.setAccountType(account.getAccountType());
        accountDTO.setBranchAddress(account.getBranchAddress());
        return accountDTO;
    }

    public static Account mapToAccount(AccountDto accountDTO, Account account) {
        account.setAccountNumber(accountDTO.getAccountNumber());
        account.setAccountType(accountDTO.getAccountType());
        account.setBranchAddress(accountDTO.getBranchAddress());
        return account;
    }
}
