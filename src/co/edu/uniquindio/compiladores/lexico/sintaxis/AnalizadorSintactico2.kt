package co.edu.uniquindio.compiladores.lexico.sintaxis

import co.edu.uniquindio.compiladores.lexico.lexico.Categoria
import co.edu.uniquindio.compiladores.lexico.lexico.Token

class AnalizadorSintactico2 (var listaTokens:ArrayList<Token>) {
    var posicionActual = 0
    var tokenActual = listaTokens[posicionActual]
    var listaErrores =  ArrayList<ErrorSintactico>()

    fun obtenerSiguienteToken(){
        posicionActual++
        if(posicionActual < listaTokens.size){
            tokenActual = listaTokens[posicionActual]
        }
    }

    fun reportarError (mensaje : String){
        listaErrores.add(ErrorSintactico(mensaje))
    }

    fun esUnidadDeCompilacion() : UnidadDeCompilacion? {
        val listaMetodos : ArrayList<Metodo> = esListaMetodos()
        return if (listaMetodos.size > 0){
            print("Metodos: \n"+listaMetodos[0].getArbolVisual())
            UnidadDeCompilacion(listaMetodos)
        }else null
    }

    fun esListaMetodos() : ArrayList<Metodo>{
        val lista : ArrayList<Metodo> = ArrayList<Metodo>()
        var m : Metodo? = esMetodo()

        while (m != null){
            lista.add(m)
            m = esMetodo()
        }
        return lista

    }
    fun esMetodo(): Metodo? {
        if(tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.lexema == "PUBLICO"){
            obtenerSiguienteToken()
            if(tokenActual.categoria == Categoria.IDENTIFICADOR_METODO){
                val nombre = tokenActual
                obtenerSiguienteToken()
                if(tokenActual.categoria == Categoria.AGRUPADORES_APERTURA && tokenActual.lexema == ".("){
                    obtenerSiguienteToken()
                    val parametros : ArrayList<Parametro> = esListaParametros()
                    if(tokenActual.categoria == Categoria.AGRUPADORES_CIERRE && tokenActual.lexema == ".)"){
                        obtenerSiguienteToken()
                        var tipoRetorno : Token? = null
                        if (tokenActual.categoria == Categoria.DOS_PUNTOS){
                            obtenerSiguienteToken()
                            tipoRetorno = esTipoRetorno()
                            obtenerSiguienteToken()
                            if(tipoRetorno == null){
                                reportarError("Falta tipo retorno del metodo")
                            }
                        }
                        val bloqueSentencias : ArrayList<Sentencia> = esBloqueSentencias()
                        if(bloqueSentencias != null){
                            return Metodo(nombre,parametros, tipoRetorno!!, bloqueSentencias)
                        }else {
                            reportarError("Faltó el bloque de sentencias en el metodo")
                        }

                    }else {
                        reportarError("Falta parentesis derecho")
                    }
                }else {
                    reportarError("Falta parentesis izquierdo")
                }
            }else {
                reportarError("Falta el nombre de la finción")
            }
        }
        return null
    }

