package com.laptrinhmangnangcao.ltm.dto.response;

import java.util.List;

public class CorrectionItem {

    private int type;
    private String original;
    private String corrected;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getCorrected() {
        return corrected;
    }

    public void setCorrected(String corrected) {
        this.corrected = corrected;
    }
}
