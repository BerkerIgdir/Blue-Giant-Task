package com.mavidev.task.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;
import java.util.UUID;


@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
//As an implementation detail, I do not want to have two persons with the same name and surname.
//Creates a composed key.
@Table(
        uniqueConstraints=
        @UniqueConstraint(columnNames={"name", "surname"})
)
public class Person {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
    Long id;


    @Column(length = 20,columnDefinition = "varchar(20)",nullable = false)
    String name;

    @Column(length = 20,columnDefinition = "varchar(20)",nullable = false)
    String surname;

    @Column(length = 40,columnDefinition = "varchar(40)",nullable = false,unique = true)
    String email;

    @CreationTimestamp
    @Column(updatable = false)
    Timestamp creationDate;

    @UpdateTimestamp
    @Column(updatable = false)
    Timestamp lastModifiedDate;

}
