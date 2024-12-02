package com.alura.library.main;

import com.alura.library.dto.BookDTO;
import com.alura.library.dto.DataDTO;
import com.alura.library.model.Author;
import com.alura.library.model.Book;
import com.alura.library.repository.BookRepository;
import com.alura.library.service.AuthorService;
import com.alura.library.service.BookService;
import com.alura.library.service.ConvertData;
import com.alura.library.service.UseAPI;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;


public class Main {
    private Scanner scanner = new Scanner(System.in);
    private UseAPI useAPI = new UseAPI();
    private static final String BASE_URL = "https://gutendex.com/books/";
    private ConvertData converter = new ConvertData();
    private List<Book> books;
    private List<Author> authors;

    @Autowired
    AuthorService authorService;
    @Autowired
    BookService bookService;


    public void showMenu() {
        var option = -1;

        while ( option != 0 ) {
            var menu = """
                    Elija la opción a través de su número:
                    1 - Buscar libro por título
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores registradps vivos en un determinado año
                    5 - Listar libros registrados por idioma
                    0 - Salir
                    
                    """;

            System.out.println(menu);
            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                   searchBookByTitle();
                   break;
                case 2:
                    listRegisteredBooks();
                    break;
                case 3:
                    listRegisteredAuthors();
                    break;
                case 4:
                    listRegisteredAuthorsAliveInAGivenYear();
                    break;
                case 5:
                    listRegisteredBooksByLanguage();
                    break;
                case 0:
                    System.out.println("Saliendo del aplicativo...");
                    break;
                default:
                    System.out.println("Opción inválida.");

            }
        }
    }


//    private BookDTO getBookData() {
//        System.out.println("Escriba el título del libro a buscar:");
//        var bookTitle = scanner.nextLine();
//        var json = useAPI.getData(BASE_URL + bookTitle.replace(" ", "+"));
//        System.out.println(json);
//        BookDTO data = converter.getData(json, BookDTO.class);
//        return data;
//    }

    private void searchBookByTitle() {

        System.out.println("\nIngresa nombre o parte del nombre a buscar: ");
        var search = scanner.nextLine();
        var json = useAPI.getData(BASE_URL + search.replace(" ", "+"));
        System.out.println(json);

        var data = converter.getData(json, DataDTO.class);

        Optional<BookDTO> datosLibrosOptional = data.books().stream()
                .filter(book -> book.title().toLowerCase().contains(search.toLowerCase()))
                .findFirst();

        if (datosLibrosOptional.isPresent()) {
            BookDTO booksData = datosLibrosOptional.get();
            System.out.println("\nLibro encontrado: " + booksData.title());

            // Obtener el nombre del autor
            String nombreAutor = booksData.authors() != null ? booksData.authors().get(0).name() : "Desconocido";
            System.out.println("Autor: " + nombreAutor);


            System.out.println("\n¿Deseas guardar este libro y autor? 1.Sí  2.No");
            int opcion = scanner.nextInt();
            scanner.nextLine();
            if (opcion == 1) {
                Author autor = booksData.authors() != null ? new Author(booksData.authors()) : null;
                Optional<Author> existenciaAutores = authorService.checkExistenceOfTheAuthor(nombreAutor);

                if (existenciaAutores.isPresent()) {
                    System.out.println("\n*** Autor ya existe en la base de datos. ***");
                    autor = existenciaAutores.get(); // Usar el autor existente
                }
                try {
                    authorService.saveAuthor(autor);
                    Optional<Book> existenciaLibro = bookService.checkExistenceOfTheBook(booksData.title());

                    if (!existenciaLibro.isPresent()) {
                        Book libro = new Book(booksData);
                        libro.setAuthors(autor);
                        bookService.saveBook(libro);
                    } else {
                        System.out.println("\n*** Libro ya existe en la base de datos. ***");
                    }
                } catch (Exception e) {
                    System.out.println("\n*** Error al intentar guardar el libro y/o autor: " + e.getMessage() + " ***");
                }

            } else {
                System.out.println("\n*** Entendido!. ***");
            }

        } else {
            System.out.println("\n*** Libro no encontrado. ***");
        }
    }

    private void listRegisteredBooks() {
        books = bookService.showBooks();
        books.stream().sorted(Comparator.comparing(Book::getId))
                .forEach(System.out::println);
    }

    private void listRegisteredAuthors() {
        authors = authorService.showAuthors();
        authors.forEach(System.out::println);
    }

    private void listRegisteredAuthorsAliveInAGivenYear() {
        System.out.println("\nIngresa el año para buscar autores: ");
        Integer year = scanner.nextInt();
        authors = authorService.searchAuthorsAliveInAGivenYear(year);
        authors.forEach(System.out::println);
    }

    private void listRegisteredBooksByLanguage() {
        System.out.println("""
                Ingresa el idioma a para buscar los libros: 
                1. es - español
                2. en - ingles
                3. fr - francés
                4. pt - portugués
                
                """);
        var option = scanner.nextInt();
        String language = "";
        switch (option) {
            case 1:
                language = "SPANISH";
                break;
            case 2:
                language = "ENGLISH";
                break;
            case 3:
                language = "FRENCH";
                break;
            case 4:
                language = "PORTUGUESE";
            default:
                System.out.println("Eleccion incorrecta de idioma\n");
        }
        books = bookService.showByLanguage (language);
        if (books.isEmpty()) {
            System.out.println("\nNo hay libros de ese idioma registrado.\n");
        } else {
            books.forEach(System.out::println);
        }
    }
}