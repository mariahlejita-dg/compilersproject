package co.edu.uniquindio.compiladores.lexico.sintaxis

import co.edu.uniquindio.compiladores.lexico.semantica.TabladeSimbolos



import javafx.scene.control.TreeItem
import kotlin.collections.ArrayList


class UnidadDeCompilacion {
    private var paquete: Paquete? = null
    private var listaImportaciones: ArrayList<Importacion>? = null
    private var clase: Clase? = null
    private var tabladeSimbolos:TabladeSimbolos? = null

    constructor()

    constructor(paquete: Paquete?, listaImportaciones: ArrayList<Importacion>, clase: Clase):this(){
        this.paquete = paquete
        this.listaImportaciones = listaImportaciones
        this.clase = clase
    }

    constructor(paquete: Paquete?, clase: Clase):this(){
        this.paquete = paquete
        this.clase = clase
    }

    constructor(listaMetodos: ArrayList<Metodo>)

    fun getArbolVisual() : TreeItem<String>{
        val raiz = TreeItem("Unidad de Compilacion")

        raiz.children.add(paquete!!.getArbolVisual())
        if(listaImportaciones != null){
            for (i in listaImportaciones!!){
                raiz.children.add(i.getArbolVisual())
            }
        }
        raiz.children.add(clase!!.getArbolVisual())


        return raiz
    }
    fun analizarSemantica(ts: TabladeSimbolos?, errores: ArrayList<ErrorSemantico>) {
        clase!!.analizarSemantica(errores, ts)
    }

    fun llenarTablaSimbolos(ts: TabladeSimbolos?) {
        clase!!.cuerpoClase!!.llenarTablaSimbolos(ts)
    }

    fun traducir(): String {
        var code = ""
        var p = ""
        p = paquete!!.traducir()
        for (i in listaImportaciones!!.indices) {
            code += if (i == listaImportaciones!!.size - 1) {
                listaImportaciones!![i].traducir()
            } else {
                listaImportaciones!![i].traducir() + ", "
            }
        }
        return """$p 
$code
${clase!!.traducir()}"""
    }


}
