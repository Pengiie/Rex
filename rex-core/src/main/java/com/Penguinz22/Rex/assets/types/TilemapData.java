package com.Penguinz22.Rex.assets;

import com.Penguinz22.Rex.tilemap.Tile;
import org.joml.Vector2i;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TilemapData {

    private TextureAtlas tileset;
    private HashMap<Vector2i, Tile> tiles;

    public TilemapData(TextureAtlas tileset, HashMap<Vector2i, Tile> tiles) {
        this.tileset = tileset;
        this.tiles = tiles;
    }

    public TextureAtlas getTileset() {
        return tileset;
    }

    public HashMap<Vector2i, Tile> getTiles() {
        return tiles;
    }

    public static TilemapData loadTilemapData(TextureAtlas tileset, String filePath) {
        try {
            HashMap<Vector2i, Tile> tiles = new HashMap<>();

            Pattern pattern = Pattern.compile("^(\\(\\d+,\\d+,\\d+\\))");

            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line = "";
            while((line = reader.readLine()) != null) {
                line = line.trim();

                Matcher matcher = pattern.matcher(line);
                while(matcher.find()) {
                    String[] numbers = matcher.group().substring(1, matcher.group().length()-1).split(",");

                    matcher = matcher.replaceFirst("").length() == 0 ?
                            pattern.matcher("") : pattern.matcher(matcher.replaceFirst("").substring(1));

                    tiles.put(new Vector2i(Integer.parseInt(numbers[0]), Integer.parseInt(numbers[1])),
                            new Tile(Integer.parseInt(numbers[2])));
                }
            }

            return new TilemapData(tileset, tiles);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
