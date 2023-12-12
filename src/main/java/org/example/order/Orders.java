package org.example.order;

import java.util.List;

public class Orders {
    private String[] ingredients;
    public Orders(){}

    public Orders(String[] ingredients) {
        this.ingredients = ingredients;
    }

    public String[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(String[] ingredients) {
        this.ingredients = ingredients;
    }
}
