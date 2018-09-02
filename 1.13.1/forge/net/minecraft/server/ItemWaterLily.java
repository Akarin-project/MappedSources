package net.minecraft.server;

public class ItemWaterLily extends ItemBlock {
    public ItemWaterLily(Block block, Item.Info item$info) {
        super(block, item$info);
    }

    public EnumInteractionResult a(ItemActionContext var1) {
        return EnumInteractionResult.PASS;
    }

    public InteractionResultWrapper<ItemStack> a(World world, EntityHuman entityhuman, EnumHand enumhand) {
        ItemStack itemstack = entityhuman.b(enumhand);
        MovingObjectPosition movingobjectposition = this.a(world, entityhuman, true);
        if (movingobjectposition == null) {
            return new InteractionResultWrapper<ItemStack>(EnumInteractionResult.PASS, itemstack);
        } else {
            if (movingobjectposition.type == MovingObjectPosition.EnumMovingObjectType.BLOCK) {
                BlockPosition blockposition = movingobjectposition.a();
                if (!world.a(entityhuman, blockposition) || !entityhuman.a(blockposition.shift(movingobjectposition.direction), movingobjectposition.direction, itemstack)) {
                    return new InteractionResultWrapper<ItemStack>(EnumInteractionResult.FAIL, itemstack);
                }

                BlockPosition blockposition1 = blockposition.up();
                IBlockData iblockdata = world.getType(blockposition);
                Material material = iblockdata.getMaterial();
                Fluid fluid = world.b(blockposition);
                if ((fluid.c() == FluidTypes.c || material == Material.ICE) && world.isEmpty(blockposition1)) {
                    world.setTypeAndData(blockposition1, Blocks.LILY_PAD.getBlockData(), 11);
                    if (entityhuman instanceof EntityPlayer) {
                        CriterionTriggers.y.a((EntityPlayer)entityhuman, blockposition1, itemstack);
                    }

                    if (!entityhuman.abilities.canInstantlyBuild) {
                        itemstack.subtract(1);
                    }

                    entityhuman.b(StatisticList.ITEM_USED.b(this));
                    world.a(entityhuman, blockposition, SoundEffects.BLOCK_LILY_PAD_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    return new InteractionResultWrapper<ItemStack>(EnumInteractionResult.SUCCESS, itemstack);
                }
            }

            return new InteractionResultWrapper<ItemStack>(EnumInteractionResult.FAIL, itemstack);
        }
    }
}