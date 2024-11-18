/**
 * spoonacular API
 * The spoonacular Nutrition, Recipe, and Food API allows you to access over thousands of recipes, thousands of ingredients, 800,000 food products, over 100,000 menu items, and restaurants. Our food ontology and semantic recipe search engine makes it possible to search for recipes using natural language queries, such as \"gluten free brownies without sugar\" or \"low fat vegan cupcakes.\" You can automatically calculate the nutritional information for any recipe, analyze recipe costs, visualize ingredient lists, find recipes for what's in your fridge, find recipes based on special diets, nutritional requirements, or favorite ingredients, classify recipes into types and cuisines, convert ingredient amounts, or even compute an entire meal plan. With our powerful API, you can create many kinds of food and especially nutrition apps.  Special diets/dietary requirements currently available include: vegan, vegetarian, pescetarian, gluten free, grain free, dairy free, high protein, whole 30, low sodium, low carb, Paleo, ketogenic, FODMAP, and Primal.
 *
 * The version of the OpenAPI document: 1.1
 * Contact: mail@spoonacular.com
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

package com.pcr.procookingrecipes.ConexionAPI.Spoonacular.com.spoonacular.client.model;

import java.math.BigDecimal;
import io.swagger.annotations.*;
import com.google.gson.annotations.SerializedName;

@ApiModel(description = "")
public class GetRecipePriceBreakdownByID200ResponseIngredientsInnerAmountMetric {
  
  @SerializedName("unit")
  private String unit = null;
  @SerializedName("value")
  private BigDecimal value = null;

  /**
   **/
  @ApiModelProperty(required = true, value = "")
  public String getUnit() {
    return unit;
  }
  public void setUnit(String unit) {
    this.unit = unit;
  }

  /**
   **/
  @ApiModelProperty(required = true, value = "")
  public BigDecimal getValue() {
    return value;
  }
  public void setValue(BigDecimal value) {
    this.value = value;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GetRecipePriceBreakdownByID200ResponseIngredientsInnerAmountMetric getRecipePriceBreakdownByID200ResponseIngredientsInnerAmountMetric = (GetRecipePriceBreakdownByID200ResponseIngredientsInnerAmountMetric) o;
    return (this.unit == null ? getRecipePriceBreakdownByID200ResponseIngredientsInnerAmountMetric.unit == null : this.unit.equals(getRecipePriceBreakdownByID200ResponseIngredientsInnerAmountMetric.unit)) &&
        (this.value == null ? getRecipePriceBreakdownByID200ResponseIngredientsInnerAmountMetric.value == null : this.value.equals(getRecipePriceBreakdownByID200ResponseIngredientsInnerAmountMetric.value));
  }

  @Override
  public int hashCode() {
    int result = 17;
    result = 31 * result + (this.unit == null ? 0: this.unit.hashCode());
    result = 31 * result + (this.value == null ? 0: this.value.hashCode());
    return result;
  }

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class GetRecipePriceBreakdownByID200ResponseIngredientsInnerAmountMetric {\n");
    
    sb.append("  unit: ").append(unit).append("\n");
    sb.append("  value: ").append(value).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
