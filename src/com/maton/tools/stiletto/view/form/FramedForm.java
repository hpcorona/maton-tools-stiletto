package com.maton.tools.stiletto.view.form;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import com.maton.tools.stiletto.model.Image;
import com.maton.tools.stiletto.model.base.IModelListener;

public class FramedForm {

	protected Composite parent;
	protected Image image;
	protected Composite container;

	protected Button framed;
	protected Text left;
	protected Text top;
	protected Text right;
	protected Text bottom;
	
	protected IModelListener listener;

	public FramedForm(Composite parent, Image image, IModelListener listener) {
		this.parent = parent;
		this.image = image;
		this.listener = listener;

		build();

		modelToScreen();

		setupEvents();
	}

	public void dispose() {
	}

	public Composite getContainer() {
		return container;
	}

	protected void build() {
		container = new Composite(parent, SWT.BORDER);
		FormLayout layout = new FormLayout();
		layout.spacing = 5;
		layout.marginTop = 5;
		layout.marginLeft = 5;
		layout.marginRight = 5;
		layout.marginBottom = 5;
		container.setLayout(layout);

		createFormSection();
	}

	protected void createFormSection() {
		Label lblFramed = new Label(container, SWT.NONE);
		framed = new Button(container, SWT.CHECK);
		framed.setText("Framed");

		Label lblLeft = new Label(container, SWT.NONE);
		lblLeft.setText("Left:");
		left = new Text(container, SWT.BORDER | SWT.SINGLE);

		Label lblTop = new Label(container, SWT.NONE);
		lblTop.setText("Top:");
		top = new Text(container, SWT.BORDER | SWT.SINGLE);

		Label lblRight = new Label(container, SWT.NONE);
		lblRight.setText("Right:");
		right = new Text(container, SWT.BORDER | SWT.SINGLE);

		Label lblBottom = new Label(container, SWT.NONE);
		lblBottom.setText("Bottom:");
		bottom = new Text(container, SWT.BORDER | SWT.SINGLE);

		FormTools.forBoth(lblFramed, framed, null, 10);
		FormTools.forBoth(lblLeft, left, framed, 0);
		FormTools.forBoth(lblTop, top, left, 0);
		FormTools.forBoth(lblRight, right, top, 0);
		FormTools.forBoth(lblBottom, bottom, right, 0);
	}

	private void setupEvents() {
		Listener generic = new Listener() {
			@Override
			public void handleEvent(Event event) {
				screenToModel();
			}
		};

		framed.addListener(SWT.Selection, generic);
		left.addListener(SWT.Modify, generic);
		top.addListener(SWT.Modify, generic);
		right.addListener(SWT.Modify, generic);
		bottom.addListener(SWT.Modify, generic);
	}
	
	protected float parseFloat(Text item, int max) {
		if (item.getText().equals("")) {
			return 0;
		}
		
		try {
			float num = Float.parseFloat(item.getText().trim());
			
			if (num > max) {
				num = max - 1;
				item.setText("" + num);
			} else if (num < 0) {
				num = 0;
				item.setText("" + num);
			}
			
			return num;
		} catch (Throwable e) {
			item.setText("0");
			return 0;
		}
	}

	protected void screenToModel() {
		if (image == null) {
			container.setVisible(false);
			return;
		}
		container.setVisible(true);

		image.setFramed(framed.getSelection());
		
		image.setLeft(parseFloat(left, image.getWidth()));
		image.setRight(parseFloat(right, image.getWidth()));
		image.setTop(parseFloat(top, image.getHeight()));
		image.setBottom(parseFloat(bottom, image.getHeight()));
		
		listener.notifyChange(image);
	}

	protected void modelToScreen() {
		if (image == null) {
			container.setVisible(false);
			return;
		}
		container.setVisible(true);

		framed.setSelection(image.isFramed());
		left.setText("" + image.getLeft());
		top.setText("" + image.getTop());
		right.setText("" + image.getRight());
		bottom.setText("" + image.getBottom());
	}

}
