package co.edu.uniquindio.compiladores.lexico.controladores

import co.edu.uniquindio.compiladores.lexico.lexico.AnalizadorLexico
import co.edu.uniquindio.compiladores.lexico.lexico.Token
import javafx.collections.FXCollections
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.*
import javafx.scene.control.cell.PropertyValueFactory
import java.net.URL
import java.util.*

class AnalizadorController: Initializable {

    @FXML lateinit var txtCodigo : TextArea
    @FXML lateinit var tablaSimbolos : TableView<Token>
    @FXML lateinit var columLexema : TableColumn<Token,String>
    @FXML lateinit var columFila : TableColumn<Token,Int>
    @FXML lateinit var columColumna : TableColumn<Token,Int>
    @FXML lateinit var columCategoria : TableColumn<Token,String>
    @FXML lateinit var tableErrores : TableView<Token>
    @FXML lateinit var columLexemaError : TableColumn<Token,String>
    @FXML lateinit var columFilaError : TableColumn<Token,String>
    @FXML lateinit var columColumnaError : TableColumn<Token,String>
    @FXML lateinit var columCategoriaError : TableColumn<Token,String>
    @FXML lateinit var tabCompilador : Tab
    @FXML lateinit var tabSimbolos : Tab
    @FXML lateinit var tabErrores : Tab
    @FXML lateinit var tapPane : TabPane



    override fun initialize(location: URL?, resources: ResourceBundle?) {
        //Tabla de simbolos
        columLexema.cellValueFactory = PropertyValueFactory("lexema")
        columFila.cellValueFactory = PropertyValueFactory("fila")
        columColumna.cellValueFactory = PropertyValueFactory("columna")
        columCategoria.cellValueFactory = PropertyValueFactory("categoria")
        //Tabla de errores
        columLexemaError.cellValueFactory = PropertyValueFactory("lexema")
        columFilaError.cellValueFactory = PropertyValueFactory("fila")
        columColumnaError.cellValueFactory = PropertyValueFactory("columna")
        columCategoriaError.cellValueFactory = PropertyValueFactory("categoria")
    }

    fun abrirArchivo(actionEvent: ActionEvent) {

    }

    fun guardarArchivo(actionEvent: ActionEvent) {

    }

    fun nuevoArchivo(actionEvent: ActionEvent) {
        txtCodigo.text = ""
        txtCodigo.requestFocus()
        tableErrores.setItems(FXCollections.observableArrayList());
        tablaSimbolos.setItems(FXCollections.observableArrayList());
    }
    @FXML
    fun compilar(actionEvent: ActionEvent) {
        if(txtCodigo.text.length > 0){
            val lexicoA = AnalizadorLexico(txtCodigo.text)
            lexicoA.analizar()
            print(lexicoA.listaErrores)
            print(lexicoA.listaSimbolos)
            tablaSimbolos.items =FXCollections.observableArrayList(lexicoA.listaSimbolos)
            tableErrores.items = FXCollections.observableArrayList(lexicoA.listaErrores)

        }
    }


}