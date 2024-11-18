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
public class SearchFoodVideos200ResponseVideosInner {
  
  @SerializedName("title")
  private String title = null;
  @SerializedName("length")
  private Integer length = null;
  @SerializedName("rating")
  private BigDecimal rating = null;
  @SerializedName("shortTitle")
  private String shortTitle = null;
  @SerializedName("thumbnail")
  private String thumbnail = null;
  @SerializedName("views")
  private Integer views = null;
  @SerializedName("youTubeId")
  private String youTubeId = null;

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
  public Integer getLength() {
    return length;
  }
  public void setLength(Integer length) {
    this.length = length;
  }

  /**
   **/
  @ApiModelProperty(required = true, value = "")
  public BigDecimal getRating() {
    return rating;
  }
  public void setRating(BigDecimal rating) {
    this.rating = rating;
  }

  /**
   **/
  @ApiModelProperty(required = true, value = "")
  public String getShortTitle() {
    return shortTitle;
  }
  public void setShortTitle(String shortTitle) {
    this.shortTitle = shortTitle;
  }

  /**
   **/
  @ApiModelProperty(required = true, value = "")
  public String getThumbnail() {
    return thumbnail;
  }
  public void setThumbnail(String thumbnail) {
    this.thumbnail = thumbnail;
  }

  /**
   **/
  @ApiModelProperty(required = true, value = "")
  public Integer getViews() {
    return views;
  }
  public void setViews(Integer views) {
    this.views = views;
  }

  /**
   **/
  @ApiModelProperty(required = true, value = "")
  public String getYouTubeId() {
    return youTubeId;
  }
  public void setYouTubeId(String youTubeId) {
    this.youTubeId = youTubeId;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SearchFoodVideos200ResponseVideosInner searchFoodVideos200ResponseVideosInner = (SearchFoodVideos200ResponseVideosInner) o;
    return (this.title == null ? searchFoodVideos200ResponseVideosInner.title == null : this.title.equals(searchFoodVideos200ResponseVideosInner.title)) &&
        (this.length == null ? searchFoodVideos200ResponseVideosInner.length == null : this.length.equals(searchFoodVideos200ResponseVideosInner.length)) &&
        (this.rating == null ? searchFoodVideos200ResponseVideosInner.rating == null : this.rating.equals(searchFoodVideos200ResponseVideosInner.rating)) &&
        (this.shortTitle == null ? searchFoodVideos200ResponseVideosInner.shortTitle == null : this.shortTitle.equals(searchFoodVideos200ResponseVideosInner.shortTitle)) &&
        (this.thumbnail == null ? searchFoodVideos200ResponseVideosInner.thumbnail == null : this.thumbnail.equals(searchFoodVideos200ResponseVideosInner.thumbnail)) &&
        (this.views == null ? searchFoodVideos200ResponseVideosInner.views == null : this.views.equals(searchFoodVideos200ResponseVideosInner.views)) &&
        (this.youTubeId == null ? searchFoodVideos200ResponseVideosInner.youTubeId == null : this.youTubeId.equals(searchFoodVideos200ResponseVideosInner.youTubeId));
  }

  @Override
  public int hashCode() {
    int result = 17;
    result = 31 * result + (this.title == null ? 0: this.title.hashCode());
    result = 31 * result + (this.length == null ? 0: this.length.hashCode());
    result = 31 * result + (this.rating == null ? 0: this.rating.hashCode());
    result = 31 * result + (this.shortTitle == null ? 0: this.shortTitle.hashCode());
    result = 31 * result + (this.thumbnail == null ? 0: this.thumbnail.hashCode());
    result = 31 * result + (this.views == null ? 0: this.views.hashCode());
    result = 31 * result + (this.youTubeId == null ? 0: this.youTubeId.hashCode());
    return result;
  }

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class SearchFoodVideos200ResponseVideosInner {\n");
    
    sb.append("  title: ").append(title).append("\n");
    sb.append("  length: ").append(length).append("\n");
    sb.append("  rating: ").append(rating).append("\n");
    sb.append("  shortTitle: ").append(shortTitle).append("\n");
    sb.append("  thumbnail: ").append(thumbnail).append("\n");
    sb.append("  views: ").append(views).append("\n");
    sb.append("  youTubeId: ").append(youTubeId).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
