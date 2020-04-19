package net.senmori.admintools.asset.assets;

import net.senmori.admintools.asset.Asset;

import java.io.File;
import java.net.URI;

public abstract class FileAsset implements Asset {

    private final URI assetLocation;

    protected FileAsset(File file) {
        this.assetLocation = file.toURI();
    }

    @Override
    public URI getAssetLocation() {
        return assetLocation;
    }

    public File getFile() {
        return new File( getAssetLocation() );
    }

    @Override
    public String toString()
    {
        return assetLocation.toString();
    }
}
