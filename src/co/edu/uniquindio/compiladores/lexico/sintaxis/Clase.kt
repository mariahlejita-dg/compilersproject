package co.edu.uniquindio.compiladores.lexico.sintaxis

import co.edu.uniquindio.compiladores.lexico.lexico.Token
import co.edu.uniquindio.compiladores.lexico.semantica.TabladeSimbolos
import javafx.scene.control.TreeItem

class Clase  {
    private var modificador : Token ?= null
    var palabra_reservada_clase : Token ?= null
    var identificador_clase : Token ?= null
    var cuerpoClase : CuerpoClase ?= null

    constructor()
    constructor(modificador : Token, palabra_reservada_clase:Token, identificador_clase: Token, cuerpoClase: CuerpoClase):this() {
        this.modificador = modificador
        this.palabra_reservada_clase = palabra_reservada_clase
        this.identificador_clase = identificador_clase
        this.cuerpoClase = cuerpoClase
    }
    constructor(modificador : Token, palabra_reservada_clase: Token, identificador_clase: Token):this() {
        this.modificador = modificador
        this.palabra_reservada_clase = palabra_reservada_clase
        this.identificador_clase = identificador_clase
    }
    fun getArbolVisual() : TreeItem<String>{
        val raiz = TreeItem("Clase")
        if(cuerpoClase != null){
            raiz.children.add( TreeItem(identificador_clase!!.lexema))
            raiz.children.add( cuerpoClase!!.getArbolVisual() )
            return raiz
        }
        raiz.children.add( TreeItem(identificador_clase!!.lexema))
        return raiz
    }

    fun analizarSemantica(errores: ArrayList<ErrorSemantico>, ts: TabladeSimbolos?) {

    }
    fun llenarTablaSimbolos(ts: TabladeSimbolos?) {
        // TODO Auto-generated method stub
    }

    fun traducir(): String {
        var code = ""
        var id = ""
        var cc = ""
        if (modificador!!.lexema.equals("PRIVADO")) {
            code = "private "
        }
        if (modificador!!.lexema.equals("PUBLICO")) {
            code = "public  "
        }
        if (identificador_clase != null) {
            id = " " + identificador_clase!!.lexema.replace("@", "").toLowerCase()
        }
        cc = cuerpoClase!!.traducir()
        return "$code  class$id(){ \n$cc\n }"
    }
}