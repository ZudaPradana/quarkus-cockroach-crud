package org.zydd.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.zydd.dto.request.FoodDataReq;
import org.zydd.models.Food;
import org.zydd.repositories.FoodRepositories;

import java.util.List;

@ApplicationScoped
@Slf4j
public class FoodServices {
    @Inject
    FoodRepositories repositories;

    public List<Food> getAllFood(){
        log.info("Get All Food Data");
        return repositories.listAll();
    }

    public Food getFoodById(Long id){
        var foodId = repositories.findById(id);
        if(foodId == null){
            log.error("Id Not Found");
            throw new RuntimeException("Id Not Found");
        }
        log.info("Retrieve Id {}", id);
        return foodId;
    }

    @Transactional
    public Food createFood(FoodDataReq foodDataReq){
        Food food = Food.builder()
                .name(foodDataReq.getFoodName())
                .category(foodDataReq.getCategory())
                .price(foodDataReq.getPrice())
                .build();
        log.info("created new food");
        repositories.persist(food);
        return food;
    }

    @Transactional
    public Food updateFood(Long id, FoodDataReq foodDataReq){
        var updateFood = getFoodById(id);
        updateFood.setName(foodDataReq.getFoodName());
        updateFood.setCategory(foodDataReq.getCategory());
        updateFood.setPrice(foodDataReq.getPrice());

        log.info("update data id {}", id);
        repositories.persist(updateFood);
        return updateFood;
    }

    public void deleteFoodById(Long id){
        var foodId = getFoodById(id).getId();
        repositories.deleteById(foodId);
    }

}
