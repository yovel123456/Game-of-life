package com.yovelb.gol.components.editor;

import java.util.Collection;
import java.util.HashSet;

public class Edit extends HashSet<Change> {
    public Edit() {
    }

    public Edit(Edit edit) {
        super(edit);
    }
}