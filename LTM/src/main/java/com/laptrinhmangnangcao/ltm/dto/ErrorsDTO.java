package com.laptrinhmangnangcao.ltm.dto;

public class ErrorsDTO extends AbstractDTO {

    private String errorContent;
    private String location;
    private String editedContent;
    private String status;
    private String checkDay;
    private String documentName;

    public String getErrorContent() {
        return errorContent;
    }

    public void setErrorContent(String errorContent) {
        this.errorContent = errorContent;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEditedContent() {
        return editedContent;
    }

    public void setEditedContent(String editedContent) {
        this.editedContent = editedContent;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCheckDay() {
        return checkDay;
    }

    public void setCheckDay(String checkDay) {
        this.checkDay = checkDay;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }
}
