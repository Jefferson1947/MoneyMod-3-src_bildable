// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.ui.glslmenu;

import java.io.FileNotFoundException;
import java.util.List;
import java.io.FileInputStream;
import java.util.Random;
import java.util.Objects;
import java.util.ArrayList;
import java.io.File;
import java.io.InputStream;

public class Shaders
{
    public GLSLSandboxShader currentshader;
    public long time;
    
    public void init() {
        try {
            final Object[] shader = this.getRandomShader();
            if (shader == null) {
                this.currentshader = null;
            }
            else {
                final String name = (String)shader[0];
                final InputStream is = (InputStream)shader[1];
                this.currentshader = new GLSLSandboxShader(name, is);
                if (!this.currentshader.initialized) {
                    this.currentshader = null;
                }
                else {
                    this.time = System.currentTimeMillis();
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            this.currentshader = null;
        }
    }
    
    public Object[] getRandomShader() throws FileNotFoundException {
        final File folder = new File("moneymod+3/shaders");
        if (!folder.exists()) {
            return null;
        }
        final List<String> shaders = new ArrayList<String>();
        for (final File file : Objects.requireNonNull(folder.listFiles())) {
            final String name = file.getName();
            if (name.endsWith(".fsh")) {
                shaders.add(name);
            }
        }
        if (shaders.size() == 0) {
            return null;
        }
        final String randomname = shaders.get(new Random().nextInt(shaders.size()));
        final FileInputStream fis = new FileInputStream(new File("moneymod+3/shaders/" + randomname));
        return new Object[] { randomname, fis };
    }
}
