package hellfirepvp.pandemic.util;

import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * This class is part of the Pandemic Mod
 * Class: BlockUtils
 * Created by HellFirePvP
 * Date: 27.11.2020 / 21:57
 */
public class BlockUtils {

    public static boolean isReplaceable(World world, BlockPos pos, BlockState state) {
        if (world.isAirBlock(pos)) {
            return true;
        }
        BlockItemUseContext ctx = TestBlockUseContext.getHandContext(world, null, Hand.MAIN_HAND, pos, Direction.UP);
        return state.isReplaceable(ctx);
    }

}
