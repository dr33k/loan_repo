/*Validation of User inputs for both POST and PUT endpoints*/
package com.agicomputers.LoanAPI.tools.validators.user_validators;

import com.agicomputers.LoanAPI.models.dto.user_dtos.CustomerDTO;
import com.agicomputers.LoanAPI.services.user_services.CustomerUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.io.File;

@Component
public class CustomerValidator implements UserValidator {
    
    CustomerDTO dto = null;
    LinkedHashMap<String, String> errors = new LinkedHashMap<>(0);
    Boolean isPost = true;

    public Boolean getPost() {
        return isPost;
    }

    public void setPost(Boolean post) {
        isPost = post;
    }

    public CustomerDTO getDto() {
        return dto;
    }

    public void setDto(CustomerDTO dto) {
        this.dto = dto;
    }

    public LinkedHashMap<String, String> getErrors() {
        return errors;
    }

    public void setErrors(LinkedHashMap<String, String> errors) {
        this.errors = errors;
    }

    @Autowired
    CustomerUserServiceImpl customerService;

    //This method validates customerFname and customerLname properties
    //It takes a 'propertyName' argument representing either's name
    //and a 'propertyValue' argument representing either's value
     public void validateName(String propertyName, String propertyValue) {
        try {
            String name = propertyValue;

            if (name.length() < 2) errors.put(propertyName, NAME_LEN);
            else if (name.matches("[!@#$%\\^&*()+=|{}\\\";:/?\\\\,.'<>`~\\[\\]]")) errors.put(propertyName, NAME_SP);
        }
        catch(NullPointerException nullEx){errors.put(propertyName, NAME_LEN);}
    }
    
    @Override
    public void validateEmail(String email){
        try {
            if (!email.matches(".+@.+\\..{2,}")) errors.put("Email: ", EMAIL_FMT);
            if (customerService.emailExists(email)) errors.put("Email: ", EMAIL_EX);
        }
        catch(NullPointerException nullEx){errors.put("Email: ", EMAIL_FMT);}
    }

    //This method validates using the international phone number format
    //It validates phone1 and phone2

    @Override
    public void validatePhone(String propertyName, String propertyValue){
        try {
            if (!propertyValue.matches("^[+-][0-9]{11,15}")) errors.put(propertyName, PHONE_FMT);
        }catch(NullPointerException nullEx) {errors.put(propertyName, PHONE_FMT);};
    }

    @Override
    public void validateNIN(String nin){
        try {
            if (!nin.matches("^[0-9]{11}$")) errors.put("NIN: ", NIN);
            if (customerService.ninExists(nin)) errors.put("Email: ", NIN_EX);
        }
        catch(NullPointerException nullEx) { errors.put("NIN: ", NIN);};
    }

    @Override
    public void validatePassphrase(String passphrase){
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
    @Override
    public void validateText(String propertyName, String propertyValue){
        try {
            String valueErrors = "";
            String value = propertyValue;
            if (value.length() < 10) errors.put(propertyName, ADDRESS);
        }catch(NullPointerException nullEx) {errors.put(propertyName, ADDRESS);};
    }

    //This method processes uploaded images and renames them from their current temporary source to a destination on the server
    @Override
    public void validatePhoto(String photo){
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
    @Override
    public CustomerDTO cleanObject(){
        if(isPost) {
            dto.setUserFname(CustomerValidator.strip(dto.getUserFname()));
            dto.setUserLname(CustomerValidator.strip(dto.getUserLname()));
            if(dto.getUserEmail()!=null)dto.setUserEmail(dto.getUserEmail().trim());
            if(dto.getUserPhone1()!=null)dto.setUserPhone1(dto.getUserPhone1().trim());
            if(dto.getUserPhone2()!=null)dto.setUserPhone2(dto.getUserPhone2().trim());
            dto.setUserNIN(strip(dto.getUserNIN()));
            dto.setUserAddress(CustomerValidator.strip(dto.getUserAddress()));
            dto.setUserOccupationLocation(CustomerValidator.strip(dto.getUserOccupationLocation()));
            dto.setUserPassportPhoto(CustomerValidator.stripFile(dto.getUserPassportPhoto()));
        }
        else{
            if(dto.getUserFname()!=null)dto.setUserFname(CustomerValidator.strip(dto.getUserFname()));
            if(dto.getUserLname()!=null)dto.setUserLname(CustomerValidator.strip(dto.getUserLname()));
            if(dto.getUserEmail()!=null)dto.setUserEmail(dto.getUserEmail().trim());
            if(dto.getUserPhone1()!=null)dto.setUserPhone1(dto.getUserPhone1().trim());
            if(dto.getUserPhone2()!=null)dto.setUserPhone2(dto.getUserPhone2().trim());
            if(dto.getUserNIN()!=null)dto.setUserNIN(strip(dto.getUserNIN()));
            if(dto.getUserAddress()!=null)dto.setUserAddress(CustomerValidator.strip(dto.getUserAddress()));
            if(dto.getUserOccupationLocation()!=null)dto.setUserOccupationLocation(CustomerValidator.strip(dto.getUserOccupationLocation()));
            if(dto.getUserPassportPhoto()!=null)dto.setUserPassportPhoto(CustomerValidator.stripFile(dto.getUserPassportPhoto()));
        }
        return (CustomerDTO) dto;
    }

    //Validates user input in the event of user registration
    @Override
    public LinkedHashMap<String, String> validate(){
        if(isPost) {
            validateName("First name: ", dto.getUserFname());
            validateName("Last name: ", dto.getUserLname());
            validatePhone("Phone No. 1: ", dto.getUserPhone1());
            validatePhone("Phone No. 2: ", dto.getUserPhone2());
            validateEmail(dto.getUserEmail());
            validatePassphrase(dto.getUserPassphrase());
            validatePhoto(dto.getUserPassportPhoto());
            validateNIN(dto.getUserNIN());
            validateText("Address: ", dto.getUserAddress());
            validateText("Occupation Location: ", dto.getUserOccupationLocation());

        }
        else{
            if(dto.getUserFname()!=null)validateName("First name: ", dto.getUserFname());
            if(dto.getUserLname()!=null)validateName("Last name: ", dto.getUserLname());
            if(dto.getUserPhone1()!=null)validatePhone("Phone No. 1: ", dto.getUserPhone1());
            if(dto.getUserPhone2()!=null)validatePhone("Phone No. 2: ", dto.getUserPhone2());
            if(dto.getUserEmail()!=null)validateEmail(dto.getUserEmail());
            if(dto.getUserPassphrase()!=null)validatePassphrase(dto.getUserPassphrase());
            if(dto.getUserPassportPhoto()!=null)validatePhoto(dto.getUserPassportPhoto());
            if(dto.getUserNIN()!=null)validateNIN(dto.getUserNIN());
            if(dto.getUserAddress()!=null)validateText("Address: ", dto.getUserAddress());
            if(dto.getUserOccupationLocation()!=null)validateText("Occupation Location: ", dto.getUserOccupationLocation());
        }
        return errors;
    }

}
