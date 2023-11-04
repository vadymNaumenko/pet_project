package de.pet_project.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
@Entity
@Data
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String contentType;
    private Long size;
    @Lob
    private byte[] data;

    @OneToOne(fetch = FetchType.LAZY)
    private User user;
    private LocalDateTime uploadDate;

    public Image(String name, String contentType, byte[] data) {
        this.name = name;
        this.contentType = contentType;
        this.data = data;
        this.uploadDate = LocalDateTime.now();
    }

    public Image() {
        uploadDate = LocalDateTime.now();
    }
}
