package co.edu.uniquindio.compiladores.lexico.sintaxis

import javafx.scene.control.TreeItem

class CuerpoClase  {
    private var metodo : Metodo ?= null
    private var cuerpoClase: CuerpoClase ?= null
    private var declaracionVariable: DeclaracionVariable ?= null

    constructor()
    constructor(metodo : Metodo, cuerpoClase: CuerpoClase):this(){
        this.metodo = metodo
        this.cuerpoClase = cuerpoClase
    }
    constructor(declaracionVariable: DeclaracionVariable, cuerpoClase: CuerpoClase):this(){
        this.declaracionVariable = declaracionVariable
        this.cuerpoClase = cuerpoClase
    }


    fun getArbolVisual() : TreeItem<String>{
        val raiz = TreeItem( "Cuerpo clase")
        if (metodo != null){
            raiz.children.add( metodo!!.getArbolVisual() )
            if(cuerpoClase != null){
                return cuerpoClase!!.getArbolVisual()
            }
        }
        if (declaracionVariable != null) {
            raiz.children.add( declaracionVariable!!.getArbolVisual() )
            if(cuerpoClase != null){
                return cuerpoClase!!.getArbolVisual()
            }
        }
        return raiz
    }
}