package com.maton.tools.stiletto.view.action;

import java.io.File;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

import com.maton.tools.stiletto.model.ImagePool;

public class ImportAction extends Action {

	Shell window;
	static ImageDescriptor icon = ImageDescriptor.createFromFile(
			ImportAction.class, "image-import.png");

	protected ImagePool pool;

	public ImportAction(Shell window, ImagePool pool) {
		this.window = window;
		this.pool = pool;

		setText("Import images...");
		setToolTipText("Import PNG images");
		setImageDescriptor(icon);
	}

	public void run() {
		FileDialog dialog = new FileDialog(window, SWT.MULTI);
		dialog.setText("Import PNG images...");
		dialog.setFilterNames(new String[] { "PNG files (*.png)" });
		dialog.setFilterExtensions(new String[] { "*.png" });
		String opened = dialog.open();

		if (opened != null) {
			File path = new File(opened).getParentFile();
			String[] files = dialog.getFileNames();

			for (int i = 0; i < files.length; i++) {
				pool.importFile(new File(path, files[i]));
			}
		}
	}
}
