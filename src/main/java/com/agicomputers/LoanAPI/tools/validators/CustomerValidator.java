/*Validation of User inputs for both POST and PUT endpoints*/
package com.agicomputers.LoanAPI.tools.validators;

import com.agicomputers.LoanAPI.models.dto.CustomerDTO;
import com.agicomputers.LoanAPI.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.io.File;

@Component
public class CustomerValidator {
    private final String NAME_LEN = "\nName should not be less than two characters";
    private final String NAME_SP = "\nSpecial characters not permitted in this field";
    private final String EMAIL_FMT = "\nIncorrect email format";
    private final String EMAIL_EX = "\nThis email already exists";
    private final String PHONE_FMT = "\nIncorrect phone number format";
    private final String ADDRESS = "\nPlease provide a valid input greater than 10 characters";
    private final String PPHRASE_LEN = "\nPassword too short";
    private final String PPHRASE_OTHERS = "\nPassword must contain an uppercase letter, a number and a special character";
    private final String NIN = "\nPlease enter a valid NIN";
    private final String NIN_EX = "\nThis NIN already exists";
    private final String PHOTO_SIZE = "\nPhoto too large";
    private final String PHOTO_FMT = "\nOnly .jpg .jpeg .png are supported.";
    private final String PHOTO_EX = "\nPhoto does not exist";

    @Autowired
    CustomerService customerService;

    private CustomerDTO cdto;
    private LinkedHashMap<String, String> errors = new LinkedHashMap<>(0);
    private Boolean isPost = true;

    public Boolean getPost() {
        return isPost;
    }

    public void setPost(Boolean post) {
        isPost = post;
    }

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

    private void validateEmail(String email){
        try {
            if (!email.matches(".+@.+\\..{2,}")) errors.put("Email: ", EMAIL_FMT);
            if (customerService.emailExists(email)) errors.put("Email: ", EMAIL_EX);
        }
        catch(NullPointerException nullEx){errors.put("Email: ", EMAIL_FMT);}
    }

    //This method validates using the international phone number format
    //It validates phone1 and phone2
    private void validatePhone(String propertyName, String propertyValue){
        try {
            if (!propertyValue.matches("^[+-][0-9]{11,15}")) errors.put(propertyName, PHONE_FMT);
        }catch(NullPointerException nullEx) {errors.put(propertyName, PHONE_FMT);};
    }

    private void validateNIN(String nin){
        try {
            if (!nin.matches("^[0-9]{11}$")) errors.put("NIN: ", NIN);
            if (customerService.ninExists(nin)) errors.put("Email: ", NIN_EX);
        }
        catch(NullPointerException nullEx) { errors.put("NIN: ", NIN);};
    }

    private void validatePassphrase(String passphrase){
        try {
            if (passphrase.length() < 8) errors.put("Passphrase: ", PPHRASE_LEN);
            else if (!passphrase.matches(".*[A-Z]+.*")  //There is no uppercase Letter
                    || !passphrase.matches(".*[a-z]+.*")// There is no lowercase letter
                    || !passphrase.matches(".*[0-9]+.*")// There is no number
                    || !passphrase.matches(".*[!#$%\\^*()+=|{}\\\"\\\\;:/?,.'<>`~\\-\\[\\]]+.*"))//There is no special character
                errors.put("Passphrase: ", PPHRASE_OTHERS);
        } catch(NullPointerException nullEx) { errors.put("Passphrase: ", PPHRASE_LEN);}
    }

    //This method validates addresses or other textbox inputs
    private void validateText(String propertyName, String propertyValue){
        try {
            String valueErrors = "";
            String value = propertyValue;
            if (value.length() < 10) errors.put(propertyName, ADDRESS);
        }catch(NullPointerException nullEx) {errors.put(propertyName, ADDRESS);};
    }

    //This method processes uploaded images and renames them from their current temporary source to a destination on the server
    private void validatePhoto(String photo){
        try {
            File photoFile = new File(photo);

            //Check if photo exists
            if(photoFile.exists()) {
                //Check photo format
                if (!photo.matches(".+\\.jpg$")
                        && !photo.matches(".+\\.jpeg$")
                        && !photo.matches(".+\\.png$")) {
                    errors.put("Photo: ", PHOTO_FMT);
                } else {
                    //Check photo size. Maximum limit is 500000 bytes ie 500KB
                    if (photoFile.length() > 500000) {
                        errors.put("Photo: ", PHOTO_SIZE);
                        return;
                    } else {
                        photoFile.renameTo(new File("C:\\Users\\Emmanuel Obiakor\\Pictures\\LoanApiImages\\passportImages\\" + photoFile.getName()));
                    }
                }
            }
            else{errors.put("Photo: ", PHOTO_EX);}
        }catch(NullPointerException nullEx){errors.put("Photo: ", PHOTO_EX);}
    }
    //This method purifies names and addresses from the request
    public static String strip(String data){
        if(!(data==null))
        data = data.trim().replaceAll("[!@#$%\\^&*()+=|{}\\\";:/?\\\\,.'<>`~\\[\\]]","");
        return data;
    }
    //This method purifies file names from the request
    public static String stripFile(String data){
        if(!(data==null))
        data = data.trim().replaceAll("[!@#%\\^&*()+=|{}\\\";?\\\\,'<>`~\\[\\]]","");
        return data;
    }

