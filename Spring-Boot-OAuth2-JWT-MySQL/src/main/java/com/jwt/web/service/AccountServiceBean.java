package com.jwt.web.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jwt.web.model.Account;
import com.jwt.web.model.Role;
import com.jwt.web.repository.AccountRepository;

import java.util.Collection;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class AccountServiceBean implements AccountService {

   
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private RoleService roleService;

  
    @Override
    public Collection<Account> findAll() {
        Collection<Account> accounts = accountRepository.findAll();
        return accounts;
    }
    @Override
    public Account findByUsername(String username) {
        Account account = accountRepository.findByUsername(username);
        return account;
    }

    /**
     * Create a new user as simple user. Find the simple user role from the database
     * add assign to the many to many collection
     * @param account - new Account of user
     * @return - the created account
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Account createNewAccount(Account account) {

        // Add the simple user role
       // Role role = roleService.findByCode("ROLE_USER");
       // Set<Role> roles = new HashSet<>();
        //roles.add(role);

        /*// Validate the password
        if (account.getPassword().length() < 8){
            throw new EntityExistsException("password should be greater than 8 characters");
        }*/

        // Encode the password
        account.setPassword(new BCryptPasswordEncoder().encode(account.getPassword()));

        // Create the role
       // account.setRoles(roles);
        return accountRepository.save(account);
    }
}
