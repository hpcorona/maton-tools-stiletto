package com.maton.tools.stiletto.model.io;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

import nanoxml.XMLElement;
import nanoxml.XMLParseException;

import com.maton.tools.stiletto.model.Action;
import com.maton.tools.stiletto.model.Actor;
import com.maton.tools.stiletto.model.Alternate;
import com.maton.tools.stiletto.model.AlternateFont;
import com.maton.tools.stiletto.model.Animation;
import com.maton.tools.stiletto.model.Bundle;
import com.maton.tools.stiletto.model.Font;
import com.maton.tools.stiletto.model.Frame;
import com.maton.tools.stiletto.model.Image;
import com.maton.tools.stiletto.model.Positioned;
import com.maton.tools.stiletto.model.Resolution;
import com.maton.tools.stiletto.model.Sprite;
import com.maton.tools.stiletto.model.Widget;
import com.maton.tools.stiletto.model.WidgetState;

public class ModelInput {
	public static boolean load(Bundle bundle) {
		bundle.clear();

		File file = bundle.getFile();

		XMLElement xml = new XMLElement();
		FileReader reader;
		try {
			reader = new FileReader(file);
			xml.parseFromReader(reader);

			@SuppressWarnings("unchecked")
			Vector<XMLElement> childs = xml.getChildren();
			for (XMLElement child : childs) {
				if (child.getName().equals("images")) {
					loadImages(child, bundle);
				} else if (child.getName().equals("sprites")) {
					loadSprites(child, bundle);
				} else if (child.getName().equals("animations")) {
					loadAnimations(child, bundle);
				} else if (child.getName().equals("actors")) {
					loadActors(child, bundle);
				} else if (child.getName().equals("fonts")) {
					loadFonts(child, bundle);
				} else if (child.getName().equals("widgets")) {
					loadWidgets(child, bundle);
				} else if (child.getName().equals("resolutions")) {
					loadResolutions(child, bundle);
				}
			}

			bundle.getImages().reload();

			return true;
		} catch (FileNotFoundException e) {
		} catch (XMLParseException e) {
		} catch (IOException e) {
		}

		return false;
	}

	public static void loadWidgets(XMLElement widgets, Bundle bundle) {
		@SuppressWarnings("unchecked")
		Vector<XMLElement> childs = widgets.getChildren();
		for (XMLElement widget : childs) {
			String widgetName = widget.getStringAttribute("name");
			Widget wdg = bundle.getWidgets().newElement(widgetName);

			@SuppressWarnings("unchecked")
			Vector<XMLElement> states = widget.getChildren();
			for (XMLElement state : states) {
				String name = state.getStringAttribute("name");
				String imgName = state.getStringAttribute("image");

				Image img = bundle.getImages().getElement(imgName);

				WidgetState stateNew = wdg.addChild(img);
				stateNew.setName(name);
			}
		}
	}

	public static void loadActors(XMLElement actors, Bundle bundle) {
		@SuppressWarnings("unchecked")
		Vector<XMLElement> childs = actors.getChildren();
		for (XMLElement actor : childs) {
			String actorName = actor.getStringAttribute("name");
			Actor act = bundle.getActors().newElement(actorName);

			@SuppressWarnings("unchecked")
			Vector<XMLElement> actions = actor.getChildren();
			for (XMLElement action : actions) {
				String name = action.getStringAttribute("name");
				String animName = action.getStringAttribute("animation");

				Animation anim = bundle.getAnimations().getElement(animName);

				Action actionNew = act.addChild(anim);
				actionNew.setName(name);
			}
		}
	}

	public static void loadImages(XMLElement images, Bundle bundle) {
		@SuppressWarnings("unchecked")
		Vector<XMLElement> childs = images.getChildren();
		for (XMLElement image : childs) {
			String name = (String) image.getAttribute("name");
			String filename = (String) image.getAttribute("filename");
			String export = (String) image.getAttribute("export");
			String framed = (String) image.getAttribute("framed");
			float left = (float) image.getDoubleAttribute("left");
			float right = (float) image.getDoubleAttribute("right");
			float top = (float) image.getDoubleAttribute("top");
			float bottom = (float) image.getDoubleAttribute("bottom");

			if (framed == null) {
				framed = "false";
			}

			Image img = bundle.getImages().loadSingle(filename);
			img.setExportName(name);
			img.setExport(export.equals("true"));
			img.setFramed(framed.equals("true"));
			img.setLeft(left);
			img.setRight(right);
			img.setTop(top);
			img.setBottom(bottom);

			loadImageAlternates(image, img);
		}
	}

	public static void loadImageAlternates(XMLElement alternates, Image image) {
		@SuppressWarnings("unchecked")
		Vector<XMLElement> childs = alternates.getChildren();
		for (XMLElement alt : childs) {
			String name = (String) alt.getAttribute("resolution");
			String filename = (String) alt.getAttribute("filename");
			float scaleX = (float) alt.getDoubleAttribute("scaleX");
			float scaleY = (float) alt.getDoubleAttribute("scaleY");

			Alternate alti = image.getAlternates().newElement(name);
			alti.setImageName(filename);
			alti.setScaleX(scaleX);
			alti.setScaleY(scaleY);
		}
	}

	public static void loadResolutions(XMLElement resolutions, Bundle bundle) {
		@SuppressWarnings("unchecked")
		Vector<XMLElement> childs = resolutions.getChildren();
		for (XMLElement res : childs) {
			String name = (String) res.getAttribute("name");
			float scale = (float) res.getDoubleAttribute("scale");
			String basedOn = (String) res.getAttribute("basedOn");
			if (basedOn == null) {
				basedOn = "";
			}

			Resolution resolution = bundle.getResolutions().newElement(name);
			resolution.setScale(scale);
			resolution.setBasedOn(basedOn);
		}
	}

