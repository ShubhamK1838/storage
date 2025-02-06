package com.newgen.storage.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
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
    @Column(name = "saved_date")
    private Date savedDate;
    @Column(name = "userid")
    private String userid;
    @Column(name = "file_type")
    private String fileType;
    @Transient
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
   transient private MultipartFile file;
    public boolean visible;

    public StorageFile() {

    }

    public void setFile(MultipartFile file) {
        setSavedDate(new Date());
        setName(file.getOriginalFilename());
        var ctype = file.getContentType();
        setFileType(ctype.substring(ctype.indexOf("/") + 1, ctype.length()));
        setSize(String.valueOf(file.getSize()));
        this.file = file;
    }
}
