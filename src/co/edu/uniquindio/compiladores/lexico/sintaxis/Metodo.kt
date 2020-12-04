package co.edu.uniquindio.compiladores.lexico.sintaxis

import co.edu.uniquindio.compiladores.lexico.lexico.Token
import co.edu.uniquindio.compiladores.lexico.semantica.TabladeSimbolos
import javafx.scene.control.TreeItem

class Metodo(var nombre: Token, var parametros: ArrayList<Parametro>, var tipoRetorno: Token, var bloqueSentencia: ArrayList<Sentencia>, var retorno: Termino) {



    /**
     * @param modificador
     * @param tipoRetorno
     * @param identificadorMetodo
     *
     * @param listaParametros
     *
     * @param listaSentencias
     */


    fun getArbolVisual() : TreeItem<String> {
        val raiz = TreeItem("Metodo")
        if(tipoRetorno != null){
            raiz.children.add(TreeItem("${nombre.lexema} : ${tipoRetorno.lexema}"))
        }else{
            raiz.children.add(TreeItem(nombre.lexema))
        }
        if( !parametros.isEmpty()){
            for (item in parametros){
                raiz.children.add( item.getArbolVisual() )
            }
        }
            for (i in bloqueSentencia){
                raiz.children.add( i.getArbolVisual() )
            }
       if(retorno != null){
           raiz.children.add(TreeItem("Retorno"))
           raiz.children.add(retorno.getArbolVisual())
       }


        return raiz
    }
    fun analizarSemantica(error: java.util.ArrayList<String?>?, tS: TabladeSimbolos?) {}
    fun llenarTablaSimbolos(ts: TabladeSimbolos?) {}
    fun traducir(): String {
        var code = ""
        var tipo = ""
        var param = ""
        var sent = ""
        var st = ""
        //st = parametros.lexema.replace("$", "").toLowerCase()
        
        if (tipoRetorno.lexema.equals("CADENA")) {
            tipo = "String "
        }
        if (tipoRetorno.lexema.equals("ENTERO")) {
            tipo = "int "
        }
        if (tipoRetorno.lexema.equals("BOOLEANO")) {
            tipo = "boolean "
        }
        if (tipoRetorno.lexema.equals("NORETORNO")) {
            tipo = "void "
        }
        if (tipoRetorno.lexema.equals("REAL")) {
            tipo = "float "
        }
        for (i in parametros!!.indices) {
            if (i == parametros!!.size - 1) {
                param += parametros!![i].traducir()
            } else {
                param += parametros!![i].traducir().toString() + ", "
            }
        }
        for (i in bloqueSentencia!!.indices) {
            if (i == bloqueSentencia!!.size - 1) {
                sent += bloqueSentencia!![i].traducir()
            } else {
                sent += bloqueSentencia!![i].traducir().toString() + "\n"
            }
        }
        return "$code  $tipo$st ($param){ \n$sent\n }"
    }
}
