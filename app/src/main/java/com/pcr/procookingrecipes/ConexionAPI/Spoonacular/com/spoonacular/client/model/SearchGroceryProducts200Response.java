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

import java.util.*;
import io.swagger.annotations.*;
import com.google.gson.annotations.SerializedName;

/**
 * 
 **/
@ApiModel(description = "")
public class SearchGroceryProducts200Response {
  
  @SerializedName("products")
  private Set<AutocompleteRecipeSearch200ResponseInner> products = null;
  @SerializedName("totalProducts")
  private Integer totalProducts = null;
  @SerializedName("type")
  private String type = null;
  @SerializedName("offset")
  private Integer offset = null;
  @SerializedName("number")
  private Integer number = null;

  /**
   **/
  @ApiModelProperty(required = true, value = "")
  public Set<AutocompleteRecipeSearch200ResponseInner> getProducts() {
    return products;
  }
  public void setProducts(Set<AutocompleteRecipeSearch200ResponseInner> products) {
    this.products = products;
  }

  /**
   **/
  @ApiModelProperty(required = true, value = "")
  public Integer getTotalProducts() {
    return totalProducts;
  }
  public void setTotalProducts(Integer totalProducts) {
    this.totalProducts = totalProducts;
  }

  /**
   **/
  @ApiModelProperty(required = true, value = "")
  public String getType() {
    return type;
  }
  public void setType(String type) {
    this.type = type;
  }

  /**
   **/
  @ApiModelProperty(required = true, value = "")
  public Integer getOffset() {
    return offset;
  }
  public void setOffset(Integer offset) {
    this.offset = offset;
  }

  /**
   **/
  @ApiModelProperty(required = true, value = "")
  public Integer getNumber() {
    return number;
  }
  public void setNumber(Integer number) {
    this.number = number;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SearchGroceryProducts200Response searchGroceryProducts200Response = (SearchGroceryProducts200Response) o;
    return (this.products == null ? searchGroceryProducts200Response.products == null : this.products.equals(searchGroceryProducts200Response.products)) &&
        (this.totalProducts == null ? searchGroceryProducts200Response.totalProducts == null : this.totalProducts.equals(searchGroceryProducts200Response.totalProducts)) &&
        (this.type == null ? searchGroceryProducts200Response.type == null : this.type.equals(searchGroceryProducts200Response.type)) &&
        (this.offset == null ? searchGroceryProducts200Response.offset == null : this.offset.equals(searchGroceryProducts200Response.offset)) &&
        (this.number == null ? searchGroceryProducts200Response.number == null : this.number.equals(searchGroceryProducts200Response.number));
  }

  @Override
  public int hashCode() {
    int result = 17;
    result = 31 * result + (this.products == null ? 0: this.products.hashCode());
    result = 31 * result + (this.totalProducts == null ? 0: this.totalProducts.hashCode());
    result = 31 * result + (this.type == null ? 0: this.type.hashCode());
    result = 31 * result + (this.offset == null ? 0: this.offset.hashCode());
    result = 31 * result + (this.number == null ? 0: this.number.hashCode());
    return result;
  }

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class SearchGroceryProducts200Response {\n");
    
    sb.append("  products: ").append(products).append("\n");
    sb.append("  totalProducts: ").append(totalProducts).append("\n");
    sb.append("  type: ").append(type).append("\n");
    sb.append("  offset: ").append(offset).append("\n");
    sb.append("  number: ").append(number).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
