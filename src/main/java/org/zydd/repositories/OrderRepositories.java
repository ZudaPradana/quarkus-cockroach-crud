package org.zydd.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.zydd.models.Order;

@ApplicationScoped
public class OrderRepositories implements PanacheRepository<Order> {
}
