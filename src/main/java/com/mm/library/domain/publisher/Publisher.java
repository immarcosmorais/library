package com.mm.library.domain.publisher;

import com.mm.library.common.AbstractEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "publishers")
@Entity(name = "Publisher")
public class Publisher extends AbstractEntity {
    private String name;
    private String country;

    public Publisher(PublisherBody body) {
        setDeleted(false);
        this.name = body.name();
        this.country = body.country();
    }

    public void update(PublisherBody body) {
        this.name = body.name();
        this.country = body.country();
    }
}
