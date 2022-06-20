package com.agicomputers.LoanAPI.services;
import com.agicomputers.LoanAPI.repositories.user_repositories.AppUserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.zip.CRC32;

@SpringBootTest
public class AppUserServiceImplTest {

    @Autowired
    AppUserRepository appUserRepository ;

    @Test
    public void tester() {
       String uid = generateAppUserUid("mikol@gmail.com");
       Assertions.assertTrue(validateUid(uid));
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

        //260ms
        /*public String generateAppUserId(String alpha, String omega) {

            //alpha's second letter from the right
            String alphaSecondLetterPrime = String.valueOf(alpha.charAt(alpha.length() - 2));
            //omicron's second letter from the right
            String omegaSecondLetterPrime = String.valueOf(omega.charAt(omega.length() - 2));

            //Random numbers from 0 to 9
            String numString1 = String.valueOf((int) (Math.random() * 10));
            String numString2 = String.valueOf((int) (Math.random() * 10));

            //Number of entries in the database + 1
            String count;
            try {
                count = String.valueOf(appUserRepository.findLastAppUserId() + 1);//This returns the most recent (largest) id in the database
            } catch (NullPointerException ex) {
                count = "1";
            }

            String id = alphaSecondLetterPrime     //Second byte (from the right) of alpha
                    .concat(numString1) //Random number
                    .concat(count)      //Next id in the database
                    .concat(numString2) //Random number
                    .concat(omegaSecondLetterPrime); //Second byte (from the right) of omega


            //Base 34 (Alphanumeric) encode the id
            //Radix 34 was chosen because for this algorithm, "y" and "z" are intended as filler bytes
            //Therefore, we dont expect them to appear randomly when a text is encoded
            String idBase34Encode = new BigInteger(1, id.getBytes(StandardCharsets.UTF_8)).toString(34);
            String idLast4Bytes = idBase34Encode.substring(idBase34Encode.length()-5,idBase34Encode.length()-1);

            //Generate a checksum
            CRC32 checksum = new CRC32();
            checksum.update(idLast4Bytes.getBytes(StandardCharsets.UTF_8)); //Use last 4 bytes for checksum

            //Base 34 encode the checksum
            String checksumBase34Encode = Long.toString(checksum.getValue(), 34);

            //Concatenate (encoded) id with checksum
            String newId = idBase34Encode.concat(" 7 ").concat(checksumBase34Encode);
            newId = capitalize(newId);

            return newId;
        }
*/

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
                count = appUserRepository.findNextAppUserId();//This returns the most recent (largest) id in the database
            } catch (NullPointerException ex) {
                count = 1L;
            }
            return count;
        }

    class Exec implements Executable {
        @Override
        public void execute() throws Throwable {
            allLetters("ti3h5c90f");
        }
    }
}
