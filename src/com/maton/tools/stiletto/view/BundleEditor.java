package com.maton.tools.stiletto.view;

import java.io.File;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabFolder2Adapter;
import org.eclipse.swt.custom.CTabFolderEvent;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ExpandBar;

import com.maton.tools.stiletto.model.Bundle;
import com.maton.tools.stiletto.view.outline.AnimationsOutline;
import com.maton.tools.stiletto.view.outline.FontsOutline;
import com.maton.tools.stiletto.view.outline.ImagesOutline;
import com.maton.tools.stiletto.view.outline.SpritesOutline;

public class BundleEditor {
	
	ImageDescriptor icon = ImageDescriptor.createFromFile(getClass(), "toolbox.png");
	
	protected File file;
	protected CTabFolder parent;
	protected CTabItem item;
	protected Composite container;
	protected SashForm sash;
	protected ExpandBar sections;
	protected ImagesOutline images;
	protected SpritesOutline sprites;
	protected AnimationsOutline animations;
	protected FontsOutline fonts;
	protected EditorContainer editors;
	protected Bundle bundle;
	
	public BundleEditor(CTabFolder parent, File file) {
		this.file = file;
		this.parent = parent;

		bundle = new Bundle(parent.getDisplay(), file);
		
		item = new CTabItem(parent, SWT.CLOSE);
		
		build();
		
		item.setControl(container);
		item.setImage(icon.createImage());
		
		if (file == null) {
			item.setText("untitled");
			item.setToolTipText("New bundle");
		} else {
			item.setText(file.getName());
			item.setToolTipText(file.getAbsolutePath());
		}
	}
	
	protected void build() {
		container = new Composite(parent, SWT.NONE);
		container.setLayout(new FillLayout());

		sash = new SashForm(container, SWT.HORIZONTAL | SWT.BORDER);
		
		sections = new ExpandBar(sash, SWT.V_SCROLL);
		sections.setSpacing(2);
		
		images = new ImagesOutline(sections, 0, bundle.getImages());
		sprites = new SpritesOutline(sections, 1, bundle.getSprites());
		animations = new AnimationsOutline(sections, 2);
		fonts = new FontsOutline(sections, 3);
		
		editors = new EditorContainer(sash);
		
		sash.setWeights(new int[] { 400, 500 });
		
		editors.getContainer().addCTabFolder2Listener(new CTabFolder2Adapter() {
			@Override
			public void maximize(CTabFolderEvent event) {
				if (sash.getMaximizedControl() == null) {
					sash.setMaximizedControl(editors.getContainer());
				} else {
					sash.setMaximizedControl(null);
				}
			}
		});
	}
	
	public CTabItem getItem() {
		return item;
	}
	
	public void refresh() {
		bundle.refresh();
	}
	
	public void save() {
		editors.save();
		bundle.save();
	}
	
	public void importImages(File[] list) {
		bundle.imports(list);
	}
	
	public void buildBundle() {
		bundle.build();
	}
	
	public Bundle getBundle() {
		return bundle;
	}
	
	public void launchEditor(Object obj) {
		editors.launchEditor(obj);
	}
	
}
