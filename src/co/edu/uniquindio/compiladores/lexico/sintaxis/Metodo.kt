package co.edu.uniquindio.compiladores.lexico.sintaxis

import co.edu.uniquindio.compiladores.lexico.lexico.Token
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

}
