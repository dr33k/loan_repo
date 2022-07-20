package com.agicomputers.LoanAPI.services.loan_services;

import com.agicomputers.LoanAPI.models.dto.LoanDTO;
import com.agicomputers.LoanAPI.models.entities.Loan;
import com.agicomputers.LoanAPI.models.enums.LoanStatus;
import com.agicomputers.LoanAPI.repositories.user_repositories.LoanRepository;
import com.agicomputers.LoanAPI.services.user_services.AppUserValidatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepository loanRepository;
    private final AppUserValidatorService appUserValidatorService;

    public HashSet<LoanDTO> getAllLoans() {
        Set<LoanDTO> loans = new HashSet<LoanDTO>(0);
        Iterable<Loan> loansFromRepo = loanRepository.findAll(PageRequest.of(0, 20));

        LoanDTO ldto;
        Iterator<Loan> iterator = loansFromRepo.iterator();
        while (iterator.hasNext()) {
            ldto = new LoanDTO();
            BeanUtils.copyProperties(iterator.next(), ldto);
            loans.add(ldto);
        }
        return (HashSet<LoanDTO>) loans;
    }

    public HashSet<LoanDTO> getAllLoansBy(String uid) {
        Set<LoanDTO> loans = new HashSet<LoanDTO>(0);
        Iterable<Loan> loansFromRepo = loanRepository.findAllByAppUserAppUserUid(uid);

        LoanDTO ldto;
        Iterator<Loan> iterator = loansFromRepo.iterator();
        while (iterator.hasNext()) {
            ldto = new LoanDTO();
            BeanUtils.copyProperties(iterator.next(), ldto);
            loans.add(ldto);
        }
        return (HashSet<LoanDTO>) loans;
    }

    public LoanDTO getLoan(String loanCode) {
        try {
            //Use the LoanRepository to find by loanName
            Optional<Loan> optionalLoan = loanRepository.findByLoanCode(loanCode);
            if (optionalLoan.isPresent()) {
                Loan loan = optionalLoan.get();
                LoanDTO ldto = new LoanDTO();
                BeanUtils.copyProperties(loan, ldto);
                return ldto;
            }

        } catch (Exception ex) {
            return null;
        }
        return null;
    }


    public LoanDTO createLoan(LoanDTO ldto) {
        Loan loan = new Loan(); //Loan Entity
        BeanUtils.copyProperties(ldto, loan);

        //Generate Loan code
        //Sample: 000_1234567890ABCDEF
        String generatedLoanCode =
                leadingZeros(loanRepository.count()+"").concat("_").concat(loan.getAppUser().getAppUserUid());

        //Set Loan's generated code
        loan.setLoanCode(generatedLoanCode);
        loan.setLoanStatus(LoanStatus.PROCESSING);

        //Calculate Loan rate
        float ratePerMonth = calculateLoanRate(ldto.getLoanPeriodMonths());
        loan.setLoanInterestRatePerMonth(ratePerMonth);

        //Calculate total loan amount
        float loanAmount = calculateLoanAmount(ratePerMonth,ldto.getLoanPrincipal(),ldto.getLoanPeriodMonths());
        loan.setLoanAmount(loanAmount);

        //Save record
        loanRepository.save(loan);

        //Copy properties into the DTO
        BeanUtils.copyProperties(loan, ldto);
        return ldto;
    }

    private String leadingZeros(String s){
        while(s.length()<3){s="0"+s;}
        return s;
    }

    private float calculateLoanRate(int months){
        float baseRate = 3F;
        float increment = 1.2F;

        switch(months){
            case 2: baseRate+=(increment * 2);
            case 3: baseRate+=(increment * 3);
            case 4: baseRate+=(increment * 4);
            case 5: baseRate+=(increment * 5);
            case 6: baseRate+=(increment * 6);
            case 7: baseRate+=(increment * 7);
            case 8: baseRate+=(increment * 8);
            case 9: baseRate+=(increment * 9);
            case 10: baseRate+=(increment * 10);
            case 11: baseRate+=(increment * 11);
            case 12: baseRate+=(increment * 12);
            case 13: baseRate+=(increment * 13);
            case 14: baseRate+=(increment * 14);
            case 15: baseRate+=(increment * 15);
            case 16: baseRate+=(increment * 16);
            case 17: baseRate+=(increment * 17);
            case 18: baseRate+=(increment * 18);
            case 1:
            default: baseRate+=(increment * 1);
        }
        return baseRate;
    }

    private float calculateLoanAmount(float rate, float principal, int months){
        return (principal * rate * months)/100;
    }

    public boolean validateLoanCode(String loanCode){
        try {
            String loanNum = loanCode.substring(0, 3);
            String uid = loanCode.substring(4);

            Integer.parseInt(loanNum);

            return((loanCode.charAt(3) == '_') && (appUserValidatorService.validateUid(uid)));

        }
        catch (Exception e){return false;}
    }
}
