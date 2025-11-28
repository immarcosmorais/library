package com.mm.library.domain.author;

import com.mm.library.common.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "authors")
@Entity(name = "Author")
public class Author extends AbstractEntity {

    private String name;
    @Column(name = "birth_date", columnDefinition = "DATETIME")
    private Date birthDate;

    public Author(AuthorBody body) {
        this.setDeleted(false);
        this.name = body.name();
        this.birthDate = body.birthDate();
    }

    public void update(AuthorBody body) {
        this.name = body.name();
        this.birthDate = body.birthDate();
    }
}
