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
public class GetMealPlanWeek200ResponseDaysInnerItemsInnerValue {
  
  @SerializedName("servings")
  private BigDecimal servings = null;
  @SerializedName("id")
  private BigDecimal id = null;
  @SerializedName("title")
  private String title = null;
  @SerializedName("imageType")
  private String imageType = null;

  /**
   **/
  @ApiModelProperty(required = true, value = "")
  public BigDecimal getServings() {
    return servings;
  }
  public void setServings(BigDecimal servings) {
    this.servings = servings;
  }

  /**
   **/
  @ApiModelProperty(required = true, value = "")
  public BigDecimal getId() {
    return id;
  }
  public void setId(BigDecimal id) {
    this.id = id;
  }

  /**
   **/
  @ApiModelProperty(required = true, value = "")
  public String getTitle() {
    return title;
  }
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   **/
  @ApiModelProperty(required = true, value = "")
  public String getImageType() {
    return imageType;
  }
  public void setImageType(String imageType) {
    this.imageType = imageType;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GetMealPlanWeek200ResponseDaysInnerItemsInnerValue getMealPlanWeek200ResponseDaysInnerItemsInnerValue = (GetMealPlanWeek200ResponseDaysInnerItemsInnerValue) o;
    return (this.servings == null ? getMealPlanWeek200ResponseDaysInnerItemsInnerValue.servings == null : this.servings.equals(getMealPlanWeek200ResponseDaysInnerItemsInnerValue.servings)) &&
        (this.id == null ? getMealPlanWeek200ResponseDaysInnerItemsInnerValue.id == null : this.id.equals(getMealPlanWeek200ResponseDaysInnerItemsInnerValue.id)) &&
        (this.title == null ? getMealPlanWeek200ResponseDaysInnerItemsInnerValue.title == null : this.title.equals(getMealPlanWeek200ResponseDaysInnerItemsInnerValue.title)) &&
        (this.imageType == null ? getMealPlanWeek200ResponseDaysInnerItemsInnerValue.imageType == null : this.imageType.equals(getMealPlanWeek200ResponseDaysInnerItemsInnerValue.imageType));
  }

  @Override
  public int hashCode() {
    int result = 17;
    result = 31 * result + (this.servings == null ? 0: this.servings.hashCode());
    result = 31 * result + (this.id == null ? 0: this.id.hashCode());
    result = 31 * result + (this.title == null ? 0: this.title.hashCode());
    result = 31 * result + (this.imageType == null ? 0: this.imageType.hashCode());
    return result;
  }

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class GetMealPlanWeek200ResponseDaysInnerItemsInnerValue {\n");
    
    sb.append("  servings: ").append(servings).append("\n");
    sb.append("  id: ").append(id).append("\n");
    sb.append("  title: ").append(title).append("\n");
    sb.append("  imageType: ").append(imageType).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
