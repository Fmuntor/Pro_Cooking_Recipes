package com.pcr.procookingrecipes.Receta;

import android.os.Parcel;
import android.os.Parcelable;

public class RecetaBusqueda implements Parcelable {
    private int id;
    private String title;
    private String image;
    private int servings;
    private int readyInMinutes;
    private String glutenFree;
    private double spoonacularScore;
    private double pricePerServing;

    public double getSpoonacularScore() {
        return spoonacularScore;
    }

    public String getGlutenFree() {
        return glutenFree;
    }

    // Getters y setters para los campos de la receta
    public RecetaBusqueda(int id, String titulo, String imagen, int servings, int readyInMinutes, String glutenFree, double spoonacularScore, double pricePerServing) {
        this.id = id;
        this.title = titulo;
        this.image = imagen;
        this.servings = servings;
        this.readyInMinutes = readyInMinutes;
        this.glutenFree = glutenFree;
        this.spoonacularScore = spoonacularScore;
        this.pricePerServing = pricePerServing;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getServings() {
        return servings;
    }

    public double getPricePerServing() {
        return pricePerServing;
    }

    public void setReadyInMinutes(int readyInMinutes) {
        this.readyInMinutes = readyInMinutes;
    }

    public int getReadyInMinutes() {
        return readyInMinutes;
    }

    // Métodos Parcelable
    public RecetaBusqueda(Parcel in) {
        id = in.readInt();
        title = in.readString();
        image = in.readString();
        servings = in.readInt();
        readyInMinutes = in.readInt();
        glutenFree = in.readString();
        spoonacularScore = in.readDouble();
        pricePerServing = in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(image);
        dest.writeInt(servings);
        dest.writeInt(readyInMinutes);
        dest.writeString(glutenFree);
        dest.writeDouble(spoonacularScore);
        dest.writeDouble(pricePerServing);

    }
    public static final Creator<RecetaBusqueda> CREATOR = new Creator<RecetaBusqueda>() {
        @Override
        public RecetaBusqueda createFromParcel(Parcel in) {
            return new RecetaBusqueda(in);
        }

        @Override
        public RecetaBusqueda[] newArray(int size) {
            return new RecetaBusqueda[size];
        }
    };
}