    fun esTipoRetorno() : Token? {
        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA) {

            if (tokenActual.lexema == "ENTERO" || tokenActual.lexema == "CADENA"
                || tokenActual.lexema == "REAL" || tokenActual.lexema == "NORETORNO"
                || tokenActual.lexema == "BOOLEANO"
            ) {
                return tokenActual;
            }

        }
        return null
    }

    fun esListaParametros() : ArrayList<Parametro>{
        val parametros : ArrayList<Parametro> = ArrayList<Parametro>()
        var p : Parametro? = esParametro()
        while(p != null){
            parametros.add(p)
            p = esParametro()
        }
        return parametros
    }

    fun esParametro() : Parametro? {

        if(tokenActual.categoria == Categoria.IDENTIFICADOR_VARIABLE){
            var identificadorVariable = tokenActual
            obtenerSiguienteToken()
            var tipoDato: Token? = null
            if(tokenActual.categoria == Categoria.DOS_PUNTOS ){
                obtenerSiguienteToken()
                tipoDato = esTipoDato()
                if(tipoDato != null){
                    obtenerSiguienteToken()
                    return Parametro(identificadorVariable, tipoDato)
                }else{
                    reportarError("Faltó el tipo de variable")
                }

            }else {
                reportarError("Faltan los dos puntos (.:)")
            }
        }
        return null
    }
    fun esTipoDato() : Token? {
        if (tokenActual.categoria === Categoria.PALABRA_RESERVADA) {
            if (tokenActual.lexema == "ENTERO" || tokenActual.lexema == "CADENA_CARACTERES" || tokenActual.lexema == "REAL" || tokenActual.lexema == "BOOLEANO" || tokenActual.lexema == "CARACTER") {
                return tokenActual
            }
        }
        return null
    }

    fun esBloqueSentencias() : ArrayList<Sentencia>{
        val sentencias : ArrayList<Sentencia> = ArrayList<Sentencia>()
        var s : Sentencia? = esSentencia()
        while(s != null){
            sentencias.add(s)
            s = esSentencia()
        }
        return sentencias
    }

    fun esSentencia() : Sentencia? {
        var sentencia : Sentencia?
        sentencia = esAsignacion()
        if(sentencia != null){
            return sentencia
        }
        return null
    }

    fun esAsignacion() : Sentencia? {
        var tipoDato : Token? = esTipoDato()
        if(tipoDato != null){
            if(tokenActual.categoria == Categoria.IDENTIFICADOR_VARIABLE){
                var identificadorVariable = tokenActual
                obtenerSiguienteToken()
                if(tokenActual.categoria == Categoria.OPERADOR_ASIGNACION){
                    var operador = tokenActual
                    obtenerSiguienteToken()
                    var expresion: Expresion? = esExpresion()
                    if(expresion != null){
                        return Asignacion(tipoDato, identificadorVariable, operador, expresion)
                    }else {
                        reportarError("Falta la expresión")
                    }
                }else {
                    reportarError("Falta operador de Asignacion")
                }
            }else {
                reportarError("Falta el identificador de variable")
            }
        }
        return null
    }
    fun esExpresion() : Expresion? {
        var expresion : Expresion? = null

        expresion =  esExpresionAritmetica()
        if(expresion != null){
            return expresion
        }

        return expresion
    }

    fun esExpresionAritmetica() : Expresion? {
        var termino : Termino? = esTermino()
        if(termino != null){
            if(tokenActual.categoria == Categoria.OPERADOR_ARITMETICO){
                val operAritm = tokenActual
                obtenerSiguienteToken()
                val term2: Termino? = esTermino()
                if (term2 != null) {
                    return ExpresionAritmetica(termino, operAritm, term2)
                } else if (tokenActual.categoria === Categoria.AGRUPADORES_APERTURA && tokenActual.lexema == ".(") {
                    val agruIzq = tokenActual
                    obtenerSiguienteToken()
                    var exprArit: Expresion? = esExpresionAritmetica() as ExpresionAritmetica
                    if (exprArit != null) {
                        if (tokenActual.categoria === Categoria.AGRUPADORES_CIERRE && tokenActual.lexema == ".)") {
                            val agruDer = tokenActual
                            obtenerSiguienteToken()
                            return ExpresionAritmetica(termino, operAritm, exprArit)
                        } else {
                            reportarError("Falta agrupador derecho en EA")
                        }
                    } else {
                        reportarError("falta expresion aritmetica en EA")
                    }
                } else {
                    reportarError("Falta agrupador izquierdo en EA")
                }
            } else {

                return ExpresionAritmetica(termino)
            }
        }
        return null
    }

    fun esTermino() : Termino? {
        var termino: Token? = null
        if (tokenActual.categoria === Categoria.IDENTIFICADOR_VARIABLE) {
            termino = tokenActual
            obtenerSiguienteToken()
            return Termino(termino)
        } else if (tokenActual.categoria === Categoria.ENTERO) {
            termino = tokenActual
            obtenerSiguienteToken()
            return Termino(termino)
        } else if (tokenActual.categoria === Categoria.REAL) {
            termino = tokenActual
            obtenerSiguienteToken()
            return Termino(termino)
        }
        return null
    }
}