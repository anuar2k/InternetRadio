package org.terasology.internetradio.system;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.terasology.entitySystem.entity.EntityRef;
import org.terasology.entitySystem.event.ReceiveEvent;
import org.terasology.entitySystem.systems.BaseComponentSystem;
import org.terasology.entitySystem.systems.RegisterMode;
import org.terasology.entitySystem.systems.RegisterSystem;
import org.terasology.internetradio.blocks.RadioBlockFamily;
import org.terasology.internetradio.component.RadioComponent;
import org.terasology.logic.common.ActivateEvent;
import org.terasology.registry.In;
import org.terasology.world.BlockEntityRegistry;
import org.terasology.world.WorldProvider;
import org.terasology.world.block.Block;
import org.terasology.world.block.BlockComponent;

@RegisterSystem(RegisterMode.AUTHORITY)
public class RadioSystem extends BaseComponentSystem {
    private static final Logger logger = LoggerFactory.getLogger(RadioSystem.class);
    @In
    WorldProvider worldProvider;
    @In
    BlockEntityRegistry blockEntityRegistry;

    @ReceiveEvent
    public void onActivate(ActivateEvent event, EntityRef entity) {
        BlockComponent bComp = entity.getComponent(BlockComponent.class);
        if (bComp == null) {
            return;
        }
        Block block = bComp.getBlock();
        if (block == null) {
            return;
        }

        logger.info(block.getBlockFamily().getDisplayName());
        logger.info(block.getBlockFamily().getClass().toString());
    }
}
