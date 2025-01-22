package com.croustify.backend.specifications;

import com.croustify.backend.bean.UserRole;
import com.croustify.backend.models.Customer;
import com.croustify.backend.models.FoodTruckOwner;
import com.croustify.backend.models.LocationOwner;
import com.croustify.backend.models.User;
import org.springframework.data.jpa.domain.Specification;


public class UserSpecifications {

    public static Specification<User> hasType(UserRole role) {
        return (root, query, criteriaBuilder) -> {
            if(role == null){
                return null;
            }
            Class<? extends User> userClass = switch (role){
                case CUSTOMER -> Customer.class;
                case FOODTRUCK_OWNER -> FoodTruckOwner.class;
                case LAND_OWNER -> LocationOwner.class;
            };
            return criteriaBuilder.equal(root.type(), userClass);
        };
    }

    public static Specification<User> hasEmail(String email) {
        return (root, query, criteriaBuilder) -> email == null ? null : criteriaBuilder.equal(root.get("userCredential").get("email"), email);
    }

    public static Specification<User> hasLastName(String lastName) {
        return (root, query, criteriaBuilder) -> lastName == null ? null: criteriaBuilder.like(root.get("lastname"), "%" + lastName + "%");
    }

    public static Specification<User> hasFirstName(String firstName) {
        return (root, query, criteriaBuilder) -> firstName == null ? null : criteriaBuilder.like(root.get("firstname"), "%" + firstName + "%");
    }

    public static Specification<User> isEnabled(Boolean enabled) {
        return (root, query, criteriaBuilder) -> enabled == null ? null : criteriaBuilder.equal(root.get("userCredential").get("enabled"), enabled);
    }
}

