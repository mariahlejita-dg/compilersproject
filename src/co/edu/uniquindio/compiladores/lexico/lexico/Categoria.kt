package co.edu.uniquindio.compiladores.lexico.lexico

enum class Categoria {

    ENTERO,
    REAL,
    CADENA_CARACTERES,
    CARACTER,
    AGRUPADORES_APERTURA,
    AGRUPADORES_CIERRE,
    OPERADOR_ARITMETICO,
    OPERADOR_RELACIONAL,
    OPERADOR_LOGICO,
    OPERADOR_ASIGNACION,
    PALABRA_RESERVADA,
    DESCONOCIDO,
    DOS_PUNTOS,
    IDENTIFICADOR_METODO,
    IDENTIFICADOR_CLASE,
    IDENTIFICADOR_VARIABLE,
    IDENTIFICADOR_TERMINAL,
    SEPARADOR_SENTENCIA,
    CONCATENACION,
    COMENTARIO_LINEA,
    COMENTARIO_BLOQUE,
    PUNTO,
    INCREMENTO,
    DECREMENTO,
    FINCODIGO

}