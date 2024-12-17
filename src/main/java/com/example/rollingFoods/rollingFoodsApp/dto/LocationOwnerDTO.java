package com.example.rollingFoods.rollingFoodsApp.dto;
import com.example.rollingFoods.rollingFoodsApp.models.embedded.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LocationOwnerDTO extends UserDTO{

    private String bankNumber;
}
