package com.maton.tools.stiletto.view.editor;

import org.eclipse.swt.custom.CTabFolder;

import com.maton.tools.stiletto.model.Actor;
import com.maton.tools.stiletto.model.Animation;
import com.maton.tools.stiletto.model.Font;
import com.maton.tools.stiletto.model.Image;
import com.maton.tools.stiletto.model.Sprite;
import com.maton.tools.stiletto.model.SpritePool;
import com.maton.tools.stiletto.model.Widget;

public class EditorFactory {
	public static void launchEditor(CTabFolder parent, Object obj) {
		DefaultEditor editor = null;

		if (obj == SpritePool.EMPTY) {
			return;
		}

		if (obj instanceof Image) {
			editor = new ImageEditor(parent, (Image) obj);
		}

		if (obj instanceof Sprite) {
			editor = new SpriteEditor(parent, (Sprite) obj);
		}

		if (obj instanceof Animation) {
			editor = new AnimationEditor(parent, (Animation) obj);
		}

		if (obj instanceof Actor) {
			editor = new ActorEditor(parent, (Actor) obj);
		}

		if (obj instanceof Font) {
			editor = new FontEditor(parent, (Font) obj);
		}
		
		if (obj instanceof Widget) {
			editor = new WidgetEditor(parent, (Widget) obj);
		}

		if (editor != null) {
			parent.setSelection(editor.getItem());
		}
	}
}
