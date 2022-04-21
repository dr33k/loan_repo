package com.agicomputers.LoanAPI.tools.validators.user_validators;

import com.agicomputers.LoanAPI.models.dto.user_dtos.UserDTO;

import java.util.LinkedHashMap;

public interface UserValidator {
  
      String NAME_LEN = "\nName should not be less than two characters";
      String NAME_SP = "\nSpecial characters not permitted in this field";
      String EMAIL_FMT = "\nIncorrect email format";
      String EMAIL_EX = "\nThis email already exists";
      String PHONE_FMT = "\nIncorrect phone number format";
       String ADDRESS = "\nPlease provide a valid input greater than 10 characters";
       String PPHRASE_LEN = "\nPassword too short";
       String PPHRASE_OTHERS = "\nPassword must contain an uppercase letter, a number and a special character";
      String NIN = "\nPlease enter a valid NIN";
       String NIN_EX = "\nThis NIN already exists";
      String PHOTO_SIZE = "\nPhoto too large";
      String PHOTO_FMT = "\nOnly .jpg .jpeg .png are supported.";
       String PHOTO_EX = "\nPhoto does not exist";

      void validateName(String propertyName, String propertyValue);
      void validateEmail(String email);
      void validatePhone(String propertyName, String propertyValue);
      void validateNIN(String nin);
      void validatePassphrase(String passphrase);
      void validateText(String propertyName, String propertyValue);
      void validatePhoto(String photo);
      LinkedHashMap<String, String> validate();
      UserDTO cleanObject();
}
