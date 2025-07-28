package ru.linali.tacos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
    @ModelAttribute
    /*Что такое Model простыми словами? Элементы с которыми работаем?
     Model – это объект,
    в котором данные пересылаются между контроллером и любым представлением, ответственным за преобразование этих данных в разметку HTML. В конечном итоге данные, помещенные в атрибуты модели,
    копируются в атрибуты запроса сервлета, где представление найдет
    их и использует для отображения страницы в браузере пользователя.
     */
    public void addIngredientsToModel(Model model){
        List<Ingredient> ingredients = Arrays.asList(
                new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
                new Ingredient("COTO", "Corn Tortilla", Type.WRAP),
                new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
                new Ingredient("CARN", "Carnitas", Type.PROTEIN),
                new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES),
                new Ingredient("LETC", "Lettuce", Type.VEGGIES),
                new Ingredient("CHED", "Cheddar", Type.CHEESE),
                new Ingredient("JACK", "Monterrey Jack", Type.CHEESE),
                new Ingredient("SLSA", "Salsa", Type.SAUCE),
                new Ingredient("SRCR", "Sour Cream", Type.SAUCE)
        );
        Type [] types = Ingredient.Type.values();

        //Передаем в модель отфильтрованные ингредиенты по выбранному типу
        for (Type type : types) {
            System.out.println(type.toString().toLowerCase());
            System.out.println(filterByType(ingredients, type));
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

    private Iterable<Ingredient> filterByType(
            List<Ingredient> ingredients, Type type) {
        return ingredients
                .stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }
}
