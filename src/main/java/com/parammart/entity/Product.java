package com.parammart.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;


@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 150)
    private String name;

    @Column(nullable = false,unique = true,length = 50)
    private String sku;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false,precision = 12,scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer stock;

    @Builder.Default
    private Boolean active=true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="brand_id")
    private Brand brand;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    void prePersist(){
        createdAt=LocalDateTime.now();
        updatedAt=LocalDateTime.now();
    }

    @PreUpdate
    void preUpdate(){
        updatedAt=LocalDateTime.now();
    }
    
    @OneToMany(
    	    mappedBy = "product",
    	    cascade = CascadeType.ALL,
    	    orphanRemoval = true
    	)
    	@Builder.Default
    	private List<ProductMedia> media = new ArrayList<>();

}