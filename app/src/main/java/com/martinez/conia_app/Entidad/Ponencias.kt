package com.martinez.conia_app.Entidad

import android.os.Parcel
import android.os.Parcelable

data class Ponencia(
    val uid: String = "N/A",
    val nombre: String = "N/A", //nombre de la ponencia
    val hora_inicio: String = "N/A", //hora de inicio
    val hora_final: String = "N/A", //hora de fin
    val duracion: String = "N/A", //duración de la ponencia en minutos
    val resumen: String = "N/A", //resumen corto de la ponencia
    val descripcion: String = "N/A", //descripción de la ponencia
    val imagenURL: String = "N/A" //la recuperaré de google
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(nombre)
        writeString(hora_inicio)
        writeString(hora_final)
        writeString(duracion)
        writeString(resumen)
        writeString(descripcion)
        writeString(imagenURL)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Ponencia> = object : Parcelable.Creator<Ponencia> {
            override fun createFromParcel(source: Parcel): Ponencia = Ponencia(source)
            override fun newArray(size: Int): Array<Ponencia?> = arrayOfNulls(size)
        }
    }
}