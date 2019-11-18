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
 * @author Christian
 */
public class MainController implements Initializable {

    private final int numcar = 900;
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
    private CheckBox g1, g2, g3, g4, g5, g6, g7, g8, g9,
            g10, g11, g12, g13, g14, g15, g16, g17, g18,
            g19, g20;
    private ArrayList<Light> listOfLightObj = new ArrayList<>();

//    private ArrayList<Car> carlist = new ArrayList<>();
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
//        prefGrid.setVisible(false);
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

//                            carlist.add(s);
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
        boolean success = false;
        Import p = new Import();
        File f;
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
            start.setDisable(false);
            return true;
        }
        return true;
    }

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
        Lane ln1 = new Lane(Lane.TURN, "lane1");
        ln1.setStartingPoint(true);
        ln1.setDisburseRate(4);
        ln1.setLight(light01);
        ln1.addCoordonate(0, 505, 119, 505, 0, 0);

        Lane ln2 = new Lane(Lane.STRAIGHT, "lane2");
        ln2.setStartingPoint(true);
        ln2.setLight(light02);
        ln2.setDisburseRate(4);
        ln2.addCoordonate(0, 467, 130, 467, 0, 0);

        Lane ln3 = new Lane(Lane.STRAIGHT, "lane3");
        ln3.setStartingPoint(true);
        ln3.setDisburseRate(4);

        ln3.addCoordonate(0, 430, 130, 430, 0, 0);
        ln3.setLight(light03);

        Lane ln4 = new Lane(Lane.TURN, "lane4");
        ln4.setStartingPoint(true);
        ln4.setLight(light04);
        ln4.setDisburseRate(2);
        ln4.addCoordonate(195, 16, 195, 195, 90, 0);

        Lane ln5 = new Lane(Lane.STRAIGHT, "lane5");
        ln5.setDisburseRate(2);
        ln5.setStartingPoint(true);
        ln5.setLight(light05);
        ln5.addCoordonate(237, 16, 237, 196, 90, 0);

        Lane ln6 = new Lane(Lane.TURN, "lane6");
        ln6.setStartingPoint(true);
        ln6.setLight(light06);
        ln6.setDisburseRate(2);
        ln6.addCoordonate(267, 16, 267, 217, 90, 0);

        Lane ln7 = new Lane(Lane.TURN, "lane7");
        ln7.setStartingPoint(true);
        ln7.setLight(light07);
        ln7.setDisburseRate(2);
//        ln7.addCoordonate(290, 16, 290, 193, 90, 0);
        ln7.addCoordonate(310, 16, 310, 193, 90, 0);

        Lane ln8 = new Lane(Lane.STRAIGHT, "lane8");
        ln8.setLight(light08);
        ln8.addCoordonate(847, 286, 355, 286, 180, 0);

//        
        Lane ln9 = new Lane(Lane.STRAIGHT, "lane9");
        ln9.setLight(light09);

        ln9.addCoordonate(810, 320, 355, 320, 180, 0);

        Lane ln10 = new Lane(Lane.TURN, "lane10");
        ln10.setLight(light10);
        ln10.addCoordonate(806, 360, 361, 360, 180, 0);

        Lane ln11 = new Lane(Lane.TURN, "lane11");
        ln11.setStartingPoint(true);
        ln11.setLight(light11);
        ln11.setDisburseRate(2);
        ln11.addCoordonate(1100, 250, 985, 250, 180, 0);

        Lane ln12 = new Lane(Lane.STRAIGHT, "lane12");
        ln12.setStartingPoint(true);
        ln12.setLight(light12);
        ln12.setDisburseRate(4);

        ln12.addCoordonate(1100, 286, 985, 286, 180, 0);

        Lane ln13 = new Lane(Lane.STRAIGHT, "lane13");
        ln13.setStartingPoint(true);
        ln13.setLight(light13);
        ln13.setDisburseRate(4);
        ln13.addCoordonate(1100, 320, 985, 320, 180, 0);

        Lane ln14 = new Lane(Lane.TURN, "lane14");
        ln14.setStartingPoint(true);
        ln14.addCoordonate(923, 725, 923, 548, 270, 0);
        ln14.setLight(light14);
        ln14.setDisburseRate(1);

        Lane ln15 = new Lane(Lane.STRAIGHT, "lane15");
        ln15.setStartingPoint(true);
        ln15.setLight(light15);
        ln15.setDisburseRate(1);
        ln15.addCoordonate(887, 725, 887, 548, 270, 0);

        Lane ln16 = new Lane(Lane.TURN, "lane16");
        ln16.setStartingPoint(true);
        ln16.setLight(light16);
        ln16.setDisburseRate(1);
        ln16.addCoordonate(847, 730, 847, 548, 270, 0);

