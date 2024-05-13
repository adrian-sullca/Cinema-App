package com.app.cinema.view;

import java.io.IOException;

import com.app.cinema.App;

import javafx.fxml.FXML;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("view/secondary");
    }
}
