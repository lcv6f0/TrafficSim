/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trafficsim;

import Core.Car;
import Core.Import;
import Core.Lane;
import Core.Light;
import Core.Roads;
import javafx.scene.control.TextField;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Path;
import javafx.scene.shape.Shape;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author Luc
 */
public class MainController implements Initializable {

    private final int numcar = 2000;
    @FXML
    private Circle light01, light02, light03, light04, light05, light06, light07, light08, light09, light10, light11, light12, light13, light14, light15, light16, light17, light18, light19, light20;

    @FXML
    private GridPane prefGrid;
    @FXML
    private ImageView img;

    @FXML

    private AnchorPane mainPane;
    @FXML
    private Button start;

    @FXML
    //for debug
    private CheckBox g1, g2, g3, g4, g5, g6, g7, g8, g9,
            g10, g11, g12, g13, g14, g15, g16, g17, g18,
            g19, g20;
    private ArrayList<Light> listOfLightObj = new ArrayList<>();

    private Light l1, l2, l3, l4, l5, l6, l7, l8, l9, l10, l11, l12, l13, l14, l15, l16, l17, l18, l19, l20;

    private Stage stage;
    private final String image_Location = (System.getenv("localappdata")) + (System.getProperty("file.separator")) + ("Traffic Simulator") + (System.getProperty("file.separator")) + ("resources") + "\\map\\map.jpeg";
    private final String car_Location_Root = (System.getenv("localappdata")) + (System.getProperty("file.separator")) + ("Traffic Simulator") + (System.getProperty("file.separator")) + ("resources\\cars\\");
    private final String propertyLocation = (System.getenv("localappdata")) + (System.getProperty("file.separator")) + ("Traffic Simulator") + (System.getProperty("file.separator")) + ("config")
            + (System.getProperty("file.separator")) + ("traffic.properties");
    ;

    private Roads roads;

    //Remember to  add the vars for optim 
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        addListener();

        Platform.runLater(() -> {
            start.requestFocus();
            start.setOnKeyReleased((d) -> {
                if (d.getCode().equals(KeyCode.ENTER)) {
                    start.fire();
                }
            });
        });
        try {
            mainPane.getChildren().forEach((node) -> {
                if ((node instanceof Line || node instanceof Shape || node instanceof Path) && !(node instanceof Circle)) {
                    node.setVisible(false);
                }
                if (node instanceof Circle) {

                    ((Circle) node).setFill(Paint.valueOf("red"));

                }

            });
        } catch (Exception d) {
            System.err.println(d.getMessage());
        }
        try (FileInputStream d1 = new FileInputStream(image_Location)) {
            img.setImage(new Image((InputStream) d1));
        } catch (Exception b) {

        }
        prefGrid.getChildren().forEach((node) -> {
            if (node instanceof TextField) {
                ((TextField) node).setText("5");
            }
        });
        prefGrid.setVisible(false); // cmment for debug
        setInitColor();
        setRoadds();
        File f = new File(car_Location_Root);
        final String[] AllFIles = f.list();