//        ln16.addPossiblePaths(inter17);
        Lane ln17 = new Lane(Lane.TURN, "lane17");
        ln17.setStartingPoint(true);
        ln17.setLight(light17);
        ln17.setDisburseRate(1);
        ln17.addCoordonate(810, 725, 810, 548, 270, 0);

        Lane ln18 = new Lane(Lane.STRAIGHT, "lane18");
        ln18.setLight(light18);
        ln18.addCoordonate(269, 467, 735, 467, 0, 0);

        Lane ln19 = new Lane(Lane.STRAIGHT, "Lane19");
        ln19.setLight(light19);
        ln19.addCoordonate(310, 430, 735, 430, 0, 0);

        Lane ln20 = new Lane(Lane.TURN, "lane20");
        ln20.setLight(light20);
        ln20.addCoordonate(311, 395, 735, 395, 0, 0);

        Lane int1 = new Lane(Lane.TURN, "inter1");
        int1.addCoordonate(119, 505, 195, 505, 0, 0);

        Lane int1_1 = new Lane(Lane.TURN, "inter1");
        int1_1.addCoordonate(195, 518, 195, 698, 90, 0);
        int1_1.setEnding(true);
        Lane int2 = new Lane(Lane.STRAIGHT, "inter2");
        int2.addCoordonate(135, 467, 260, 467, 0, 0);

        Lane int2_1 = new Lane(Lane.STRAIGHT, "inter2_1");
        int2_1.addCoordonate(135, 467, 309, 430, -10, -3);

        Lane int3 = new Lane(Lane.STRAIGHT, "inter3");
        int3.addCoordonate(131, 430, 309, 395, -10, -3);
//        dafefdqef;

        Lane int4 = new Lane(Lane.TURN, "inter4");

        int4.addCoordonate(195, 195, 195, 245, 90, 0);
        Lane int4_1 = new Lane(Lane.TURN, "inter4");
        int4_1.addCoordonate(195, 247, 0, 247, 90, 0);
        int4_1.setEnding(true);

        Lane int6 = new Lane(Lane.STRAIGHT, "inter6");
        int6.addCoordonate(269, 217, 269, 473, 90, 0);

        Lane int7 = new Lane(Lane.TURN, "inter7");
        int7.addCoordonate(310, 193, 310, 430, 90, 0);

        Lane int8 = new Lane(Lane.STRAIGHT, "inter8");
        int8.addCoordonate(355, 286, 192, 258, 188, -3);

        Lane int9 = new Lane(Lane.STRAIGHT, "inter9");
        int9.addCoordonate(355, 320, 192, 292, 188, -3);

        Lane int10 = new Lane(Lane.TURN, "inter10");
        int10.addCoordonate(361, 360, 279, 360, 180, 0);

        Lane end10 = new Lane(Lane.STRAIGHT, "end10");
        end10.setEnding(true);
        end10.addCoordonate(279, 360, 279, 730, 90, 0);

        Lane int20 = new Lane(Lane.TURN, "inter20");

        int20.addCoordonate(742, 395, 845, 395, 0, 0);
        Lane end20 = new Lane(Lane.TURN, "end20");
        end20.setEnding(true);
        end20.addCoordonate(846, 395, 846, 50, 270, 0);

        Lane int19 = new Lane(Lane.STRAIGHT, "inter19");
        int19.addCoordonate(785, 430, 940, 435, 8.7, 2);
        
        Lane int19_1 = new Lane(Lane.STRAIGHT, "inter19_1");
        int19_1.addCoordonate(740, 430, 785, 430, 0, 0);

        Lane int18 = new Lane(Lane.STRAIGHT, "inter18");
        int18.addCoordonate(780, 472, 940, 485, 8.7, 2);
        Lane int18_1 = new Lane(Lane.STRAIGHT, "inter18_1");
        int18_1.addCoordonate(750, 467, 770, 467, 0, 0);


        Lane int17 = new Lane(Lane.TURN, "inter17");
        int17.addCoordonate(810, 548, 810, 320, 270, 0);

        Lane int16 = new Lane(Lane.TURN, "inter16");
        int16.addCoordonate(847, 530, 847, 320, 270, 0);

        Lane int14 = new Lane(Lane.TURN, "inter14");
        int14.addCoordonate(923, 544, 923, 490, 270, 0);

        Lane end14 = new Lane(Lane.TURN, "end14");

        end14.addCoordonate(923, 490, 1100, 490, 0, 0);
        end14.setEnding(true);

        Lane int11 = new Lane(Lane.TURN, "inter11");

        int11.addCoordonate(960, 250, 915, 250, 180, 0);

        Lane end11 = new Lane(Lane.TURN, "end11");
        end11.setEnding(true);

        end11.addCoordonate(915, 246, 915, 50, 270, 0);

        Lane int12 = new Lane(Lane.TURN, "inter12");
        int12.addCoordonate(970, 286, 810, 320, 170, 3);

        Lane int12_1 = new Lane(Lane.TURN, "inter12_1");
        int12_1.addCoordonate(970, 286, 860, 286, 180, 0);

        Lane int13 = new Lane(Lane.TURN, "inter13");
        int13.addCoordonate(970, 320, 805, 360, 170, 3);

        Lane en6 = new Lane(Lane.STRAIGHT, "end6");
        en6.setEnding(true);
        en6.addCoordonate(847, 300, 847, 50, 270, 0);

        Lane en7 = new Lane(Lane.STRAIGHT, "end7");
        en7.addCoordonate(887, 549, 887, 50, 270, 0);
        en7.setEnding(true);

        Lane en9 = new Lane(Lane.STRAIGHT, "end9");
        en9.setEnding(true);
        en9.addCoordonate(940, 455, 1120, 455, 0, 0);

        Lane en10 = new Lane(Lane.STRAIGHT, "end10");
        en10.setEnding(true);
        en10.addCoordonate(940, 487, 1120, 487, 0, 0);

        Lane en4 = new Lane(Lane.STRAIGHT, "end4");
        en4.setEnding(true);
        en4.addCoordonate(192, 292, 0, 292, 180, 0);

        Lane en5 = new Lane(Lane.STRAIGHT, "end5");
        en5.setEnding(true);
        en5.addCoordonate(192, 255, 0, 255, 180, 0);

        Lane en2 = new Lane(Lane.STRAIGHT, "end2");
        en2.addCoordonate(237, 217, 237, 725, 90, 0);
        en2.setEnding(true);

        Lane en3 = new Lane(Lane.STRAIGHT, "end3");
        en3.setEnding(true);
        en3.addCoordonate(270, 485, 270, 725, 90, 0);

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

              roads.addRoad(ln7);
        roads.addRoad(ln1);
