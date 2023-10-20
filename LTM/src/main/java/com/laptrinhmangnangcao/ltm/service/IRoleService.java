package com.laptrinhmangnangcao.ltm.service;

import com.laptrinhmangnangcao.ltm.dto.RoleDTO;

import java.util.List;
import java.util.Map;

public interface IRoleService {
    List<RoleDTO> findAll();
    Map<String,String> getRoles();
}
