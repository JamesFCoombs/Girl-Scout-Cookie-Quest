package edu.andover.cwong.gscq.model;

import java.io.IOException;
import java.io.File;
import java.util.Scanner;

import edu.andover.cwong.gscq.model.nav.Floor;

// IO encapsulation to load a floor layout from a given file.
public class FloorLoader {
    public static Floor loadFloor(String path) {
        try {
            Scanner sc = new Scanner(new File(path));
            // load every number from the file
            while(sc.hasNextLine()) {
                // We can't just use sc.nextInt() because we want to know how
                // big each row and each column is
                String[] ids = sc.nextLine().split(",\\s*");
                int[] row = new int[ids.length];
                for (int i = 0 ; i < ids.length ; i++) {
                    row[i] = Integer.parseInt(ids[i]);
                }
                Floor.constructFloor(row);
            }
            sc.close();
            return Floor.getConstructed();
        }
        catch (IOException e) {
            System.err.println("Unable to locate floor. Aborting.");
            System.exit(-1);
            // this is unreachable
            return null;
        }
        catch (IllegalArgumentException e) {
            System.err.println("Improperly formatted floor map. Aborting.");
            System.exit(-2);
            // this is also unreachable
            return null;
        }
    }
}
