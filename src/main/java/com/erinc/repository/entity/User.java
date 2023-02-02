package com.erinc.repository.entity;
/*
    name
    surname
    username-->benzersiz olsun null olmasın
    password--- null olmasın  uzunlugu 32 olsun
 */

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

/*

    adresleri typlerina gore mapleyelim

 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Embedded
    private Name name;
    @Column(unique = true,nullable = false)
    private String username;
    @Column(nullable = false,length = 32)
    private String password;
    @Enumerated(EnumType.STRING)
    private EGender gender;
    @ElementCollection
    List<String> interests;
    @ElementCollection()
    private Map<AddressType,Address> address;
    private int postCount;
}
