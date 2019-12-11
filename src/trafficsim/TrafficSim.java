/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trafficsim;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 *
 * @author Luc
 */
public class TrafficSim extends Application {

  

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));

        Parent root = (Parent) loader.load();
        Scene scene = new Scene(root);
        MainController controller = (MainController) loader.getController();
        controller.setStage(stage);
      
        Screen screen = Screen.getPrimary();

        stage.setTitle("Traffic Signal Sim");

        javafx.geometry.Rectangle2D bounds = screen.getVisualBounds();
        stage.setX(bounds.getMinX());
        stage.setY(bounds.getMinY());
       stage.setWidth(1169); //hide the light debug pane
//        stage.setWidth(1600); //enable for debug
        stage.setHeight(760);

        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
      
    }

}