        Thread test = new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < numcar; i++) {
//                    new Thread() {
//                        @Override
//                        public void run() {
                    try (FileInputStream d1 = new FileInputStream(car_Location_Root + AllFIles[new Random().nextInt(100) % 10])) {
                        ImageView d = new ImageView();
                        d.setImage(new Image((InputStream) d1));
//                            d.setFitWidth(81);
//                            d.setFitHeight(32);
                        d.setFitWidth(50);
                        d.setFitHeight(20);
                        d.setX(500);
                        d.setY(318 - 2);
                        d.setRotate(180);
                        d.setVisible(false);

                        Car s = new Car((Roads) roads.clone(), d);
                        s.setCarName("car " + i);
                        s.setMainPane(mainPane);
                        s.starter();
                        Platform.runLater(() -> {
                            mainPane.getChildren().add(d);
                        });
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }

            }
        };
        test.run();
    }

    private boolean populateLight() {
        Import p = new Import();
        Properties properties;
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a file");
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            properties = p.Load(file);

            l1 = new Light((Properties) properties.clone(), light01);
            l2 = new Light((Properties) properties.clone(), light02);
            l3 = new Light((Properties) properties.clone(), light03);
            l4 = new Light((Properties) properties.clone(), light04);
            l5 = new Light((Properties) properties.clone(), light05);
            l6 = new Light((Properties) properties.clone(), light06);
            l7 = new Light((Properties) properties.clone(), light07);
            l8 = new Light((Properties) properties.clone(), light08);
            l9 = new Light((Properties) properties.clone(), light09);
            l10 = new Light((Properties) properties.clone(), light10);
            l11 = new Light((Properties) properties.clone(), light11);
            l12 = new Light((Properties) properties.clone(), light12);
            l13 = new Light((Properties) properties.clone(), light13);
            l14 = new Light((Properties) properties.clone(), light14);
            l15 = new Light((Properties) properties.clone(), light15);
            l16 = new Light((Properties) properties.clone(), light16);
            l17 = new Light((Properties) properties.clone(), light17);
            l18 = new Light((Properties) properties.clone(), light18);
            l19 = new Light((Properties) properties.clone(), light19);
            l20 = new Light((Properties) properties.clone(), light20);

            listOfLightObj.add(l1);
            listOfLightObj.add(l2);
            listOfLightObj.add(l3);
            listOfLightObj.add(l4);
            listOfLightObj.add(l5);
            listOfLightObj.add(l6);
            listOfLightObj.add(l7);
            listOfLightObj.add(l8);
            listOfLightObj.add(l9);
            listOfLightObj.add(l10);
            listOfLightObj.add(l11);
            listOfLightObj.add(l12);
            listOfLightObj.add(l13);
            listOfLightObj.add(l14);
            listOfLightObj.add(l15);
            listOfLightObj.add(l16);
            listOfLightObj.add(l17);
            listOfLightObj.add(l18);
            listOfLightObj.add(l19);
            listOfLightObj.add(l20);

            return true;
        }
        return false;
    }

    /**
     * Used only for debug
     */
    private void addListener() {
        prefGrid.getChildren().forEach((node) -> {
            if (node instanceof TextField) {
                ((TextField) node).focusedProperty().addListener((ChangeListener<Boolean>) (ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) -> {
                    if (!((TextField) node).getText().equals("")) {
                        try {
                            if (Integer.parseInt(((TextField) node).getText()) > 15 && Integer.parseInt(((TextField) node).getText()) <= 120) {
                                ((TextField) node).setStyle("-fx-border-color: green");
                            } else {
                                ((TextField) node).setStyle("-fx-border-color: red");
                            }
                        } catch (NumberFormatException s) {
                            ((TextField) node).setStyle("-fx-border-color: red");
                        }
                    }
                });
            }
        });
    }

    private void setRoadds() {

        roads = new Roads();
        Lane ln1 = new Lane("lane1", 0, 505, 115, 505, 0, 0);
        ln1.setStartingPoint(true);
        ln1.setDisburseRate(4);
        ln1.setLight(light01);

        Lane ln2 = new Lane("lane2", 0, 467, 120, 467, 0, 0);
        ln2.setStartingPoint(true);
        ln2.setLight(light02);
        ln2.setDisburseRate(4);

        Lane ln3 = new Lane("lane3", 0, 430, 130, 430, 0, 0);
        ln3.setStartingPoint(true);
        ln3.setDisburseRate(4);

        ln3.setLight(light03);

        Lane ln4 = new Lane("lane4", 195, 14, 195, 190, 90, 0);
        ln4.setStartingPoint(true);
        ln4.setLight(light04);
        ln4.setDisburseRate(2);

        Lane ln5 = new Lane("lane5", 237, 14, 237, 190, 90, 0);
        ln5.setDisburseRate(2);
        ln5.setStartingPoint(true);
        ln5.setLight(light05);

        Lane ln6 = new Lane("lane6", 267, 14, 267, 190, 90, 0);
        ln6.setStartingPoint(true);
        ln6.setLight(light06);
        ln6.setDisburseRate(2);

        Lane ln7 = new Lane("lane7", 310, 16, 310, 193, 90, 0);
        ln7.setStartingPoint(true);
        ln7.setLight(light07);
        ln7.setDisburseRate(2);
//        ln7.addCoordonate(290, 16, 290, 193, 90, 0);

        Lane ln8 = new Lane("lane8", 847, 286, 375, 286, 180, 0);
        ln8.setLight(light08);

//        
        Lane ln9 = new Lane("lane9", 810, 320, 375, 286, 180, 0);
        ln9.setLight(light09);

        Lane ln10 = new Lane("lane10", 806, 360, 381, 360, 180, 0);
        ln10.setLight(light10);

        Lane ln11 = new Lane("lane11", 1115, 250, 1000, 250, 180, 0);
        ln11.setStartingPoint(true);
        ln11.setLight(light11);
        ln11.setDisburseRate(2);

        Lane ln12 = new Lane("lane12", 1115, 286, 1000, 286, 180, 0);
        ln12.setStartingPoint(true);
        ln12.setLight(light12);
        ln12.setDisburseRate(4);

        Lane ln13 = new Lane("lane13", 1115, 320, 1000, 320, 180, 0);
        ln13.setStartingPoint(true);
        ln13.setLight(light13);
        ln13.setDisburseRate(4);

        Lane ln14 = new Lane("lane14", 923, 730, 923, 570, 270, 0);
        ln14.setStartingPoint(true);

        ln14.setLight(light14);
        ln14.setDisburseRate(1);

        Lane ln15 = new Lane("lane15", 887, 730, 887, 570, 270, 0);
        ln15.setStartingPoint(true);
        ln15.setLight(light15);
        ln15.setDisburseRate(1);

        Lane ln16 = new Lane("lane16", 847, 730, 847, 570, 270, 0);
        ln16.setStartingPoint(true);
        ln16.setLight(light16);
        ln16.setDisburseRate(1);

//        ln16.addPossiblePaths(inter17);
        Lane ln17 = new Lane("lane17", 810, 730, 810, 570, 270, 0);
        ln17.setStartingPoint(true);
        ln17.setLight(light17);
        ln17.setDisburseRate(1);

        Lane ln18 = new Lane("lane18", 269, 467, 725, 467, 0, 0);
        ln18.setLight(light18);

        Lane ln19 = new Lane("Lane19", 310, 430, 735, 430, 0, 0);
        ln19.setLight(light19);

        Lane ln20 = new Lane("lane20", 311, 395, 735, 395, 0, 0);
        ln20.setLight(light20);

        Lane int1 = new Lane("inter1", 140, 505, 195, 505, 0, 0);

        Lane int1_1 = new Lane("inter1", 195, 518, 195, 698, 90, 0);
        int1_1.setEnding(true);
        Lane int2 = new Lane("inter2", 135, 467, 250, 467, 0, 0);

        Lane int2_1 = new Lane("inter2_1", 135, 467, 309, 430, -10, -3);

        Lane int3 = new Lane("inter3", 131, 430, 309, 395, -10, -3);
//        dafefdqef;

        Lane int4 = new Lane("inter4", 195, 210, 195, 245, 90, 0);

        Lane int6 = new Lane("inter6", 269, 217, 269, 453, 90, 0);

        Lane int7 = new Lane("inter7", 310, 210, 310, 430, 90, 0);

        Lane int8 = new Lane("inter8", 355, 286, 210, 260, 188, -2.5);

        Lane int9 = new Lane("inter9", 355, 320, 210, 300, 188, -2.5);

        Lane int10 = new Lane("inter10", 355, 360, 295, 360, 180, 0);

        Lane end10 = new Lane("end10", 279, 360, 279, 730, 90, 0);
        end10.setEnding(true);

        Lane int20 = new Lane("inter20", 762, 395, 845, 395, 0, 0);

        Lane end20 = new Lane("end20", 846, 395, 846, 50, 270, 0);
        end20.setEnding(true);

        Lane int19 = new Lane("inter19", 785, 430, 940, 435, 8.7, 2);

        Lane int19_1 = new Lane("inter19_1", 760, 430, 785, 430, 0, 0);

        Lane int18 = new Lane("inter18", 780, 472, 930, 480, 8.7, 1.6);

        Lane int18_1 = new Lane("inter18_1", 760, 467, 770, 467, 0, 0);

        Lane int17 = new Lane("inter17", 810, 548, 810, 350, 270, 0);

        Lane int16 = new Lane("inter16", 847, 544, 847, 330, 270, 0);

        Lane int14 = new Lane("inter14", 923, 544, 923, 490, 270, 0);

        Lane end14 = new Lane("end14", 923, 490, 1100, 490, 0, 0);
        end14.setEnding(true);

        Lane int11 = new Lane("inter11", 960, 250, 930, 250, 180, 0);

        Lane end11 = new Lane("end11", 915, 246, 915, 50, 270, 0);
        end11.setEnding(true);

        Lane int12 = new Lane("inter12", 970, 286, 850, 320, 170, 3.1);

        Lane int12_1 = new Lane("inter12_1", 980, 286, 890, 286, 180, 0);

        Lane int13 = new Lane("inter13", 970, 320, 835, 360, 170, 3.1);

        Lane en6 = new Lane("end6", 847, 300, 847, 50, 270, 0);
        en6.setEnding(true);

        Lane en7 = new Lane("end7", 887, 540, 887, 50, 270, 0);

        en7.setEnding(true);

        Lane en9 = new Lane("end9", 960, 455, 1120, 455, 0, 0);
        en9.setEnding(true);

        Lane en10 = new Lane("end10", 940, 490, 1120, 490, 0, 0);
        en10.setEnding(true);

        Lane en4 = new Lane("end4", 192, 292, 0, 292, 180, 0);
        en4.setEnding(true);

        Lane en5 = new Lane("end5", 192, 255, 0, 255, 180, 0);
        en5.setEnding(true);

        Lane en2 = new Lane("end2", 237, 217, 237, 725, 90, 0);

        en2.setEnding(true);

        Lane en3 = new Lane("end3", 270, 510, 270, 725, 90, 0);
        en3.setEnding(true);

        ln1.addPossiblePaths(int1);
        ln2.addPossiblePaths(int2_1);
        ln2.addPossiblePaths(int2);

        ln3.addPossiblePaths(int3);
        ln4.addPossiblePaths(int4);
        ln5.addPossiblePaths(en2);
        ln6.addPossiblePaths(int6);

        ln7.addPossiblePaths(int7);
        ln8.addPossiblePaths(int8);
        ln9.addPossiblePaths(int9);
        ln10.addPossiblePaths(int10);
        ln11.addPossiblePaths(int11);
        int11.addPossiblePaths(end11);
        ln12.addPossiblePaths(int12_1);
        ln12.addPossiblePaths(int12);

//        ln13.addPossiblePaths(ln9);
        ln13.addPossiblePaths(int13);

        ln14.addPossiblePaths(int14);
        int14.addPossiblePaths(end14);

        ln15.addPossiblePaths(en7);
        ln16.addPossiblePaths(int16);

        ln17.addPossiblePaths(int17);
        ln18.addPossiblePaths(int18_1);
        int18_1.addPossiblePaths(int18);
        ln19.addPossiblePaths(int19_1);
        int19_1.addPossiblePaths(int19);
        ln20.addPossiblePaths(int20);
        int1.addPossiblePaths(int1_1);
        int2.addPossiblePaths(ln18);
        int2_1.addPossiblePaths(ln19);
        int3.addPossiblePaths(ln20);
        int4.addPossiblePaths(en5);
        int6.addPossiblePaths(ln18);
        int6.addPossiblePaths(en3);
        int10.addPossiblePaths(end10);
        int7.addPossiblePaths(ln19);

        int8.addPossiblePaths(en5);
        int9.addPossiblePaths(en4);
        int13.addPossiblePaths(ln10);
        int12.addPossiblePaths(ln9);
        int12_1.addPossiblePaths(ln8);
        int19.addPossiblePaths(en9);
        int18.addPossiblePaths(en10);
        int17.addPossiblePaths(ln9);
        int16.addPossiblePaths(ln8);
        int16.addPossiblePaths(en6);

        int20.addPossiblePaths(end20);
        roads.addRoad(ln17);
        roads.addRoad(ln1);
        roads.addRoad(ln2);
        roads.addRoad(ln3);
        roads.addRoad(ln4);
        roads.addRoad(ln5);
        roads.addRoad(ln6);
        roads.addRoad(ln7);
        roads.addRoad(ln8);
        roads.addRoad(ln9);
        roads.addRoad(ln10);
        roads.addRoad(ln11);
        roads.addRoad(ln12);
        roads.addRoad(ln13);
        roads.addRoad(ln14);
        roads.addRoad(ln15);
        roads.addRoad(ln16);

        roads.addRoad(ln18);
        roads.addRoad(ln19);
        roads.addRoad(ln20);
        roads.addRoad(int1);
        roads.addRoad(int1_1);
        roads.addRoad(int2);
        roads.addRoad(int2_1);
        roads.addRoad(int3);
        roads.addRoad(int4);
        roads.addRoad(int6);
        roads.addRoad(int7);
        roads.addRoad(int8);
        roads.addRoad(int9);
        roads.addRoad(int10);
        roads.addRoad(end10);
        roads.addRoad(int11);
        roads.addRoad(end11);
        roads.addRoad(int12);
        roads.addRoad(int12_1);
        roads.addRoad(int13);

        roads.addRoad(int14);
        roads.addRoad(end14);
        roads.addRoad(int16);
        roads.addRoad(int17);
        roads.addRoad(int18);
        roads.addRoad(int18_1);
        roads.addRoad(int19);
        roads.addRoad(int19_1);
        roads.addRoad(int20);
        roads.addRoad(end20);
        roads.addRoad(en2);
        roads.addRoad(en3);
        roads.addRoad(en4);
        roads.addRoad(en5);
        roads.addRoad(en6);
        roads.addRoad(en7);

        roads.addRoad(en9);
        roads.addRoad(en10);

    }

    private void setInitColor() {

        g1.setOnAction((d) -> {
            if (g1.isSelected()) {
                light01.setFill(Paint.valueOf("green"));
            } else {
                light01.setFill(Paint.valueOf("red"));
            }
        });
        g2.setOnAction((d) -> {
            if (g2.isSelected()) {
                light02.setFill(Paint.valueOf("green"));
            } else {
                light02.setFill(Paint.valueOf("red"));
            }
        });
        g3.setOnAction((d) -> {
            if (g3.isSelected()) {
                light03.setFill(Paint.valueOf("green"));
            } else {
                light03.setFill(Paint.valueOf("red"));
            }
        });
        g4.setOnAction((d) -> {
            if (g4.isSelected()) {
                light04.setFill(Paint.valueOf("green"));
            } else {
                light04.setFill(Paint.valueOf("red"));
            }
        });
        g5.setOnAction((d) -> {
            if (g5.isSelected()) {
                light05.setFill(Paint.valueOf("green"));
            } else {
                light05.setFill(Paint.valueOf("red"));
            }
        });
        g6.setOnAction((d) -> {
            if (g6.isSelected()) {
                light06.setFill(Paint.valueOf("green"));
            } else {
                light06.setFill(Paint.valueOf("red"));
            }
        });
        g7.setOnAction((d) -> {
            if (g7.isSelected()) {
                light07.setFill(Paint.valueOf("green"));
            } else {
                light07.setFill(Paint.valueOf("red"));
            }
        });
        g8.setOnAction((d) -> {
            if (g8.isSelected()) {
                light08.setFill(Paint.valueOf("green"));
            } else {
                light08.setFill(Paint.valueOf("red"));
            }
        });
        g9.setOnAction((d) -> {
            if (g9.isSelected()) {
                light09.setFill(Paint.valueOf("green"));
            } else {
                light09.setFill(Paint.valueOf("red"));
            }
        });
        g10.setOnAction((d) -> {
            if (g10.isSelected()) {
                light10.setFill(Paint.valueOf("green"));
            } else {
                light10.setFill(Paint.valueOf("red"));
            }
        });
        g11.setOnAction((d) -> {
            if (g11.isSelected()) {
                light11.setFill(Paint.valueOf("green"));
            } else {
                light11.setFill(Paint.valueOf("red"));
            }
        });
        g12.setOnAction((d) -> {
            if (g12.isSelected()) {
                light12.setFill(Paint.valueOf("green"));
            } else {
                light12.setFill(Paint.valueOf("red"));
            }
        });
        g13.setOnAction((d) -> {
            if (g13.isSelected()) {
                light13.setFill(Paint.valueOf("green"));
            } else {
                light13.setFill(Paint.valueOf("red"));
            }
        });
        g14.setOnAction((d) -> {
            if (g14.isSelected()) {
                light14.setFill(Paint.valueOf("green"));
            } else {
                light14.setFill(Paint.valueOf("red"));
            }
        });
        g15.setOnAction((d) -> {
            if (g15.isSelected()) {
                light15.setFill(Paint.valueOf("green"));
            } else {
                light15.setFill(Paint.valueOf("red"));
            }
        });
        g16.setOnAction((d) -> {
            if (g16.isSelected()) {
                light16.setFill(Paint.valueOf("green"));
            } else {
                light16.setFill(Paint.valueOf("red"));
            }
        });
        g17.setOnAction((d) -> {
            if (g17.isSelected()) {
                light17.setFill(Paint.valueOf("green"));
            } else {
                light17.setFill(Paint.valueOf("red"));
            }
        });
        g18.setOnAction((d) -> {
            if (g18.isSelected()) {
                light18.setFill(Paint.valueOf("green"));
            } else {
                light18.setFill(Paint.valueOf("red"));
            }
        });
        g19.setOnAction((d) -> {
            if (g19.isSelected()) {
                light19.setFill(Paint.valueOf("green"));
            } else {
                light19.setFill(Paint.valueOf("red"));
            }
        });
        g20.setOnAction((d) -> {
            if (g20.isSelected()) {
                light20.setFill(Paint.valueOf("green"));
            } else {
                light20.setFill(Paint.valueOf("red"));
            }
        });

    }

    void setStage(Stage stage) {
        this.stage = stage;
        stage.setResizable(false);
        stage.setOnCloseRequest((l) -> {
            listOfLightObj.forEach((f) -> {
                f.stop();
            });
            System.exit(0);
        });
    }

    @FXML
    private void initCar() {
        if (populateLight()) {
            roads.done();
            start.setDisable(true);
        }else{
            Alert a= new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error");
            a.setHeaderText("Input for light");
            a.setContentText("Please select a file");
            a.show();
        }
    }

    public String getProperty(String string) {
        Properties properties = null;

        try {
            Reader reader = new InputStreamReader(new FileInputStream(propertyLocation), "UTF-8");
            //Reader reader = new InputStreamReader(new FileInputStream(path), "UTF-8");
            try (BufferedReader fin = new BufferedReader(reader)) {
                properties = new Properties();

                properties.load(fin);
            }
        } catch (IOException io) {
        }
        return properties.getProperty(string);
    }
}
