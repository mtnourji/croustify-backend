package com.croustify.backend.models;


import jakarta.persistence.*;


@Entity
@Table(name = "customer")
public class Customer extends User{





    //Relation ManyToOne avec UserCredential car un customer appartient à un UserCredential
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserCredential userCredential;

}
