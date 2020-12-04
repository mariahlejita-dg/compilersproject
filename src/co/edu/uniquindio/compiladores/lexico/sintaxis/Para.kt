package co.edu.uniquindio.compiladores.lexico.sintaxis

import co.edu.uniquindio.compiladores.lexico.lexico.Token
import co.edu.uniquindio.compiladores.lexico.semantica.Simbolo
import co.edu.uniquindio.compiladores.lexico.semantica.TabladeSimbolos
import javafx.scene.control.TreeItem
import java.util.ArrayList
import javax.swing.tree.DefaultMutableTreeNode

class Para: Sentencia {
    private var para: Token? = null
    var expresion: Expresion? = null
    private var decremento: SentenciaDecremento? = null
    private var incremento: SentenciaIncremento? = null
    private var listaSentencias: ArrayList<Sentencia>? = null

    constructor(para: Token, expresion: Expresion, decremento: SentenciaDecremento,
                incremento: SentenciaIncremento,listaSentencias: ArrayList<Sentencia>) {
        this.para = para
        this.expresion = expresion
        this.decremento=decremento
        this.incremento=incremento
        this.listaSentencias=listaSentencias
    }

    override fun getArbolVisual() : TreeItem<String>
    {
            val raiz = TreeItem("Para")
            raiz.children.add(expresion!!.getArbolVisual())
            if (decremento != null) {
                if (listaSentencias != null) {
                    for (sentencia in listaSentencias!!) {
                        raiz.children.add(sentencia.getArbolVisual())
                    }
                }
            }
            if (incremento != null) {
                if (listaSentencias != null) {
                    for (sentencia in listaSentencias!!) {
                        raiz.children.add(sentencia.getArbolVisual())
                    }
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

    override fun traducir(): String {
        //TODO
        val code = "for"
        var sent = ""
        if (decremento != null) {
        }
        for (i in listaSentencias!!.indices) {
            if (i == listaSentencias!!.size - 1) {
                sent += listaSentencias!![i].traducir()
            } else {
                sent += listaSentencias!![i].traducir().toString() + ", "
            }
        }
        return """$code (${expresion!!.traducir()}; ) {
$sent
 }"""
    }

}