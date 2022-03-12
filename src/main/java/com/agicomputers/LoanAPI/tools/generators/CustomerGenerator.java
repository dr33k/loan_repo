package com.agicomputers.LoanAPI.tools.generators;

import com.agicomputers.LoanAPI.models.dto.CustomerDTO;
import com.agicomputers.LoanAPI.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;

public class CustomerGenerator {

    @Autowired
    static CustomerService customerService;

    //alpha and omicron represents the customer's first and last names respectively
    public static String generateCustomerId(String alpha, String omicron){

        String alphaSecondLetter =String.valueOf(Character.toUpperCase(alpha.charAt(1)));
        String omicronSecondLetter = String.valueOf(Character.toUpperCase(omicron.charAt(1)));

        String alphaSecondLetterPrime = String.valueOf(Character.toUpperCase(alpha.charAt(alpha.length()-2)));
        String omicronSecondLetterPrime =String.valueOf(Character.toUpperCase(omicron.charAt(omicron.length()-2)));

        String sqlRegex = omicronSecondLetter + alphaSecondLetterPrime +"7" + "%" + "7"+omicronSecondLetterPrime + alphaSecondLetter;
        HashSet<CustomerDTO> matchedUsers = customerService.getAllCustomers(sqlRegex);
        int duplicateUserFactor = (matchedUsers.isEmpty())? 0 : matchedUsers.size();

        String draft = omicronSecondLetter + alphaSecondLetterPrime + "7" + duplicateUserFactor +"7"+ omicronSecondLetterPrime +alphaSecondLetter;
        return draft;
    }

}
