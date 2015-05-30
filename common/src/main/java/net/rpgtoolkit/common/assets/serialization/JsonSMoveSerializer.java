/*
 * See LICENSE.md in the distribution for the full license text including,
 * but not limited to, a notice of warranty and distribution rights.
 */
package net.rpgtoolkit.common.assets.serialization;

import java.io.File;
import java.io.IOException;

import net.rpgtoolkit.common.CorruptAssetException;
import net.rpgtoolkit.common.assets.AbstractAssetSerializer;
import net.rpgtoolkit.common.assets.AssetDescriptor;
import net.rpgtoolkit.common.assets.AssetHandle;
import net.rpgtoolkit.common.assets.SpecialMove;
import net.rpgtoolkit.common.io.Paths;
import net.rpgtoolkit.common.utilities.JSON;
import org.json.JSONObject;

/**
 *
 * @author Joel Moore
 */
public class JsonSMoveSerializer
        extends AbstractAssetSerializer {

    @Override
    public boolean canSerialize(AssetDescriptor descriptor) {
        final String ext = Paths.getExtension(descriptor.getURI().getPath());
        return (ext.contains("itm.json"));
    }

    @Override
    public boolean canDeserialize(AssetDescriptor descriptor) {
        return canSerialize(descriptor);
    }

    @Override
    public void serialize(AssetHandle handle)
            throws IOException, CorruptAssetException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void deserialize(AssetHandle handle)
            throws IOException, CorruptAssetException {
        
        JSONObject json = JSON.load(new File(handle.getDescriptor().getURI()));
        if(json == null) { throw new IOException(); }
        final SpecialMove asset = new SpecialMove();
        asset.harvestJSON(json);
        
        handle.setAsset(asset);
        
    }

}
