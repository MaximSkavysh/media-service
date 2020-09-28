package com.lobster.ecommerce.media.model;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.GenericGenerator;

import javax.validation.constraints.*;

import javax.persistence.*;

@Entity
@Table(name = "files")
@Data
@NoArgsConstructor
public class FileDb {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "native")
    @GenericGenerator(name = "native", strategy = "increment")
    @Column(name = "id")
    private Long id;

    @Column(name = "product_id")
    @NotNull
    private Long product_id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "url")
    private String url;

    public FileDb(Long product_id, String fileName, String contentType, String url) {
        this.name = fileName;
        this.type = contentType;
        this.url = url;
        this.product_id = product_id;
    }
}
