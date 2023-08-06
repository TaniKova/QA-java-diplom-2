package practicum.entity;

public class Order {
    private static final String[] defaultIngredients = {
            "61c0c5a71d1f82001bdaaa6f",
            "61c0c5a71d1f82001bdaaa70",
            "61c0c5a71d1f82001bdaaa71",
            "61c0c5a71d1f82001bdaaa72",
            "61c0c5a71d1f82001bdaaa6e",
            "61c0c5a71d1f82001bdaaa73",
            "61c0c5a71d1f82001bdaaa74",
            "61c0c5a71d1f82001bdaaa6c",
            "61c0c5a71d1f82001bdaaa75",
            "61c0c5a71d1f82001bdaaa76",
            "61c0c5a71d1f82001bdaaa77",
            "61c0c5a71d1f82001bdaaa78",
            "61c0c5a71d1f82001bdaaa79",
            "61c0c5a71d1f82001bdaaa7a"
    };

    private final String[] ingredients;

    public Order(String[] ingredients){
        this.ingredients = ingredients;
    }

    public static Order getWithDefaultIngredients(){
        return new Order(defaultIngredients);
    }

    public static Order getWithoutIngredients(){
        return new Order(new String[]{});
    }

}
