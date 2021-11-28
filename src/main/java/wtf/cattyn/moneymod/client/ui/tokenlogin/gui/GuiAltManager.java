//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.ui.tokenlogin.gui;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.gui.GuiSlot;
import wtf.cattyn.moneymod.client.ui.tokenlogin.Account;
import wtf.cattyn.moneymod.Main;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class GuiAltManager extends GuiScreen
{
    private List list;
    private int currentslot;
    private GuiButton login;
    private GuiButton edit;
    private GuiButton add;
    private GuiButton delete;
    
    public void initGui() {
        this.list = new List();
        this.buttonList.add(this.login = new GuiButton(354, this.width / 2 - 154, this.height - 26, 70, 20, "Login"));
        this.buttonList.add(this.edit = new GuiButton(355, this.width / 2 - 74, this.height - 26, 70, 20, "Edit"));
        this.buttonList.add(this.add = new GuiButton(356, this.width / 2 + 4, this.height - 26, 70, 20, "Add"));
        this.buttonList.add(this.delete = new GuiButton(357, this.width / 2 + 80, this.height - 26, 70, 20, "Delete"));
        this.buttonList.add(new GuiButton(1337, 2, 2, 100, 20, "Back"));
        Main.getManager().resetState();
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        if (Main.getAltManager().getAccounts().size() <= 0) {
            this.login.enabled = false;
            this.edit.enabled = false;
            this.delete.enabled = false;
        }
        else {
            this.login.enabled = true;
            this.edit.enabled = true;
            this.delete.enabled = true;
        }
        this.list.drawScreen(mouseX, mouseY, partialTicks);
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.drawCenteredString(this.mc.fontRenderer, "Access Token Login", this.width / 2, 8, -1);
        this.drawCenteredString(this.mc.fontRenderer, Main.getManager().getManagerState(), this.width / 2, 32 - this.fontRenderer.FONT_HEIGHT - 4, -1);
    }
    
    protected void actionPerformed(final GuiButton button) {
        if (button.id == 1337) {
            this.mc.displayGuiScreen((GuiScreen)null);
        }
        else if (button.id == 356) {
            this.mc.displayGuiScreen((GuiScreen)new GuiAddAccount());
        }
        else if (button.id == 355) {
            this.mc.displayGuiScreen((GuiScreen)new GuiEditAccount(Main.getAltManager().getAccounts().get(this.currentslot), this.currentslot));
        }
        else if (button.id == 354) {
            final Account account = Main.getAltManager().getAccounts().get(this.currentslot);
            Main.getAltManager().getAccounts().remove(account);
            account.timeused = System.currentTimeMillis();
            Main.getAltManager().addAccount(account);
            Main.getManager().login(account.username, account.accessToken, account.clientToken);
        }
        else if (button.id == 357) {
            final Account account = Main.getAltManager().getAccounts().get(this.currentslot);
            Main.getAltManager().removeAccount(account);
            this.mc.displayGuiScreen((GuiScreen)new GuiAltManager());
        }
    }
    
    public void handleMouseInput() {
        try {
            super.handleMouseInput();
        }
        catch (Exception ex) {}
        this.list.handleMouseInput();
    }
    
    class List extends GuiSlot
    {
        public List() {
            super(GuiAltManager.this.mc, GuiAltManager.this.width, GuiAltManager.this.height, 32, GuiAltManager.this.height - 32, 14);
        }
        
        protected int getSize() {
            return Main.getAltManager().getAccounts().size();
        }
        
        protected void elementClicked(final int slotIndex, final boolean isDoubleClick, final int mouseX, final int mouseY) {
            GuiAltManager.this.currentslot = slotIndex;
        }
        
        protected boolean isSelected(final int slotIndex) {
            return GuiAltManager.this.currentslot == slotIndex;
        }
        
        protected void drawBackground() {
            GuiAltManager.this.drawBackground(0);
        }
        
        protected void drawSlot(final int slotIndex, final int xPos, final int yPos, final int heightIn, final int mouseXIn, final int mouseYIn, final float partialTicks) {
            final Account account = Main.getAltManager().getAccounts().get(slotIndex);
            final String lastused = Main.getAltManager().getLastUsedAccount();
            String username = account.username;
            if (username.equals(lastused)) {
                username = username + ChatFormatting.DARK_GRAY + " # last used account";
            }
            GuiAltManager.this.drawString(this.mc.fontRenderer, username, xPos + 2, yPos + 1, -1);
        }
    }
}
