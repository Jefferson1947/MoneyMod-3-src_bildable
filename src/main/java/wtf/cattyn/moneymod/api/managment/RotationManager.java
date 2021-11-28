//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.api.managment;

import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import wtf.cattyn.moneymod.util.Globals;

public class RotationManager implements Globals
{
    private float yaw;
    private float pitch;
    
    public void update() {
        this.yaw = RotationManager.mc.player.rotationYaw;
        this.pitch = RotationManager.mc.player.rotationPitch;
    }
    
    public void reset() {
        RotationManager.mc.player.rotationYaw = this.yaw;
        RotationManager.mc.player.rotationPitch = this.pitch;
        RotationManager.mc.player.rotationYawHead = this.yaw;
    }
    
    public void set(final float yaw, final float pitch, final boolean packet) {
        if (packet) {
            RotationManager.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(yaw, pitch, RotationManager.mc.player.onGround));
        }
        else {
            RotationManager.mc.player.rotationYaw = yaw;
            RotationManager.mc.player.rotationYawHead = yaw;
            RotationManager.mc.player.rotationPitch = pitch;
        }
    }
    
    public float[] look(final BlockPos bp, final boolean packet) {
        return this.look(bp, packet, true);
    }
    
    public float[] look(final Entity bp, final boolean packet) {
        return this.look(bp, packet, true);
    }
    
    public float[] look(final BlockPos bp, final boolean packet, final boolean set) {
        final float[] angles = calcAngle(RotationManager.mc.player.getPositionEyes(RotationManager.mc.getRenderPartialTicks()), new Vec3d((double)(bp.getX() + 0.5f), (double)(bp.getY() + 0.5f), (double)(bp.getZ() + 0.5f)));
        if (set) {
            this.set(angles[0], angles[1], packet);
        }
        return angles;
    }
    
    public float[] look(final Entity entity, final boolean packet, final boolean set) {
        final float[] angles = calcAngle(RotationManager.mc.player.getPositionEyes(RotationManager.mc.getRenderPartialTicks()), entity.getPositionEyes(RotationManager.mc.getRenderPartialTicks()));
        if (set) {
            this.set(angles[0], angles[1], packet);
        }
        return angles;
    }
    
    public static float[] calcAngle(final Vec3d from, final Vec3d to) {
        final double difX = to.x - from.x;
        final double difY = (to.y - from.y) * -1.0;
        final double difZ = to.z - from.z;
        final double dist = MathHelper.sqrt(difX * difX + difZ * difZ);
        return new float[] { (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(difZ, difX)) - 90.0), (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(difY, dist))) };
    }
}
