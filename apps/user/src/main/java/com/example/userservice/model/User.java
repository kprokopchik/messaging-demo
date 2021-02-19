package com.example.userservice.model;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Data
@Entity
@Table(name = "user")
public class User {

    @Id
    private String id;

    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL)
    List<Quota> quotas;
}
