package com.agicomputers.LoanAPI.tools.validators;

import com.agicomputers.LoanAPI.models.dto.CustomerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.io.File;

@Component
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
    private final String PHOTO_FMT = "\nOnly .jpg .jpeg .png are supported.";
    private final String PHOTO_EX = "\nPhoto does not exist";


    private CustomerDTO cdto;
    private LinkedHashMap<String, String> errors = new LinkedHashMap<>(0);

    public CustomerDTO getCdto() {
        return cdto;
    }

    public void setCdto(CustomerDTO cdto) {
        this.cdto = cdto;
    }

    public LinkedHashMap<String, String> getErrors() {
        return errors;
    }

    public void setErrors(LinkedHashMap<String, String> errors) {
        this.errors = errors;
    }


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
        validateCustomerOccupationLocation();

        return (LinkedHashMap<String,String>)errors;
    }
    //This method validates customerFname and customerLname properties
    //It takes a 'propertyName' argument representing either's name
    //and a 'propertyValue' argument representing either's value
    private void validateName(String propertyName, String propertyValue) {
        try {
            String name = propertyValue;

            if (name.length() < 2) errors.put(propertyName, NAME_LEN);
            else if (name.matches("[!@#$%\\^&*()+=|{}\\\";:/?\\\\,.'<>`~\\[\\]]")) errors.put(propertyName, NAME_SP);
        }
        catch(NullPointerException nullEx){errors.put(propertyName, NAME_LEN);}
    }

    private void validateFname(){
        validateName("First name: ",cdto.getCustomerFname());
    }
    private void validateLname(){
        validateName("Last name: ",cdto.getCustomerLname());
    }

    private void validateEmail(){
        try {
            String email = cdto.getCustomerEmail();
            if (!email.matches(".+@.+\\..{2,}")) errors.put("Email: ", EMAIL_FMT);
        }
        catch(NullPointerException nullEx){errors.put("Email: ", EMAIL_FMT);}
    }

    private void validatePhone(String propertyName, String propertyValue){
        try {
            if (!propertyValue.matches("^[+-][0-9]{11,15}")) errors.put(propertyName, PHONE_FMT);
        }catch(NullPointerException nullEx) {errors.put(propertyName, PHONE_FMT);};
    }
    private void validatePhone1(){
        validatePhone("Phone No. 1: ",cdto.getCustomerPhone1());
    }
    private void validatePhone2(){
        validatePhone("Phone No. 2: ",cdto.getCustomerPhone2());
    }

    private void validateNIN(){
        try {
            String nin = cdto.getCustomerNIN();
            if (!nin.matches("^[0-9]{11}$")) errors.put("NIN: ", NIN);
        }
        catch(NullPointerException nullEx) { errors.put("NIN: ", NIN);};
    }

    private void validatePassphrase(){
        try {
            String passphrase = cdto.getCustomerPassphrase();
            if (passphrase.length() < 8) errors.put("Passphrase: ", PPHRASE_LEN);
            else if (!passphrase.matches(".*[A-Z]+.*")  //There is no uppercase Letter
                    || !passphrase.matches(".*[a-z]+.*")// There is no lowercase letter
                    || !passphrase.matches(".*[0-9]+.*")// There is no number
                    || !passphrase.matches(".*[!#$%\\^*()+=|{}\\\"\\\\;:/?,.'<>`~\\-\\[\\]]+.*"))//There is no special character
                errors.put("Passphrase: ", PPHRASE_OTHERS);
        } catch(NullPointerException nullEx) { errors.put("Passphrase: ", PPHRASE_LEN);}
    }
    private void validateText(String propertyName, String propertyValue){
        try {
            String valueErrors = "";
            String value = propertyValue;
            if (value.length() < 10) errors.put(propertyName, ADDRESS);
        }catch(NullPointerException nullEx) {errors.put(propertyName, ADDRESS);};
    }

    private void validateCustomerAddress(){
        validateText("Address: ", cdto.getCustomerAddress());
    }
    private void validateCustomerOccupationLocation(){
        validateText("Occupation Location: ",cdto.getCustomerOccupationLocation());
    }
    private void validatePhoto(){
        try {
            String photo = cdto.getCustomerPassportPhoto();
            File photoFile = new File(photo);

            if(photoFile.exists()) {
                if (!photo.matches(".+\\.jpg$")
                        && !photo.matches(".+\\.jpeg$")
                        && !photo.matches(".+\\.png$")) {
                    errors.put("Photo: ", PHOTO_FMT);
                } else {

                    if (photoFile.length() > 500000) {
                        errors.put("Photo: ", PHOTO_SIZE);
                        return;
                    } else {
                        photoFile.renameTo(new File("C:\\Users\\Emmanuel Obiakor\\Pictures\\LoanApiImages\\passportImages\\" + photoFile.getName()));
                    }
                }
            }
            else{errors.put("Photo: ", PHOTO_EX);}
        }catch(NullPointerException nullEx){errors.put("Photo: ", PHOTO_SIZE);}
    }

    public static String strip(String data){
        if(!(data==null))
        data = data.trim().replaceAll("[!@#$%\\^&*()+=|{}\\\";:/?\\\\,.'<>`~\\[\\]]","");
        return data;
    }
    public static String stripFile(String data){
        if(!(data==null))
        data = data.trim().replaceAll("[!@#%\\^&*()+=|{}\\\";?\\\\,'<>`~\\[\\]]","");
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
        cdto.setCustomerOccupationLocation(CustomerValidator.strip(cdto.getCustomerOccupationLocation()));
        cdto.setCustomerPassportPhoto(CustomerValidator.stripFile(cdto.getCustomerPassportPhoto()));

        return cdto;
    }
}
