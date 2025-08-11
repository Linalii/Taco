package ru.linali.tacos.entity;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class TacoOrder implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    @NotBlank(message = "Не указано название доставка ")
    private String deliveryName;

    @NotBlank(message = "Не указана улица доставки")
    private String deliveryStreet;

    @NotBlank(message = "Не указан город доставки")
    private String deliveryCity;

    @NotBlank(message = "Не указана область доставки")
    private String deliveryState;

    @NotBlank(message = "Не указан индекс доставки")
    private String deliveryZip;

    @CreditCardNumber(message = "Невалидный номер карты")
    private String ccNumber;

    @Pattern(regexp="^(0[1-9]|1[0-2])([\\/])([2-9][0-9])$",
            message="Необходимый формат -  MM/ГГ, например 08/09(9-е августа)")
    private String ccExpiration;

    @Digits(integer=3, fraction=0, message="Invalid CVV")
    private String ccCVV;

    private List<Taco> tacos = new ArrayList<>();

    public void addTaco(Taco taco){
        this.tacos.add(taco);
    }

}
