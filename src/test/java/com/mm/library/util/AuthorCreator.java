package com.mm.library.util;

import com.mm.library.domain.author.Author;
import com.mm.library.domain.author.AuthorBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AuthorCreator {

    private static List<String> authorNames = List.of(
            "Machado de Assis",
            "Clarice Lispector",
            "Jorge Amado",
            "Cecília Meireles",
            "Carlos Drummond de Andrade",
            "Monteiro Lobato",
            "Graciliano Ramos",
            "Guimarães Rosa",
            "Rachel de Queiroz",
            "Lygia Fagundes Telles",
            "Paulo Coelho",
            "Ariano Suassuna",
            "Nélida Piñon",
            "Rubem Fonseca",
            "Lima Barreto",
            "José de Alencar",
            "Manuel Bandeira",
            "João Cabral de Melo Neto",
            "Cora Coralina",
            "Adélia Prado",
            "Fernando Pessoa",
            "Luís de Camões",
            "Eça de Queirós",
            "José Saramago",
            "António Lobo Antunes",
            "Sophia de Mello Breyner Andresen",
            "Miguel Torga",
            "Camilo Castelo Branco",
            "William Shakespeare",
            "Jane Austen",
            "Charles Dickens",
            "Virginia Woolf",
            "George Orwell",
            "J.K. Rowling",
            "Agatha Christie",
            "Ernest Hemingway",
            "F. Scott Fitzgerald",
            "Mark Twain",
            "Edgar Allan Poe",
            "Emily Brontë",
            "Charlotte Brontë",
            "Leo Tolstoy",
            "Fyodor Dostoevsky",
            "Gabriel García Márquez",
            "Isabel Allende",
            "Mario Vargas Llosa",
            "Julio Cortázar",
            "Jorge Luis Borges",
            "Pablo Neruda",
            "Franz Kafka",
            "Albert Camus",
            "Victor Hugo",
            "Marcel Proust"
    );

    public static  List<Author> createAuthorListToBeSaved(int quantity) {
        List<Author> authors = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            authors.add(Author.builder().name(authorNames.get(i)).birthDate(new Date()).build());
        }
        return  authors;
    }

    public static Author createAuthorToBeSaved() {
        int index = (int) (Math.random() * authorNames.size());
        return Author.builder().name(authorNames.get(index)).birthDate(new Date()).build();
    }

    public static AuthorBody createAuthorBody() {
        Author author = createAuthorToBeSaved();
        return new AuthorBody(author.getName(), author.getBirthDate());
    }
}
