/*Validation of User inputs for both POST and PUT endpoints*/
package com.agicomputers.LoanAPI.services.user_services;

import com.agicomputers.LoanAPI.repositories.user_repositories.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import com.agicomputers.LoanAPI.models.dto.AppUserDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.LinkedHashMap;
import java.io.File;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppUserValidatorService{
    final String NAME_LEN = "\nName should not be less than two characters";
    final String NAME_SP = "\nSpecial characters not permitted in this field";
    final String EMAIL_FMT = "\nIncorrect email format";
    final String DOB_FMT = "\nIncorrect DOB format";
    final String DOB_FMT_YMD = "\nIncorrect DOB format, only YYYY-MM-DD supported";
    final String DOB_18 = "\nYou must be 18 years or older";
    final String EMAIL_EX = "\nThis email already exists";
    final String PHONE_FMT = "\nIncorrect phone number format";
    final String ADDRESS = "\nPlease provide a valid input greater than 10 characters";
    final String PPHRASE_LEN = "\nPassword too short";
    final String PPHRASE_OTHERS = "\nPassword must contain an uppercase letter, a number and a special character";
    final String NIN = "\nPlease enter a valid NIN";
    final String NIN_EX = "\nA user with this NIN already exists";

    final String BVN = "\nPlease enter a valid BVN";
    final String BVN_EX = "\nA user with this BVN already exists";
    final String PHOTO_SIZE = "\nPhoto too large";
    final String PHOTO_FMT = "\nOnly .jpg .jpeg .png are supported.";
    final String PHOTO_EX = "\nPhoto does not exist";

    @Autowired
    private final AppUserServiceImpl appUserService;
    @Autowired
    private final AppUserRepository appUserRepository;

    AppUserDTO dto = null;
    LinkedHashMap<String, String> errors = new LinkedHashMap<>(0);
    Boolean isPost = true;

    public Boolean getPost() {
        return isPost;
    }

    public void setPost(Boolean post) {
        isPost = post;
    }

    public AppUserDTO getDto() {
        return dto;
    }

    public void setDto(AppUserDTO dto) {
        this.dto = dto;
    }

    public LinkedHashMap<String, String> getErrors() {
        return errors;
    }

    public void setErrors(LinkedHashMap<String, String> errors) {
        this.errors = errors;
    }

    //This method validates appUserFname and appUserLname properties
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

    public void validateDob(LocalDate userDob){
        try {
        LocalDate now = LocalDate.now();
        Period userAge =  userDob.until(now);

            if (userDob.getYear() < 1900 || userDob.getYear() > now.getYear()) errors.put("dob_year", DOB_FMT_YMD);
            else if (userDob.getMonthValue() < 1 || userDob.getMonthValue() > 12) errors.put("dob_month", DOB_FMT_YMD);
            else if (userDob.getDayOfMonth()< 1 || userDob.getDayOfMonth() > userDob.lengthOfMonth()) errors.put("dob_day", DOB_FMT_YMD);
            else if (userAge.getYears()< 18) errors.put("dob", DOB_18);
        }
        catch(NullPointerException nullEx){errors.put("dob", DOB_FMT);}
        catch(Exception e){errors.put("dob",DOB_FMT_YMD);}
    }
    public void validateEmail(String email){
        try {
            if (!email.matches(".+@.+\\..{2,}")) errors.put("email", EMAIL_FMT);
            else if (emailExists(email)) errors.put("email", EMAIL_EX);
        }
        catch(NullPointerException nullEx){errors.put("email", EMAIL_FMT);}
    }

    //This method validates using the international phone number format
    //It validates phone1 and phone2
    public void validatePhone(String propertyName, String propertyValue){
        try {
            if (!propertyValue.matches("^[+-][0-9]{11,15}$")) errors.put(propertyName, PHONE_FMT);
        }catch(NullPointerException nullEx) {errors.put(propertyName, PHONE_FMT);};
    }

    
    public void validateNIN(String nin){
        try {
            if (!nin.matches("^[0-9]{11}$")) errors.put("nin", NIN);
            else if (ninExists(nin)) errors.put("nin", NIN_EX);
        }
        catch(NullPointerException nullEx) { errors.put("nin", NIN);};
    }

    public void validateBVN(String bvn){
        try {
            if (!bvn.matches("^[0-9]{10}$")) errors.put("bvn", BVN);
            else if (bvnExists(bvn)) errors.put("bvn", BVN_EX);
        }
        catch(NullPointerException nullEx) { errors.put("bvn", BVN);};
    }

    public void validatePassword(String password){
        try {
            if (password.length() < 8) errors.put("password", PPHRASE_LEN);
            else if (!password.matches(".*[A-Z]+.*")  //There is no uppercase Letter
                    || !password.matches(".*[a-z]+.*")// There is no lowercase letter
                    || !password.matches(".*[0-9]+.*")// There is no number
                    || !password.matches(".*[!#&@$%\\^*()+=|{}\\\"\\\\;:/?,.'<>`~\\-\\[\\]]+.*"))//There is no special character
                errors.put("password", PPHRASE_OTHERS);
        } catch(NullPointerException nullEx) { errors.put("password", PPHRASE_LEN);}
    }

    //This method validates addresses or other textbox inputs
    
    public void validateText(String propertyName, String propertyValue){
        try {
            String value = propertyValue;
            if (value.length() < 10) errors.put(propertyName, ADDRESS);
        }catch(NullPointerException nullEx) {errors.put(propertyName, ADDRESS);};
    }

    //This method processes uploaded images and renames them from their current temporary source to a destination on the server
    
    public void validatePhoto(String photo){
        try {
            File photoFile = new File(photo);

            //Check if photo exists
            if(photoFile.exists()) {
                //Check photo format
                if (!photo.matches(".+\\.jpg$")
                        && !photo.matches(".+\\.jpeg$")
                        && !photo.matches(".+\\.png$")) {
                    errors.put("photo", PHOTO_FMT);
                } else {
                    //Check photo size. Maximum limit is 500000 bytes ie 500KB
                    if (photoFile.length() > 500000) {
                        errors.put("photo", PHOTO_SIZE);
                        return;
                    } else {
                        photoFile.renameTo(new File("C:\\Users\\Emmanuel Obiakor\\Pictures\\LoanApiImages\\passportImages\\" + photoFile.getName()));
                    }
                }
            }
            else{errors.put("photo", PHOTO_EX);}
        }catch(NullPointerException nullEx){errors.put("photo", PHOTO_EX);}
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
    
    public AppUserDTO cleanObject(){
        if(isPost) {
            dto.setAppUserFname(AppUserValidatorService.strip(dto.getAppUserFname()));
            dto.setAppUserLname(AppUserValidatorService.strip(dto.getAppUserLname()));
            if(dto.getAppUserEmail()!=null)dto.setAppUserEmail(dto.getAppUserEmail().trim());
            if(dto.getAppUserPhone1()!=null)dto.setAppUserPhone1(dto.getAppUserPhone1().trim());
            if(dto.getAppUserPhone2()!=null)dto.setAppUserPhone2(dto.getAppUserPhone2().trim());
            dto.setAppUserNIN(strip(dto.getAppUserNIN()));
            dto.setAppUserBVN(strip(dto.getAppUserBVN()));
            dto.setAppUserAddress(AppUserValidatorService.strip(dto.getAppUserAddress()));
            dto.setAppUserOccupationLocation(AppUserValidatorService.strip(dto.getAppUserOccupationLocation()));
           // dto.setAppUserPassportPhoto(AppUserValidatorService.stripFile(dto.getAppUserPassportPhoto()));
        }
        else{
            if(dto.getAppUserFname()!=null)dto.setAppUserFname(AppUserValidatorService.strip(dto.getAppUserFname()));
            if(dto.getAppUserLname()!=null)dto.setAppUserLname(AppUserValidatorService.strip(dto.getAppUserLname()));
            if(dto.getAppUserEmail()!=null)dto.setAppUserEmail(dto.getAppUserEmail().trim());
            if(dto.getAppUserPhone1()!=null)dto.setAppUserPhone1(dto.getAppUserPhone1().trim());
            if(dto.getAppUserPhone2()!=null)dto.setAppUserPhone2(dto.getAppUserPhone2().trim());
            if(dto.getAppUserNIN()!=null)dto.setAppUserNIN(strip(dto.getAppUserNIN()));
            if(dto.getAppUserAddress()!=null)dto.setAppUserAddress(AppUserValidatorService.strip(dto.getAppUserAddress()));
            if(dto.getAppUserOccupationLocation()!=null)dto.setAppUserOccupationLocation(AppUserValidatorService.strip(dto.getAppUserOccupationLocation()));
           // if(dto.getAppUserPassportPhoto()!=null)dto.setAppUserPassportPhoto(AppUserValidatorService.stripFile(dto.getAppUserPassportPhoto()));
        }
        return (AppUserDTO) dto;
    }

    //Validates user input in the event of user registration
    
    public LinkedHashMap<String, String> validate(){
        if(isPost) {
            validateName("first_name", dto.getAppUserFname());
            validateName("last_name", dto.getAppUserLname());
            validatePhone("phone_no_1", dto.getAppUserPhone1());
            validatePhone("phone_no_2", dto.getAppUserPhone2());
            validateEmail(dto.getAppUserEmail());
            validateDob(dto.getAppUserDob());
            validatePassword(dto.getAppUserPassword());
           // validatePhoto(dto.getAppUserPassportPhoto());
            validateNIN(dto.getAppUserNIN());
            validateBVN(dto.getAppUserBVN());
            validateText("address", dto.getAppUserAddress());
            validateText("occupation_location", dto.getAppUserOccupationLocation());

        }
        else{
            if(dto.getAppUserFname()!=null)validateName("first_name", dto.getAppUserFname());
            if(dto.getAppUserLname()!=null)validateName("last_name", dto.getAppUserLname());
            if(dto.getAppUserPhone1()!=null)validatePhone("phone_no_1", dto.getAppUserPhone1());
            if(dto.getAppUserPhone2()!=null)validatePhone("phone_no_1", dto.getAppUserPhone2());
            if(dto.getAppUserEmail()!=null)validateEmail(dto.getAppUserEmail());
            if(dto.getAppUserDob()!=null)validateDob(dto.getAppUserDob());
            if(dto.getAppUserPassword()!=null)validatePassword(dto.getAppUserPassword());
           // if(dto.getAppUserPassportPhoto()!=null)validatePhoto(dto.getAppUserPassportPhoto());
            if(dto.getAppUserNIN()!=null)validateNIN(dto.getAppUserNIN());
            if(dto.getAppUserBVN()!=null)validateBVN(dto.getAppUserBVN());
            if(dto.getAppUserAddress()!=null)validateText("address:", dto.getAppUserAddress());
            if(dto.getAppUserOccupationLocation()!=null)validateText("occupation_location", dto.getAppUserOccupationLocation());
        }
        return errors;
    }

    public boolean validateUid(String uid){
        if(!uid.matches("^[0-9]{10}[A-Z]{5}$")){return false; }

        String first10Bytes = uid.substring(0,10);
        //Last 5 bytes decapitalized for processing
        String last5Bytes = decapitalize(uid.substring(10));

        String checksumLast5Bytes = appUserService.generateChecksum(first10Bytes);

        return last5Bytes.equals(checksumLast5Bytes);
    }

    private String decapitalize(String word){
        for(int i = 0;i<word.length();i++) {
            char character = word.charAt(i);
            if(Character.isLetter(character)&& Character.isUpperCase(character))
                word = word.replace(character,Character.toLowerCase(character));
        }
        return word;
    }


    private Boolean emailExists(String email) {
        Boolean emailExists = appUserRepository.existsByEmail(email);   //Id gotten with the email as a search key
        return emailExists;
    }

    private Boolean ninExists(String nin) {
        Boolean ninExists= appUserRepository.existsByNin(nin);     //Id gotten with the NIN as a search key
        return ninExists;
    }

    private boolean bvnExists(String bvn) {
        Boolean bvnExists = appUserRepository.existsByBvn(bvn);     //Id gotten with the BVN as a search key
        return bvnExists;
    }


}
