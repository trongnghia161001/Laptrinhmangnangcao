package com.laptrinhmangnangcao.ltm.converter;

import com.laptrinhmangnangcao.ltm.dto.UserDTO;
import com.laptrinhmangnangcao.ltm.entity.UsersEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    @Autowired
    private ModelMapper modelMapper;

    public UserDTO convertToDto(UsersEntity entity) {
        UserDTO result = modelMapper.map(entity, UserDTO.class);
        return result;
    }

    public UsersEntity convertToEntity(UserDTO dto) {
        UsersEntity result = modelMapper.map(dto, UsersEntity.class);
        return result;
    }
}
