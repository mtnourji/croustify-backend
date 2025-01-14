package com.croustify.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleDTO {
    private String name;

    public RoleDTO() {
    }

    public RoleDTO(String name) {
        this.name = name;
    }

}
