package com.agicomputers.LoanAPI;

import com.agicomputers.LoanAPI.repositories.user_repositories.AppUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Assertions;
import com.agicomputers.LoanAPI.services.user_services.AppUserServiceImpl;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.temporal.TemporalUnit;
import java.util.zip.CRC32;

@SpringBootTest
class LoanApiApplicationTests {
}
