package co.edu.uniquindio.compiladores.lexico.semantica


class TabladeSimbolos {

    var listaErrores: ArrayList<Error> = ArrayList()

    var listaSimbolos: ArrayList<Simbolo> = ArrayList()


    /**
     * Permite guardar un símbolo de tipo variable en la tabla de símbolos
     */

    fun guardarSimboloVariable(nombre: String, tipo: String, ambito: String, fila: Int, columna: Int): Simbolo? {


        for (s in listaSimbolos) {


            if (listaSimbolos != null) {

                if (s.nombre == nombre && s.ambito == ambito)


                    return s


            } else {


            }



        }
        return null
    }

    /**
     * Permite guardar un símbolo de tipo función en la tabla de símbolos
     */


    fun guardarSimboloFuncion(nombre: String, tipo: String, tiposParametros: ArrayList<String>): Simbolo? {

        for (s in listaSimbolos) {


            if (listaSimbolos != null) {

                if (s.nombre == nombre && s.tipoParamentos == tiposParametros)


                    return s

            } else {

            }



        }
        return null
    }

    fun buscarSimboloVariable(nombre: String, ambito: String): Simbolo? {

        for (simbolo in listaSimbolos) {

            if (simbolo.ambito != null) {

                if (nombre == simbolo.nombre && ambito == simbolo.ambito) {

                    return simbolo
                }
            }
        }

        return null
    }


    fun buscarSimboloFuncion(nombre: String, ambito: String, tiposParametros: ArrayList<String>): Simbolo? {

        for (simbolo in listaSimbolos) {

            if (simbolo.tipoParamentos != tiposParametros) {

                if (nombre == simbolo.nombre && tiposParametros == simbolo.tipoParamentos)

                    return simbolo


            }
        }
        return null
    }



}




