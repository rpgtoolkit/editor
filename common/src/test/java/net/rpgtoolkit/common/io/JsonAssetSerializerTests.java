/*
 * See LICENSE.md in the distribution for the full license text including,
 * but not limited to, a notice of warranty and distribution rights.
 */
package net.rpgtoolkit.common.io;

import java.io.IOException;
import net.rpgtoolkit.common.CorruptAssetException;
import net.rpgtoolkit.common.assets.AssetDescriptor;
import net.rpgtoolkit.common.assets.AssetHandle;
import net.rpgtoolkit.common.assets.AssetSerializer;
import net.rpgtoolkit.common.assets.SpecialMove;
import net.rpgtoolkit.common.assets.resources.ResourceAssetHandle;
import net.rpgtoolkit.common.assets.serialization.JsonSMoveSerializer;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 * @author Chris Hutchinson <chris@cshutchinson.com>
 */
public final class JsonAssetSerializerTests {
        
    @Test
    public void testJsonSMoveSerialization() 
            throws CorruptAssetException, IOException {
        
//        final AssetDescriptor descriptor = new AssetDescriptor(
//                "resource:///assets//legacy//test.itm");
        
        AssetSerializer serializer = new JsonSMoveSerializer();
//        AssetDescriptor descriptor = new AssetDescriptor(
//                "file:///Users//Joel//Documents//NetBeansProjects//rpgtoolkit//editor//common//src//test//resources//assets//test2.spc.json");
        AssetDescriptor input = new AssetDescriptor(
                "resource:///assets//testIn.spc.json");
        AssetHandle smove = deserialize(serializer, input);
        SpecialMove mv = (SpecialMove)smove.getAsset();
        validate(mv);
        
        //resources are not for writing. not sure how to test writing right now.
//        AssetDescriptor output = new AssetDescriptor(
//                "resource:///assets//testIn.spc.json");
////        smove.getAsset().setDescriptor(output);
//        serializer.serialize(smove);
//        
//        smove = deserialize(serializer, output);
//        mv = (SpecialMove)smove.getAsset();
//        validate(mv);
        
    }

    private void validate(SpecialMove mv) {
        assertEquals("Test Move 2", mv.getName());
        assertEquals("Fooooooo JSON Style!", mv.getDescription());
        assertEquals(10, mv.getMpCost());
        assertEquals(10, mv.getFightPower());
        assertEquals("", mv.getRpgcodeProgram());
        assertEquals(0, mv.getMpDrainedFromTarget());
        assertEquals("test.ste", mv.getAssociatedStatusEffect());
        assertEquals("start_e.anm", mv.getAssociatedAnimation());
        assertEquals(true, mv.getCanUseInBattle());
        assertEquals(true, mv.getCanUseInMenu());
    }
    
    private AssetHandle deserialize(AssetSerializer serializer, 
            AssetDescriptor descriptor) 
            throws CorruptAssetException, IOException {
        
        final AssetHandle handle = new ResourceAssetHandle(descriptor);
        
        assertNotEquals(null, serializer);
        assertNotEquals(null, handle);
        assertEquals(null, handle.getAsset());
        assertTrue(serializer.canDeserialize(descriptor));
        
        serializer.deserialize(handle);
        
        assertNotEquals(null, handle.getAsset());
        
        return handle;

    }
       
}
