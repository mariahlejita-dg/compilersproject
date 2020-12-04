package co.edu.uniquindio.compiladores.lexico.semantica

class Simbolo {
    var nombre: String = ""
    var tipo: String= " "
    var ambito:String?=""
    var fila:Int = 0
    var columna:Int = 0
    var tipoParamentos:ArrayList<String>? = null

    /**
     * Constructor para crear un simbolo tipo valor
     */

    constructor(nombre: String, tipodato: String,ambito:String,fila:Int,columna:Int){


        this.nombre = nombre
        this.tipo = tipodato
        this.ambito = ambito
        this.fila = fila
        this.columna = columna
    }

    /**
     * Constructor para crear un simbolo tipo metodo o funcion
     */

    constructor(nombre: String, tipodato: String, tipoParamentos:ArrayList<String>,ambito:String){


        this.nombre = nombre
        this.tipo = tipodato
        this.ambito = ambito
        this.tipoParamentos= tipoParamentos

    }

    override fun toString(): String {
        return  if(tipoParamentos==null){
             "Simbolo(nombre='$nombre', tipo='$tipo', ambito=$ambito, fila=$fila, columna=$columna)"
        }else{
            "Simbolo(nombre='$nombre', tipo='$tipo', ambito=$ambito, tipoParamentos=$tipoParamentos)"
        }



    }


}