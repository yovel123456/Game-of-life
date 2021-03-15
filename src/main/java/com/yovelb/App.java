package com.yovelb;

import com.yovelb.model.Board;
import com.yovelb.model.BoundedBoard;
import com.yovelb.viewmodel.ApplicationState;
import com.yovelb.viewmodel.ApplicationViewModel;
import com.yovelb.viewmodel.BoardViewModel;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) {
        ApplicationViewModel appViewModel = new ApplicationViewModel(ApplicationState.EDITING);
        BoardViewModel boardViewModel = new BoardViewModel();

        Board initialBoard = new BoundedBoard(10, 10);

        MainView mainView = new MainView(appViewModel, boardViewModel, initialBoard);
        Scene scene = new Scene(mainView, 640, 480);
        stage.setScene(scene);
        stage.show();

        boardViewModel.setBoard(initialBoard);
    }
    public static void main(String[] args) { launch(); }
}