package co.edu.uniquindio.compiladores.lexico.sintaxis

import co.edu.uniquindio.compiladores.lexico.lexico.Token
import co.edu.uniquindio.compiladores.lexico.semantica.Simbolo
import co.edu.uniquindio.compiladores.lexico.semantica.TabladeSimbolos
import javafx.scene.control.TreeItem

class Impresion : Sentencia{
    private var palabraReservada : Token ?= null
    private var exp : Expresion ?= null
    constructor(palabraReservada: Token, exp: Expresion){
        this.palabraReservada = palabraReservada
        this.exp = exp
    }
    override fun getArbolVisual() : TreeItem<String> {
        val raiz = TreeItem("Imprimir")
        var expresiones = ArrayList<Expresion>()
        expresiones.add(exp!!)
        for (e in expresiones) {
            raiz.children.add(e.getArbolVisual())
        }
        return raiz
    }
    fun analizarSemantica(error: java.util.ArrayList<String?>?, tS: TabladeSimbolos?, ambito: Simbolo?) {
        // TODO Auto-generated method stub
    }

    fun llenarTablaSimbolos(ts: TabladeSimbolos?, ambito: Simbolo?) {
        // TODO Auto-generated method stub
    }

    override fun traducir(): String {
        val expresion = exp!!.traducir()
        return "JOptionPane.showMessageDialog(null, $expresion);"
    }
}