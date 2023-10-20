package com.laptrinhmangnangcao.ltm.converter;

import com.laptrinhmangnangcao.ltm.dto.DocumentDTO;
import com.laptrinhmangnangcao.ltm.entity.DocumentsEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DocumentConverter {

    @Autowired
    private ModelMapper modelMapper;

    public DocumentDTO convertToDto(DocumentsEntity entity) {
        DocumentDTO result = modelMapper.map(entity, DocumentDTO.class);
        return result;
    }
}
