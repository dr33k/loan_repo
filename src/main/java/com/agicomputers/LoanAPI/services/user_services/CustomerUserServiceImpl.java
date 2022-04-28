package com.agicomputers.LoanAPI.services.user_services;

import com.agicomputers.LoanAPI.models.dto.user_dtos.CustomerDTO;
import com.agicomputers.LoanAPI.models.entities.Customer;
import com.agicomputers.LoanAPI.repositories.user_repositories.AuthenticatedUserRepository;
import com.agicomputers.LoanAPI.repositories.user_repositories.CustomerRepository;
import com.agicomputers.LoanAPI.repositories.user_repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class CustomerUserServiceImpl implements UserService, UserDetailsService {

    UserRepository customerRepository;
    PasswordEncoder passwordEncoder;
    AuthenticatedUserRepository authenticatedUserRepository;

    @Autowired
    public CustomerUserServiceImpl(@Qualifier("customerRepository1") UserRepository customerRepository,
                                   PasswordEncoder passwordEncoder,
                                   AuthenticatedUserRepository authenticatedUserRepository) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticatedUserRepository=  authenticatedUserRepository;
    }

    @Override
    public HashSet<CustomerDTO> getAllUsers() {
        Set<CustomerDTO> customers = new HashSet<CustomerDTO>(0);
        Iterable<Customer> customersFromRepo = customerRepository.findAll();

        CustomerDTO cdto;
        Iterator<Customer> iterator = customersFromRepo.iterator();
        while (iterator.hasNext()) {
            cdto = new CustomerDTO();
            BeanUtils.copyProperties(iterator.next(), cdto);
            customers.add(cdto);
        }
        return (HashSet<CustomerDTO>) customers;
    }

    @Override
    public CustomerDTO getUser(String customerId) {
        //Extract Database Id for quicker indexed search
        try {
            Long id = getDbId(customerId);

        //Use the CustomerRepository to findBy Id
       Optional<Customer> optionalCustomer = customerRepository.findById(id);
      if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            CustomerDTO cdto = new CustomerDTO();
            BeanUtils.copyProperties(customer, cdto);
            return cdto;
        }

      }catch (NumberFormatException ex){
            return null;
        }
      return null;
    }

    @Override
    public CustomerDTO getUserWithEmail(String email) {
        Optional<Customer> customerWithEmail = customerRepository.findByEmail(email);
        if (customerWithEmail.isPresent()){
            Customer customer = customerWithEmail.get();
            CustomerDTO cdto = new CustomerDTO();
            BeanUtils.copyProperties(customer,cdto);
            return cdto;
        }
        else return null;
    }

    @Override
    public Boolean deleteUser(String customerId) {
        //Extract Database Id for quicker indexed search
        try {
            Long id = getDbId(customerId);

            if (customerRepository.existsById(id)) {
                customerRepository.deleteById(id);
                return true;
            }
        }catch (NumberFormatException ex){return false;}
        return false;
    }

    @Override
    @Transactional
    public CustomerDTO createUser(CustomerDTO cdto) {
        Customer customer = new Customer(); //Customer Entity
        BeanUtils.copyProperties(cdto, customer);

        //Set Customer's  encoded password
        customer.setCustomerPassphrase(passwordEncoder.encode(cdto.getUserPassphrase()));
        //Save record
        customerRepository.save(customer);

        //Get Customer's custom generated Id and database generated id
        String generatedCustomerId = generateCustomerId(cdto.getUserFname(), cdto.getUserLname());
        Long id = getDbId(generatedCustomerId);

        //Update saved record
        customerRepository.updateCustomerId(id,generatedCustomerId);

        //Set Customer's custom generated Id in the Customer object
        customer.setCustomerId(generatedCustomerId);

        //Copy properties into the DTO
        BeanUtils.copyProperties(customer, cdto);
        return cdto;
    }

    @Override
    @Transactional
    public CustomerDTO updateUser(CustomerDTO cdto) {
        Long id = getDbId(cdto.getUserId());
        Optional<Customer> customerOptional = customerRepository.findById(id);
        if(customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            if(cdto.getUserFname() != null)customer.setCustomerFname(cdto.getUserFname());
            if(cdto.getUserLname() != null)customer.setCustomerLname(cdto.getUserLname());
            if(cdto.getUserPhone1() != null)customer.setCustomerPhone1(cdto.getUserPhone1());
            if(cdto.getUserPhone2() != null)customer.setCustomerPhone2(cdto.getUserPhone2());
            if(cdto.getUserEmail() != null)customer.setCustomerEmail(cdto.getUserEmail());
            if(cdto.getUserPassphrase() != null)customer.setCustomerPassphrase(cdto.getUserPassphrase());
            if(cdto.getUserPassportPhoto() != null)customer.setCustomerPassportPhoto(cdto.getUserPassportPhoto());
            if(cdto.getUserNIN() != null)customer.setCustomerNIN(cdto.getUserNIN());
            if(cdto.getUserAddress() != null)customer.setCustomerAddress(cdto.getUserAddress());
            if(cdto.getUserOccupation() != null)customer.setCustomerOccupation(cdto.getUserOccupation());
            if(cdto.getUserOccupationLocation() != null)customer.setCustomerOccupationLocation(cdto.getUserOccupationLocation());

            customerRepository.save(customer);

            BeanUtils.copyProperties(customer,cdto);
            return cdto;
        }
        return null;
    }

    @Override
    public Boolean emailExists(String email) {
        Optional<Long> idWithEmail = customerRepository.existsByEmail(email);   //Id gotten with the email as a search key
        return idWithEmail.isPresent();
    }

    @Override
    public Boolean ninExists(String nin) {
        Optional<Long> idWithNin = customerRepository.existsByNin(nin);     //Id gotten with the NIN as a search key
        return idWithNin.isPresent();
    }

    //alpha and omega represents the customer's first and last names respectively
        private String generateCustomerId(String alpha, String omega) {
            //alpha's second letter
            String alphaSecondLetter = String.valueOf(Character.toUpperCase(alpha.charAt(1)));
            //omicron's second letter
            String omegaSecondLetter = String.valueOf(Character.toUpperCase(omega.charAt(1)));

            //alpha's second letter from the right
            String alphaSecondLetterPrime = String.valueOf(Character.toUpperCase(alpha.charAt(alpha.length() - 2)));
            //omicron's second letter from the right
            String omegaSecondLetterPrime = String.valueOf(Character.toUpperCase(omega.charAt(omega.length() - 2)));
            /*
            String sqlRegex = omegaSecondLetter + alphaSecondLetterPrime + "7" + "%" + "7" + omegaSecondLetterPrime + alphaSecondLetter;
            HashSet<CustomerDTO> matchedUsers = getAllCustomers(sqlRegex);
            int duplicateUserFactor = (matchedUsers.isEmpty()) ? 0 : matchedUsers.size();
            String draft = omegaSecondLetter + alphaSecondLetterPrime + "7" + duplicateUserFactor + "7" + omegaSecondLetterPrime + alphaSecondLetter;

             */
            //Random numbers from 0 to 9
            String numString1 = String.valueOf((int)(Math.random()*10));
            String numString2 = String.valueOf((int)(Math.random()*10));
            //Number of entries in the database + 1
            String count;
            try {
                count = String.valueOf(customerRepository.findLastId());//This returns the most recent (largest) id in the database
            }catch(NullPointerException ex){
                count = "1";
            }

            String newId = omegaSecondLetter
                    .concat(alphaSecondLetterPrime)
                    .concat(numString1)
                    .concat(count)
                    .concat(numString2)
                    .concat(omegaSecondLetterPrime)
                    .concat(alphaSecondLetter);

            return newId;
        }

        private Long getDbId(String customerId) throws NumberFormatException{
            String id = customerId.substring(3,customerId.length()-3);
            return Long.valueOf(id);
        }

    //This method returns th Customer (UserDetails) Object directly without the
    //use of a Data Transfer Object
    //It is used for authentication by the container's DaoAuthenticationProvider
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return authenticatedUserRepository.getAuthenticatedUser(username)
                .orElseThrow(()->new UsernameNotFoundException(String.format("Username %s not found",username)));

    }
}
