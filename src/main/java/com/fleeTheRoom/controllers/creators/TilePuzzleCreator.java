package com.fleeTheRoom.controllers.creators;

import com.fleeTheRoom.models.puzzles.tiles.Tile;
import com.fleeTheRoom.models.puzzles.tiles.TilesPuzzle;

import java.io.BufferedReader;
import java.io.FileReader;

public class TilePuzzleCreator {

    private final String filename;
    private final int difficulty;

    public TilePuzzleCreator(String filename, int difficulty) {
        this.filename = filename;
        this.difficulty = difficulty;
    }

    public TilesPuzzle createPuzzle() {
        BufferedReader fileReader = null;

        int height, width;
        TilesPuzzle puzzle = null;

        final String DELIMITER = ",";
        Tile[] tiles;

        String line;
        try {
            fileReader = new BufferedReader(new FileReader(filename));

            line = fileReader.readLine();
            width = Integer.parseInt(line.split(DELIMITER)[0]);
            height = Integer.parseInt(line.split(DELIMITER)[1]);


            int horzDivs = (int)Math.pow(2.0, difficulty) * 3;
            int vertDivs = (int)Math.pow(2.0, difficulty) * 2;
            int horzLenght = width/horzDivs;
            int vertLength = height/vertDivs;

            tiles = new Tile[horzDivs*vertDivs];

            for(int i = 0; i < vertDivs; ++i) {
                for(int j = 0; j < horzDivs; ++j) {
                    tiles[i*horzDivs + j] = new Tile(j*horzLenght + 5, i*vertLength + 5, horzLenght, vertLength);
                }
                for(int k = 0; k < vertLength; ++k) {
                    line = fileReader.readLine();
                    int horPos = 0;
                    for(int h = 0; h < horzDivs; ++h) {
                        tiles[i*horzDivs + h].addElem(line.substring(horPos, horPos + horzLenght));
                        horPos += horzLenght;
                    }
                }
            }
            tiles[0].setActive(true);
            for(Tile tile : tiles) {
                tile.checkEmpty();
            }

            puzzle = new TilesPuzzle(width + 10, height + 10, width, height, difficulty, tiles);

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                assert fileReader != null;
                fileReader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return puzzle;
    }
}
