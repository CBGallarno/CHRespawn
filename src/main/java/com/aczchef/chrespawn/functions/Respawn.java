package com.aczchef.chrespawn.functions;

import com.comphenix.protocol.Packets;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.laytonsmith.PureUtilities.Version;
import com.laytonsmith.abstraction.MCCommandSender;
import com.laytonsmith.abstraction.MCPlayer;
import com.laytonsmith.abstraction.bukkit.BukkitMCPlayer;
import com.laytonsmith.annotations.api;
import com.laytonsmith.annotations.startup;
import com.laytonsmith.core.CHVersion;
import com.laytonsmith.core.Static;
import com.laytonsmith.core.constructs.Construct;
import com.laytonsmith.core.constructs.Target;
import com.laytonsmith.core.environments.CommandHelperEnvironment;
import com.laytonsmith.core.environments.Environment;
import com.laytonsmith.core.exceptions.ConfigRuntimeException;
import com.laytonsmith.core.functions.AbstractFunction;
import com.laytonsmith.core.functions.Exceptions.ExceptionType;
import org.bukkit.entity.Player;

/**
 *
 * @author cgallarno
 */
public class Respawn {
    
    @api
    public static class force_respawn extends AbstractFunction {

	public ExceptionType[] thrown() {
	    throw new UnsupportedOperationException("Not supported yet.");
	}

	public boolean isRestricted() {
	    return false;
	}

	public Boolean runAsync() {
	    return false;
	}

	public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
	    MCCommandSender s = environment.getEnv(CommandHelperEnvironment.class).GetCommandSender();
	    MCPlayer p = null;

	    if(s instanceof MCPlayer) {
		p = (MCPlayer) s;
	    }
	    if(args.length == 1) {
		p = Static.GetPlayer(args[0], t);
	    }
	    Static.AssertPlayerNonNull(p, t);
	    
	    if(p.isDead()) {
		PacketContainer packet = new PacketContainer(Packets.Client.CLIENT_COMMAND);
		packet.getIntegers().write(0, 1);
		
		Player player = ((BukkitMCPlayer) p)._Player();
		try {
		    ProtocolLibrary.getProtocolManager().recieveClientPacket(player, packet);
		} catch (Exception e) {
		    System.out.print(e);
		    throw new RuntimeException("Cannot recieve packet.", e);
		}
	    }
	    
	    return null;
	}

	public String getName() {
	    return "force_respawn";
	}

	public Integer[] numArgs() {
	    return new Integer[] {0, 1};
	}

	public String docs() {
	    throw new UnsupportedOperationException("Not supported yet.");
	}

	public Version since() {
	    return CHVersion.V3_3_1;
	}
	
    }
}
