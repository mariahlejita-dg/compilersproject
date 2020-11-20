package co.edu.uniquindio.compiladores.lexico.sintaxis

import java.util.*


class UnidadDeCompilacion {
    private var paquete: Paquete? = null
    private var listaImportaciones: ArrayList<Importacion>? = null
    private var clase: Clase? = null
    constructor()
    constructor(paquete: Paquete,listaImportaciones: ArrayList<Importacion>,clase: Clase):this(){
        this.paquete = paquete
        this.listaImportaciones = listaImportaciones
        this.clase = clase
    }
    constructor(paquete: Paquete,clase: Clase):this(){
        this.paquete = paquete
        this.clase = clase
    }
}