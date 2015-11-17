package edu.andover.cwong.gscq.view;

import java.io.IOException;
import java.io.File;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
// I hate to have to import this because this potentially masks jfx Color
import java.awt.Color;
import javax.imageio.ImageIO;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.embed.swing.SwingFXUtils;

import edu.andover.cwong.gscq.model.nav.Floor;
import edu.andover.cwong.gscq.model.nav.Tile;

// XXX - This entire class is rather janky in that we combine awt and fx core
// classes to provide a service that javafx doesn't.
public class FloorViewBuilder extends ImageView {
    public static final int TILE_DIMENSIONS = 68;
    public static final int TOP_LEFT_BLANKS = 3;
    public static final int BOT_RIGHT_BLANKS = 2;
    public static final int EDGE_BLANKS = TOP_LEFT_BLANKS + BOT_RIGHT_BLANKS;
    // There doesn't appear to be a way to tile together images in memory
    // without having it display the entire thing in javafx, so we instead
    // draw our "master floor image" onto an awt BufferedImage and show
    // parts of that on the screen.
    public static Image constructImage(Floor f) {
        BufferedImage[] tileImages = new BufferedImage[Tile.NUM_TYPES];
        try {
            for (int i = 0 ; i < tileImages.length ; i++) {
                tileImages[i] = ImageIO.read(new File(
                        String.format("res/%d.png", i)
                ));
            }
        }
        catch (IOException ioe) {
            System.err.println("Unable to load tile images. Aborting.");
            System.exit(1);
        }
        // XXX - Properly this should be in the try-catch block
        BufferedImage result = new BufferedImage(
                (f.getWidth()+EDGE_BLANKS) * TILE_DIMENSIONS,
                (f.getHeight()+EDGE_BLANKS) * TILE_DIMENSIONS,
                BufferedImage.TYPE_INT_ARGB
        );
        Graphics2D g = result.createGraphics();
        g.setBackground(Color.BLACK);
        g.clearRect(0, 0, result.getWidth(), result.getHeight());
        for (int i = 0 ; i < f.getHeight() ; i++) {
            for (int j = 0 ; j < f.getWidth() ; j++) {
                g.drawImage(
                        tileImages[f.getTile(i, j).getID()], null,
                        (i+TOP_LEFT_BLANKS)*TILE_DIMENSIONS,
                        (j+BOT_RIGHT_BLANKS)*TILE_DIMENSIONS
                );
            }
        }
        return SwingFXUtils.toFXImage(result, null);
    }
}
