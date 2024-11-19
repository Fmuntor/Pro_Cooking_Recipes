package com.pcr.procookingrecipes.Receta;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.List;

public class RecetaBusqueda implements Parcelable {
    private int id;
    private String title;
    private String image;
    private int servings;
    private int readyInMinutes;
    private int cookingMinutes;
    private int preparationMinutes;

    public int getPreparationMinutes() {
        return preparationMinutes;
    }

    public void setPreparationMinutes(int preparationMinutes) {
        this.preparationMinutes = preparationMinutes;
    }

    // Getters y setters para los campos de la receta
    public RecetaBusqueda(int id, String titulo, String imagen, int servings, int readyInMinutes, int cookingMinutes, int preparationMinutes) {
        this.id = id;
        this.title = titulo;
        this.image = imagen;
        this.servings = servings;
        this.readyInMinutes = readyInMinutes;
        this.cookingMinutes = cookingMinutes;
        this.preparationMinutes = preparationMinutes;
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

    public void setReadyInMinutes(int readyInMinutes) {
        this.readyInMinutes = readyInMinutes;
    }

    public int getCookingMinutes() {
        return cookingMinutes;
    }

    public void setCookingMinutes(int cookingMinutes) {
        this.cookingMinutes = cookingMinutes;
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
        cookingMinutes = in.readInt();
        preparationMinutes = in.readInt();
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
        dest.writeInt(cookingMinutes);
        dest.writeInt(preparationMinutes);

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

