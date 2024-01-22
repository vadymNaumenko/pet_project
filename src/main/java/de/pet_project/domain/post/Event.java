package de.pet_project.domain.post;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    private String title;
    @Column(name = "image_url")
    private String imageUrl;
    private String text;
    @Column(name = "date")
    private LocalDate date;
    @Column(name = "is_deleted")
    private Boolean isDeleted;
    @OneToMany(mappedBy = "postId")
    List<CommitForPost> commits;

}
