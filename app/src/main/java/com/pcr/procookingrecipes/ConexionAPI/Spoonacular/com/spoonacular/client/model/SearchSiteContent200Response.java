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
public class SearchSiteContent200Response {
  
  @SerializedName("Articles")
  private Set<SearchSiteContent200ResponseArticlesInner> articles = null;
  @SerializedName("Grocery Products")
  private Set<SearchSiteContent200ResponseGroceryProductsInner> groceryProducts = null;
  @SerializedName("Menu Items")
  private Set<SearchSiteContent200ResponseGroceryProductsInner> menuItems = null;
  @SerializedName("Recipes")
  private Set<SearchSiteContent200ResponseGroceryProductsInner> recipes = null;

  /**
   **/
  @ApiModelProperty(required = true, value = "")
  public Set<SearchSiteContent200ResponseArticlesInner> getArticles() {
    return articles;
  }
  public void setArticles(Set<SearchSiteContent200ResponseArticlesInner> articles) {
    this.articles = articles;
  }

  /**
   **/
  @ApiModelProperty(required = true, value = "")
  public Set<SearchSiteContent200ResponseGroceryProductsInner> getGroceryProducts() {
    return groceryProducts;
  }
  public void setGroceryProducts(Set<SearchSiteContent200ResponseGroceryProductsInner> groceryProducts) {
    this.groceryProducts = groceryProducts;
  }

  /**
   **/
  @ApiModelProperty(required = true, value = "")
  public Set<SearchSiteContent200ResponseGroceryProductsInner> getMenuItems() {
    return menuItems;
  }
  public void setMenuItems(Set<SearchSiteContent200ResponseGroceryProductsInner> menuItems) {
    this.menuItems = menuItems;
  }

  /**
   **/
  @ApiModelProperty(required = true, value = "")
  public Set<SearchSiteContent200ResponseGroceryProductsInner> getRecipes() {
    return recipes;
  }
  public void setRecipes(Set<SearchSiteContent200ResponseGroceryProductsInner> recipes) {
    this.recipes = recipes;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SearchSiteContent200Response searchSiteContent200Response = (SearchSiteContent200Response) o;
    return (this.articles == null ? searchSiteContent200Response.articles == null : this.articles.equals(searchSiteContent200Response.articles)) &&
        (this.groceryProducts == null ? searchSiteContent200Response.groceryProducts == null : this.groceryProducts.equals(searchSiteContent200Response.groceryProducts)) &&
        (this.menuItems == null ? searchSiteContent200Response.menuItems == null : this.menuItems.equals(searchSiteContent200Response.menuItems)) &&
        (this.recipes == null ? searchSiteContent200Response.recipes == null : this.recipes.equals(searchSiteContent200Response.recipes));
  }

  @Override
  public int hashCode() {
    int result = 17;
    result = 31 * result + (this.articles == null ? 0: this.articles.hashCode());
    result = 31 * result + (this.groceryProducts == null ? 0: this.groceryProducts.hashCode());
    result = 31 * result + (this.menuItems == null ? 0: this.menuItems.hashCode());
    result = 31 * result + (this.recipes == null ? 0: this.recipes.hashCode());
    return result;
  }

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class SearchSiteContent200Response {\n");
    
    sb.append("  articles: ").append(articles).append("\n");
    sb.append("  groceryProducts: ").append(groceryProducts).append("\n");
    sb.append("  menuItems: ").append(menuItems).append("\n");
    sb.append("  recipes: ").append(recipes).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
