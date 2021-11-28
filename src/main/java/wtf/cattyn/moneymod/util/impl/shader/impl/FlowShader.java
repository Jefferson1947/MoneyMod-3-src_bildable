//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.util.impl.shader.impl;

import org.lwjgl.opengl.GL20;
import net.minecraft.client.gui.ScaledResolution;
import wtf.cattyn.moneymod.util.impl.shader.FramebufferShader;

public class FlowShader extends FramebufferShader
{
    public static final FlowShader INSTANCE;
    public float time;
    
    public FlowShader() {
        super("flow.frag");
    }
    
    @Override
    public void setupUniforms() {
        this.setupUniform("resolution");
        this.setupUniform("time");
    }
    
    @Override
    public void updateUniforms() {
        GL20.glUniform2f(this.getUniform("resolution"), (float)new ScaledResolution(FlowShader.mc).getScaledWidth(), (float)new ScaledResolution(FlowShader.mc).getScaledHeight());
        GL20.glUniform1f(this.getUniform("time"), 1.0f);
    }
    
    static {
        INSTANCE = new FlowShader();
    }
}
