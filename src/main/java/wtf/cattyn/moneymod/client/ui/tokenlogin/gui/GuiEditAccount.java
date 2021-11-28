//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.ui.tokenlogin.gui;

import wtf.cattyn.moneymod.Main;
import wtf.cattyn.moneymod.client.ui.tokenlogin.Account;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.GuiScreen;

public class GuiEditAccount extends GuiScreen
{
    private GuiTextField username;
    private GuiTextField accessToken;
    private GuiTextField clientToken;
    private GuiButton edit;
    private GuiButton back;
    private Account account;
    private int index;
    
    public GuiEditAccount(final Account account, final int index) {
        this.account = account;
        this.index = index;
    }
    
    public void initGui() {
        final int midwidth = this.width / 2;
        final int baseheight = this.height / 2 - 48;
        this.username = new GuiTextField(444, this.fontRenderer, midwidth - 100, baseheight, 200, 20);
        this.accessToken = new GuiTextField(555, this.fontRenderer, midwidth - 100, baseheight + this.fontRenderer.FONT_HEIGHT + 20 + 13, 200, 20);
        this.clientToken = new GuiTextField(666, this.fontRenderer, midwidth - 100, baseheight + this.fontRenderer.FONT_HEIGHT * 2 + 66, 200, 20);
        this.username.setMaxStringLength(16);
        this.accessToken.setMaxStringLength(999);
        this.clientToken.setMaxStringLength(999);
        this.username.setText(this.account.username);
        this.accessToken.setText(this.account.accessToken);
        this.clientToken.setText(this.account.clientToken);
        this.buttonList.add(this.edit = new GuiButton(777, midwidth - 100, this.clientToken.y + this.clientToken.height + 5, 98, 20, "Edit"));
        this.buttonList.add(this.back = new GuiButton(888, midwidth + 2, this.clientToken.y + this.clientToken.height + 5, 100, 20, "Back"));
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.edit.enabled = (this.username.getText() != null && this.username.getText().length() > 0 && this.accessToken.getText() != null && this.accessToken.getText().length() > 0);
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.username.drawTextBox();
        this.accessToken.drawTextBox();
        this.clientToken.drawTextBox();
        final int midwidth = this.width / 2;
        this.drawCenteredString(this.fontRenderer, "Edit Account", midwidth, this.username.y - 45, -1);
        this.drawCenteredString(this.fontRenderer, "Username", midwidth, this.username.y - this.fontRenderer.FONT_HEIGHT - 4, -1);
        this.drawCenteredString(this.fontRenderer, "Access Token", midwidth, this.accessToken.y - this.fontRenderer.FONT_HEIGHT - 4, -1);
        this.drawCenteredString(this.fontRenderer, "Client Token (optional)", midwidth, this.clientToken.y - this.fontRenderer.FONT_HEIGHT - 4, -1);
    }
    
    protected void actionPerformed(final GuiButton button) {
        if (button.id == 888) {
            this.mc.displayGuiScreen((GuiScreen)new GuiAltManager());
        }
        else if (button.id == 777 && this.edit.enabled) {
            Main.getAltManager().getAccounts().remove(this.index);
            Main.getAltManager().addAccount(this.account);
            this.mc.displayGuiScreen((GuiScreen)new GuiAltManager());
        }
    }
    
    protected void keyTyped(final char typedChar, final int keyCode) {
        try {
            super.keyTyped(typedChar, keyCode);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        this.username.textboxKeyTyped(typedChar, keyCode);
        this.accessToken.textboxKeyTyped(typedChar, keyCode);
        this.clientToken.textboxKeyTyped(typedChar, keyCode);
    }
    
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        try {
            super.mouseClicked(mouseX, mouseY, mouseButton);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        this.username.mouseClicked(mouseX, mouseY, mouseButton);
        this.accessToken.mouseClicked(mouseX, mouseY, mouseButton);
        this.clientToken.mouseClicked(mouseX, mouseY, mouseButton);
    }
}
