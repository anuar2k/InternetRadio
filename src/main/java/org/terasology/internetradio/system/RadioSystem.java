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
import org.terasology.world.block.family.BlockFamily;

@RegisterSystem(RegisterMode.AUTHORITY)
public class RadioSystem extends BaseComponentSystem {

    @In
    WorldProvider worldProvider;

    @ReceiveEvent(components = {RadioComponent.class, BlockComponent.class})
    public void onActivate(ActivateEvent event, EntityRef entity, BlockComponent bComp) {
        Block block = bComp.getBlock();
        if (block == null) {
            return;
        }
        BlockFamily blockFamily = block.getBlockFamily();
        if (blockFamily == null) {
            return;
        }
        if (blockFamily instanceof RadioBlockFamily) {
            block = ((RadioBlockFamily) blockFamily).getBlockForStateSwitch(block);
            worldProvider.setBlock(bComp.getPosition(), block);
        }
    }
}
