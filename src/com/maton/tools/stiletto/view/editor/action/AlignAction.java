package com.maton.tools.stiletto.view.editor.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;

import com.maton.tools.stiletto.model.Image;
import com.maton.tools.stiletto.model.base.Positioned;
import com.maton.tools.stiletto.view.table.PositionedTable;

public class AlignAction extends Action {
	
	public static final int BOTTOM = 0;
	public static final int CENTER = 1;
	public static final int LEFT = 2;
	public static final int MIDDLE = 3;
	public static final int RIGHT = 4;
	public static final int TOP = 5;
	
	static String DESCRIPTIONS[] = {
		"Align bottom",
		"Align center",
		"Align left",
		"Align middle",
		"Align right",
		"Align top"
	};
	
	static ImageDescriptor IMAGES[] = {
		ImageDescriptor.createFromFile(AlignAction.class, "layers-alignment-bottom.png"),
		ImageDescriptor.createFromFile(AlignAction.class, "layers-alignment-center.png"),
		ImageDescriptor.createFromFile(AlignAction.class, "layers-alignment-left.png"),
		ImageDescriptor.createFromFile(AlignAction.class, "layers-alignment-middle.png"),
		ImageDescriptor.createFromFile(AlignAction.class, "layers-alignment-right.png"),
		ImageDescriptor.createFromFile(AlignAction.class, "layers-alignment.png")
	};
	
	protected PositionedTable table;
	protected int align;
	
	public AlignAction(PositionedTable table, int align) {
		this.table = table;
		this.align = align;
		
		this.setText(DESCRIPTIONS[align]);
		this.setImageDescriptor(IMAGES[align]);
	}
	
	public void run() {
		Positioned<Image> positioned = table.getSelected();
		if (positioned == null) {
			return;
		}
		
		int width = positioned.getSource().getWidth();
		int height = positioned.getSource().getHeight();
		
		switch (align) {
		case BOTTOM:
			positioned.setY(-height);
			break;
		case CENTER:
			positioned.setX(-width / 2);
			break;
		case LEFT:
			positioned.setX(0);
			break;
		case MIDDLE:
			positioned.setY(-height / 2);
			break;
		case RIGHT:
			positioned.setX(-width);
			break;
		case TOP:
			positioned.setY(0);
			break;
		default:
		}
		
		table.getSprite().notifyChange(positioned);
	}

}
