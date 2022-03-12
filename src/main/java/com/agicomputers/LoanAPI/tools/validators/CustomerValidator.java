package com.agicomputers.LoanAPI.tools.validators;

import com.agicomputers.LoanAPI.models.dto.CustomerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.io.File;

public class CustomerValidator {
    private final String NAME_LEN = "\nName should not be less than two characters";
    private final String NAME_SP = "\nSpecial characters not permitted in this field";
    private final String EMAIL_FMT = "\nIncorrect email format";
    private final String PHONE_FMT = "\nIncorrect phone number format";
    private final String ADDRESS = "\nPlease provide a valid input greater than 10 characters";
    private final String PPHRASE_LEN = "\nPassword too short";
    private final String PPHRASE_OTHERS = "\nPassword must contain an uppercase letter, a number and a special character";
    private final String NIN = "\nPlease enter a valid NIN";
    private final String PHOTO_SIZE = "\nPhoto too large";
    private final String PHOTO_EX = "\nOnly .jpg .jpeg .png are supported.";


    private CustomerDTO cdto;
    private LinkedHashMap<String, String> errors;

    public CustomerValidator(CustomerDTO cdto){ this.cdto = cdto;    }

    public LinkedHashMap<String, String> validate(){
        validateFname();
        validateLname();
        validatePhone1();
        validatePhone2();
        validateEmail();
        validatePassphrase();
        validatePhoto();
        validateNIN();
        validateCustomerAddress();
        validateCustomerOccupationDescription();

        return (LinkedHashMap<String,String>)errors;
    }
    //This method validates customerFname and customerLname properties
    //It takes a 'propertyName' argument representing either's name
    //and a 'propertyValue' argument representing either's value
    private void validateName(String propertyName, String propertyValue) {
        String name = propertyValue;

        if(name.length()<2)  errors.put(propertyName,NAME_LEN);
        else if(name.matches("[!@#$%\\^&*()+=|{}\\\";:/?\\\\,.'<>`~\\[\\]]"))errors.put(propertyName,NAME_SP);
    }

    private void validateFname(){
        validateName("First name: ",cdto.getCustomerFname());
    }
    private void validateLname(){
        validateName("Last name: ",cdto.getCustomerLname());
    }

    private void validateEmail(){
        String email = cdto.getCustomerEmail();
        if(email.matches(".+@.+\\..{2,}"))errors.put("Email: ",EMAIL_FMT);
    }

    private void validatePhone(String propertyName, String propertyValue){
        if(!propertyValue.matches("^[+-][0-9]{11,15}")) errors.put(propertyName,PHONE_FMT);

    }
    private void validatePhone1(){
        validatePhone("Phone No. 1: ",cdto.getCustomerPhone1());
    }
    private void validatePhone2(){
        validatePhone("Phone No. 2: ",cdto.getCustomerPhone2());
    }

    private void validateNIN(){
        String nin = cdto.getCustomerNIN();
        if(!nin.matches("^[0-9]{11}$"))errors.put("NIN: ", NIN);
    }

    private void validatePassphrase(){
        String  passphrase = cdto.getCustomerNIN();
        if(passphrase.length()< 8) errors.put("Passphrase: ", PPHRASE_LEN);
        else if(!(passphrase.matches("[A-Z]")
                && passphrase.matches("[a-z]]")
                && passphrase.matches("[0-9]")
                && passphrase.matches("[!#$%\\^*()+=|{}\\\"\\\\;:/?,.'<>`~\\-\\[\\]]")))
            errors.put("Passphrase: ",PPHRASE_OTHERS);
    }
    private void validateText(String propertyName, String propertyValue){
        String valueErrors = "";
        String value = propertyValue;
        if(value.length() < 10) errors.put(propertyName,ADDRESS);
    }

    private void validateCustomerAddress(){
        validateText("Address: ", cdto.getCustomerAddress());
    }
    private void validateCustomerOccupationDescription(){
        validateText("Occupation Description: ",cdto.getCustomerOccupationDescription());
    }
    private void validatePhoto(){
        String photo = cdto.getCustomerPassportPhoto();
        if((!photo.matches("\\.jpg")
                && !photo.matches("\\.jpeg")
                && !photo.matches("\\.png"))
        ||
        !photo.matches(".+\\..+")) {
            errors.put("Photo: ",PHOTO_EX);
            return;
        }

        else {
                File photoFile = new File(photo);
                if (photoFile.length()>3072) {
                    errors.put("Photo: ", PHOTO_SIZE);
                    return;
                }
        }
    }

    public static String strip(String data){
        data = data.trim().replaceAll("[!@#$%\\^&*()+=|{}\\\";:/?\\\\,.'<>`~\\[\\]]","");
        return data;
    }
    public static String stripFile(String data){
        data = data.trim().replaceAll("[!@#$%\\^&*()+=|{}\\\";:?\\\\,'<>`~\\[\\]]","");
        return data;
    }
    public CustomerDTO cleanObject(){
        cdto.setCustomerFname(CustomerValidator.strip(cdto.getCustomerFname()));
        cdto.setCustomerLname(CustomerValidator.strip(cdto.getCustomerLname()));
        cdto.setCustomerEmail(cdto.getCustomerEmail().trim());
        cdto.setCustomerPhone1(cdto.getCustomerPhone1().trim());
        cdto.setCustomerPhone2(cdto.getCustomerPhone2().trim());
        cdto.setCustomerNIN(strip(cdto.getCustomerNIN()));
        cdto.setCustomerAddress(CustomerValidator.strip(cdto.getCustomerAddress()));
        cdto.setCustomerOccupationDescription(CustomerValidator.strip(cdto.getCustomerOccupationDescription()));
        cdto.setCustomerPassportPhoto(CustomerValidator.stripFile(cdto.getCustomerPassportPhoto()));

        return cdto;
    }
}
