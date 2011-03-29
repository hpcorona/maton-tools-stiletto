package com.maton.tools.stiletto.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.eclipse.swt.graphics.ImageData;

public class SwingTools {

	public static ImageData convertToSWT(BufferedImage bufferedImage) {
		try {
			ByteArrayOutputStream pos = new ByteArrayOutputStream();

			ImageIO.write(bufferedImage, "png", pos);

			ByteArrayInputStream pis = new ByteArrayInputStream(
					pos.toByteArray());
			ImageData data = new ImageData(pis);

			return data;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
