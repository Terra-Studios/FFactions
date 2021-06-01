package dev.sebastianb.ffactions.claim;

import net.minecraft.util.math.ChunkPos;

import java.text.SimpleDateFormat;

public class FactionChunk {

    private ChunkPos chunkPos;
    // private final SimpleDateFormat dateClaimed;

    public FactionChunk(ChunkPos chunkPos) {

        this.chunkPos = chunkPos;
        // this.dateClaimed  = new SimpleDateFormat("yyyy-MM-dd:hh-mm-ss");


    }


    public ChunkPos getChunkPos() {
        return chunkPos;
    }

//    public SimpleDateFormat getDateClaimed() {
//        return dateClaimed;
//    }
}
