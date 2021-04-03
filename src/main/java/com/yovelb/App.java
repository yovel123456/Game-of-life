package com.yovelb;

import com.yovelb.logic.*;
import com.yovelb.model.Board;
import com.yovelb.model.BoundedBoard;
import com.yovelb.util.event.EventBus;
import com.yovelb.view.InfoBar;
import com.yovelb.view.MainView;
import com.yovelb.view.SimulationCanvas;
import com.yovelb.view.Toolbar;
import com.yovelb.viewmodel.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) {
        EventBus eventBus = new EventBus();

        ApplicationStateManager appStateManager = new ApplicationStateManager();
        BoardViewModel boardViewModel = new BoardViewModel();
        Board initialBoard = new BoundedBoard(20, 12);

        Editor editor = new Editor(initialBoard);
        eventBus.listenFor(DrawModeEvent.class, editor::handle);
        eventBus.listenFor(BoardEvent.class, editor::handle);
        editor.getCursorPositionProperty().listen(cursorPosition -> boardViewModel.getCursorPositionProperty().set(cursorPosition));

        Simulator simulator = new Simulator(appStateManager);
        eventBus.listenFor(SimulatorEvent.class, simulator::handle);
        editor.getBoard().listen(editorBoard -> {
            simulator.getInitialBoard().set(editorBoard);
            boardViewModel.getBoardProperty().set(editorBoard);
        });
        simulator.getCurrentBoard().listen(simulationBoard -> boardViewModel.getBoardProperty().set(simulationBoard));

        appStateManager.getAppStateProperty().listen(editor::onAppStateChanged);

        boardViewModel.getBoardProperty().set(initialBoard);

        SimulationCanvas simulationCanvas = new SimulationCanvas(boardViewModel, eventBus);
        Toolbar toolbar = new Toolbar(eventBus);

        InfoBarViewModel infoBarViewModel = new InfoBarViewModel();
        editor.getDrawModeProperty().listen(drawMode -> infoBarViewModel.getCurrentDrawModeProperty().set(drawMode));
        editor.getCursorPositionProperty().listen(cursorPosition -> infoBarViewModel.getCursorPositionProperty().set(cursorPosition));

        InfoBar infoBar = new InfoBar(infoBarViewModel);


        MainView mainView = new MainView(eventBus);

        mainView.setTop(toolbar);
        mainView.setCenter(simulationCanvas);
        mainView.setBottom(infoBar);

        Scene scene = new Scene(mainView, 1000, 800);
        stage.setScene(scene);
        stage.show();

    }
    public static void main(String[] args) { launch(); }
}