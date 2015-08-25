package com.conveyal.data.census;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Import data from the US Census into a seamless store in S3 or on disk.
 */
public class CensusMain {
    protected static final Logger LOG = LoggerFactory.getLogger(CensusMain.class);

    public static void main (String... args) throws Exception {
        File indir = new File(args[0]);
        File tiger = new File(indir, "tiger");

        ShapeDataStore store = new ShapeDataStore();

        // load up the tiger files
        LOG.info("Loading TIGER (geometry)");
        for (File f : tiger.listFiles()) {
            if (!f.getName().endsWith(".shp"))
                continue;

            LOG.info("Loading file {}", f);

            TigerLineSource src = new TigerLineSource(f);
            src.load(store);
        }

        store.writeTiles(new File(indir, "tiles"));
    }
}
