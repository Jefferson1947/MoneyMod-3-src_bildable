// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.api.managment;

import java.util.LinkedHashSet;
import java.util.Set;

public class FriendManager
{
    private final Set<String> friends;
    
    public FriendManager() {
        this.friends = new LinkedHashSet<String>();
    }
    
    public Set<String> get(int i) {
        return this.friends;
    }
    
    public void add(final String name) {
        this.friends.add(name);
    }
    
    public void del(final String name) {
        this.friends.remove(name);
    }
    
    public boolean is(final String name) {
        return this.friends.stream().anyMatch(friend -> friend.equalsIgnoreCase(name));
    }
}
