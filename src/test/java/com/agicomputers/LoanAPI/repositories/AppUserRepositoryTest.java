package com.agicomputers.LoanAPI.repositories;

import com.agicomputers.LoanAPI.repositories.user_repositories.AppUserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.Duration;


@SpringBootTest
public class AppUserRepositoryTest {

    @Autowired
    @Qualifier("appUserRepository1")
    AppUserRepository appUserRepository ;

    @Test
    public void testId(){
        Assertions.assertEquals(appUserRepository.findNextAppUserId(),0);
    }

    class Exec implements Executable {
        //210ms 230
       /* public void searchQuery(){

            String[] fnamlnam= appUserRepository.findCustomerByLastName("Wilson");
            System.out.println(fnamlnam[0]);
        }
*/
        @Override
        public void execute() throws Throwable {
        }
    }

}
