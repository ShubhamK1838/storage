package com.newgen.storage.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Setter
@Getter
@ToString
@Entity
public class StorageFile {

    @Id
    private String id;
    private String name;
    private String size;
    @Column(name="user-date")
    private Date savedDate;
    @Column(name="userid")
    private String userid;
    @Column(name="fileType")
    private String fileType;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private MultipartFile multipartFile;

    public StorageFile() {

    }

    public void setMultipartFile(MultipartFile multipartFile) {
        setSavedDate(new Date());
        setName(multipartFile.getOriginalFilename());
        var ctype = multipartFile.getContentType();
        setFileType(ctype.substring(ctype.indexOf("/") + 1, ctype.length()));
        setSize(String.valueOf(multipartFile.getSize()));
        this.multipartFile = multipartFile;
    }
}
