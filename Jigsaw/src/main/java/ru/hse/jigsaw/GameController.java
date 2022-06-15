package ru.hse.jigsaw;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

import java.net.URL;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Class of controler of game.
 */
public class GameController implements Initializable {
    /**
     * Fielsd of field of game, new figure, starting time, finish time(for timer) and timer.
     */
    Field field;
    Figure newFig;
    LocalTime startTime;
    LocalTime finishTime;


    Timer timer;
    /**
     * Flag if game stopped.
     */
    boolean gameStopped = false;
    /**
     * All elements on scene.
     */
    @FXML
    private HBox hbox;

    @FXML
    private Label labelMove;

    @FXML
    private Canvas canvasField;

    @FXML
    private Canvas canvasHighlightField;

    @FXML
    private Canvas canvasNext;

    @FXML
    private Label labelWatch;

    /**
     * Constructor of game controller.
     */
    public GameController() {
        field = new Field();
        newFig = Figure.getRandomFigure();
        timer = new Timer();
    }

    /**
     * Method for restarting timer.
     */
    private void timerRestart() {
        startTime = LocalTime.now();
        timer.purge();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (gameStopped) this.cancel();
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        onTimer();
                    }
                });
            }
        }, 0, 1000);
    }

    /**
     * Method for stopping timer.
     */
    private void timerStop() {
        //timer.cancel();
        finishTime = LocalTime.now();
        timer.purge();
    }

    /**
     * Event of restarting game.
     */
    @FXML
    private void onButtonRestart() {
        //Stage stage = (Stage) canvasField.getScene().getWindow();
        //stage.close();
        timerRestart();
        field.restart();
        gameStopped = false;
        processNextMove();
    }

    /**
     * Event of finishing game.
     */
    @FXML
    private void onButtonFinish() {
        timerStop();
        gameStopped = true;
        long seconds = Duration.between(startTime, finishTime).toSeconds();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Хотите выйти из игры?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        alert.setHeaderText("Игра Завершена");
        alert.setContentText(
                String.format("Сделано %d ходов \nвремя: %02d:%02d\nХотите выйти из игры?", field.getMoves(), seconds / 60, seconds % 60));
        alert.setTitle("Игра Завершена");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            Platform.exit();
            System.exit(0);
        }

    }

    /**
     * Method for generating next move.
     */
    private void processNextMove() {
        newFig = Figure.getRandomFigure();
        labelMove.setText(String.format("ход:%d", field.getMoves() + 1));
        redrawNextFigure();
        redrawField();
    }

    /**
     * Initializing all elements.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        startTime = LocalTime.now();
        timerRestart();
        boolean fit = field.isFit(Figure.getFigure(0), 1, 0);
        hbox.widthProperty().addListener(observable -> onResize());
        hbox.heightProperty().addListener(observable -> onResize());
        canvasField.widthProperty().addListener(observable -> redrawField());
        canvasField.heightProperty().addListener(observable -> redrawField());

        redrawField();
        redrawNextFigure();
        field.restart();
        processNextMove();

    }

    /**
     * Redrawing field.
     */
    private void onResize() {
        double x = Math.min(hbox.getWidth() - 40, hbox.getHeight() - 40);
        canvasField.setWidth(x);
        canvasField.setHeight(x);
        canvasHighlightField.setWidth(x);
        canvasHighlightField.setHeight(x);
        redrawField();
    }

    /**
     * Getting cell size.
     *
     * @return - cell size.
     */
    private double getFieldCellSize() {
        return canvasField.getWidth() / Field.N_CELLS;
    }

    /**
     * Event of starting drag.
     *
     * @param ev
     */
    @FXML
    private void onDragStart(MouseEvent ev) {
        if (gameStopped) return;
        double x = ev.getX();
        double y = ev.getY();
        canvasNext.startFullDrag();
        //System.out.println("X="+x+" y="+y);

    }

    /**
     * Event of drag moving figure.
     *
     * @param ev
     */
    @FXML
    private void onDragMove(MouseEvent ev) {
        if (gameStopped) return;
        GraphicsContext gc = canvasHighlightField.getGraphicsContext2D();
        gc.clearRect(0, 0, canvasHighlightField.getWidth(), canvasHighlightField.getHeight());

        gc.setStroke(Color.ROSYBROWN);
        gc.setFill(Color.DARKSLATEBLUE);
        gc.setLineDashes();
        gc.setLineWidth(2);

        double sz = getFieldCellSize();
        int xc = (int) Math.floor(ev.getX() / sz);
        int yc = (int) Math.floor(ev.getY() / sz);
        for (int i = 0; i < newFig.getNBlocks(); i++) {
            gc.strokeRect(sz * (xc + newFig.getBlock(i).x()),
                    sz * (yc + newFig.getBlock(i).y()),
                    sz, sz);
            gc.fillRect(sz * (xc + newFig.getBlock(i).x()),
                    sz * (yc + newFig.getBlock(i).y()),
                    sz, sz);
        }
    }

    /**
     * Event of stopping figure(drag).
     *
     * @param ev
     */
    @FXML
    private void onDragStop(MouseEvent ev) {
        if (gameStopped) return;
        GraphicsContext gc = canvasHighlightField.getGraphicsContext2D();
        gc.clearRect(0, 0, canvasHighlightField.getWidth(), canvasHighlightField.getHeight());
        double sz = getFieldCellSize();
        int xc = (int) Math.floor(ev.getX() / sz);
        int yc = (int) Math.floor(ev.getY() / sz);
        //System.out.println("Drop: x="+xc+" y="+yc);
        if (field.isFit(newFig, xc, yc)) {
            field.makeMove(newFig, xc, yc);
            redrawField();
            processNextMove();

        }

    }

    /**
     * Method for initializing timer.
     */
    private void onTimer() {
        long seconds = Duration.between(startTime, LocalTime.now()).toSeconds();
        labelWatch.setText(String.format("%02d:%02d", seconds / 60, seconds % 60));
    }

    /**
     * Redraing field.
     */
    private void redrawField() {
        double w = canvasField.getWidth();
        double h = canvasField.getHeight();
        int nC = Field.N_CELLS;
        GraphicsContext gc = canvasField.getGraphicsContext2D();
        gc.clearRect(0, 0, w, h);
        gc.setFill(Color.WHEAT);
        gc.fillRect(0, 0, w, h);
        gc.setStroke(Color.BLUEVIOLET);
        gc.setLineWidth(0.5);
        gc.setLineDashes(2);
        for (int i = 0; i <= nC; i++) {
            gc.strokeLine((i * w) / nC, 0, (i * w) / nC, h);
        }
        for (int i = 0; i <= 9; i++) {
            gc.strokeLine(0, (i * h) / nC, w, (i * h) / nC);
        }
        gc.setLineDashes();
        gc.setLineWidth(3);
        gc.strokeRect(0, 0, w, h);

        // Figure:
        gc.setStroke(Color.ROSYBROWN);
        gc.setFill(Color.DARKSLATEBLUE);
        gc.setLineDashes();
        gc.setLineWidth(2);
        for (int i = 0; i < nC; i++)
            for (int j = 0; j < nC; j++)
                if (field.get(i, j) == 1) {
                    gc.fillRect((i * w) / nC, (j * h) / nC, w / nC, h / nC);
                    gc.strokeRect((i * w) / nC, (j * h) / nC, w / nC, h / nC);
                }
    }

    /**
     * Method for redrawing next Figure for next step of game.
     */
    private void redrawNextFigure() {
        double w = canvasNext.getWidth();
        double h = canvasNext.getHeight();
        GraphicsContext gc = canvasNext.getGraphicsContext2D();

        // BackGround:
        gc.clearRect(0, 0, w, h);
        gc.setFill(Color.WHEAT);
        gc.fillRect(0, 0, w, h);
        gc.setStroke(Color.BLUEVIOLET);
        gc.setLineWidth(0.5);
        gc.setLineDashes(2);
        for (int i = 0; i <= 3; i++) {
            gc.strokeLine((i * w) / 3, 0, (i * w) / 3, h);
        }
        for (int i = 0; i <= 3; i++) {
            gc.strokeLine(0, (i * h) / 3, w, (i * h) / 3);
        }
        gc.setLineDashes();
        gc.setLineWidth(3);
        gc.strokeRect(0, 0, w, h);

        // Figure:
        gc.setStroke(Color.ROSYBROWN);
        gc.setFill(Color.DARKSLATEBLUE);
        gc.setLineDashes();
        gc.setLineWidth(2);
        for (int i = 0; i < newFig.getNBlocks(); i++) {
            Block block = newFig.getBlock(i);
            double x = ((block.x() + 1) * w) / 3;
            double y = ((block.y() + 1) * h) / 3;
            gc.fillRect(x, y, w / 3, h / 3);
            gc.strokeRect(x, y, w / 3, h / 3);
        }
    }
}
