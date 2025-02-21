package com.mohammadalikassem.charrejli.accounts.jobs;

import com.mohammadalikassem.charrejli.accounts.entities.Account;
import com.mohammadalikassem.charrejli.accounts.repositories.AccountsRepository;
import com.mohammadalikassem.charrejli.modules.parsers.lb.touch.TouchParser;
import com.mohammadalikassem.charrejli.modules.parsers.lb.touch.models.TouchNumberCredentials;
import com.mohammadalikassem.charrejli.modules.parsers.lb.touch.models.TouchNumberDetails;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
@Transactional
@DisallowConcurrentExecution
public class UpdateAccountDetailsJob implements Job {
    final TouchParser parser;
    private static final Logger logger = LoggerFactory.getLogger(UpdateAccountDetailsJob.class);
    final
    AccountsRepository accountsRepository;

    public UpdateAccountDetailsJob(TouchParser parser, AccountsRepository accountsRepository) {
        this.parser = parser;
        this.accountsRepository = accountsRepository;
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        logger.debug("Started Job");
        Optional<Account> account = accountsRepository.getAccountToRefresh(Account.Operator.MTC, List.of(Account.Status.PROCESSING), LocalDateTime.now().minusHours(6) );
        account.ifPresent((a)->{
            logger.info("Processing {}'{}'", a.getId(), a.getNumber());

            a.setStatus(Account.Status.PROCESSING);
            accountsRepository.save(a);

            try {
                TouchNumberDetails details = this.parser.getNumberDetails(new TouchNumberCredentials(a.getUsername(), a.getPassword()));
                logger.info("Got account details to process {} '{}' , details:{}", a.getId(), a.getNumber(), details);

                a.setBalance(details.balance());
                a.setValidity(details.expiry());
                a.setStatus(Account.Status.REST);
                a.setLastUpdatedAt(LocalDateTime.now());
            } catch (Exception e) {
//                throw new RuntimeException(e);
                a.setStatus(Account.Status.FAILED);
                logger.info("Failed to process {} '{}' {}", a.getId(), a.getNumber(), e.getStackTrace());

            }
            accountsRepository.save(a);
        });
    }
}
