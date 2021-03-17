package com.yovelb;

import com.yovelb.model.Board;
import com.yovelb.model.BoundedBoard;
import com.yovelb.view.SimulationCanvas;
import com.yovelb.viewmodel.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) {
        ApplicationViewModel appViewModel = new ApplicationViewModel(ApplicationState.EDITING);
        BoardViewModel boardViewModel = new BoardViewModel();
        Board initialBoard = new BoundedBoard(20, 12);
        EditorViewModel editorViewModel = new EditorViewModel(boardViewModel, initialBoard);
        SimulationViewModel simulationViewModel = new SimulationViewModel(boardViewModel);

        appViewModel.listenToAppState(editorViewModel::onAppStateChanged);
        appViewModel.listenToAppState(simulationViewModel::onAppStateChanged);

        boardViewModel.setBoard(initialBoard);

        SimulationCanvas simulationCanvas = new SimulationCanvas(boardViewModel, editorViewModel);
        Toolbar toolbar = new Toolbar(appViewModel, editorViewModel, simulationViewModel);
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