package com.laptrinhmangnangcao.ltm.entity;

import javax.persistence.*;

@Entity
@Table(name = "errors")
public class ErrorsEntity extends BaseEntity {

    @Column(name = "errorcontent")
    private String errorContent;

    @Column(name = "location")
    private String location;

    @Column(name = "editedcontent")
    private String editedContent;

    @Column(name = "status")
    private String status;

    @Column(name = "checkday")
    private String checkDay;

    @ManyToOne
    @JoinColumn(name = "id_document")
    private DocumentsEntity document;

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

    public DocumentsEntity getDocument() {
        return document;
    }

    public void setDocument(DocumentsEntity document) {
        this.document = document;
    }
}
