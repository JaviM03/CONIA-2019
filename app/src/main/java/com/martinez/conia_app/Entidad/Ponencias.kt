package com.martinez.conia_app.Entidad

data class Ponencia(
    val nombre: String = "N/A", //nombre de la ponencia
    val hora_inicio: String = "N/A", //hora de inicio
    val hora_final: String = "N/A", //hora de fin
    val duracion: String = "N/A", //duración de la ponencia en minutos
    val resumen: String = "N/A", //resumen corto de la ponencia
    val descripcion: String = "N/A", //descripción de la ponencia
    val imagenURL: String = "N/A" //la recuperaré de google
)