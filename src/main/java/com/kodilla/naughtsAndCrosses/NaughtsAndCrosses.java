package com.kodilla.naughtsAndCrosses;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;

import javafx.scene.control.*;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;


import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Random;


public class NaughtsAndCrosses<Tile> extends Application {

    private boolean isMoveX = true;
    private boolean theEnd;
    private boolean correctMove=true;
    private int moveCounter = 0;



    private final Image board = new Image("file:src/main/resources/boardBrown1.jpg");
    private final Image cross = new Image("file:src/main/resources/cross1.png");
    private final Image naught = new Image("file:src/main/resources/naught.png");
    private final Image menu = new Image ("file:src/main/resources/menu.png");
    private final List<Button> buttons = new LinkedList<>();
    private final List<Button> emptyButtons = new LinkedList<>();
    private final Random random = new Random();
    private final GridPane grid = new GridPane();



    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        BackgroundSize backgroundSize = new BackgroundSize(770, 980, true, true, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(board, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);

        GridPane grid = new GridPane();
        grid.setGridLinesVisible(true);
        grid.setAlignment(Pos.TOP_LEFT);
//        grid.setPadding(new Insets(213, 70, 213, 70));
//        grid.setHgap(100.0);
//        grid.setVgap(100.0);
        grid.setBackground(background);

//        grid.addColumn(0);
//        grid.addColumn(1);
//        grid.addColumn(2);
//        grid.addColumn(3);
//        grid.addColumn(4);
//        grid.addColumn(5);
//        grid.addColumn(6);
//        grid.addRow(0);
//        grid.addRow(1);
//        grid.addRow(2);
//        grid.addRow(3);
//        grid.addRow(4);
//        grid.addRow(5);
//        grid.addRow(6);

        grid.getColumnConstraints().add(new ColumnConstraints(20));
        grid.getColumnConstraints().add(new ColumnConstraints(135));
        grid.getColumnConstraints().add(new ColumnConstraints(160));
        grid.getColumnConstraints().add(new ColumnConstraints(160));
        grid.getColumnConstraints().add(new ColumnConstraints(160));
        grid.getColumnConstraints().add(new ColumnConstraints(110));
        grid.getColumnConstraints().add(new ColumnConstraints(20));
        grid.getRowConstraints().add(new RowConstraints(30));
        grid.getRowConstraints().add(new RowConstraints(30));
        grid.getRowConstraints().add(new RowConstraints(210));
        grid.getRowConstraints().add(new RowConstraints(160));
        grid.getRowConstraints().add(new RowConstraints(160));
        grid.getRowConstraints().add(new RowConstraints(160));
        grid.getRowConstraints().add(new RowConstraints(200));
        grid.getRowConstraints().add(new RowConstraints(30));

        ImageView img1 = new ImageView(cross);
        img1.setFitHeight(140);
        img1.setFitHeight(140);
        img1.setPreserveRatio(true);

        ImageView img2 = new ImageView(naught);
        img2.setFitWidth(140);
        img2.setFitHeight(140);
        img2.setPreserveRatio(true);

        MenuBar menuBar = new MenuBar();
        grid.add(menuBar, 1, 1);
        Menu menu1 = new Menu("Select symbol");
        menuBar.getMenus().add(menu1);
//        menu1.setGraphic(new ImageView(menu));
        MenuItem menuItem1 = new MenuItem("O");
        MenuItem menuItem2 = new MenuItem("X");

        menu1.getItems().add(menuItem1);
        menu1.getItems().add(menuItem2);


        Scene scene = new Scene(grid,768, 1024, Color.WHITE);

        primaryStage.setTitle("Naughts & Crosses");
        primaryStage.setScene(scene);
        primaryStage.show();



        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Button button = new Button();
                button.setMaxSize(200, 200);
                button.setMinSize(200, 200);
                button.setStyle("-fx-background-color: transparent;");

                button.setOnAction(event -> {
                    emptyButtons.remove(button);

                    if (isMoveX) {
                        button.setGraphic(new ImageView(cross));
                        correctMove = true;
                        button.setDisable(true);
                        checkGame();

                        isMoveX = !isMoveX;
                        makeComputerMove();
                    } else {
                        button.setGraphic(new ImageView(naught));
                        correctMove = true;
                        button.setDisable(true);
                        checkGame();

                        isMoveX = !isMoveX;//(czy nie powinno być inMoveX = true?)
                    }
                } );

                if (correctMove) {
                    moveCounter++;
                }

                buttons.add(button);
                emptyButtons.add(button);
                grid.add(button, i+2, j+3);

            }
        }
    }

     private void checkGame() {
        checkHorizontal();
        checkVertical();
        checkDiagonal();
         Alert alert = new Alert(Alert.AlertType.INFORMATION);

        if (checkHorizontal() || checkVertical() || checkDiagonal()){
            theEnd = true;
            System.out.println ("Wygrana!");
            buttons.forEach( button -> button.setDisable(true));

            alert.setTitle("Game result");
            alert.setHeaderText("Victory!");
            alert.setContentText("Click OK to play again!");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                System.out.println ("Nowa gra");
            } else {
                // ... user chose CANCEL or closed the dialog
            }


        } else {
             for (int i=0; i<9; i++) {
                 Button button = buttons.get(i);
                 if (button.getGraphic() == null) {
                     return;
                 }
            }
             System.out.println ("Remis!");
            alert.setTitle("Game result");
            alert.setHeaderText("Draw!");
            alert.setContentText("Click OK to play again!");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                System.out.println ("Nowa gra");
            } else {
                // ... user chose CANCEL or closed the dialog
            }
            }

        }


    private boolean checkHorizontal() {

        for (int i=0; i<9; i= i+3){
            Button button1 = buttons.get(i);
            Button button2 = buttons.get(i+1);
            Button button3 = buttons.get(i+2);

            if (button1.getGraphic() != null && button2.getGraphic() != null && button3.getGraphic() != null) {
                Image image1 = ((ImageView)button1.getGraphic()).getImage();
                Image image2 = ((ImageView)button2.getGraphic()).getImage();
                Image image3 = ((ImageView)button3.getGraphic()).getImage();

                if (image1.equals(image2) && image2.equals(image3)) {
                    return true;
                }
            }
        }

            return false;
        }


    private boolean checkVertical() {

        for (int i=0; i<3; i++){
            Button button1 = buttons.get(i);
            Button button2 = buttons.get(i+3);
            Button button3 = buttons.get(i+6);

            if (button1.getGraphic() != null && button2.getGraphic() != null && button3.getGraphic() != null) {
                Image image1 = ((ImageView)button1.getGraphic()).getImage();
                Image image2 = ((ImageView)button2.getGraphic()).getImage();
                Image image3 = ((ImageView)button3.getGraphic()).getImage();

                if (image1.equals(image2) && image2.equals(image3)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean checkDiagonal() {


        Button button1 = buttons.get(0);
        Button button2 = buttons.get(4);
        Button button3 = buttons.get(8);
        Button button4 = buttons.get(2);
        Button button5 = buttons.get(6);

        if (button1.getGraphic() != null && button2.getGraphic() != null && button3.getGraphic() != null) {
            Image image1 = ((ImageView) button1.getGraphic()).getImage();
            Image image2 = ((ImageView) button2.getGraphic()).getImage();
            Image image3 = ((ImageView) button3.getGraphic()).getImage();

            if (image1.equals(image2) && image2.equals(image3)) {
                return true;
            }
        }

        if (button4.getGraphic() != null && button2.getGraphic() != null && button5.getGraphic() != null) {
            Image image4 = ((ImageView) button4.getGraphic()).getImage();
            Image image2 = ((ImageView) button2.getGraphic()).getImage();
            Image image5 = ((ImageView) button5.getGraphic()).getImage();

            if (image4.equals(image2) && image2.equals(image5)) {
                return true;
            }
        }
        return false;
    }

         private void makeComputerMove() {
        if (emptyButtons.size()>0) {
            int index = random.nextInt(emptyButtons.size());
            emptyButtons.get(index).fire();
        }
     }
}

