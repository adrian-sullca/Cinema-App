package com.app.cinema.view;

import java.io.IOException;

import com.app.cinema.App;

import javafx.fxml.FXML;

public class SecondaryController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("view/primary");
    }
}