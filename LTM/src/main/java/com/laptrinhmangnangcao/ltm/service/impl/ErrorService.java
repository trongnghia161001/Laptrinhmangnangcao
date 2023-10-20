package com.laptrinhmangnangcao.ltm.service.impl;


import com.laptrinhmangnangcao.ltm.dto.response.CorrectionItem;
import com.laptrinhmangnangcao.ltm.entity.DocumentsEntity;
import com.laptrinhmangnangcao.ltm.entity.ErrorsEntity;
import com.laptrinhmangnangcao.ltm.entity.UsersEntity;
import com.laptrinhmangnangcao.ltm.repository.DocumentsRepository;
import com.laptrinhmangnangcao.ltm.repository.ErrorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
public class ErrorService {

    @Autowired
    private ErrorsRepository errorsRepository;

    @Autowired
    private DocumentsRepository documentsRepository;

    @Transactional
    public void insertError(Long id, CorrectionItem correctionItem) {
        ErrorsEntity errorsEntity = new ErrorsEntity();
        Optional<DocumentsEntity> documents = documentsRepository.findById(id);
        if (documents.isPresent()) {
            errorsEntity.setDocument(documents.get());
        }
        errorsEntity.setErrorContent(correctionItem.getOriginal());
        errorsEntity.setEditedContent(correctionItem.getCorrected());
        errorsRepository.save(errorsEntity);
    }
}
