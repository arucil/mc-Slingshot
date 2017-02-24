package plodsoft.slingshot.events;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import plodsoft.slingshot.Slingshot;
import plodsoft.slingshot.network.MessageSyncConfig;

public class EventHandlerPlayerLogin {
	@SubscribeEvent
	public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent e) {
		if (e.player instanceof EntityPlayerMP) {
			FMLLog.info("======================== send sync-config packet to player " + e.player.getName());
			Slingshot.proxy.networkWrapper.sendTo(new MessageSyncConfig(), (EntityPlayerMP) e.player);
		} else {
			FMLLog.info("player " + e.player.getName() + " login, but ssp");
			((EntityPlayerSP) e.player).sendChatMessage("you're dead");
		}
	}
}
