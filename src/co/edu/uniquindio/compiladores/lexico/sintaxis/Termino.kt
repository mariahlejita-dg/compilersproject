package co.edu.uniquindio.compiladores.lexico.sintaxis

import co.edu.uniquindio.compiladores.lexico.lexico.Categoria
import co.edu.uniquindio.compiladores.lexico.lexico.Token
import co.edu.uniquindio.compiladores.lexico.semantica.Simbolo
import co.edu.uniquindio.compiladores.lexico.semantica.TabladeSimbolos
import javafx.scene.control.TreeItem
import java.util.ArrayList

class Termino (var termino: Token) {

    fun getArbolVisual() : TreeItem<String> {
        val raiz = TreeItem( "Termino" )
        if( termino != null ){
            raiz.children.add( TreeItem(termino.lexema) )
        }
        return raiz
    }

    fun traducir(): String {
        var st = ""
        if (termino != null && termino.categoria === Categoria.IDENTIFICADOR_VARIABLE) {
            st = termino.lexema.replace("#", "")
            return st
        }
        return termino.lexema
    }

    fun analizarSemantica(errores: ArrayList<String?>, ts: TabladeSimbolos, ambito: Simbolo) {
        if (termino != null) {
            for (simbolo in ts.listaSimbolos) {
                if (simbolo.ambito!= null) {
                    if (termino.lexema.equals(simbolo.nombre) && simbolo.tipo.equals(simbolo.tipo)
                        && simbolo.ambito.equals(simbolo.ambito)
                    ) {
                        return
                    }
                } else {
                    if (termino.lexema.equals(simbolo.nombre) && simbolo.ambito.equals(simbolo.nombre)) {
                        return
                    }
                }
            }

        }
    }
}