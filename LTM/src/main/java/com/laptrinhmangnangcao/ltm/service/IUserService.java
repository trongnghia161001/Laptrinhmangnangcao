package com.laptrinhmangnangcao.ltm.service;

import com.laptrinhmangnangcao.ltm.dto.PasswordDTO;
import com.laptrinhmangnangcao.ltm.dto.UserDTO;
import com.laptrinhmangnangcao.ltm.exception.MyException;
import org.springframework.data.domain.Pageable;;

import java.util.List;



public interface IUserService {

//    UserDTO login(String username, String password);

    UserDTO findOneByUserNameAndStatus(String name, int status);

    List<UserDTO> getUsers(String searchValue, Pageable pageable);

    int getTotalItems(String searchValue);

    UserDTO findOneByUserName(String userName);

    UserDTO findUserById(long id);

    UserDTO insert(UserDTO userDTO);

    UserDTO update(Long id, UserDTO userDTO);

    void updatePassword(long id, PasswordDTO userDTO) throws MyException;

    UserDTO resetPassword(long id);

    UserDTO updateProfileOfUser(String id, UserDTO userDTO);

    void delete(long[] ids);



}
