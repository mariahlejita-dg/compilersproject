package co.edu.uniquindio.compiladores.lexico.sintaxis

import co.edu.uniquindio.compiladores.lexico.lexico.Token
import javafx.scene.control.TreeItem

class Importacion {
    private var palabra_reservada_importar : Token ?= null
    private var nombre_paquete : String ?= null
    private var punto : Token ?= null
    private var identificador_clase : Token ?= null
    constructor(palabra_reservada_importar : Token, nombre_paquete : String, identificador_clase : Token){
        this.palabra_reservada_importar = palabra_reservada_importar
        this.nombre_paquete = nombre_paquete
        this.identificador_clase = identificador_clase
    }
    fun getArbolVisual() : TreeItem<String> {
        val raiz = TreeItem ( "Importacion" )
        raiz.children.add( TreeItem(nombre_paquete))
        raiz.children.add( TreeItem(identificador_clase!!.lexema) )
        return raiz
    }
}