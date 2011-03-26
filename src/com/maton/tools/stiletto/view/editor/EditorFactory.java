package com.maton.tools.stiletto.view.editor;

import org.eclipse.swt.custom.CTabFolder;

import com.maton.tools.stiletto.model.Image;
import com.maton.tools.stiletto.model.Sprite;

public class EditorFactory {
	public static void launchEditor(CTabFolder parent, Object obj) {
		DefaultEditor editor = null;

		if (obj instanceof Image) {
			editor = new ImageEditor(parent, (Image) obj);
		}

		if (obj instanceof Sprite) {
			editor = new SpriteEditor(parent, (Sprite) obj);
		}

		if (editor != null) {
			parent.setSelection(editor.getItem());
		}
	}
}