roads.addRoad(ln2);
      roads.addRoad(ln3);
        roads.addRoad(ln4);
        roads.addRoad(ln5);
        roads.addRoad(ln6);
    
        roads.addRoad(ln8);
        roads.addRoad(ln9);
        roads.addRoad(ln10);
        roads.addRoad(ln11);
        roads.addRoad(ln12);
        roads.addRoad(ln13);
        roads.addRoad(ln14);
        roads.addRoad(ln15);
        roads.addRoad(ln16);
        roads.addRoad(ln17);
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
        new Thread() {
            public void run() {
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
        }.start();

    }

    void setStage(Stage stage) {
        this.stage = stage;
        stage.setResizable(false);
        stage.setOnCloseRequest((l) -> {
            listOfLightObj.forEach((f) -> {
                f.stop();
            });

//            carlist.forEach((c) -> {
//                try {
//
//                    c.close();
//                } catch (Exception v) {
//
//                }
//            });
            System.exit(0);
        });
    }

   

    @FXML
    private void initCar() {
//        populateLight();
////////////        g1.setDisable(true);
////////////        g2.setDisable(true);
////////////        g3.setDisable(true);
////////////        g4.setDisable(true);
////////////        g5.setDisable(true);
////////////        g6.setDisable(true);
////////////        g7.setDisable(true);
////////////        g8.setDisable(true);
////////////        g9.setDisable(true);
////////////        g10.setDisable(true);
////////////        g11.setDisable(true);
////////////        g12.setDisable(true);
////////////        g13.setDisable(true);
////////////        g14.setDisable(true);
////////////        g15.setDisable(true);
////////////        g16.setDisable(true);
////////////        g17.setDisable(true);
////////////        g18.setDisable(true);
////////////        g19.setDisable(true);
////////////        g20.setDisable(true);;
////////////        start.setDisable(true);
        //Have to put this somewhere else
        if (populateLight()) {
//        if (true) {
            roads.done();
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