	public static void loadSprites(XMLElement sprites, Bundle bundle) {
		@SuppressWarnings("unchecked")
		Vector<XMLElement> childs = sprites.getChildren();
		for (XMLElement sprite : childs) {
			String spriteName = sprite.getStringAttribute("name");
			String rendered = sprite.getStringAttribute("rendered");
			String imageName = sprite.getStringAttribute("image");

			Sprite spr = bundle.getSprites().newElement(spriteName);
			spr.setImageName(imageName);
			spr.setRendered(rendered.equals("true"));

			@SuppressWarnings("unchecked")
			Vector<XMLElement> modules = sprite.getChildren();
			for (XMLElement module : modules) {
				String name = module.getStringAttribute("name");
				int x = module.getIntAttribute("x");
				int y = module.getIntAttribute("y");
				int alpha = module.getIntAttribute("alpha");
				float rotation = (float) module.getDoubleAttribute("rotation");
				boolean flipX = module.getBooleanAttribute("flipX", "true",
						"false", false);
				boolean flipY = module.getBooleanAttribute("flipY", "true",
						"false", false);

				Image img = bundle.getImages().getElement(name);

				Positioned pos = spr.addChild(img);
				pos.setX(x);
				pos.setY(y);
				pos.setAlpha(alpha);
				pos.setRotation(rotation);
				pos.setFlipX(flipX);
				pos.setFlipY(flipY);
			}
		}
	}

	public static void loadAnimations(XMLElement animations, Bundle bundle) {
		@SuppressWarnings("unchecked")
		Vector<XMLElement> childs = animations.getChildren();
		for (XMLElement animation : childs) {
			String animName = animation.getStringAttribute("name");
			Animation anim = bundle.getAnimations().newElement(animName);

			@SuppressWarnings("unchecked")
			Vector<XMLElement> frames = animation.getChildren();
			for (XMLElement frame : frames) {
				String spriteName = frame.getStringAttribute("sprite");
				int time = frame.getIntAttribute("time");

				Sprite spr = bundle.getSprites().getElement(spriteName);

				Frame fra = anim.addChild(spr);
				fra.setTime(time);
			}
		}
	}

	public static void loadFonts(XMLElement fonts, Bundle bundle) {
		@SuppressWarnings("unchecked")
		Vector<XMLElement> childs = fonts.getChildren();
		for (XMLElement fnt : childs) {
			String name = fnt.getStringAttribute("name");
			String face = fnt.getStringAttribute("face");
			int size = fnt.getIntAttribute("size");
			boolean bold = fnt.getBooleanAttribute("bold", "true", "false",
					false);
			boolean italic = fnt.getBooleanAttribute("italic", "true", "false",
					false);

			boolean fill = fnt.getBooleanAttribute("fill", "true", "false",
					true);
			boolean fillBlur = fnt.getBooleanAttribute("fillBlur", "true", "false",
					true);
			int fillAngle = fnt.getIntAttribute("fillAngle");
			int fillColor0 = fnt.getIntAttribute("fillColor0");
			int fillColor1 = fnt.getIntAttribute("fillColor1");

			boolean stroke = fnt.getBooleanAttribute("stroke", "true", "false",
					false);
			int strokeWidth = fnt.getIntAttribute("strokeWidth");
			int strokeAngle = fnt.getIntAttribute("strokeAngle");
			int strokeColor0 = fnt.getIntAttribute("strokeColor0");
			int strokeColor1 = fnt.getIntAttribute("strokeColor1");

			boolean shadow = fnt.getBooleanAttribute("shadow", "true", "false",
					true);
			boolean shadowBlur = fnt.getBooleanAttribute("shadowBlur", "true", "false",
					true);
			int shadowX = fnt.getIntAttribute("shadowX");
			int shadowY = fnt.getIntAttribute("shadowY");
			int shadowAlpha = fnt.getIntAttribute("shadowAlpha");
			int shadowColor = fnt.getIntAttribute("shadowColor");

			String characters = fnt.getStringAttribute("characters");

			Font font = bundle.getFonts().newElement(name);
			font.setName(name);
			font.setFace(face);
			font.setSize(size);
			font.setBold(bold);
			font.setItalic(italic);

			font.setFill(fill);
			font.setFillBlur(fillBlur);
			font.setFillAngle(fillAngle);
			font.setFillColor0(new Color(fillColor0));
			font.setFillColor1(new Color(fillColor1));

			font.setStroke(stroke);
			font.setStrokeWidth(strokeWidth);
			font.setStrokeAngle(strokeAngle);
			font.setStrokeColor0(new Color(strokeColor0));
			font.setStrokeColor1(new Color(strokeColor1));

			font.setShadow(shadow);
			font.setShadowBlur(shadowBlur);
			font.setShadowX(shadowX);
			font.setShadowY(shadowY);
			font.setShadowAlpha(shadowAlpha);
			font.setShadowColor(new Color(shadowColor));

			font.setCharactersList(characters);
			
			loadFontAlternates(fnt, font);
		}
	}

	public static void loadFontAlternates(XMLElement alternates, Font font) {
		@SuppressWarnings("unchecked")
		Vector<XMLElement> childs = alternates.getChildren();
		for (XMLElement alt : childs) {
			String name = (String) alt.getAttribute("resolution");
			int size = alt.getIntAttribute("size");
			int stroke = alt.getIntAttribute("stroke");
			int shadowX = alt.getIntAttribute("shadowX");
			int shadowY = alt.getIntAttribute("shadowY");

			AlternateFont alti = font.getAlternates().newElement(name);
			alti.setSize(size);
			alti.setStroke(stroke);
			alti.setShadowX(shadowX);
			alti.setShadowY(shadowY);
		}
	}

}
