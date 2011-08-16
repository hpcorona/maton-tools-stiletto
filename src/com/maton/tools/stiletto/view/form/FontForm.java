package com.maton.tools.stiletto.view.form;

import java.awt.GraphicsEnvironment;

import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;

import com.maton.tools.stiletto.model.Font;
import com.maton.tools.stiletto.view.editor.action.DeleteAlternateFontAction;
import com.maton.tools.stiletto.view.table.AlternateFontsTable;
import com.maton.tools.stiletto.view.table.DefaultTable;

public class FontForm {

	protected Composite parent;
	protected Font font;
	protected Composite container;

	protected TabFolder folder;

	protected Combo face;
	protected Text size;
	protected Button bold;
	protected Button italic;

	protected Button fill;
	protected Button fillAngleVertical;
	protected Button fillAngleHorizontal;
	protected Button fillColor0;
	protected Button fillColor1;

	protected Button stroke;
	protected Text strokeWidth;
	protected Button strokeAngleVertical;
	protected Button strokeAngleHorizontal;
	protected Button strokeColor0;
	protected Button strokeColor1;

	protected Button shadow;
	protected Text shadowX;
	protected Text shadowY;
	protected Scale shadowAlpha;
	protected Button shadowColor;

	protected Text characters;

	protected AlternateFontsTable table;

	protected Runnable updater;

	public FontForm(Composite parent, Font font) {
		this.parent = parent;
		this.font = font;

		build();

		modelToScreen();

		updater = new Runnable() {
			@Override
			public void run() {
				screenToModel();

				Display.getCurrent().timerExec(250, this);
			}
		};

		Display.getCurrent().timerExec(250, updater);
	}

	public void dispose() {
		Display.getCurrent().timerExec(-1, updater);
	}

	public Composite getContainer() {
		return container;
	}

	protected void build() {
		folder = new TabFolder(parent, SWT.BORDER);

		TabItem config = new TabItem(folder, SWT.BORDER);
		config.setText("Font");
		config.setControl(createConfig());

		TabItem chars = new TabItem(folder, SWT.BORDER);
		chars.setText("Chars");
		chars.setControl(createChars());

		TabItem res = new TabItem(folder, SWT.BORDER);
		res.setText("Res.");
		res.setControl(createTable());

		setupEvents();
	}

	protected Composite createConfig() {
		Composite container = new Composite(folder, SWT.BORDER);
		this.container = container;

		FormLayout layout = new FormLayout();
		layout.spacing = 5;
		layout.marginTop = 5;
		layout.marginLeft = 5;
		layout.marginRight = 5;
		layout.marginBottom = 5;
		container.setLayout(layout);

		createFontSection();
		createFillSection();
		createStrokeSection();
		createShadowSection();

		return container;
	}

	protected Composite createChars() {
		Composite container = new Composite(folder, SWT.BORDER);
		this.container = container;

		FormLayout layout = new FormLayout();
		layout.spacing = 5;
		layout.marginTop = 5;
		layout.marginLeft = 5;
		layout.marginRight = 5;
		layout.marginBottom = 5;
		container.setLayout(layout);

		Label lblChars = new Label(container, SWT.NONE);
		lblChars.setText("Chars:");
		characters = new Text(container, SWT.MULTI | SWT.WRAP | SWT.BORDER
				| SWT.V_SCROLL);
		characters.setLayoutData(new GridData(GridData.FILL_BOTH));
		FormTools.forBoth(lblChars, characters, null, 5);
		FormData data = (FormData) characters.getLayoutData();
		data.bottom = new FormAttachment(100);

		return container;
	}

