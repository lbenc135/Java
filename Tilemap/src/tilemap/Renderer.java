package tilemap;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Renderer extends Applet {

	static int[][] tilemap;
	static int rows, columns;

	@Override
	public void init() {
		setSize(800, 400);
		setBackground(Color.BLACK);
		createTilemap();
	}

	private void createTilemap() {
		tilemap = new int[50][30];
		rows = tilemap.length;
		columns = tilemap[1].length;

		Random r = new Random();
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				tilemap[i][j] = r.nextInt(5);
			}

		}

	}

	@Override
	public void paint(Graphics g) {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				int modi = 16 * i;
				int modj = 16 * j;

				switch (tilemap[i][j]) {
				case 0:
					g.setColor(Color.RED);
					g.fillRect(modi, modj, 16, 16);
					break;
				case 1:
					g.setColor(Color.BLUE);
					g.fillRect(modi, modj, 16, 16);
					break;
				case 2:
					g.setColor(Color.YELLOW);
					g.fillRect(modi, modj, 16, 16);
					break;
				case 3:
					g.setColor(Color.WHITE);
					g.fillRect(modi, modj, 16, 16);
					break;
				case 4:
					g.setColor(Color.GREEN);
					g.fillRect(modi, modj, 16, 16);
					break;
				}
			}

		}
	}
}
