package com.kodilla.naughtsAndCrosses;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;

import javafx.scene.control.*;

import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import javafx.stage.Stage;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Random;


public class NaughtsAndCrosses extends Application {

    private static final Image board = new Image("file:src/main/resources/boardBrown1.jpg");
    private static final Image cross = new Image("file:src/main/resources/cross1.png");
    private static final Image naught = new Image("file:src/main/resources/naught.png");
    private final List<Button> buttons = new LinkedList<>();
    private final List<Button> emptyButtons = new LinkedList<>();
    private final Random random = new Random();
    private static final RadioMenuItem menuItemO = new RadioMenuItem("O");
    private static final RadioMenuItem menuItemX = new RadioMenuItem("X");
    private static final RadioMenuItem basic = new RadioMenuItem("Basic");
    private static final RadioMenuItem advanced = new RadioMenuItem("Advanced");
    private boolean isMoveX = true;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        BackgroundSize backgroundSize = new BackgroundSize(770, 980, true, true, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(board, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);

        GridPane grid = new GridPane();
//        grid.setGridLinesVisible(true);
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setBackground(background);

        grid.getColumnConstraints().add(new ColumnConstraints(20));
        grid.getColumnConstraints().add(new ColumnConstraints(135));
        grid.getColumnConstraints().add(new ColumnConstraints(160));
        grid.getColumnConstraints().add(new ColumnConstraints(160));
        grid.getColumnConstraints().add(new ColumnConstraints(160));
        grid.getColumnConstraints().add(new ColumnConstraints(110));
        grid.getColumnConstraints().add(new ColumnConstraints(20));
        grid.getRowConstraints().add(new RowConstraints(30));
        grid.getRowConstraints().add(new RowConstraints(30));
        grid.getRowConstraints().add(new RowConstraints(177));
        grid.getRowConstraints().add(new RowConstraints(160));
        grid.getRowConstraints().add(new RowConstraints(162));
        grid.getRowConstraints().add(new RowConstraints(162));
        grid.getRowConstraints().add(new RowConstraints(200));
        grid.getRowConstraints().add(new RowConstraints(30));

        ImageView img1 = new ImageView(cross);
        img1.setFitWidth(140);
        img1.setFitWidth(140);
        img1.setPreserveRatio(true);

        ImageView img2 = new ImageView(naught);
        img2.setFitWidth(140);
        img2.setFitHeight(140);
        img2.setPreserveRatio(true);

        MenuBar menuBar = new MenuBar();
        menuBar.setStyle("-fx-border-color: black;" + "-fx-border-width: 2.0;");
        grid.add(menuBar, 1, 1);

        Menu menu1 = new Menu("Select symbol");
        menu1.setStyle("-fx-font-weight: bold;" + "-fx-font-style: italic;");
        menuBar.getMenus().add(menu1);

        MenuBar menuBar2 = new MenuBar();
        menuBar2.setStyle("-fx-border-color: black;" + "-fx-border-width: 2.0;");
        grid.add(menuBar2, 2, 1);

        Menu menu2 = new Menu("Select level");
        menu2.setStyle("-fx-font-weight: bold;" + "-fx-font-style: italic;");
        menuBar2.getMenus().add(menu2);

        ToggleGroup toggleGroup = new ToggleGroup();
        toggleGroup.getToggles().add(menuItemO);
        toggleGroup.getToggles().add(menuItemX);

        menu1.getItems().add(menuItemO);
        menu1.getItems().add(menuItemX);


        ToggleGroup toggleGroup2 = new ToggleGroup();
        toggleGroup2.getToggles().add(basic);
        toggleGroup2.getToggles().add(advanced);

        menu2.getItems().add(basic);
        menu2.getItems().add(advanced);

        Scene scene = new Scene(grid, 768, 955, Color.WHITE);

        primaryStage.setTitle("Naughts & Crosses");
        primaryStage.setScene(scene);
        primaryStage.show();


        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Button button = new Button();
                button.setMaxSize(150, 150);
                button.setMinSize(150, 150);
                button.setStyle("-fx-background-color: transparent;");

                buttons.add(button);
                emptyButtons.add(button);
                grid.add(button, i + 2, j + 3);


                button.setOnAction(event -> {
                    emptyButtons.remove(button);

                    if (!menuItemO.isSelected() && !menuItemX.isSelected()) {
                        button.setDisable(true);
                    } else if (isMoveX && basic.isSelected()) {
                        if (menuItemX.isSelected()) {
                            button.setGraphic(new ImageView(cross));
                        } else {
                            button.setGraphic(new ImageView(naught));
                        }
                        button.setDisable(true);
                        checkGame();

                        isMoveX = !isMoveX;
                        makeComputerMoveBasic();
                    } else if (!isMoveX && basic.isSelected()) {
                        if (menuItemX.isSelected()) {
                            button.setGraphic(new ImageView(naught));
                        } else {
                            button.setGraphic(new ImageView(cross));
                        }
                        button.setDisable(true);
                        checkGame();

                        isMoveX = !isMoveX;
                    } else if (isMoveX && advanced.isSelected()) {
                        if (menuItemX.isSelected()) {
                            button.setGraphic(new ImageView(cross));
                        } else {
                            button.setGraphic(new ImageView(naught));
                        }
                        button.setDisable(true);
                        checkGame();

                        isMoveX = !isMoveX;
                        makeComputerMoveAdvanced();
                    } else if (!isMoveX && advanced.isSelected()) {
                        if (menuItemX.isSelected()) {
                            button.setGraphic(new ImageView(naught));
                        } else {
                            button.setGraphic(new ImageView(cross));
                        }
                        button.setDisable(true);
                        checkGame();

                        isMoveX = !isMoveX;
                    }
                });
            }
        }
    }

    private void checkGame() {

        checkHorizontal();
        checkVertical();
        checkDiagonal();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        if (checkHorizontal() || checkVertical() || checkDiagonal()) {

            System.out.println("Wygrana!");
            buttons.forEach(button -> button.setDisable(true));

            alert.setTitle("Game result");
            alert.setHeaderText("Victory!");
            alert.setContentText("Click OK and play again!");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                System.out.println("Nowa gra");
            }
        } else {
            for (int i = 0; i < 9; i++) {
                Button button = buttons.get(i);
                if (button.getGraphic() == null) {
                    return;
                }
            }
            System.out.println("Remis!");
            alert.setTitle("Game result");
            alert.setHeaderText("Draw!");
            alert.setContentText("Click OK and play again!");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                System.out.println("Nowa gra");
            }
        }
    }

    private boolean checkHorizontal() {

        for (int i = 0; i < 9; i = i + 3) {
            Button button1 = buttons.get(i);
            Button button2 = buttons.get(i + 1);
            Button button3 = buttons.get(i + 2);

            if (button1.getGraphic() != null && button2.getGraphic() != null && button3.getGraphic() != null) {
                Image image1 = ((ImageView) button1.getGraphic()).getImage();
                Image image2 = ((ImageView) button2.getGraphic()).getImage();
                Image image3 = ((ImageView) button3.getGraphic()).getImage();

                if (image1.equals(image2) && image2.equals(image3)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkVertical() {

        for (int i = 0; i < 3; i++) {
            Button button1 = buttons.get(i);
            Button button2 = buttons.get(i + 3);
            Button button3 = buttons.get(i + 6);

            if (button1.getGraphic() != null && button2.getGraphic() != null && button3.getGraphic() != null) {
                Image image1 = ((ImageView) button1.getGraphic()).getImage();
                Image image2 = ((ImageView) button2.getGraphic()).getImage();
                Image image3 = ((ImageView) button3.getGraphic()).getImage();

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

    private void makeComputerMoveBasic() {

        if (emptyButtons.size() > 0) {
            int index = random.nextInt(emptyButtons.size());
            emptyButtons.get(index).fire();
        }
    }

    private void makeComputerMoveAdvanced() {

        if (moveHorizontal()) {
            return;
        } else if (moveVertical()) {
            return;
        } else if (moveDiagonal()) {
            return;
        } else {
            Button button = buttons.get(4);
            ImageView image = ((ImageView) button.getGraphic());
            if (image == null) {
                button.fire();
            } else {
                if (emptyButtons.size() > 0) {
                    int index = random.nextInt(emptyButtons.size());
                    emptyButtons.get(index).fire();
                    System.out.println("Random move");
                }
            }
        }
    }

    private boolean moveHorizontal() {

        for (int i = 0; i < 3; i++) {
            Button button1 = buttons.get(i);
            Button button2 = buttons.get(i + 3);
            Button button3 = buttons.get(i + 6);

            ImageView image1 = ((ImageView) button1.getGraphic());
            ImageView image2 = ((ImageView) button2.getGraphic());
            ImageView image3 = ((ImageView) button3.getGraphic());

            if (emptyButtons.size() > 0) {
                if (menuItemX.isSelected()) {
                    if (image1 != null && image2 != null && image1.getImage().equals(naught) && image2.getImage().equals(naught) && button3.getGraphic() == null) {
                        button3.fire();
                        return true;
                    } else if (image1 != null && image3 != null && image1.getImage().equals(naught) && image3.getImage().equals(naught) && button2.getGraphic() == null) {
                        button2.fire();
                        return true;
                    } else if (image2 != null && image3 != null && image2.getImage().equals(naught) && image3.getImage().equals(naught) && button1.getGraphic() == null) {
                        button1.fire();
                        return true;
                    } else if (image1 != null && image2 != null && image1.getImage().equals(cross) && image1.getImage().equals(cross) && button3.getGraphic() == null) {
                        button3.fire();
                        return true;
                    } else if (image1 != null && image3 != null && image1.getImage().equals(cross) && image3.getImage().equals(cross) && button2.getGraphic() == null) {
                        button2.fire();
                        return true;
                    } else if (image2 != null && image3 != null && image2.getImage().equals(cross) && image3.getImage().equals(cross) && button1.getGraphic() == null) {
                        button1.fire();
                        return true;
                    }
                }
                if (menuItemO.isSelected()) {
                    if (image1 != null && image2 != null && image1.getImage().equals(cross) && image2.getImage().equals(cross) && button3.getGraphic() == null) {
                        button3.fire();
                        return true;
                    } else if (image1 != null && image3 != null && image1.getImage().equals(cross) && image3.getImage().equals(cross) && button2.getGraphic() == null) {
                        button2.fire();
                        return true;
                    } else if (image2 != null && image3 != null && image2.getImage().equals(cross) && image3.getImage().equals(cross) && button1.getGraphic() == null) {
                        button1.fire();
                        return true;
                    } else if (image1 != null && image2 != null && image1.getImage().equals(naught) && image1.getImage().equals(naught) && button3.getGraphic() == null) {
                        button3.fire();
                        return true;
                    } else if (image1 != null && image3 != null && image1.getImage().equals(naught) && image3.getImage().equals(naught) && button2.getGraphic() == null) {
                        button2.fire();
                        return true;
                    } else if (image2 != null && image3 != null && image2.getImage().equals(naught) && image3.getImage().equals(naught) && button1.getGraphic() == null) {
                        button1.fire();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean moveVertical() {

        for (int i = 0; i < 9; i = i + 3) {
            Button button4 = buttons.get(i);
            Button button5 = buttons.get(i + 1);
            Button button6 = buttons.get(i + 2);

            ImageView image4 = ((ImageView) button4.getGraphic());
            ImageView image5 = ((ImageView) button5.getGraphic());
            ImageView image6 = ((ImageView) button6.getGraphic());

            if (emptyButtons.size() > 0) {
                if (menuItemX.isSelected()) {
                    if (image4 != null && image5 != null && image4.getImage().equals(naught) && image5.getImage().equals(naught) && button6.getGraphic() == null) {
                        button6.fire();
                        return true;
                    } else if (image4 != null && image6 != null && image4.getImage().equals(naught) && image6.getImage().equals(naught) && button5.getGraphic() == null) {
                        button5.fire();
                        return true;
                    } else if (image5 != null && image6 != null && image5.getImage().equals(naught) && image6.getImage().equals(naught) && button4.getGraphic() == null) {
                        button4.fire();
                        return true;
                    }
                }
                if (menuItemX.isSelected()) {
                    if (image4 != null && image5 != null && image4.getImage().equals(cross) && image5.getImage().equals(cross) && button6.getGraphic() == null) {
                        button6.fire();
                        return true;
                    } else if (image4 != null && image6 != null && image4.getImage().equals(cross) && image6.getImage().equals(cross) && button5.getGraphic() == null) {
                        button5.fire();
                        return true;
                    } else if (image5 != null && image6 != null && image5.getImage().equals(cross) && image6.getImage().equals(cross) && button4.getGraphic() == null) {
                        button4.fire();
                        return true;
                    }
                }
                if (menuItemO.isSelected()) {
                    if (image4 != null && image5 != null && image4.getImage().equals(cross) && image5.getImage().equals(cross) && button6.getGraphic() == null) {
                        button6.fire();
                        return true;
                    } else if (image4 != null && image6 != null && image4.getImage().equals(cross) && image6.getImage().equals(cross) && button5.getGraphic() == null) {
                        button5.fire();
                        return true;
                    } else if (image5 != null && image6 != null && image5.getImage().equals(cross) && image6.getImage().equals(cross) && button4.getGraphic() == null) {
                        button4.fire();
                        return true;
                    }
                }
                if (menuItemO.isSelected()) {
                    if (image4 != null && image5 != null && image4.getImage().equals(naught) && image5.getImage().equals(naught) && button6.getGraphic() == null) {
                        button6.fire();
                        return true;
                    } else if (image4 != null && image6 != null && image4.getImage().equals(naught) && image6.getImage().equals(naught) && button5.getGraphic() == null) {
                        button5.fire();
                        return true;
                    } else if (image5 != null && image6 != null && image5.getImage().equals(naught) && image6.getImage().equals(naught) && button4.getGraphic() == null) {
                        button4.fire();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean moveDiagonal() {

        Button button7 = buttons.get(0);
        Button button8 = buttons.get(4);
        Button button9 = buttons.get(8);
        Button button10 = buttons.get(2);
        Button button11 = buttons.get(6);

        ImageView image7 = ((ImageView) button7.getGraphic());
        ImageView image8 = ((ImageView) button8.getGraphic());
        ImageView image9 = ((ImageView) button9.getGraphic());
        ImageView image10 = ((ImageView) button10.getGraphic());
        ImageView image11 = ((ImageView) button11.getGraphic());

        if (emptyButtons.size() > 0) {
            if (menuItemX.isSelected()) {
                if (image7 != null && image8 != null && image7.getImage().equals(naught) && image8.getImage().equals(naught) && button9.getGraphic() == null) {
                    button9.fire();
                    return true;
                } else if (image7 != null && image9 != null && image7.getImage().equals(naught) && image9.getImage().equals(naught) && button8.getGraphic() == null) {
                    button8.fire();
                    return true;
                } else if (image8 != null && image9 != null && image8.getImage().equals(naught) && image9.getImage().equals(naught) && button7.getGraphic() == null) {
                    button7.fire();
                    return true;
                } else if (image10 != null && image8 != null && image10.getImage().equals(naught) && image8.getImage().equals(naught) && button11.getGraphic() == null) {
                    button11.fire();
                    return true;
                } else if (image10 != null && image11 != null && image10.getImage().equals(naught) && image11.getImage().equals(naught) && button8.getGraphic() == null) {
                    button8.fire();
                    return true;
                } else if (image8 != null && image11 != null && image8.getImage().equals(naught) && image11.getImage().equals(naught) && button10.getGraphic() == null) {
                    button10.fire();
                    return true;
                } else if (image7 != null && image8 != null && image7.getImage().equals(cross) && image8.getImage().equals(cross) && button9.getGraphic() == null) {
                    button9.fire();
                    return true;
                } else if (image7 != null && image9 != null && image7.getImage().equals(cross) && image9.getImage().equals(cross) && button8.getGraphic() == null) {
                    button8.fire();
                    return true;
                } else if (image8 != null && image9 != null && image8.getImage().equals(cross) && image9.getImage().equals(cross) && button7.getGraphic() == null) {
                    button7.fire();
                    return true;
                } else if (image10 != null && image8 != null && image10.getImage().equals(cross) && image8.getImage().equals(cross) && button11.getGraphic() == null) {
                    button11.fire();
                    return true;
                } else if (image10 != null && image11 != null && image10.getImage().equals(cross) && image11.getImage().equals(cross) && button8.getGraphic() == null) {
                    button8.fire();
                    return true;
                } else if (image8 != null && image11 != null && image8.getImage().equals(cross) && image11.getImage().equals(cross) && button10.getGraphic() == null) {
                    button10.fire();
                    return true;
                }
            }
            if (menuItemO.isSelected()) {
                if (image7 != null && image8 != null && image7.getImage().equals(cross) && image8.getImage().equals(cross) && button9.getGraphic() == null) {
                    button9.fire();
                    return true;
                } else if (image7 != null && image9 != null && image7.getImage().equals(cross) && image9.getImage().equals(cross) && button8.getGraphic() == null) {
                    button8.fire();
                    return true;
                } else if (image8 != null && image9 != null && image8.getImage().equals(cross) && image9.getImage().equals(cross) && button7.getGraphic() == null) {
                    button7.fire();
                    return true;
                } else if (image10 != null && image8 != null && image10.getImage().equals(cross) && image8.getImage().equals(cross) && button11.getGraphic() == null) {
                    button11.fire();
                    return true;
                } else if (image10 != null && image11 != null && image10.getImage().equals(cross) && image11.getImage().equals(cross) && button8.getGraphic() == null) {
                    button8.fire();
                    return true;
                } else if (image8 != null && image11 != null && image8.getImage().equals(cross) && image11.getImage().equals(cross) && button10.getGraphic() == null) {
                    button10.fire();
                    return true;
                } else if (image7 != null && image8 != null && image7.getImage().equals(naught) && image8.getImage().equals(naught) && button9.getGraphic() == null) {
                    button9.fire();
                    return true;
                } else if (image7 != null && image9 != null && image7.getImage().equals(naught) && image9.getImage().equals(naught) && button8.getGraphic() == null) {
                    button8.fire();
                    return true;
                } else if (image8 != null && image9 != null && image8.getImage().equals(naught) && image9.getImage().equals(naught) && button7.getGraphic() == null) {
                    button7.fire();
                    return true;
                } else if (image10 != null && image8 != null && image10.getImage().equals(naught) && image8.getImage().equals(naught) && button11.getGraphic() == null) {
                    button11.fire();
                    return true;
                } else if (image10 != null && image11 != null && image10.getImage().equals(naught) && image11.getImage().equals(naught) && button8.getGraphic() == null) {
                    button8.fire();
                    return true;
                } else if (image8 != null && image11 != null && image8.getImage().equals(naught) && image11.getImage().equals(naught) && button10.getGraphic() == null) {
                    button10.fire();
                    return true;
                }
            }
        }
        return false;
    }
}

