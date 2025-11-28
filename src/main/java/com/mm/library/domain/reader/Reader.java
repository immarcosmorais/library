package com.mm.library.domain.reader;

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
@Table(name = "readers")
@Entity(name = "Reader")
public class Reader extends AbstractEntity {
    private String name;
    private String email;
    private String phone;

    public Reader(ReaderBody body) {
        setDeleted(false);
        this.name = body.name();
        this.email = body.email();
        this.phone = body.phone();
    }

    public void update(ReaderBody body) {
        this.name = body.name();
        this.email = body.email();
        this.phone = body.phone();
    }
}
