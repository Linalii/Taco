package ru.linali.tacos.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.logging.SocketHandler;
import java.util.stream.Collectors;

import ru.linali.tacos.JdbcIngredientRepository;
import ru.linali.tacos.entity.Ingredient;
import ru.linali.tacos.entity.Ingredient.Type;
import ru.linali.tacos.entity.Taco;
import ru.linali.tacos.entity.TacoOrder;

@Controller
@RequestMapping("/design")
/*SessionAttributes что это?
Она указывает, что объект TacoOrder,
объявленный в классе чуть ниже, должен поддерживаться на уровне
сеанса. Это важно, потому что создание тако также является первым
шагом в создании заказа, и созданный нами заказ необходимо будет
перенести в сеанс, охватывающий несколько запросов.
*/
@SessionAttributes("tacoOrder")
public class DesignTacoController {
    private static final Logger log = LoggerFactory.getLogger(DesignTacoController.class);
    private final JdbcIngredientRepository jdbcIngredientRepository;

    @Autowired
    public DesignTacoController(JdbcIngredientRepository jdbcIngredientRepository) {
        this.jdbcIngredientRepository = jdbcIngredientRepository;
    }

    @ModelAttribute
    public void addIngredientsToModel(Model model){
        Iterable<Ingredient> ingredients = jdbcIngredientRepository.findAll();
        Type[] types = Ingredient.Type.values();
        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(),
                    filterByType(ingredients, type));
        }
    }

    @ModelAttribute(name = "tacoOrder")
    public TacoOrder order(){
        return new TacoOrder();
    }

    @ModelAttribute(name = "taco")
    public Taco taco(){
        return new Taco();
    }

    @GetMapping
    public String showDesignForm(){
        return "design";
    }

    @PostMapping
    public String processTaco(@Valid Taco taco, Errors errors,
                              @ModelAttribute TacoOrder tacoOrder) {
        if (errors.hasErrors()){
            return "design";
        }
        tacoOrder.addTaco(taco);
        log.info("Processing taco: {}", taco);
        return "redirect:/orders/current";
    }

    private Iterable<Ingredient> filterByType(
            List<Ingredient> ingredients, Type type) {
        return ingredients
                .stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }
}
