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

    // Constructor
    public double getSpoonacularScore() {
        return spoonacularScore;
    }

    // Getters y setters
    public String getGlutenFree() {
        return glutenFree;
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

    public int getServings() {
        return servings;
    }

    public double getPricePerServing() {
        return pricePerServing;
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