    //These methods use the strip and stripFile methods to purify user input
    public CustomerDTO cleanObject(){
        if(isPost) {
            cdto.setCustomerFname(CustomerValidator.strip(cdto.getCustomerFname()));
            cdto.setCustomerLname(CustomerValidator.strip(cdto.getCustomerLname()));
            cdto.setCustomerEmail(cdto.getCustomerEmail().trim());
            cdto.setCustomerPhone1(cdto.getCustomerPhone1().trim());
            cdto.setCustomerPhone2(cdto.getCustomerPhone2().trim());
            cdto.setCustomerNIN(strip(cdto.getCustomerNIN()));
            cdto.setCustomerAddress(CustomerValidator.strip(cdto.getCustomerAddress()));
            cdto.setCustomerOccupationLocation(CustomerValidator.strip(cdto.getCustomerOccupationLocation()));
            cdto.setCustomerPassportPhoto(CustomerValidator.stripFile(cdto.getCustomerPassportPhoto()));
        }
        else{
            if(cdto.getCustomerFname()!=null)cdto.setCustomerFname(CustomerValidator.strip(cdto.getCustomerFname()));
            if(cdto.getCustomerLname()!=null)cdto.setCustomerLname(CustomerValidator.strip(cdto.getCustomerLname()));
            if(cdto.getCustomerEmail()!=null)cdto.setCustomerEmail(cdto.getCustomerEmail().trim());
            if(cdto.getCustomerPhone1()!=null)cdto.setCustomerPhone1(cdto.getCustomerPhone1().trim());
            if(cdto.getCustomerPhone2()!=null)cdto.setCustomerPhone2(cdto.getCustomerPhone2().trim());
            if(cdto.getCustomerNIN()!=null)cdto.setCustomerNIN(strip(cdto.getCustomerNIN()));
            if(cdto.getCustomerAddress()!=null)cdto.setCustomerAddress(CustomerValidator.strip(cdto.getCustomerAddress()));
            if(cdto.getCustomerOccupationLocation()!=null)cdto.setCustomerOccupationLocation(CustomerValidator.strip(cdto.getCustomerOccupationLocation()));
            if(cdto.getCustomerPassportPhoto()!=null)cdto.setCustomerPassportPhoto(CustomerValidator.stripFile(cdto.getCustomerPassportPhoto()));
        }
        return cdto;
    }
    //Validates user input in the event of user registration
    public LinkedHashMap<String, String> validate(){
        if(isPost) {
            validateName("First name: ", cdto.getCustomerFname());
            validateName("Last name: ", cdto.getCustomerLname());
            validatePhone("Phone No. 1: ", cdto.getCustomerPhone1());
            validatePhone("Phone No. 2: ", cdto.getCustomerPhone2());
            validateEmail(cdto.getCustomerEmail());
            validatePassphrase(cdto.getCustomerPassphrase());
            validatePhoto(cdto.getCustomerPassportPhoto());
            validateNIN(cdto.getCustomerNIN());
            validateText("Address: ", cdto.getCustomerAddress());
            validateText("Occupation Location: ", cdto.getCustomerOccupationLocation());

        }
        else{
            if(cdto.getCustomerFname()!=null)validateName("First name: ", cdto.getCustomerFname());
            if(cdto.getCustomerLname()!=null)validateName("Last name: ", cdto.getCustomerLname());
            if(cdto.getCustomerPhone1()!=null)validatePhone("Phone No. 1: ", cdto.getCustomerPhone1());
            if(cdto.getCustomerPhone2()!=null)validatePhone("Phone No. 2: ", cdto.getCustomerPhone2());
            if(cdto.getCustomerEmail()!=null)validateEmail(cdto.getCustomerEmail());
            if(cdto.getCustomerPassphrase()!=null)validatePassphrase(cdto.getCustomerPassphrase());
            if(cdto.getCustomerPassportPhoto()!=null)validatePhoto(cdto.getCustomerPassportPhoto());
            if(cdto.getCustomerNIN()!=null)validateNIN(cdto.getCustomerNIN());
            if(cdto.getCustomerAddress()!=null)validateText("Address: ", cdto.getCustomerAddress());
            if(cdto.getCustomerOccupationLocation()!=null)validateText("Occupation Location: ", cdto.getCustomerOccupationLocation());
        }
        return errors;
    }

}
