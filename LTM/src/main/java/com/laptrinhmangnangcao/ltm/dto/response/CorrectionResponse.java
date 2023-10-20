package com.laptrinhmangnangcao.ltm.dto.response;

import java.util.List;

public class CorrectionResponse {

    private List<List<Object>> correction;

    public List<List<Object>> getCorrection() {
        return correction;
    }

    public void setCorrection(List<List<Object>> correction) {
        this.correction = correction;
    }
}