	protected Composite createTable() {
		Composite container = new Composite(folder, SWT.BORDER);
		this.container = container;
		
		GridLayout layout = new GridLayout();
		layout.marginBottom = 0;
		layout.marginHeight = 0;
		layout.marginLeft = 0;
		layout.marginRight = 0;
		layout.marginTop = 0;
		layout.marginWidth = 0;
		layout.verticalSpacing = 0;
		layout.horizontalSpacing = 0;
		container.setLayout(layout);

		ToolBarManager toolbar = new ToolBarManager(SWT.BORDER | SWT.WRAP);
		toolbar.createControl(container);
		
		table = new AlternateFontsTable(container,
				DefaultTable.DEFAULT_TABLE_STYLE, font);
		
		toolbar.add(new DeleteAlternateFontAction(table, font));
		toolbar.update(true);

		GridData gd = null;

		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		gd.minimumHeight = 200;
		gd.horizontalSpan = 1;
		toolbar.getControl().setLayoutData(gd);
		toolbar.update(true);

		gd = new GridData();
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessVerticalSpace = true;
		gd.horizontalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		gd.horizontalSpan = 1;
		table.getTable().setLayoutData(gd);
		
		return container;
	}

	protected void createFontSection() {
		Label lblFace = new Label(container, SWT.NONE);
		lblFace.setText("Face:");
		buildFontCombo();

		Label lblSize = new Label(container, SWT.NONE);
		lblSize.setText("Size:");
		size = new Text(container, SWT.SINGLE | SWT.BORDER);

		Label lblBold = new Label(container, SWT.NONE);
		bold = new Button(container, SWT.CHECK);
		bold.setText("Bold");

		Label lblItalic = new Label(container, SWT.NONE);
		italic = new Button(container, SWT.CHECK);
		italic.setText("Italic");

		// Layout
		FormTools.forBoth(lblFace, face, null, 0);
		FormTools.forBoth(lblSize, size, face, 0);
		FormTools.forBoth(lblBold, bold, size, 0);
		FormTools.forBoth(lblItalic, italic, bold, 0);
	}

	protected void buildFontCombo() {
		face = new Combo(container, SWT.BORDER);

		GraphicsEnvironment gEnv = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		String fonts[] = gEnv.getAvailableFontFamilyNames();

		for (String name : fonts) {
			face.add(name);
		}
	}

	protected void createFillSection() {
		Label lblFill = new Label(container, SWT.NONE);
		fill = new Button(container, SWT.CHECK);
		fill.setText("Fill");

		Label lblAngleVert = new Label(container, SWT.NONE);
		lblAngleVert.setText("Angle:");
		Group grp = new Group(container, SWT.NONE);
		grp.setLayout(new RowLayout());
		fillAngleVertical = new Button(grp, SWT.RADIO);
		fillAngleVertical.setText("Horizontal");
		fillAngleHorizontal = new Button(grp, SWT.RADIO);
		fillAngleHorizontal.setText("Vertical");

		Label lblGradient = new Label(container, SWT.NONE);
		lblGradient.setText("Color:");
		colorsFill = new Group(container, SWT.NONE);
		colorsFill.setLayout(new RowLayout(SWT.HORIZONTAL));
		fillColor0 = createColorButton(colorsFill);
		fillColor1 = createColorButton(colorsFill);

		FormTools.forBoth(lblFill, fill, italic, 5);
		FormTools.forBoth(lblAngleVert, grp, fill, 0);
		FormTools.forBoth(lblGradient, colorsFill, grp, 0);
	}

	Group colorsFill = null;
	Group colorsStroke = null;

	protected void createStrokeSection() {
		Label lblStroke = new Label(container, SWT.NONE);
		stroke = new Button(container, SWT.CHECK);
		stroke.setText("Stroke");

		Label lblStrokeWidth = new Label(container, SWT.NONE);
		lblStrokeWidth.setText("Width:");
		strokeWidth = new Text(container, SWT.BORDER | SWT.SINGLE);

		Label lblAngleVert = new Label(container, SWT.NONE);
		lblAngleVert.setText("Angle:");
		Group grp = new Group(container, SWT.NONE);
		grp.setLayout(new RowLayout());
		strokeAngleVertical = new Button(grp, SWT.RADIO);
		strokeAngleVertical.setText("Horizontal");
		strokeAngleHorizontal = new Button(grp, SWT.RADIO);
		strokeAngleHorizontal.setText("Vertical");

		Label lblGradient = new Label(container, SWT.NONE);
		lblGradient.setText("Color:");
		colorsStroke = new Group(container, SWT.NONE);
		colorsStroke.setLayout(new RowLayout(SWT.HORIZONTAL));
		strokeColor0 = createColorButton(colorsStroke);
		strokeColor1 = createColorButton(colorsStroke);

		FormTools.forBoth(lblStroke, stroke, colorsFill, 5);
		FormTools.forBoth(lblStrokeWidth, strokeWidth, stroke, 0);
		FormTools.forBoth(lblAngleVert, grp, strokeWidth, 0);
		FormTools.forBoth(lblGradient, colorsStroke, grp, 0);
	}

