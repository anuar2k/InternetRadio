package org.terasology.internetradio.blocks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.terasology.math.Side;
import org.terasology.math.geom.Vector3i;
import org.terasology.world.BlockEntityRegistry;
import org.terasology.world.WorldProvider;
import org.terasology.world.block.Block;
import org.terasology.world.block.BlockUri;
import org.terasology.world.block.family.AbstractBlockFamily;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RadioBlockFamily extends AbstractBlockFamily {
    private static final Logger logger = LoggerFactory.getLogger(RadioBlockFamily.class);
    private Block archetypeBlock;
    private Map<String, Block> blocks;

    public RadioBlockFamily(BlockUri familyUri, Block archetypeBlock, HashMap<String, Block> blocks, List<String> categories) {
        super(familyUri, categories);
        this.archetypeBlock = archetypeBlock;
        this.blocks = blocks;
    }

    @Override
    public Block getBlockForPlacement(WorldProvider worldProvider, BlockEntityRegistry blockEntityRegistry, Vector3i location, Side attachmentSide, Side direction) {
        if (attachmentSide.isHorizontal()) {
            logger.info(attachmentSide.toString());
            return blocks.get("off;"+attachmentSide);
        }
        if (direction != null) {
            logger.info(direction.toString());
            return blocks.get("off;"+direction);
        } else {
            logger.info("archetype");
            return getArchetypeBlock();
        }
    }

    @Override
    public Block getArchetypeBlock() {
        return archetypeBlock;
    }

    @Override
    public Block getBlockFor(BlockUri blockUri) {
        return blocks.get(blockUri.getIdentifier().toString());
    }

    @Override
    public Iterable<Block> getBlocks() {
        return blocks.values();
    }
}
