package co.edu.uniquindio.compiladores.lexico.sintaxis

import javafx.scene.control.TreeItem
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
    fun getArbolVisual() : TreeItem<String>{
        val raiz = TreeItem("Unidad de Compilacion")
        raiz.children.add(paquete!!.getArbolVisual())
        if(listaImportaciones != null){
            for (i in listaImportaciones!!){
                raiz.children.add(i.getArbolVisual())
            }
        }
        raiz.children.add(clase!!.getArbolVisual())
        return raiz
    }
}