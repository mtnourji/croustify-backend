package com.croustify.backend.specifications;

import com.croustify.backend.models.Favorite;
import com.croustify.backend.models.FoodTruck;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class FoodTruckSpecifications {

    public static Specification<FoodTruck> isOpen(Boolean isOpen) {
        return (root, query, cb) -> isOpen != null ? cb.equal(root.get("isOpen"), isOpen) : null;
    }

    public static Specification<FoodTruck> hasCategories(List<Long> categoryIds) {
        return (root, query, cb) -> {
            if (categoryIds == null || categoryIds.isEmpty()) {
                return null;
            }
            Join<Object, Object> categories = root.join("categories");
            return categories.get("id").in(categoryIds);
        };
    }

    public static Specification<FoodTruck> isFavoriteForUser(Long userCredentialId) {
        return (root, query, cb) -> {
            if (userCredentialId == null) {
                return null;
            }

            query.distinct(true);
            Root<Favorite> favoriteRoot = query.from(Favorite.class);

            return cb.and(
                    cb.equal(favoriteRoot.get("userCredential").get("id"), userCredentialId),
                    cb.equal(favoriteRoot.get("foodTruck"), root)
            );
        };
    }
}

