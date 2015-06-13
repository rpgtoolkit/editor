/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.rpgtoolkit.common.assets.serialization;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import static java.lang.System.out;
import java.net.URI;
import net.rpgtoolkit.common.CorruptAssetException;
import net.rpgtoolkit.common.assets.AbstractAssetSerializer;
import net.rpgtoolkit.common.assets.AssetHandle;
import net.rpgtoolkit.common.utilities.JSON;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

/**
 *
 * @author Joel Moore
 */
public abstract class AbstractJsonSerializer
        extends AbstractAssetSerializer {
    
    @Override
    public void serialize(AssetHandle handle)
            throws IOException, CorruptAssetException {
        URI f = handle.getDescriptor().getURI();
        String output;
        try {
            output = this.toJSONString(handle);
        } catch(Exception ex) {
            String message = "JSON data saving failed: " + ex
                    + "...while attempting to prepare save data for "
                    + handle.getAsset() + ", which was going to be saved to "
                    + f + "...Saving canceled.";
            //Logger.getLogger(JSON.class.getName()).log(Level.SEVERE, null, ex);
            throw new CorruptAssetException(message);
        }
        try (BufferedWriter writer = handle.getWriter()) {
            writer.write(output);
            out.println("JSON written to " + f.getPath());
        } catch(IOException ex) {
            String message = "JSON data saving failed: " + ex
                    + "...while attempting to save " + handle.getAsset()
                    + " to " + f;
            //Logger.getLogger(JSON.class.getName()).log(Level.SEVERE, null, ex);
            throw new IOException(message);
        }
    }
    
    /**
     * Loads a JSON object from disk at the File provided.
     * @param handle
     * @return the JSONObject that was loaded, or null if load failed
     * @throws java.io.IOException
     * @throws net.rpgtoolkit.common.CorruptAssetException
     */
    public static JSONObject load(AssetHandle handle)
            throws IOException, CorruptAssetException {
        URI f = handle.getDescriptor().getURI();
        try (BufferedReader reader = handle.getReader()){
            StringBuilder source = new StringBuilder();
            String line = reader.readLine();
            while(line != null) {
                source.append(line);
                line = reader.readLine();
            }
//            out.println(source.toString());
            JSONObject json = new JSONObject(source.toString());
            out.println("JSON read from " + f.getPath());
//            out.println(json.toString());
            return json;
        } catch(IOException ex) {
            String message = "JSON data loading failed: " + ex
                    + "...while attempting to load " + f;
            //Logger.getLogger(JSON.class.getName()).log(Level.SEVERE, null, ex);
            throw new IOException(message);
        } catch(JSONException ex) {
            String message = "JSON data loading failed: " + ex
                    + "...while attempting to load " + f;
            //Logger.getLogger(JSON.class.getName()).log(Level.SEVERE, null, ex);
            throw new CorruptAssetException(message);
        }
    }

    public String toJSONString(AssetHandle handle) {
        JSONStringer json = new JSONStringer();
        json.object();
        this.populateJSON(json, handle);
        json.endObject();
        return JSON.toPrettyJSON(json);
    }
    

    /**
     * Given a JSONStringer that has started making a JSON object and an
     * AssetHandle for an asset this serializer can handle, add the asset's data
     * to that object without ending the object. Subclasses designed to handle a
     * subclass of the asset type should override toJSONString() and
     * populateJSON(), calling both super.populateJSON() and populateJSON() from
     * toJSONString() to add the asset's parent class's info to the JSON object
     * alongside the asset subclass's info.
     *
     * @param json
     * @param handle
     */
    public abstract void populateJSON(JSONStringer json, AssetHandle handle);

}
