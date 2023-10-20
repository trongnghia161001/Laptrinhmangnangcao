package com.laptrinhmangnangcao.ltm.service.impl;

import com.laptrinhmangnangcao.ltm.converter.RoleConverter;
import com.laptrinhmangnangcao.ltm.dto.RoleDTO;
import com.laptrinhmangnangcao.ltm.entity.RolesEntity;
import com.laptrinhmangnangcao.ltm.repository.RoleRepository;
import com.laptrinhmangnangcao.ltm.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RoleService implements IRoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleConverter roleConverter;

    public List<RoleDTO> findAll() {
        List<RolesEntity> roleEntities = roleRepository.findAll();
        List<RoleDTO> list = new ArrayList<>();
        roleEntities.forEach(item -> {
            RoleDTO roleDTO = roleConverter.convertToDto(item);
            list.add(roleDTO);
        });
        return list;
    }

    @Override
    public Map<String, String> getRoles() {
        Map<String,String> roleTerm = new HashMap<>();
        List<RolesEntity> roleEntity = roleRepository.findAll();
        roleEntity.forEach(entity ->{
            RoleDTO roleDTO = roleConverter.convertToDto(entity);
            roleTerm.put(roleDTO.getCode(), roleDTO.getName());
        });
        return roleTerm;
    }
}
