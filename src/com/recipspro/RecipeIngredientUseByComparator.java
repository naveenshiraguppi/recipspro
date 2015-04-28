package com.recipspro;

import java.time.LocalDate;
import java.util.Comparator;

public class RecipeIngredientUseByComparator implements Comparator<Recipe>{

	@Override
	public int compare(Recipe a, Recipe b) {
		if (a != null && b != null) {
			LocalDate d1 = a.getNearestUseBy();
			LocalDate d2 = b.getNearestUseBy();
			if (d1 == null && d2 == null)
				return 0;
			else if (d1 == null) {
				return -1;
			} else if (d2 == null) {
				return 1;
			} else {
				if (d1.isBefore(d2))
					return 1;
				else
					return -1;
			}
		}
		return -1;
	}

}
