package com.laptrinhmangnangcao.ltm.service.impl;

import com.laptrinhmangnangcao.ltm.constant.SystemConstant;
import com.laptrinhmangnangcao.ltm.converter.UserConverter;
import com.laptrinhmangnangcao.ltm.dto.PasswordDTO;
import com.laptrinhmangnangcao.ltm.dto.UserDTO;
import com.laptrinhmangnangcao.ltm.entity.RolesEntity;
import com.laptrinhmangnangcao.ltm.entity.UsersEntity;
import com.laptrinhmangnangcao.ltm.exception.MyException;
import com.laptrinhmangnangcao.ltm.repository.RoleRepository;
import com.laptrinhmangnangcao.ltm.repository.UserRepository;
import com.laptrinhmangnangcao.ltm.service.IUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserServiceCustom implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;


//    @Override
//    public UserDTO login(String username, String password) {
//        UsersEntity user = userRepository.findByUserName(username);
//        UserDTO dto = new UserDTO();
//        if(user != null) {
//            BeanUtils.copyProperties(user, dto);
//            if(passwordEncoder.matches(password, dto.getPassword())) {
//                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
//                String access_token = JWT.create()
//                        .withSubject(user.getUserName())
//                        .withClaim("role", user.getRoles())
//                        .withExpiresAt(new Date(System.currentTimeMillis()+ (1 * 60 * 10000)))
//                        .sign(algorithm);
//                String refreshtoken = JWT.create()
//                        .withSubject(user.getUserName())
//                        .withExpiresAt(new Date(System.currentTimeMillis()+ (10080 * 60 * 10000)))
//                        .sign(algorithm);
//                dto.setToken(access_token);
//                dto.setRefreshtoken(refreshtoken);
//            }
//        }
//        return dto;
//    }

    @Override
    public UserDTO findOneByUserNameAndStatus(String name, int status) {
        return userConverter.convertToDto(userRepository.findByUserNameAndStatus(name, status));
    }

    @Override
    public List<UserDTO> getUsers(String searchValue, Pageable pageable) {
        Page<UsersEntity> users = null;
        if (StringUtils.isNotBlank(searchValue)) {
            users =
                    userRepository.findByUserNameContainingIgnoreCaseOrFullNameContainingIgnoreCaseAndStatusNot(searchValue, searchValue,0, pageable);
        } else {
            users = userRepository.findByStatusNot(0, pageable);
        }
        List<UsersEntity> newsEntities = users.getContent();
        List<UserDTO> result = new ArrayList<>();
        for (UsersEntity userEntity : newsEntities) {
            UserDTO userDTO = userConverter.convertToDto(userEntity);
            userDTO.setRoleName(userEntity.getRoles().get(0).getName());
            result.add(userDTO);
        }
        return result;
    }

    @Override
    public int getTotalItems(String searchValue) {
        int totalItem = 0;
        if (StringUtils.isNotBlank(searchValue)) {
            totalItem =
                    (int) userRepository.countByUserNameContainingIgnoreCaseOrFullNameContainingIgnoreCaseAndStatusNot(searchValue, searchValue, 0);
        } else {
            totalItem = (int) userRepository.countByStatusNot(0);
        }
        return totalItem;
    }

    @Override
    public UserDTO findOneByUserName(String userName) {
        UsersEntity userEntity = userRepository.findByUserName(userName);
        UserDTO userDTO = userConverter.convertToDto(userEntity);
        return userDTO;
    }

    @Override
    public UserDTO findUserById(long id) {
        Optional<UsersEntity> entity = userRepository.findById(id);
        if (entity.isPresent()) {
            List<RolesEntity> roles = entity.get().getRoles();
            UserDTO dto = userConverter.convertToDto(entity.get());
            roles.forEach(item -> {
                dto.setRoleCode(item.getCode());
            });
            return dto;
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public UserDTO insert(UserDTO newUser) {
        RolesEntity role = roleRepository.findByCode(newUser.getRoleCode());
        UsersEntity userEntity = userConverter.convertToEntity(newUser);
        userEntity.setRoles(Stream.of(role).collect(Collectors.toList()));
        userEntity.setPassword(passwordEncoder.encode(SystemConstant.PASSWORD_DEFAULT));
        return userConverter.convertToDto(userRepository.save(userEntity));
    }

    @Override
    @Transactional
    public UserDTO update(Long id, UserDTO updateUser) {
        Optional<UsersEntity> oldUser = userRepository.findById(id);
        UsersEntity userEntity = userConverter.convertToEntity(updateUser);
        if (oldUser.isPresent()) {
            userEntity.setUserName(oldUser.get().getUserName());
            userEntity.setPassword(oldUser.get().getPassword());
            return userConverter.convertToDto(userRepository.save(userEntity));
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public void updatePassword(long id, PasswordDTO passwordDTO) throws MyException {
        Optional<UsersEntity> user = userRepository.findById(id);
        if (user.isPresent()) {
            if (passwordEncoder.matches(passwordDTO.getOldPassword(),
                    user.get().getPassword())
                    && passwordDTO.getNewPassword().equals(passwordDTO.getConfirmPassword())) {
                user.get().setPassword(passwordEncoder.encode(passwordDTO.getNewPassword()));
                userRepository.save(user.get());
            } else {
                throw new MyException(SystemConstant.CHANGE_PASSWORD_FAIL);
            }
        }
    }

    @Override
    @Transactional
    public UserDTO resetPassword(long id) {
        Optional<UsersEntity> userEntity = userRepository.findById(id);
        if (userEntity.isPresent()) {
            userEntity.get().setPassword(passwordEncoder.encode(SystemConstant.PASSWORD_DEFAULT));
            return userConverter.convertToDto(userRepository.save(userEntity.get()));
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public UserDTO updateProfileOfUser(String username, UserDTO updateUser) {
        UsersEntity oldUser = userRepository.findByUserName(username);
        oldUser.setUserName(updateUser.getUserName());
        return userConverter.convertToDto(userRepository.save(oldUser));
    }

    @Override
    @Transactional
    public void delete(long[] ids) {
        for (Long item : ids) {
            Optional<UsersEntity> userEntity = userRepository.findById(item);
            if (userEntity.isPresent()) {
                userRepository.save(userEntity.get());
            }
        }
    }
}
