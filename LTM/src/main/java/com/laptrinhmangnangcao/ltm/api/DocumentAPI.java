package com.laptrinhmangnangcao.ltm.api;


import com.laptrinhmangnangcao.ltm.dto.DocumentDTO;
import com.laptrinhmangnangcao.ltm.dto.response.ErrorsResponse;
import com.laptrinhmangnangcao.ltm.service.impl.DocumentService;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/api/document")
public class DocumentAPI {

    @Autowired
    private DocumentService documentService;

    private ExecutorService executor = Executors.newCachedThreadPool();

    @CacheEvict(value = "documentCache", allEntries = true)
    @PostMapping("/{id}/upload")
    public CompletableFuture<DocumentDTO> uploadFile(@PathVariable("id") long id,
                                                     @RequestParam("file") MultipartFile file) {
        CompletableFuture<DocumentDTO> future = CompletableFuture.supplyAsync(() -> {
            try (InputStream inputStream = file.getInputStream()) {
                XWPFDocument doc = new XWPFDocument(inputStream);
                StringBuilder content = new StringBuilder();
                List<XWPFParagraph> paragraphs = doc.getParagraphs();
                for (XWPFParagraph paragraph : paragraphs) {
                    content.append(paragraph.getText());
                    content.append("\n");
                }
                DocumentDTO documentDTO = documentService.insertDocument(id, content.toString());
                return documentDTO;
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Lỗi khi xử lý file.", e);
            }
        }, executor);

        return future.whenComplete((result, exception) -> {
            if (exception != null) { // neu co loi
                exception.printStackTrace();
            } else {
                clearDocumentCache();
            }
        });
    }

    @CacheEvict(value = "documentCache", allEntries = true)
    public void clearDocumentCache() {

    }

    @GetMapping("/{id}/error-list")
    public List<ErrorsResponse> getErrors(@PathVariable("id") long id) {
        List<ErrorsResponse> responses = documentService.getErrors(id);
        return responses;
    }
}
