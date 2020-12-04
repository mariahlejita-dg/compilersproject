package co.edu.uniquindio.compiladores.lexico.sintaxis

import co.edu.uniquindio.compiladores.lexico.lexico.Token
import co.edu.uniquindio.compiladores.lexico.semantica.Simbolo
import co.edu.uniquindio.compiladores.lexico.semantica.TabladeSimbolos
import javafx.scene.control.TreeItem
import java.util.ArrayList

class Asignacion (var tipoDato : Token, var identificadorVariable : Token, var operadorAsignacion : Token, var expresion : Expresion):Sentencia(){


override fun getArbolVisual() : TreeItem<String>{
    val raiz = TreeItem("Asignación")
        raiz.children.add(TreeItem("${identificadorVariable.lexema} : ${tipoDato.lexema}"))
        raiz.children.add( TreeItem(operadorAsignacion.lexema) )
        raiz.children.add(expresion.getArbolVisual())
    return raiz
}

    override fun traducir(): String {
        var code = ""
        var id = ""
        var tipo = ""
        if (tipoDato!!.lexema.equals("CADENA")) {
            tipo = "String "
        }
        if (tipoDato!!.lexema.equals("ENTERO")) {
            tipo = "int "
        }
        if (tipoDato!!.lexema.equals("BOOLEANO")) {
            tipo = "boolean "
        }
        if (tipoDato!!.lexema.equals("REAL")) {
            tipo = "float "
        }
        if (operadorAsignacion!!.lexema.equals(":+")) {
            code = "+="
        }
        if (operadorAsignacion!!.lexema.equals(":-")) {
            code = "-="
        }
        if (operadorAsignacion!!.lexema.equals("::")) {
            code = "="
        }
        if (operadorAsignacion!!.lexema.equals(":*")) {
            code = "*="
        }
        if (operadorAsignacion!!.lexema.equals(":/")) {
            code = "/="
        }
        id = identificadorVariable!!.lexema.replace("#", "")
        return tipo + " " + id + " " + code + " " + expresion.traducir() + ";"
    }

    fun analizarSemantica(error: ArrayList<String?>?, tS: TabladeSimbolos?, ambito: Simbolo?) {
        // TODO Auto-generated method stub
    }

    fun llenarTablaSimbolos(ts: TabladeSimbolos?, ambito: Simbolo?) {
        // TODO Auto-generated method stub
    }
}