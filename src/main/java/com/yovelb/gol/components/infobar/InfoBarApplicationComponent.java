package com.yovelb.gol.components.infobar;

import com.yovelb.gol.ApplicationComponent;
import com.yovelb.gol.ApplicationContext;
import com.yovelb.gol.components.editor.EditorState;

public class InfoBarApplicationComponent implements ApplicationComponent {
    @Override
    public void initComponent(ApplicationContext context) {
        EditorState editorState = context.getStateRegistry().getState(EditorState.class);

        InfoBarViewModel viewModel = new InfoBarViewModel();
        editorState.getCursorPositionProperty().listen(viewModel.getCursorPositionProperty()::set);
        editorState.getDrawModeProperty().listen(viewModel.getCurrentDrawModeProperty()::set);

        InfoBar infoBar = new InfoBar(viewModel);
        context.getMainView().setBottom(infoBar);
    }

    @Override
    public void initState(ApplicationContext context) {

    }
}
