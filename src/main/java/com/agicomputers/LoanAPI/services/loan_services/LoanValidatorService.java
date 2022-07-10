package com.agicomputers.LoanAPI.services.loan_services;

import com.agicomputers.LoanAPI.models.dto.LoanDTO;
import com.agicomputers.LoanAPI.models.dto.LoanDTO;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
@Service
public class LoanValidatorService {

    private final Float MAX_PRINCIPAL = 1500000.00F;
    private final Integer MAX_MONTH = 18;
    private final String NULL="Value cannot be null";
    private final String LENGTH="Input too little";
    private final String CONSTRAINT_LOW="Invalid Value";
    private final String CONSTRAINT_HIGH="Maximum Value exceeded";

    private LoanDTO dto = null;
    LinkedHashMap<String, String> errors = new LinkedHashMap<>(0);

    public LoanDTO getDto() {
        return dto;
    }

    public void setDto(LoanDTO dto) {
        this.dto = dto;
    }

    public LinkedHashMap<String, String> getErrors() {
        return errors;
    }

    public void setErrors(LinkedHashMap<String, String> errors) {
        this.errors = errors;
    }


    public static String strip(String data) {
        if (!(data == null))
            data = data.trim().replaceAll("[!@#$%\\^&*()+=|{}\\\";:/?\\\\,.'<>`~\\[\\]]", "");
        return data;
    }

    public LoanDTO cleanObject() {
        dto.setPurposeOfLoan(LoanValidatorService.strip(dto.getPurposeOfLoan()));
        return dto;
    }

    public LinkedHashMap<String, String> validate() {
        validateText("Purpose of loan: ", dto.getPurposeOfLoan());
        validatePrincipal(dto.getLoanPrincipal());
        validateMonths(dto.getLoanPeriodMonths());
        return errors;
    }

    public void validateText(String propertyName, String propertyValue){
        try {
            String value = propertyValue;
            if (value.length() < 5) errors.put(propertyName, LENGTH);
        }catch(NullPointerException nullEx) {errors.put(propertyName, LENGTH);}

    }

    public void validatePrincipal(Float amount){
        try{
            if(amount<0.0F) errors.put("Principal: ",CONSTRAINT_LOW);
            else if(amount>MAX_PRINCIPAL) errors.put("Principal: ",CONSTRAINT_HIGH);
        }catch(NullPointerException nullEx) {errors.put("Principal: ", NULL);}
    }
    public void validateMonths(int amount){
        try{
            if(amount<0) errors.put("Time: ",CONSTRAINT_LOW);
            else if(amount>MAX_MONTH) errors.put("Time: ",CONSTRAINT_HIGH);
        }catch(NullPointerException nullEx) {errors.put("Time: ", NULL);}
    }

}