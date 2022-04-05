package com.alterdekim.fractals;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class SatPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	public int startX = 0;
	public int endX = getWidth();

	@Override
	public void paintComponent(Graphics g) {
		float color = ((float) 1) / ((float) getWidth()) * ((float) startX);
		for( int x = startX; x < endX; x++ ) {
			g.setColor(Color.getHSBColor(0f, color, 0.7f));
			g.fillRect(x, 0, 1, getHeight());
			color += ((float) 1) / ((float) getWidth());
		}
	}
}