	protected void createShadowSection() {
		Label lblShadow = new Label(container, SWT.NONE);
		shadow = new Button(container, SWT.CHECK);
		shadow.setText("Shadow");

		Label lblShadowX = new Label(container, SWT.NONE);
		lblShadowX.setText("X off:");
		shadowX = new Text(container, SWT.SINGLE | SWT.BORDER);

		Label lblShadowY = new Label(container, SWT.NONE);
		lblShadowY.setText("Y off:");
		shadowY = new Text(container, SWT.SINGLE | SWT.BORDER);

		Label lblAlpha = new Label(container, SWT.NONE);
		lblAlpha.setText("Alpha:");
		shadowAlpha = new Scale(container, SWT.BORDER);
		shadowAlpha.setMaximum(255);
		shadowAlpha.setMinimum(0);

		Label lblColor = new Label(container, SWT.NONE);
		lblColor.setText("Color:");
		shadowColor = createColorButton(container);

		FormTools.forBoth(lblShadow, shadow, colorsStroke, 5);
		FormTools.forBoth(lblShadowX, shadowX, shadow, 0);
		FormTools.forBoth(lblShadowY, shadowY, shadowX, 0);
		FormTools.forBoth(lblAlpha, shadowAlpha, shadowY, 0);
		FormTools.forBoth(lblColor, shadowColor, shadowAlpha, 0);
	}

	private void setupEvents() {
		shadowX.addListener(SWT.Verify, new Listener() {
			@Override
			public void handleEvent(Event e) {
				String string = e.text;
				char[] chars = new char[string.length()];
				string.getChars(0, chars.length, chars, 0);
				for (int i = 0; i < chars.length; i++) {
					if (!('0' <= chars[i] && chars[i] <= '9')) {
						e.doit = false;
						return;
					}
				}
			}
		});

		shadowY.addListener(SWT.Verify, new Listener() {
			@Override
			public void handleEvent(Event e) {
				String string = e.text;
				char[] chars = new char[string.length()];
				string.getChars(0, chars.length, chars, 0);
				for (int i = 0; i < chars.length; i++) {
					if (!('0' <= chars[i] && chars[i] <= '9')) {
						e.doit = false;
						return;
					}
				}
			}
		});

		size.addListener(SWT.Verify, new Listener() {
			@Override
			public void handleEvent(Event e) {
				String string = e.text;
				char[] chars = new char[string.length()];
				string.getChars(0, chars.length, chars, 0);
				for (int i = 0; i < chars.length; i++) {
					if (!('0' <= chars[i] && chars[i] <= '9')) {
						e.doit = false;
						return;
					}
				}
			}
		});

		strokeWidth.addListener(SWT.Verify, new Listener() {
			@Override
			public void handleEvent(Event e) {
				String string = e.text;
				char[] chars = new char[string.length()];
				string.getChars(0, chars.length, chars, 0);
				for (int i = 0; i < chars.length; i++) {
					if (!('0' <= chars[i] && chars[i] <= '9')) {
						e.doit = false;
						return;
					}
				}
			}
		});
	}

