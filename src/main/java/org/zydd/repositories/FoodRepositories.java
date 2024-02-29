package org.zydd.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.zydd.models.Food;

@ApplicationScoped
public class FoodRepositories implements PanacheRepository<Food> {
}
