package be.shop.slow_delivery.product.domain;

import be.shop.slow_delivery.common.domain.BaseTimeEntity;
import com.mysema.commons.lang.Assert;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class IngredientGroup extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ingredient_group_id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Embedded
    private SelectCount selectCount;

    @OneToMany(mappedBy = "ingredientGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<IngredientInGroup> ingredients = new ArrayList<>();

    @Builder
    public IngredientGroup(String name, SelectCount selectCount) {
        Assert.hasText(name, "name");
        Assert.notNull(selectCount, "selectCount");

        this.name = name;
        this.selectCount = selectCount;
    }

    public void addIngredient(Ingredient ingredient) {
        ingredients.add(new IngredientInGroup(this, ingredient));
    }
}