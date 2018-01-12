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
import java.util.Set;

@RegisterBlockFamilyFactory("radio")
public class RadioBlockFamilyFactory implements BlockFamilyFactory {
    private static final Logger logger = LoggerFactory.getLogger(RadioBlockFamilyFactory.class);
    private static final ImmutableSet<String> NAMES = ImmutableSet.of(
            "false;FRONT",
            "false;RIGHT",
            "false;BACK",
            "false;LEFT",
            "true;FRONT",
            "true;RIGHT",
            "true;BACK",
            "true;LEFT"
    );

    @Override
    public BlockFamily createBlockFamily(BlockFamilyDefinition definition, BlockBuilderHelper blockBuilder) {
        HashMap<String, Block> blocks = new HashMap<>();

        for (Side side : Side.horizontalSides()) {
            createBlock(blocks, definition, blockBuilder, false, side);
            createBlock(blocks, definition, blockBuilder, true, side);
            logger.info(side.toString());
        }

        logger.info(String.valueOf(blocks.get("false;FRONT") == null));
        logger.info(String.valueOf(blocks.size()));

        return new RadioBlockFamily(new BlockUri(definition.getUrn()), blocks.get("false;FRONT"), blocks, definition.getCategories());
    }

    private void createBlock(HashMap<String, Block> blocks, BlockFamilyDefinition definition, BlockBuilderHelper helper, boolean state, Side side) {
        String section = String.valueOf(state)+';'+side.toString();
        Block newBlock = helper.constructSimpleBlock(definition, section);
        newBlock.setUri(new BlockUri(definition.getUrn(), new Name(section)));
        blocks.put(section, newBlock);
    }

    @Override
    public Set<String> getSectionNames() {
        return NAMES;
    }
}
