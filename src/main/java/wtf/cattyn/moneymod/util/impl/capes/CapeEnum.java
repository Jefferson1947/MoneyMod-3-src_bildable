// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.util.impl.capes;

import net.minecraft.util.ResourceLocation;

public enum CapeEnum
{
    PEPSI(new ResourceLocation("minecraft:moneymod/capes/pepsi.png"), "pepsi"), 
    SHIT(new ResourceLocation("minecraft:moneymod/capes/shit.png"), "idk"), 
    NIGGER(new ResourceLocation("minecraft:moneymod/capes/nigger.png"), "nigger"), 
    SQUIDGAME(new ResourceLocation("minecraft:moneymod/capes/squidgame.png"), "squidgame"), 
    PIGCAPE(new ResourceLocation("minecraft:moneymod/capes/pigcape.png"), "pigcape"), 
    GAYCAPE(new ResourceLocation("minecraft:moneymod/capes/1488.png"), "1488"), 
    CAPEDOSIA(new ResourceLocation("minecraft:moneymod/capes/capedosia.png"), "capedosia");
    
    private ResourceLocation loc;
    private String name;
    
    private CapeEnum(final ResourceLocation loc, final String name) {
        this.loc = loc;
        this.name = name;
    }
    
    public ResourceLocation getResourceLocation() {
        return this.loc;
    }
    
    public String getCapeName() {
        return this.name;
    }
}
