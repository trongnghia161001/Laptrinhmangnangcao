package com.laptrinhmangnangcao.ltm.service.impl;

import com.laptrinhmangnangcao.ltm.converter.DocumentConverter;
import com.laptrinhmangnangcao.ltm.dto.DocumentDTO;
import com.laptrinhmangnangcao.ltm.dto.response.ErrorsResponse;
import com.laptrinhmangnangcao.ltm.entity.DocumentsEntity;
import com.laptrinhmangnangcao.ltm.entity.ErrorsEntity;
import com.laptrinhmangnangcao.ltm.entity.UsersEntity;
import com.laptrinhmangnangcao.ltm.repository.DocumentsRepository;
import com.laptrinhmangnangcao.ltm.repository.ErrorsRepository;
import com.laptrinhmangnangcao.ltm.repository.UserRepository;
import com.laptrinhmangnangcao.ltm.service.IDocxReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
public class DocumentService implements IDocxReaderService {

    @Autowired
    private DocumentsRepository documentsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ErrorsRepository errorsRepository;

    @Autowired
    private DocumentConverter documentConverter;

    @Transactional
    public DocumentDTO insertDocument(Long id, String newString) {
        DocumentsEntity documentsEntity = new DocumentsEntity();
        Optional<UsersEntity> userEntity = userRepository.findById(id);
        if (userEntity.isPresent()) {
            documentsEntity.setUser(userEntity.get());
        }
        documentsEntity.setDocumentName(newString);
        return documentConverter.convertToDto(documentsRepository.save(documentsEntity));
    }

//    public List<ErrorsResponse> getErrors(Long userId) {
//        List<ErrorsResponse> responses = new ArrayList<>();
//        Map<Long, ErrorsResponse> documentMap = new HashMap<>();
//
//        List<DocumentsEntity> documentsEntities = documentsRepository.findByUserId(userId);
//
//        documentsEntities.forEach(document -> {
//            Long documentId = document.getId();
//            List<ErrorsEntity> errorsEntities = errorsRepository.findByDocumentId(documentId);
//
//            errorsEntities.forEach(entity -> {
//                // Check if we have an ErrorsResponse for this documentId
//                if (!documentMap.containsKey(documentId)) {
//                    ErrorsResponse errorsResponse = new ErrorsResponse();
//                    errorsResponse.setDocumentName(document.getDocumentName());
//                    documentMap.put(documentId, errorsResponse);
//                }
//
//                // Append Content and Edited Content with commas
//                ErrorsResponse errorsResponse = documentMap.get(documentId);
//                errorsResponse.setErrorContent(errorsResponse.getErrorContent() == null
//                        ? entity.getErrorContent()
//                        : errorsResponse.getErrorContent() + ", " + entity.getErrorContent());
//                errorsResponse.setEditedContent(errorsResponse.getEditedContent() == null
//                        ? entity.getEditedContent()
//                        : errorsResponse.getEditedContent() + ", " + entity.getEditedContent());
//
//                // Set Created Date and Modified Date once
//                if (errorsResponse.getCreatedDate() == null) {
//                    errorsResponse.setCreatedDate(entity.getCreatedDate());
//                }
//                if (errorsResponse.getModifiedDate() == null) {
//                    errorsResponse.setModifiedDate(entity.getModifiedDate());
//                }
//            });
//        });
//
//        // Add all unique responses to the final list
//        responses.addAll(documentMap.values());
//
//        return responses;
//    }

    public List<ErrorsResponse> getErrors(Long userId) {
        List<ErrorsResponse> responses = new ArrayList<>();
        Map<Long, ErrorsResponse> documentMap = new HashMap<>();

        List<DocumentsEntity> documentsEntities = documentsRepository.findByUserId(userId);

        documentsEntities.forEach(document -> {
            Long documentId = document.getId();
            List<ErrorsEntity> errorsEntities = errorsRepository.findByDocumentId(documentId);

            for (int index = 0; index < errorsEntities.size(); index++) {
                ErrorsEntity entity = errorsEntities.get(index);

                // Check if we have an ErrorsResponse for this documentId
                if (!documentMap.containsKey(documentId)) {
                    ErrorsResponse errorsResponse = new ErrorsResponse();
                    errorsResponse.setDocumentName(document.getDocumentName());
                    documentMap.put(documentId, errorsResponse);
                }

                // Append Content and Edited Content with commas
                ErrorsResponse errorsResponse = documentMap.get(documentId);
                if (errorsResponse.getErrorContent() == null) {
                    // If ErrorContent is null, set it without a comma
                    errorsResponse.setErrorContent(entity.getErrorContent());
                } else {
                    // If ErrorContent is not null, append with a comma
                    errorsResponse.setErrorContent(errorsResponse.getErrorContent() + ", " + entity.getErrorContent());
                }

                if (errorsResponse.getEditedContent() == null) {
                    // If EditedContent is null, set it without a comma
                    errorsResponse.setEditedContent(entity.getEditedContent());
                } else {
                    // If EditedContent is not null, append with a comma
                    errorsResponse.setEditedContent(errorsResponse.getEditedContent() + ", " + entity.getEditedContent());
                }

                // Set Created Date and Modified Date once
                if (errorsResponse.getCreatedDate() == null) {
                    errorsResponse.setCreatedDate(entity.getCreatedDate());
                }
                if (errorsResponse.getModifiedDate() == null) {
                    errorsResponse.setModifiedDate(entity.getModifiedDate());
                }
            }
        });

        // Add all unique responses to the final list
        responses.addAll(documentMap.values());

        return responses;
    }



}
