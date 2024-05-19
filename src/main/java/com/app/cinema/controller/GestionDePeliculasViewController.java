package com.app.cinema.controller;

import com.app.cinema.dao.PeliculaDAO;
import com.app.cinema.dao.TrabajadorDAO;
import com.app.cinema.dao.UsuarioDAO;
import com.app.cinema.enums.Genero;
import com.app.cinema.enums.Rol;
import com.app.cinema.enums.TipoUsuario;
import com.app.cinema.model.Cliente;
import com.app.cinema.model.DNI;
import com.app.cinema.model.Pelicula;
import com.app.cinema.model.Trabajador;
import com.app.cinema.model.Usuario;
import com.app.cinema.util.AlertUtils;

import java.io.IOException;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class GestionDePeliculasViewController {
    @FXML
    private ComboBox<Genero> cmbxGeneroPelicula;
    @FXML
    private TableView<Pelicula> tableViewPeliculas;
    @FXML
    private TableColumn<Pelicula, String> tableColumnTitulo;
    @FXML
    private TableColumn<Pelicula, Genero> tableColumnGenero;
    @FXML
    private TableColumn<Pelicula, Double> tableColumnDuracion;
    @FXML
    private TableColumn<Pelicula, String> tableColumnDescripcion;
    @FXML
    private TableColumn<Pelicula, String> tableColumnFotoPortada;
    @FXML
    private Button añadirBoton;
    @FXML
    private Button actualizarBoton;
    @FXML
    private Button eliminarBoton;
    @FXML
    private TextField fieldTituloTxt;
    @FXML
    private TextField fieldDuracionTxt;  
    @FXML
    private TextField fieldPortadaTxt;  
    @FXML
    private TextField fieldDescripcionTxt;


    PeliculaDAO peliculaDAO = new PeliculaDAO();
    List<Pelicula> listaPeliculas = peliculaDAO.selectAll();

    private ObservableList<Pelicula> peliculas = FXCollections.observableArrayList(listaPeliculas);


    public void initialize(){
        initCmbxGeneroPelicula();
        this.tableColumnTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        this.tableColumnGenero.setCellValueFactory(new PropertyValueFactory<>("genero"));
        this.tableColumnDuracion.setCellValueFactory(new PropertyValueFactory<>("duracion"));
        this.tableColumnDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        this.tableColumnFotoPortada.setCellValueFactory(new PropertyValueFactory<>("fotoPortada"));
        this.tableViewPeliculas.setItems(peliculas);
    }

    @FXML
    private void seleccionarPeliculaTabla(MouseEvent event) throws IOException {
        Pelicula pelicula = this.tableViewPeliculas.getSelectionModel().getSelectedItem();
        if (pelicula != null) {
            this.cmbxGeneroPelicula.setValue(pelicula.getGenero());
            this.fieldTituloTxt.setText(pelicula.getTitulo());
            this.fieldDuracionTxt.setText(pelicula.getDuracion() + "");
            this.fieldDescripcionTxt.setText(pelicula.getDescripcion());
            this.fieldPortadaTxt.setText(pelicula.getFotoPortada());
        }
    }

    @FXML
    public void accionAñadirBoton(ActionEvent event) {
        try {
            Genero genero = this.cmbxGeneroPelicula.getValue();
            String titulo = this.fieldTituloTxt.getText();
            String duracion = this.fieldDuracionTxt.getText();
            String descripcion = this.fieldDescripcionTxt.getText();
            String fotoPortada = this.fieldPortadaTxt.getText();

            Pelicula pelicula = new Pelicula(0, fotoPortada, titulo, Double.parseDouble(duracion), descripcion, genero, null);
            if (peliculas.contains(pelicula)) {
                AlertUtils.mostrarVentanaError(AlertType.ERROR, "Error", "Error: Pelicula ya registrada en el sistema");
            } else {
                peliculaDAO.insert(pelicula);
                List<Pelicula> listPeliculasBD = peliculaDAO.selectAll();
                Pelicula ultimaPeliculaBD = listPeliculasBD.get(listPeliculasBD.size() - 1);
                Pelicula peliculaNueva = new Pelicula(ultimaPeliculaBD.getIdPelicula(), fotoPortada, titulo, Double.parseDouble(duracion), descripcion, genero, null);
                this.peliculas.add(peliculaNueva);
                this.tableViewPeliculas.setItems(this.peliculas);
                this.tableViewPeliculas.refresh();
                AlertUtils.mostrarVentanaExito(AlertType.INFORMATION, "Operacion Exitosa", "Pelicula añadida correctamente");
            }
        } catch (NumberFormatException e) {
            AlertUtils.mostrarVentanaError(AlertType.ERROR, "Error", "Error: Formato de Dato no válido");
        }
    }
    
    @FXML
    private void accionActualizarBoton(ActionEvent event) throws IOException {
        Pelicula pelicula = this.tableViewPeliculas.getSelectionModel().getSelectedItem();
        if (pelicula == null) {
            AlertUtils.mostrarVentanaError(AlertType.ERROR, "Error", "Error: Pelicula no seleccionado");
        } else {
            try {
                Genero genero = this.cmbxGeneroPelicula.getValue();
                String titulo = this.fieldTituloTxt.getText();
                String duracion = this.fieldDuracionTxt.getText();
                String descripcion = this.fieldDescripcionTxt.getText();
                String fotoPortada = this.fieldPortadaTxt.getText();

                //boolean correoDuplicado = trabajadores.stream().anyMatch(t -> !t.equals(trabajador) && t.getCorreo().equals(correo));
                boolean fotoPortadaDuplicada = peliculas.stream().anyMatch(p -> !p.equals(pelicula) && p.getFotoPortada().equals(fotoPortada));
    
                if (fotoPortadaDuplicada) {
                    AlertUtils.mostrarVentanaError(AlertType.ERROR, "Error", "Error: Foto de portada duplicada");
                } else {
                    pelicula.setTitulo(titulo);
                    pelicula.setDuracion(Double.parseDouble(duracion));
                    pelicula.setDescripcion(descripcion);
                    pelicula.setGenero(genero);
                    pelicula.setFotoPortada(fotoPortada);
                    peliculaDAO.update(pelicula);
                    AlertUtils.mostrarVentanaExito(AlertType.INFORMATION, "Operacion Exitosa", "Trabajador actualizado correctamente");
                    this.tableViewPeliculas.refresh();

                }
            } catch (NumberFormatException e) {
                AlertUtils.mostrarVentanaError(AlertType.ERROR, "Error", "Error: Formato de dato no valido");
            }
        }
    }
    

    @FXML
    private void accionEliminarBoton(ActionEvent event) {
        Pelicula pelicula = this.tableViewPeliculas.getSelectionModel().getSelectedItem();
        if (pelicula == null) {
            AlertUtils.mostrarVentanaError(AlertType.ERROR, "Error", "Error: Pelicula no seleccionada");
        } else {
            this.peliculas.remove(pelicula);
            this.tableViewPeliculas.setItems(peliculas);
            peliculaDAO.delete(pelicula);
            AlertUtils.mostrarVentanaExito(AlertType.INFORMATION, "Operacion Exitosa", "Pelicula eliminada correctamente");
            this.tableViewPeliculas.refresh();
        }
    }
    
    private void initCmbxGeneroPelicula() {
        this.cmbxGeneroPelicula.setItems(FXCollections.observableArrayList(Genero.values()));
    }
}
