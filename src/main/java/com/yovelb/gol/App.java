package com.yovelb.gol;

import com.yovelb.app.command.CommandExecutor;
import com.yovelb.gol.logic.*;
import com.yovelb.gol.logic.editor.BoardEvent;
import com.yovelb.gol.logic.editor.DrawModeEvent;
import com.yovelb.gol.logic.editor.Editor;
import com.yovelb.gol.logic.simulator.Simulator;
import com.yovelb.gol.logic.simulator.SimulatorEvent;
import com.yovelb.gol.model.Board;
import com.yovelb.gol.model.BoundedBoard;
import com.yovelb.gol.state.EditorState;
import com.yovelb.gol.state.SimulatorState;
import com.yovelb.app.state.StateRegistry;
import com.yovelb.app.event.EventBus;
import com.yovelb.gol.view.InfoBar;
import com.yovelb.gol.view.MainView;
import com.yovelb.gol.view.SimulationCanvas;
import com.yovelb.gol.view.Toolbar;
import com.yovelb.gol.viewmodel.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) {
        EventBus eventBus = new EventBus();
        StateRegistry stateRegistry = new StateRegistry();
        CommandExecutor commandExecutor = new CommandExecutor(stateRegistry);

        //ApplicationStateManager appStateManager = new ApplicationStateManager();
        BoardViewModel boardViewModel = new BoardViewModel();
        Board initialBoard = new BoundedBoard(20, 12);

        EditorState editorState = new EditorState(initialBoard);
        stateRegistry.registerState(EditorState.class, editorState);
        Editor editor = new Editor(editorState, commandExecutor);
        eventBus.listenFor(DrawModeEvent.class, editor::handle);
        eventBus.listenFor(BoardEvent.class, editor::handle);
        editorState.getCursorPositionProperty().listen(cursorPosition -> boardViewModel.getCursorPositionProperty().set(cursorPosition));

        SimulatorState simulatorState = new SimulatorState(initialBoard);
        stateRegistry.registerState(SimulatorState.class, simulatorState);

        Simulator simulator = new Simulator(simulatorState, commandExecutor);
        eventBus.listenFor(SimulatorEvent.class, simulator::handle);
        editorState.getBoardProperty().listen(editorBoard -> {
            simulatorState.getBoardProperty().set(editorBoard);
            boardViewModel.getBoardProperty().set(editorBoard);
        });
        simulatorState.getBoardProperty().listen(simulationBoard -> boardViewModel.getBoardProperty().set(simulationBoard));

        /*appStateManager.getAppStateProperty().listen(editor::onAppStateChanged);
        appStateManager.getAppStateProperty().listen(newState -> {
            if (newState == ApplicationState.EDITING) {
                Board currentBoard = editorState.getBoardProperty().get();
                boardViewModel.getBoardProperty().set(currentBoard);
                simulatorState.getBoardProperty().set(currentBoard);
            }
        });*/

        boardViewModel.getBoardProperty().set(initialBoard);

        SimulationCanvas simulationCanvas = new SimulationCanvas(eventBus);
        Toolbar toolbar = new Toolbar(eventBus);

        InfoBarViewModel infoBarViewModel = new InfoBarViewModel();
        editorState.getDrawModeProperty().listen(drawMode -> infoBarViewModel.getCurrentDrawModeProperty().set(drawMode));
        editorState.getCursorPositionProperty().listen(cursorPosition -> infoBarViewModel.getCursorPositionProperty().set(cursorPosition));

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