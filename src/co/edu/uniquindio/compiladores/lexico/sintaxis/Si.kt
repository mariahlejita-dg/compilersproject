package co.edu.uniquindio.compiladores.lexico.sintaxis

import co.edu.uniquindio.compiladores.lexico.lexico.Token
import co.edu.uniquindio.compiladores.lexico.semantica.Simbolo
import co.edu.uniquindio.compiladores.lexico.semantica.TabladeSimbolos
import javafx.scene.control.TreeItem
import java.util.ArrayList


class Si: Sentencia {
    private var si: Token? = null
    var expresion: ExpresionRelacional? = null
    var listaSentencias: ArrayList<Sentencia>? = null

    constructor(si: Token, expresion: ExpresionRelacional) : super() {
        this.si = si
        this.expresion = expresion
    }

    /**
     * @param si
     *
     * @param expresion
     *
     * @param listaSentencias
     */
    constructor(si: Token, expresion: ExpresionRelacional,listaSentencias: ArrayList<Sentencia>?
    ) : super() {
        this.si = si
        this.expresion = expresion
        this.listaSentencias = listaSentencias
    }



    override fun getArbolVisual() : TreeItem<String>
    {
            val raiz = TreeItem("Si")
            raiz.children.add(expresion!!.getArbolVisual())
            if (listaSentencias != null) {
                for (sentencia in listaSentencias!!) {
                    raiz.children.add(sentencia.getArbolVisual())
                }
            }
            return raiz
        }
    fun analizarSemantica(error: ArrayList<String?>?, tS: TabladeSimbolos?, ambito: Simbolo?) {
        // TODO Auto-generated method stub
    }

    fun llenarTablaSimbolos(ts: TabladeSimbolos?, ambito: Simbolo?) {
        // TODO Auto-generated method stub
    }

    override fun traducir(): String? {
        val code = "if"
        var sent: String? = ""
        for (i in listaSentencias!!.indices) {
            sent += if (i == listaSentencias!!.size - 1) {
                listaSentencias!![i].traducir()
            } else {
                listaSentencias!![i].traducir() + ", "
            }
        }
        return """$code (${expresion!!.traducir()} )  { 
$sent
 }"""
    }
}