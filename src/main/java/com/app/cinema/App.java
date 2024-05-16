package com.app.cinema;

import java.io.IOException;

import com.app.cinema.controller.LoginController;
import com.app.cinema.controller.RegisterController;
import com.app.cinema.controller.RootLayoutClienteController;
import com.app.cinema.controller.RootLayoutController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class App extends Application {

    private Stage primaryStage;
    private BorderPane rootLayoutBorderPane;
    
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Cinema");
        primaryStage.setResizable(false);
        iniciarRootLayout();
        mostrarIniciarSesionView();
    }
    
    public void iniciarRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("view/rootLayout.fxml"));
            rootLayoutBorderPane = (BorderPane) loader.load();
 
            Scene sceneRootLayout = new Scene(rootLayoutBorderPane);
            primaryStage.setScene(sceneRootLayout);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void mostrarIniciarSesionView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("view/iniciarSesion.fxml"));
            AnchorPane iniciarSesionAnchorPane = (AnchorPane) loader.load();
    
            // Obtener el controlador del FXML cargado
            LoginController controller = loader.getController();
            controller.setApp(this); // Pasar la referencia de App al controlador
    
            rootLayoutBorderPane.setCenter(iniciarSesionAnchorPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    // Método para cargar la vista de registro en el mismo AnchorPane
    public void mostrarRegistroView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("view/registroView.fxml"));
            AnchorPane registroAnchorPane = (AnchorPane) loader.load();

            // Reemplazamos el contenido del AnchorPane con la vista de registro
            rootLayoutBorderPane.setCenter(registroAnchorPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // Método para cerrar la ventana actual e iniciar la vista de RootLayoutCliente
    public void mostrarRootLayoutCliente() {
        try {
            FXMLLoader rootLoader = new FXMLLoader();
            rootLoader.setLocation(App.class.getResource("view/rootLayoutCliente.fxml"));
            BorderPane rootLayoutCliente = (BorderPane) rootLoader.load();

            // Crear un nuevo cargador para cargar el AnchorPane inicial
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("view/perfilCliente.fxml"));
            AnchorPane perfilClienteAnchorPane = (AnchorPane) loader.load();
            rootLayoutCliente.setRight(perfilClienteAnchorPane);

            // Obtener el controlador del FXML cargado
            RootLayoutClienteController controller = rootLoader.getController();
            controller.setRootLayoutCliente(rootLayoutCliente);

            Scene sceneRootLayoutCliente = new Scene(rootLayoutCliente);
            primaryStage.setScene(sceneRootLayoutCliente);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // Método para cerrar la ventana actual e iniciar la vista de RootLayoutCliente
    public void mostrarRootLayoutInicioSesion() {
        try {
            FXMLLoader rootLoader = new FXMLLoader();
            rootLoader.setLocation(App.class.getResource("view/rootLayout.fxml"));
            BorderPane rootLayout = (BorderPane) rootLoader.load();

            // Crear un nuevo cargador para cargar el AnchorPane inicial
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("view/iniciarSesion.fxml"));
            AnchorPane iniciarSesionAnchorPane = (AnchorPane) loader.load();
            rootLayout.setCenter(iniciarSesionAnchorPane);

            // Obtener el controlador del FXML cargado
            RootLayoutController controller = rootLoader.getController();
            controller.setRootLayoutIniciarSesion(rootLayout);

            Scene sceneRootLayoutCliente = new Scene(rootLayout);
            primaryStage.setScene(sceneRootLayoutCliente);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	public Stage getPrimaryStage() {
		return primaryStage;
	}

    public static void main(String[] args) {
        launch(args);
    }
}