package com.laptrinhmangnangcao.ltm.api;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.laptrinhmangnangcao.ltm.dto.response.CorrectionItem;
import com.laptrinhmangnangcao.ltm.dto.response.CorrectionResponse;
import com.laptrinhmangnangcao.ltm.service.impl.ErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/error")
public class ErrorAPI {

    @Autowired
    private ErrorService errorService;

    @Cacheable(value = "aiCache", key = "#requestMap.get('text')")
    @PostMapping("/{id}/ai")
    public CompletableFuture<String> ai(@PathVariable("id") long id,
                                        @RequestBody Map<String, Object> requestMap) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                String text = (String) requestMap.get("text");
                String pythonApiUrl = "http://localhost:8080/spelling";
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);

                RestTemplate restTemplate = new RestTemplate();
                HttpEntity<String> requestEntity = new HttpEntity<>(text, headers);

                ResponseEntity<String> responseEntity = restTemplate.exchange(
                        pythonApiUrl,
                        HttpMethod.POST,
                        requestEntity,
                        String.class
                );

                String pythonResponse = responseEntity.getBody();

                ObjectMapper objectMapper = new ObjectMapper();
                CorrectionResponse correctionResponse = objectMapper.readValue(pythonResponse, CorrectionResponse.class);

                List<CorrectionItem> correctionItems = new ArrayList<>();
                List<List<Object>> correctionList = correctionResponse.getCorrection();

                for (List<Object> correction : correctionList) {
                    if (correction.size() >= 2) {
                        int type = (int) correction.get(0);
                        String original = (String) correction.get(1);
                        String corrected = correction.size() >= 3 ? (String) correction.get(2) : null;

                        CorrectionItem item = new CorrectionItem();
                        item.setType(type);
                        item.setOriginal(original);
                        item.setCorrected(corrected);

                        correctionItems.add(item);
                    }
                }

                for (CorrectionItem item : correctionItems) {
                    if (item.getType() == 1) {
                        errorService.insertError(id, item);
                    }
                }

                return pythonResponse;
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Lỗi khi xử lý dữ liệu.", e);
            }
        });
    }
}
