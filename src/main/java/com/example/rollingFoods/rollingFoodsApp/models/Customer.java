package com.example.rollingFoods.rollingFoodsApp.models;


import com.example.rollingFoods.rollingFoodsApp.models.embedded.Address;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Entity
@Table(name = "customer")
public class Customer extends User{





    //Relation ManyToOne avec UserCredential car un customer appartient à un UserCredential
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserCredential userCredential;

}
