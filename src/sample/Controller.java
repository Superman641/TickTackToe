package sample;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.PickResult;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeType;

import java.util.LinkedList;
import java.util.List;

public class Controller {
    private static final double RADIUS = 50d;

    private static StringBuilder circleSeq = new StringBuilder("000000000");
    private static StringBuilder cristSeq = new StringBuilder("000000000");

    public Button stopButton;
    private boolean isCircles = false;
    private boolean started = false;

    private boolean playerMoved = false;

    public CheckBox circleSwitch;
    public GridPane gameField;

    public Pane pane00;
    public Pane pane10;
    public Pane pane20;
    public Pane pane01;
    public Pane pane11;
    public Pane pane21;
    public Pane pane02;
    public Pane pane12;
    public Pane pane22;

    private static List<Pane> panes;
    public Button startButton;

    public Controller() {

    }

    public void startApp(ActionEvent e) {
        if (!started) {
            started = true;
            circleSeq = new StringBuilder("000000000");
            cristSeq = new StringBuilder("000000000");

            if (panes == null) {
                panes = new LinkedList<Pane>();

                panes.add(pane00);
                panes.add(pane01);
                panes.add(pane10);
                panes.add(pane11);
                panes.add(pane02);
                panes.add(pane20);
                panes.add(pane12);
                panes.add(pane21);
                panes.add(pane22);
            } else {
                for (Pane pane : panes) {
                    pane.getChildren().clear();
                }
            }
            startButton.setDisable(true);
            stopButton.setDisable(false);
        }
    }

    public void stopApp(ActionEvent e) {
        if (started) {
            started = false;

            startButton.setDisable(false);
            stopButton.setDisable(true);
        }
    }

    public void fieldClicked(MouseEvent event) {
        if (!started) {
            return;
        }

        if (AI.isGameOver(circleSeq.toString())) {
            //todo add circle wins logic
            System.out.println("circes wins, start new game");
            stopApp(new ActionEvent());
            return;
        }

        if (AI.isGameOver(cristSeq.toString())) {
            //todo add crist wins logic
            System.out.println("crists wins, start new game");
            stopApp(new ActionEvent());

            return;
        }

        if (event.getTarget() instanceof Pane) {
            Pane target = (Pane) event.getTarget();

            if(!target.getChildren().isEmpty()){
                return;
            }

            if (isCircles) {
                Circle circle = new Circle(RADIUS);
                circle.setFill(Paint.valueOf("rgb(244,244,244)"));
                circle.setStroke(Paint.valueOf("BLACK"));
                circle.setStrokeWidth(10d);
                circle.setStrokeType(StrokeType.INSIDE);

                circle.setLayoutX(RADIUS);
                circle.setLayoutY(RADIUS);

                target.getChildren().clear();
                target.getChildren().add(circle);

            } else {
                target.getChildren().clear();
                Line line1 = new Line(-50d, 0d, 50d, 100d);
                line1.setLayoutX(50d);
                line1.setStrokeWidth(10d);
                Line line2 = new Line(100d, 0d, 0d, 100d);
                line2.setStrokeWidth(10d);

                target.getChildren().add(line1);
                target.getChildren().add(line2);
            }

            int currentId = Integer.valueOf(target.getId());
            System.out.println("current id=" + currentId);

            if (isCircles) {
                circleSeq.replace(currentId, currentId + 1, "1");
            } else {
                cristSeq.replace(currentId, currentId + 1, "1");
            }

            System.out.println("circle seq " + circleSeq);
            System.out.println("crist seq " + cristSeq);

            playerMoved = true;
            computerMove(); //how to get rid of it???
        }
    }

    public void computerMove() {
        if (!playerMoved) {
            return;
        }
        if (isCircles) {
            System.out.println("available moves="+AI.findAvailableMoves(circleSeq.toString()));
            System.out.println("best move:" + AI.findBestMove(circleSeq.toString()));
        } else {
            System.out.println("available moves="+AI.findAvailableMoves(cristSeq.toString()));
            System.out.println("best move:" + AI.findBestMove(cristSeq.toString()));
            int move = AI.findBestMove(cristSeq.toString());
            clickOnPane(move);

        }



        playerMoved = false;
    }

    public void changeCircleSwitch(ActionEvent actionEvent) {

        CheckBox cb = (CheckBox) actionEvent.getSource();
        System.out.println("isSelected " + cb.isSelected());

        isCircles = !isCircles;
    }

    private void clickOnPane(int moveIndex){
        System.out.println("move index " + moveIndex);
        Pane pane = panes.get(moveIndex);
        if (!isCircles) {
            Circle circle = new Circle(RADIUS);
            circle.setFill(Paint.valueOf("rgb(244,244,244)"));
            circle.setStroke(Paint.valueOf("BLACK"));
            circle.setStrokeWidth(10d);
            circle.setStrokeType(StrokeType.INSIDE);

            circle.setLayoutX(RADIUS);
            circle.setLayoutY(RADIUS);

            pane.getChildren().clear();
            pane.getChildren().add(circle);

        } else {
            pane.getChildren().clear();
            Line line1 = new Line(-50d, 0d, 50d, 100d);
            line1.setLayoutX(50d);
            line1.setStrokeWidth(10d);
            Line line2 = new Line(100d, 0d, 0d, 100d);
            line2.setStrokeWidth(10d);

            pane.getChildren().add(line1);
            pane.getChildren().add(line2);
        }

    }
}
