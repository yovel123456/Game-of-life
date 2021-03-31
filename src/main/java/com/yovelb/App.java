package com.yovelb;

import com.yovelb.model.Board;
import com.yovelb.model.BoundedBoard;
import com.yovelb.util.event.EventBus;
import com.yovelb.view.SimulationCanvas;
import com.yovelb.viewmodel.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) {
        EventBus eventBus = new EventBus();

        ApplicationViewModel appViewModel = new ApplicationViewModel();
        BoardViewModel boardViewModel = new BoardViewModel();
        Board initialBoard = new BoundedBoard(20, 12);

        EditorViewModel editorViewModel = new EditorViewModel(boardViewModel, initialBoard);
        eventBus.listenFor(DrawModeEvent.class, editorViewModel::handle);
        eventBus.listenFor(BoardEvent.class, editorViewModel::handle);

        SimulationViewModel simulationViewModel = new SimulationViewModel(boardViewModel, appViewModel, editorViewModel);
        eventBus.listenFor(SimulatorEvent.class, simulationViewModel::handle);

        appViewModel.getAppStateProperty().listen(editorViewModel::onAppStateChanged);

        boardViewModel.getBoardProperty().set(initialBoard);

        SimulationCanvas simulationCanvas = new SimulationCanvas(boardViewModel, editorViewModel, eventBus);
        Toolbar toolbar = new Toolbar(eventBus);
        InfoBar infoBar = new InfoBar(editorViewModel);

        MainView mainView = new MainView(editorViewModel);

        mainView.setTop(toolbar);
        mainView.setCenter(simulationCanvas);
        mainView.setBottom(infoBar);

        Scene scene = new Scene(mainView, 1000, 800);
        stage.setScene(scene);
        stage.show();

    }
    public static void main(String[] args) { launch(); }
}