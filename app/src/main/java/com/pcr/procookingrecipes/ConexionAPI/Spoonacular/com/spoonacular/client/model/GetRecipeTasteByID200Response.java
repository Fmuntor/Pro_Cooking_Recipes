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

/**
 * 
 **/
@ApiModel(description = "")
public class GetRecipeTasteByID200Response {
  
  @SerializedName("sweetness")
  private BigDecimal sweetness = null;
  @SerializedName("saltiness")
  private BigDecimal saltiness = null;
  @SerializedName("sourness")
  private BigDecimal sourness = null;
  @SerializedName("bitterness")
  private BigDecimal bitterness = null;
  @SerializedName("savoriness")
  private BigDecimal savoriness = null;
  @SerializedName("fattiness")
  private BigDecimal fattiness = null;
  @SerializedName("spiciness")
  private BigDecimal spiciness = null;

  /**
   **/
  @ApiModelProperty(required = true, value = "")
  public BigDecimal getSweetness() {
    return sweetness;
  }
  public void setSweetness(BigDecimal sweetness) {
    this.sweetness = sweetness;
  }

  /**
   **/
  @ApiModelProperty(required = true, value = "")
  public BigDecimal getSaltiness() {
    return saltiness;
  }
  public void setSaltiness(BigDecimal saltiness) {
    this.saltiness = saltiness;
  }

  /**
   **/
  @ApiModelProperty(required = true, value = "")
  public BigDecimal getSourness() {
    return sourness;
  }
  public void setSourness(BigDecimal sourness) {
    this.sourness = sourness;
  }

  /**
   **/
  @ApiModelProperty(required = true, value = "")
  public BigDecimal getBitterness() {
    return bitterness;
  }
  public void setBitterness(BigDecimal bitterness) {
    this.bitterness = bitterness;
  }

  /**
   **/
  @ApiModelProperty(required = true, value = "")
  public BigDecimal getSavoriness() {
    return savoriness;
  }
  public void setSavoriness(BigDecimal savoriness) {
    this.savoriness = savoriness;
  }

  /**
   **/
  @ApiModelProperty(required = true, value = "")
  public BigDecimal getFattiness() {
    return fattiness;
  }
  public void setFattiness(BigDecimal fattiness) {
    this.fattiness = fattiness;
  }

  /**
   **/
  @ApiModelProperty(required = true, value = "")
  public BigDecimal getSpiciness() {
    return spiciness;
  }
  public void setSpiciness(BigDecimal spiciness) {
    this.spiciness = spiciness;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GetRecipeTasteByID200Response getRecipeTasteByID200Response = (GetRecipeTasteByID200Response) o;
    return (this.sweetness == null ? getRecipeTasteByID200Response.sweetness == null : this.sweetness.equals(getRecipeTasteByID200Response.sweetness)) &&
        (this.saltiness == null ? getRecipeTasteByID200Response.saltiness == null : this.saltiness.equals(getRecipeTasteByID200Response.saltiness)) &&
        (this.sourness == null ? getRecipeTasteByID200Response.sourness == null : this.sourness.equals(getRecipeTasteByID200Response.sourness)) &&
        (this.bitterness == null ? getRecipeTasteByID200Response.bitterness == null : this.bitterness.equals(getRecipeTasteByID200Response.bitterness)) &&
        (this.savoriness == null ? getRecipeTasteByID200Response.savoriness == null : this.savoriness.equals(getRecipeTasteByID200Response.savoriness)) &&
        (this.fattiness == null ? getRecipeTasteByID200Response.fattiness == null : this.fattiness.equals(getRecipeTasteByID200Response.fattiness)) &&
        (this.spiciness == null ? getRecipeTasteByID200Response.spiciness == null : this.spiciness.equals(getRecipeTasteByID200Response.spiciness));
  }

  @Override
  public int hashCode() {
    int result = 17;
    result = 31 * result + (this.sweetness == null ? 0: this.sweetness.hashCode());
    result = 31 * result + (this.saltiness == null ? 0: this.saltiness.hashCode());
    result = 31 * result + (this.sourness == null ? 0: this.sourness.hashCode());
    result = 31 * result + (this.bitterness == null ? 0: this.bitterness.hashCode());
    result = 31 * result + (this.savoriness == null ? 0: this.savoriness.hashCode());
    result = 31 * result + (this.fattiness == null ? 0: this.fattiness.hashCode());
    result = 31 * result + (this.spiciness == null ? 0: this.spiciness.hashCode());
    return result;
  }

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class GetRecipeTasteByID200Response {\n");
    
    sb.append("  sweetness: ").append(sweetness).append("\n");
    sb.append("  saltiness: ").append(saltiness).append("\n");
    sb.append("  sourness: ").append(sourness).append("\n");
    sb.append("  bitterness: ").append(bitterness).append("\n");
    sb.append("  savoriness: ").append(savoriness).append("\n");
    sb.append("  fattiness: ").append(fattiness).append("\n");
    sb.append("  spiciness: ").append(spiciness).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
