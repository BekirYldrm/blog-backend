package com.pinsoft.intern.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Data // Getter, Setter, toString, equals ve hashCode metotlarını otomatik olarak oluşturur
@NoArgsConstructor // Parametresiz constructor
@AllArgsConstructor // Tüm alanları alacak şekilde bir constructor oluşturur
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String password;
    private String email;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<Save> savedBlogs;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<Like> likedBlogs;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<Comment> comments;

    @ManyToOne
    @JoinColumn(name = "role_id")
    @JsonBackReference
    private Role role;
}
