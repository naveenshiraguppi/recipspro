package com.recipspro;

import java.time.LocalDate;
import java.util.Comparator;

public class RecipeIngredientUseByAndAmountComparator implements Comparator<Recipe>{

	@Override
	public int compare(Recipe a, Recipe b) {
		if (a != null && b != null) {
			Ingredient i1 = a.getNearestUseByIngredient();
			Ingredient i2 = b.getNearestUseByIngredient();;
			LocalDate d1 = i1.getUse_by();
			LocalDate d2 = i2.getUse_by();
			if (d1 == null && d2 == null)
				return 0;
			else if (d1 == null) {
				return -1;
			} else if (d2 == null) {
				return 1;
			} else {
				if (d1.isBefore(d2))
					return 1;
				else if(d1.equals(d2)){
					float ratio1 = (i1.getAmount()*100.0f)/i1.getTotalAmount();
					float ratio2 = (i2.getAmount()*100.0f)/i2.getTotalAmount();
					if(ratio1 == ratio2)
						return 0;
					if(ratio1 < ratio2)
						return -1;
					else
						return 1;
				}else
					return -1;
			}
		}
		return -1;
	}

}
