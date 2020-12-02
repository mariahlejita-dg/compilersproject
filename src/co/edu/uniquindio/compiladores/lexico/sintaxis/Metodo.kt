package co.edu.uniquindio.compiladores.lexico.sintaxis

import co.edu.uniquindio.compiladores.lexico.lexico.Token
import javafx.scene.control.TreeItem

class Metodo(var nombre: Token, var parametros: ArrayList<Parametro>, var tipoRetorno: Token, var bloqueSentencia: ArrayList<Sentencia>, var retorno: Termino) {



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
}
