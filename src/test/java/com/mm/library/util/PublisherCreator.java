package com.mm.library.util;

import com.mm.library.domain.publisher.Publisher;
import com.mm.library.domain.publisher.PublisherBody;

public class PublisherCreator {
    public static Publisher createPublisherToBeSaved() {
        return Publisher.builder().name("Editora Abril").country("Brasil").build();
    }

    public static PublisherBody createPublisherBody() {
        return new PublisherBody("Editora Abril", "Brasil");
    }
}