	protected void screenToModel() {
		font.setFace(face.getText());
		font.setSize(Integer.valueOf("0" + size.getText()));
		font.setBold(bold.getSelection());
		font.setItalic(italic.getSelection());

		font.setFill(fill.getSelection());
		font.setFillAngle(fillAngleHorizontal.getSelection() ? 0 : 1);
		font.setFillColor0(getSwingColor(fillColor0));
		font.setFillColor1(getSwingColor(fillColor1));

		font.setStroke(stroke.getSelection());
		font.setStrokeWidth(Integer.valueOf("0" + strokeWidth.getText()));
		font.setStrokeAngle(strokeAngleHorizontal.getSelection() ? 0 : 1);
		font.setStrokeColor0(getSwingColor(strokeColor0));
		font.setStrokeColor1(getSwingColor(strokeColor1));

		font.setShadow(shadow.getSelection());
		font.setShadowAlpha(shadowAlpha.getSelection());
		font.setShadowColor(getSwingColor(shadowColor));
		font.setShadowX(Integer.valueOf("0" + shadowX.getText()));
		font.setShadowY(Integer.valueOf("0" + shadowY.getText()));

		font.setCharacters(characters.getText());
	}

	protected void modelToScreen() {
		face.setText(font.getFace());
		size.setText("" + font.getSize());
		bold.setSelection(font.isBold());
		italic.setSelection(font.isItalic());

		fill.setSelection(font.isFill());
		fillAngleHorizontal.setSelection(font.getFillAngle() == 0);
		setSwingColor(fillColor0, font.getFillColor0());
		setSwingColor(fillColor1, font.getFillColor1());

		stroke.setSelection(font.isStroke());
		strokeWidth.setText("" + font.getStrokeWidth());
		strokeAngleHorizontal.setSelection(font.getStrokeAngle() == 0);
		setSwingColor(strokeColor0, font.getStrokeColor0());
		setSwingColor(strokeColor1, font.getStrokeColor1());

		shadow.setSelection(font.isShadow());
		shadowAlpha.setSelection(font.getShadowAlpha());
		setSwingColor(shadowColor, font.getShadowColor());
		shadowX.setText("" + font.getShadowX());
		shadowY.setText("" + font.getShadowY());

		characters.setText(font.getCharacters());
	}

	protected Button createColorButton(Composite parent) {
		final Button clr = new Button(parent, SWT.FLAT);

		clr.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ColorDialog dialog = new ColorDialog(FontForm.this.parent
						.getShell());

				if (clr.getData() != null) {
					Color color = (Color) clr.getData();
					System.out.println(color);
					dialog.setRGB(color.getRGB());
				}

				RGB rgb = dialog.open();
				if (rgb != null) {
					Color color = new Color(FontForm.this.parent.getDisplay(),
							rgb);
					Image img = createColorImage(
							FontForm.this.parent.getDisplay(), color);

					if (clr.getImage() != null) {
						clr.getImage().dispose();
					}

					clr.setImage(img);
					clr.setData(color);
				}

				screenToModel();
			}
		});

		return clr;
	}

	public java.awt.Color getSwingColor(Button btn) {
		java.awt.Color color = java.awt.Color.BLACK;

		if (btn.getData() != null) {
			Color swtCol = (Color) btn.getData();

			color = new java.awt.Color(swtCol.getRed(), swtCol.getGreen(),
					swtCol.getBlue());
		}

		return color;
	}

	public void setSwingColor(Button btn, java.awt.Color color) {
		Color col = new Color(parent.getDisplay(), color.getRed(),
				color.getGreen(), color.getBlue());

		setColor(btn, col);
	}

	public void setColor(Button btn, Color color) {
		Image img = createColorImage(FontForm.this.parent.getDisplay(), color);

		if (btn.getImage() != null) {
			btn.getImage().dispose();
		}

		btn.setImage(img);
		btn.setData(color);
	}

	public static Image createColorImage(Display display, Color color) {
		Image icon = new Image(display, 50, 24);

		GC gc = new GC(icon);
		gc.setBackground(color);
		gc.fillRectangle(0, 0, 50, 24);
		gc.dispose();

		return icon;
	}
}
