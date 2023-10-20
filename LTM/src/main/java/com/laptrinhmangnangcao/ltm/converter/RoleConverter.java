package com.laptrinhmangnangcao.ltm.converter;

import com.laptrinhmangnangcao.ltm.dto.RoleDTO;
import com.laptrinhmangnangcao.ltm.entity.RolesEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoleConverter {

    @Autowired
    private ModelMapper modelMapper;

    public RoleDTO convertToDto(RolesEntity entity) {
        RoleDTO result = modelMapper.map(entity, RoleDTO.class);
        return result;
    }

    public RolesEntity convertToEntity(RoleDTO dto) {
        RolesEntity result = modelMapper.map(dto, RolesEntity.class);
        return result;
    }
}
