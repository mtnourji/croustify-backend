package com.croustify.backend.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "location_owner")
public class LocationOwner extends User {

    @Column(name = "bank_number")
    private String bankNumber;


    //Relation OneToMany avec LocationSite car un proprio peut avoir plusieurs locationSite
    @OneToMany(mappedBy ="locationOwner", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<LocationSite> locationSites = new HashSet<>();

    //Relation ManyToOne avec UserCredential car un locationOwner appartient à un UserCredential
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserCredential userCredential;





}
