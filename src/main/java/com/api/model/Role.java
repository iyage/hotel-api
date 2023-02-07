package com.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String roleName;
    @JsonIgnore
    @ManyToMany(mappedBy = "roles",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Set<UserDao>users = new HashSet<>();
}
