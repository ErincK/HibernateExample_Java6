package com.erinc.repository.entity;
/*
    sokak ismi
    şehir ismi
    ulke ismi

 */

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class Address {
    private String street;
    private String city;
    private String country;
}
