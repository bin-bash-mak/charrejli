package com.mohammadalikassem.charrejli.endpoints;

import com.mohammadalikassem.charrejli.accounts.entities.Account;
import com.mohammadalikassem.charrejli.accounts.repositories.AccountsRepository;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
import com.vaadin.hilla.crud.CrudRepositoryService;

import java.util.UUID;

@BrowserCallable
@AnonymousAllowed
public class AccountsCrudService
        extends CrudRepositoryService<Account, Long, AccountsRepository> {
}