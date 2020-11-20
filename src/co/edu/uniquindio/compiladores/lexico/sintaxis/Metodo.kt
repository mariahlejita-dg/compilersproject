package co.edu.uniquindio.compiladores.lexico.sintaxis

import co.edu.uniquindio.compiladores.lexico.lexico.Token
import javafx.scene.control.TreeItem
import javax.smartcardio.CardTerminal

class Metodo {
    private var modificador : Token ?= null
    private var tipoRetornno : Token ?= null
    private var identificadorMetodo : Token ?= null
    private var listaParametros = ArrayList<Parametro>()
    private var listaSentencias = ArrayList<Sentencia>()
    private var terminal : Token ?= null

    constructor()
    constructor(modificador : Token, tipoRetorno : Token, identificadorMetodo : Token): this(){
        this.modificador = modificador
        this.tipoRetornno = tipoRetorno
        this.identificadorMetodo = identificadorMetodo
    }
    constructor(modificador : Token, tipoRetorno : Token, identificadorMetodo : Token,
                listaParametros : ArrayList<Parametro>, listaSentencias : ArrayList<Sentencia>):this(){
        this.modificador = modificador
        this.tipoRetornno = tipoRetorno
        this.identificadorMetodo = identificadorMetodo
        this.listaParametros = listaParametros
        this.listaSentencias = listaSentencias
    }
    constructor(modificador : Token, tipoRetorno : Token, identificadorMetodo : Token,
                listaParametros : ArrayList<Parametro>):this(){
        this.modificador = modificador
        this.tipoRetornno = tipoRetorno
        this.identificadorMetodo = identificadorMetodo
        this.listaParametros = listaParametros
    }
    constructor(modificador : Token, listaSentencias : ArrayList<Sentencia>, tipoRetorno : Token, identificadorMetodo : Token):this(){
        this.modificador = modificador
        this.tipoRetornno = tipoRetorno
        this.identificadorMetodo = identificadorMetodo
        this.listaSentencias = listaSentencias
    }
   fun getArbolVisual() : TreeItem<String> {
        val raiz = TreeItem("Metodo")
        raiz.children.add( TreeItem(tipoRetornno!!.lexema))
        raiz.children.add( TreeItem(identificadorMetodo!!.lexema) )

        if( listaParametros != null){
            for (item in listaParametros){
                raiz.children.add( item.getArbolVisual() )
            }
        }
        if( listaSentencias != null){
            for (i in listaSentencias){
                raiz.children.add( i.getArbolVisual() )
            }
        }
        return raiz
    }
}
