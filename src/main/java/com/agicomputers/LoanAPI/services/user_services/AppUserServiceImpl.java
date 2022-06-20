package com.agicomputers.LoanAPI.services.user_services;

import com.agicomputers.LoanAPI.models.entities.AppUser;
import com.agicomputers.LoanAPI.repositories.user_repositories.AuthenticatedUserRepository;
import com.agicomputers.LoanAPI.repositories.user_repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.agicomputers.LoanAPI.models.dto.user_dtos.AppUserDTO;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.zip.CRC32;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class AppUserServiceImpl implements UserService, UserDetailsService {

    UserRepository appUserRepository;
    PasswordEncoder passwordEncoder;
    AuthenticatedUserRepository authenticatedUserRepository;

    @Autowired
    public AppUserServiceImpl(@Qualifier("appUserRepository1") UserRepository appUserRepository,
                                   PasswordEncoder passwordEncoder,
                                   AuthenticatedUserRepository authenticatedUserRepository) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticatedUserRepository=  authenticatedUserRepository;
    }

    @Override
    public HashSet<AppUserDTO> getAllUsers() {
        Set<AppUserDTO> appUsers = new HashSet<AppUserDTO>(0);
        Iterable<AppUser> appUsersFromRepo = appUserRepository.findAll();

        AppUserDTO udto;
        Iterator<AppUser> iterator = appUsersFromRepo.iterator();
        while (iterator.hasNext()) {
            udto = new AppUserDTO();
            BeanUtils.copyProperties(iterator.next(), udto);
            appUsers.add(udto);
        }
        return (HashSet<AppUserDTO>) appUsers;
    }

    @Override
    public AppUserDTO getUser(String appUserUid) {
        //Extract Database Id for quicker indexed search
        try {
        //Use the AppUserRepository to find by appUserUid
       Optional<AppUser> optionalAppUser = appUserRepository.findByAppUserUid(appUserUid);
      if (optionalAppUser.isPresent()) {
            AppUser appUser = optionalAppUser.get();
            AppUserDTO udto = new AppUserDTO();
            BeanUtils.copyProperties(appUser, udto);
            return udto;
        }

      }catch (Exception ex){
            return null;
        }
      return null;
    }

    @Override
    public AppUserDTO getUserWithEmail(String email) {
        Optional<AppUser> appUserWithEmail = appUserRepository.findByEmail(email);
        if (appUserWithEmail.isPresent()){
            AppUser appUser = appUserWithEmail.get();
            AppUserDTO udto = new AppUserDTO();
            BeanUtils.copyProperties(appUser,udto);
            return udto;
        }
        else return null;
    }

    @Override
    public Boolean deleteUser(String appUserId) {
        try {
            Optional<Long> idOptional = appUserRepository.existsByAppUserUid(appUserId);
            if (idOptional.isPresent()) {
                appUserRepository.deleteById(idOptional.get());
                return true;
            }
        }catch (Exception ex){return false;}
        return false;
    }

    @Override
    @Transactional
    public AppUserDTO createUser(AppUserDTO udto) {
        AppUser appUser = new AppUser(); //AppUser Entity
        BeanUtils.copyProperties(udto, appUser);

        //Set AppUser's  encoded password
        appUser.setAppUserPassphrase(passwordEncoder.encode(udto.getAppUserPassphrase()));

        //Get AppUser's custom generated Uid
        String generatedAppUserUid = generateAppUserUid(udto.getAppUserEmail());
        //Set AppUser's custom generated Uid
        appUser.setAppUserUid(generatedAppUserUid);

        //Save record
        appUserRepository.save(appUser);

        //Copy properties into the DTO
        BeanUtils.copyProperties(appUser, udto);
        return udto;
    }

    @Override
    @Transactional
    public AppUserDTO updateUser(AppUserDTO udto) {
        try {

        Optional<AppUser> appUserOptional = appUserRepository.findByAppUserUid(udto.getAppUserUid());
        if(appUserOptional.isPresent()) {
            AppUser appUser = appUserOptional.get();
            if(udto.getAppUserFname() != null)appUser.setAppUserFname(udto.getAppUserFname());
            if(udto.getAppUserLname() != null)appUser.setAppUserLname(udto.getAppUserLname());
            if(udto.getAppUserPhone1() != null)appUser.setAppUserPhone1(udto.getAppUserPhone1());
            if(udto.getAppUserPhone2() != null)appUser.setAppUserPhone2(udto.getAppUserPhone2());
            if(udto.getAppUserEmail() != null)appUser.setAppUserEmail(udto.getAppUserEmail());
            if(udto.getAppUserPassphrase() != null)appUser.setAppUserPassphrase(udto.getAppUserPassphrase());
            if(udto.getAppUserPassportPhoto() != null)appUser.setAppUserPassportPhoto(udto.getAppUserPassportPhoto());
            if(udto.getAppUserNIN() != null)appUser.setAppUserNIN(udto.getAppUserNIN());
            if(udto.getAppUserBVN() != null)appUser.setAppUserBVN(udto.getAppUserBVN());
            if(udto.getAppUserAddress() != null)appUser.setAppUserAddress(udto.getAppUserAddress());
            if(udto.getAppUserOccupation() != null)appUser.setAppUserOccupation(udto.getAppUserOccupation());
            if(udto.getAppUserOccupationLocation() != null)appUser.setAppUserOccupationLocation(udto.getAppUserOccupationLocation());

            appUserRepository.save(appUser);

            BeanUtils.copyProperties(appUser,udto);
            return udto;
        }
        }
        catch (Exception ex){ return  null;  }

        return null;
    }


    private String allLetters(String checksumBase36Encode){
        //Check for the non-letter/number
        for(int i = 0; i<checksumBase36Encode.length();i++){
            if(!Character.isLetter(checksumBase36Encode.charAt(i))) {
                int number = Integer.parseInt(Character.toString(checksumBase36Encode.charAt(i)));
                int codePoint = number + 97; //This randomly returns a codepoint between "a" and "j", j inclusive
                char[] chars = Character.toChars(codePoint);
                checksumBase36Encode = checksumBase36Encode.replace(checksumBase36Encode.charAt(i), chars[0]);
            }
        }
        return checksumBase36Encode;
    }
    private String generateAppUserUid(String email) {

        if (email.matches(".+@.+\\..{2,}")) {
            String id = nextId() + email;

            //Base 10 (Alphanumeric) encode the id
            String idBase10Encode = new BigInteger(1, id.getBytes(StandardCharsets.UTF_8)).toString(10);

            //Get a 10 byte value if less than 10 bytes
            while (idBase10Encode.length() < 10) {
                idBase10Encode += Integer.toString((int) (Math.random() * 10));
            }

            String idfirst10Bytes = idBase10Encode.substring(0, 10);

            String checksumLast5Bytes = generateChecksum(idfirst10Bytes);

            //Capitalize for user
            checksumLast5Bytes = capitalize(checksumLast5Bytes);
            //Concatenate (encoded) id with checksum
            String newId = idfirst10Bytes.concat(checksumLast5Bytes);

            return newId;
        }else return null;
    }

    public boolean validateUid(String uid){
        if(!uid.matches("^[0-9]{10}[A-Z]{5}$")){return false; }

        String first10Bytes = uid.substring(0,10);
        //Last 5 bytes decapitalized for processing
        String last5Bytes = decapitalize(uid.substring(10));

        String checksumLast5Bytes = generateChecksum(first10Bytes);

        return last5Bytes.equals(checksumLast5Bytes);
    }

    private String capitalize(String word){
        char character;
        for(int i = 0;i<word.length();i++) {
            character = word.charAt(i);
            if(Character.isLetter(character)&& Character.isLowerCase(character))
                word = word.replace(word.charAt(i),Character.toUpperCase(character));
        }
        return word;
    }

    private String decapitalize(String word){
        for(int i = 0;i<word.length();i++) {
            char character = word.charAt(i);
            if(Character.isLetter(character)&& Character.isUpperCase(character))
                word = word.replace(character,Character.toLowerCase(character));
        }
        return word;
    }

    private String generateChecksum(String data){

        //Generate a checksum
        CRC32 checksum = new CRC32();
        checksum.update(data.getBytes(StandardCharsets.UTF_8));

        //Base 36 encode the checksum
        String first10BytesChecksumEncode = Long.toString(checksum.getValue(), 36);
        first10BytesChecksumEncode = allLetters(first10BytesChecksumEncode);//Encode all numbers to lower case letters
        String checksumLast5Bytes = first10BytesChecksumEncode.substring(first10BytesChecksumEncode.length() - 5); //Take last 5 bytes of encoded checksum

        return checksumLast5Bytes;
    }

    //172ms 230ms
    public long nextId(){
        long count;
        try {
            count = appUserRepository.findNextAppUserId();//This returns the next id in the sequence generator
        } catch (NullPointerException ex) {
            count = 1L;
        }
        return count;
    }

    //This method returns th AppUser (UserDetails) Object directly without the
    //use of a Data Transfer Object
    //It is used for authentication by the container's DaoAuthenticationProvider
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return authenticatedUserRepository.getAuthenticatedUser(username)
                .orElseThrow(()->new UsernameNotFoundException(String.format("Username %s not found",username)));

    }
}
