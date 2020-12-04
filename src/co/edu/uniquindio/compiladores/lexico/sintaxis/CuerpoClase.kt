package co.edu.uniquindio.compiladores.lexico.sintaxis

import co.edu.uniquindio.compiladores.lexico.semantica.TabladeSimbolos
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

    constructor(metodo : Metodo):this(){
        this.metodo = metodo
    }
    constructor(declaracionVariable: DeclaracionVariable):this(){
        this.declaracionVariable = declaracionVariable
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
    fun traducir(): String {
        var codigo = ""
        if (metodo != null) {
            codigo += metodo!!.traducir().toString() + "\n"
            if (cuerpoClase != null) {
                codigo += cuerpoClase!!.traducir()
            }
        } else if (declaracionVariable != null) {
            codigo += declaracionVariable!!.traducir().toString() + "\n"
            if (cuerpoClase != null) {
                codigo += cuerpoClase!!.traducir()
            }
        }
        return codigo
    }

    fun analizarSemantica(errores: ArrayList<String?>?, ts: TabladeSimbolos?) {

    }

    fun llenarTablaSimbolos(ts: TabladeSimbolos?) {

    }
}