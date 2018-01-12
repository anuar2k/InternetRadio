package org.terasology.internetradio.blocks;

import com.google.common.collect.ImmutableSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.terasology.math.Rotation;
import org.terasology.math.Side;
import org.terasology.naming.Name;
import org.terasology.world.block.Block;
import org.terasology.world.block.BlockBuilderHelper;
import org.terasology.world.block.BlockUri;
import org.terasology.world.block.family.BlockFamily;
import org.terasology.world.block.family.BlockFamilyFactory;
import org.terasology.world.block.family.RegisterBlockFamilyFactory;
import org.terasology.world.block.loader.BlockFamilyDefinition;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RegisterBlockFamilyFactory("radio")
public class RadioBlockFamilyFactory implements BlockFamilyFactory {
    private static final ImmutableSet<String> NAMES = ImmutableSet.of("on", "off");

    @Override
    public BlockFamily createBlockFamily(BlockFamilyDefinition definition, BlockBuilderHelper blockBuilder) {
        HashMap<String, Block> blocks = new HashMap<>();

        for (Rotation rot : Rotation.horizontalRotations()) {
            Side side = rot.rotate(Side.FRONT);
            createBlock(blocks, definition, blockBuilder, "off", side, rot);
            createBlock(blocks, definition, blockBuilder, "on", side, rot);
        }

        return new RadioBlockFamily(new BlockUri(definition.getUrn()), blocks.get("off;FRONT"), blocks, definition.getCategories());
    }

    private void createBlock(HashMap<String, Block> blocks, BlockFamilyDefinition definition, BlockBuilderHelper helper, String section, Side side, Rotation rot) {
        Block newBlock = helper.constructTransformedBlock(definition, section, rot);

        String name = section+';'+side.toString();
        newBlock.setUri(new BlockUri(definition.getUrn(), new Name(name)));
        blocks.put(name, newBlock);
    }

    @Override
    public Set<String> getSectionNames() {
        return NAMES;
    }
}
