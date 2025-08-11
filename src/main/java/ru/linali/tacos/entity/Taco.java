package ru.linali.tacos.entity;

import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NonNull;

import java.util.Date;
import java.util.List;

@Data
public class Taco {
    private Long id;

    private Date createdAt = new Date();
    @NonNull
    @Size(min = 5, message = "Имя должно содержать хотя бы 5 букв!")
    private String name;

    @NonNull
    @Size(min= 1, message = "Выберите хотя бы 1 ингредиент")
    private List<Ingredient> ingredients;

    public Taco() {

    }
}
