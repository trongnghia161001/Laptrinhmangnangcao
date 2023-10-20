package com.laptrinhmangnangcao.ltm.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "documents")
public class DocumentsEntity extends BaseEntity {

    @Column(name = "documentname")
    private String documentName;

    @Column(name = "url")
    private String url;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private UsersEntity user;

    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL)
    private List<ErrorsEntity> error = new ArrayList<>();

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public UsersEntity getUser() {
        return user;
    }

    public void setUser(UsersEntity user) {
        this.user = user;
    }

    public List<ErrorsEntity> getError() {
        return error;
    }

    public void setError(List<ErrorsEntity> error) {
        this.error = error;
    }
}
