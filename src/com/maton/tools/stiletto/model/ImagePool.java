package com.maton.tools.stiletto.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.util.ArrayList;

import com.maton.tools.stiletto.model.base.BasePool;

public class ImagePool extends BasePool<Image> {
	private BundleContext ctx;

	public ImagePool(BundleContext ctx) {
		super();
		this.ctx = ctx;
	}

	public void free() {
		for (Image img : elements) {
			img.free();
			notifyChange(img);
		}
	}

	public void removeUnloaded() {
		ArrayList<Image> toDelete = new ArrayList<Image>();

		for(Image img : elements) {
			if (!img.isLoaded()) {
				toDelete.add(img);
			}
		}

		for (Image img : toDelete) {
			notifyDelete(img);
			elements.remove(img);
		}
	}

	public void reload() {
		free();

		File f = new File(ctx.getImagePath(""));
		File[] pngs = f.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File arg0, String arg1) {
				return arg1.toLowerCase().endsWith(".png");
			}
		});

		for (File png : pngs) {
			loadSingle(png);
		}
	}

	public Image loadSingle(String filename) {
		String path = ctx.getImagePath(filename);
		
		return loadSingle(new File(path));
	}
	
	public Image loadSingle(File png) {
		String fileName = png.getName();

		Image img = getElement(fileName);
		if (img == null) {
			img = new Image(fileName, ctx);

			elements.add(img);

			notifyNew(img);
		} else {
			img.reload();

			notifyChange(img);
		}

		return img;
	}
	
	public boolean importFile(File source) {
		File target = new File(ctx.getImagePath(""));
		if (source.getAbsolutePath().equals(target.getAbsolutePath())) {
			loadSingle(source);
		}

		File destination = new File(target, source.getName());

		try {
			FileInputStream fis = new FileInputStream(source);
			FileOutputStream fos = new FileOutputStream(destination);
			try {
				byte[] buf = new byte[1024];
				int i = 0;
				while ((i = fis.read(buf)) != -1) {
					fos.write(buf, 0, i);
				}
			} catch (Exception e) {
				return false;
			} finally {
				try {
					if (fis != null)
						fis.close();
					if (fos != null)
						fos.close();
				} catch (Throwable e) {
				}
			}
			return true;
		} catch (Throwable e) {
			return false;
		}
	}

	@Override
	public void clear() {
		for (Image img : elements) {
			img.free();
		}
		
		super.clear();
	}

	@Override
	public Image createElement(String name) {
		return new Image(name, ctx);
	}

}
