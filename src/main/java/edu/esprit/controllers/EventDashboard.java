package edu.esprit.controllers;

import edu.esprit.entities.Evenement;
import edu.esprit.services.ServiceEvenement;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
public class EventDashboard implements Initializable {
    @FXML
    private VBox eventsLayout;
    private Scene currentScene;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Evenement> evenements = new ArrayList<>(getEvenements());
        for (Evenement evenement : evenements) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL location = getClass().getResource("/EventItem.fxml");
            fxmlLoader.setLocation(location);

            try {
                GridPane gridPane = fxmlLoader.load();
                EventItem eventItemController = fxmlLoader.getController();
                eventItemController.setData(evenement);
                eventsLayout.getChildren().add(gridPane);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private List<Evenement> getEvenements() {
        List<Evenement> evenementList = new ArrayList<>();
        ServiceEvenement serviceEvenement = new ServiceEvenement();
        Set<Evenement> evenements = serviceEvenement.getAll();

        for (Evenement evenement : evenements) {
            item(evenement, evenementList);
        }

        return evenementList;
    }

    void item(Evenement evenement, List<Evenement> evenementList) {
        evenement.setNomEvent(evenement.getNomEvent());
        evenement.setDateEtHeureDeb(evenement.getDateEtHeureDeb());
        evenement.setDateEtHeureFin(evenement.getDateEtHeureFin());
        evenement.setCapaciteMax(evenement.getCapaciteMax());
        evenement.setCategorie(evenement.getCategorie());
        evenementList.add(evenement);
    }

    @FXML
    void AjouterEvenementClick(ActionEvent event) {
        try {
            // Charger le fichier FXML de l'interface d'ajout d'événement
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterEvent.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène
            AjouterEvent controller = loader.getController();

            // Obtenir la scène actuelle à partir de l'un des éléments de l'interface actuelle
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL); // Rend la fenêtre modale
            stage.setTitle("Ajouter événement");
            stage.setScene(new Scene(root));

            // Afficher la fenêtre de ajout
            stage.showAndWait();
        } catch (IOException e) {
            // Gérer les exceptions liées au chargement de l'interface
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de navigation");
            alert.setContentText("Une erreur s'est produite lors de la navigation vers l'interface d'ajout d'événement.");
            alert.show();
        }
    }
    @FXML
    void AjouterVoteClick(ActionEvent event) {
        try {
            // Charger le fichier FXML de l'interface d'ajout d'événement
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterVote.fxml"));
            Parent root = loader.load();

            AjouterVote controller = loader.getController();

            // Obtenir la scène actuelle à partir de l'un des éléments de l'interface actuelle
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL); // Rend la fenêtre modale
            stage.setTitle("Ajouter proposition");
            stage.setScene(new Scene(root));

            // Afficher la fenêtre de ajout
            stage.showAndWait();

        } catch (IOException e) {
            // Gérer les exceptions liées au chargement de l'interface
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de navigation");
            alert.setContentText("Une erreur s'est produite lors de la navigation vers l'interface d'ajout de proposition.");
            alert.show();
        }
    }
    public void loadEvents() {
        // Effacer les événements existants de l'interface
        eventsLayout.getChildren().clear();

        // Charger à nouveau la liste des événements à partir de la base de données
        List<Evenement> evenements = new ArrayList<>(getEvenements());
        for (Evenement evenement : evenements) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL location = getClass().getResource("/EventItem.fxml");
            fxmlLoader.setLocation(location);

            try {
                GridPane gridPane = fxmlLoader.load();
                EventItem eventItemController = fxmlLoader.getController();
                eventItemController.setData(evenement);
                eventsLayout.getChildren().add(gridPane);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public BorderPane firstborderpane;
    @FXML
    private AnchorPane MainAnchorPaneBaladity;
    @FXML
    private BorderPane SecondBorderPane;
    @FXML
    private VBox MainLeftSidebar;
    private boolean isSidebarVisible = true;

    @FXML
    void BTNToggleSidebar(ActionEvent event) {
        TranslateTransition sideBarTransition = new TranslateTransition(Duration.millis(400), MainLeftSidebar);
        double sidebarWidth = MainLeftSidebar.getWidth();
        if (isSidebarVisible) {
            // Hide sidebar
            sideBarTransition.setByX(-sidebarWidth);
            isSidebarVisible = false;
            // Adjust the width of SecondBorderPane
            SecondBorderPane.setPrefWidth(SecondBorderPane.getWidth() + sidebarWidth);
            // Translate SecondBorderPane to the left to take the extra space
            TranslateTransition borderPaneTransition = new TranslateTransition(Duration.millis(250), SecondBorderPane);
            borderPaneTransition.setByX(-sidebarWidth);
            borderPaneTransition.play();
        } else {
            // Show sidebar
            sideBarTransition.setByX(sidebarWidth);
            isSidebarVisible = true;
            // Adjust the width of SecondBorderPane
            SecondBorderPane.setPrefWidth(SecondBorderPane.getWidth() - sidebarWidth);
            // Reset the translation of SecondBorderPane to 0
            TranslateTransition borderPaneTransition = new TranslateTransition(Duration.millis(250), SecondBorderPane);
            borderPaneTransition.setByX(sidebarWidth);
            borderPaneTransition.play();
        }

        sideBarTransition.play();
    }

    public void BTNGestionEvennement(ActionEvent actionEvent) {

    }

    public void BTNGestionUser(ActionEvent actionEvent) {
    }

    public void BTNGestionRec(ActionEvent actionEvent) {

    }

    public void BTNGestionAct(ActionEvent actionEvent) {

    }

    public void BTNGestionEquipement(ActionEvent actionEvent) {
    }

    public void BTNGestionTache(ActionEvent actionEvent) {

    }


}


