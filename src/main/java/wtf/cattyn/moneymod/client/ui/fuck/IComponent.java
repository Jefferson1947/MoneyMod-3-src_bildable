// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.ui.fuck;

public interface IComponent
{
    void drawScreen(final int p0, final int p1, final float p2);
    
    void keyTyped(final char p0, final int p1);
    
    void mouseClicked(final int p0, final int p1, final int p2);
    
    void mouseReleased(final int p0, final int p1, final int p2);
    
    void update(final int p0, final int p1);
    
    default double[] getSize() {
        return new double[] { 30.0, 12.0 };
    }
}
