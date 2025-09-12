package com.example.library.app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

public class UploadBookRequest {

    @NotBlank(message = "title is required")
    private String title;

    @NotBlank(message = "author is required")
    private String author;

    @Size(max = 20, message = "ISBN length is 20")
    private String isbn;

    @NotBlank(message = "categories is required")
    private String categories;

    @NotBlank(message = "summary is required")
    private String summary;

    @NotNull
    private MultipartFile cover;

    public UploadBookRequest() {
    }

    public UploadBookRequest(String title, String author, String isbn, String categories, String summary, MultipartFile cover) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.categories = categories;
        this.summary = summary;
        this.cover = cover;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setCover(MultipartFile cover) {
        this.cover = cover;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getCategories() {
        return categories;
    }

    public String getSummary() {
        return summary;
    }

    public MultipartFile getCover() {
        return cover;
    }
}
