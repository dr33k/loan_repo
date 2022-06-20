package com.agicomputers.LoanAPI.services.user_services;

import java.util.HashSet;
import com.agicomputers.LoanAPI.models.dto.user_dtos.AppUserDTO;

public interface UserService {

    HashSet<AppUserDTO> getAllUsers();
    //HashSet<AppUserDTO> getAllCustomers(String sqlRegex);
    AppUserDTO createUser(AppUserDTO udtoReq);
    AppUserDTO getUser(String userUid);
    AppUserDTO getUserWithEmail(String email);
    AppUserDTO updateUser(AppUserDTO udto);
    Boolean deleteUser(String userUid);


}
