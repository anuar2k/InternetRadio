package org.terasology.internetradio.blocks;

import org.terasology.math.Rotation;
import org.terasology.math.Side;
import org.terasology.math.geom.Vector3i;
import org.terasology.naming.Name;
import org.terasology.world.block.Block;
import org.terasology.world.block.BlockBuilderHelper;
import org.terasology.world.block.BlockUri;
import org.terasology.world.block.family.AbstractBlockFamily;
import org.terasology.world.block.family.BlockSections;
import org.terasology.world.block.family.RegisterBlockFamily;
import org.terasology.world.block.loader.BlockFamilyDefinition;

import java.util.HashMap;

@RegisterBlockFamily("radio")
@BlockSections({"off", "on"})
public class RadioBlockFamily extends AbstractBlockFamily {
    HashMap<String, Block> blocks = new HashMap<>();

    public RadioBlockFamily(BlockFamilyDefinition definition, BlockBuilderHelper helper) {
        super(definition, helper);
        super.setBlockUri(new BlockUri(definition.getUrn()));
        super.setCategory(definition.getCategories());

        for (Rotation rot : Rotation.horizontalRotations()) {
            Side side = rot.rotate(Side.FRONT);
            createBlock(blocks, definition, helper, "off", side, rot);
            createBlock(blocks, definition, helper, "on", side, rot);
        }
    }

    @Override
    public Block getBlockForPlacement(Vector3i location, Side attachmentSide, Side direction) {
        if (attachmentSide.isHorizontal()) {
            return blocks.get("off;"+attachmentSide);
        }
        if (direction != null) {
            return blocks.get("off;"+direction);
        } else {
            return getArchetypeBlock();
        }
    }

    public Block getBlockForStateSwitch(Block block) {
        String[] name = block.getURI().getIdentifier().toString().split(";");
        name[0] = name[0].equals("off") ? "on" : "off";
        return blocks.get(name[0]+';'+name[1]);
    }

    @Override
    public Block getArchetypeBlock() {
        return blocks.get("off;FRONT");
    }

    @Override
    public Block getBlockFor(BlockUri blockUri) {
        return blocks.get(blockUri.getIdentifier().toString());
    }

    @Override
    public Iterable<Block> getBlocks() {
        return blocks.values();
    }

    private void createBlock(HashMap<String, Block> blocks, BlockFamilyDefinition definition, BlockBuilderHelper helper, String section, Side side, Rotation rot) {
        Block newBlock = helper.constructTransformedBlock(definition, section, rot);

        String name = section+';'+side.toString();
        newBlock.setUri(new BlockUri(definition.getUrn(), new Name(name)));
        newBlock.setBlockFamily(this);
        blocks.put(name, newBlock);
    }
}
