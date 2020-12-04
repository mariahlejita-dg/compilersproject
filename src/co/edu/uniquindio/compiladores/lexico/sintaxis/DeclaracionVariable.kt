package co.edu.uniquindio.compiladores.lexico.sintaxis

import co.edu.uniquindio.compiladores.lexico.lexico.Token
import co.edu.uniquindio.compiladores.lexico.semantica.Simbolo
import co.edu.uniquindio.compiladores.lexico.semantica.TabladeSimbolos
import javafx.scene.control.TreeItem
import java.util.ArrayList

class DeclaracionVariable (var identificadorVariable : Token, var tipoDato : Token ) : Sentencia() {
    override fun getArbolVisual() : TreeItem<String>{
        val raiz = TreeItem( "Declaracion Variable" )
        raiz.children.add( TreeItem(identificadorVariable.lexema) )
        raiz.children.add( TreeItem(tipoDato.lexema) )
        return raiz
    }

    fun analizarSemantica(error: ArrayList<String?>?, tS: TabladeSimbolos?, ambito: Simbolo?) {
        // TODO Auto-generated method stub
    }

    fun llenarTablaSimbolos(ts: TabladeSimbolos?, ambito: Simbolo?) {
        // TODO Auto-generated method stub
    }

    override fun traducir(): String {
        var code = ""
        var st = ""
        if (tipoDato.lexema.equals("CADENA")) {
            code = "String "
        }
        if (tipoDato.lexema.equals("ENTERO")) {
            code = "int "
        }
        if (tipoDato.lexema.equals("BOOLEANO")) {
            code = "boolean "
        }
        if (tipoDato.lexema.equals("REAL")) {
            code = "float "
        }
        st = identificadorVariable.lexema.replace("#", "")
        return "$code $st ;"
    }
}