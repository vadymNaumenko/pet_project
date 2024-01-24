package de.pet_project.domain.image;

import de.pet_project.domain.enums.State;
import de.pet_project.domain.promotion.Promotion;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "images_promotions")
public class ImagePromotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_promotion_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "promotion_id")
    private Promotion promotion;

    @ManyToOne
    @JoinColumn(name = "image_id")
    private Image image;

    private boolean isMain;
    private boolean isDeleted;
}
