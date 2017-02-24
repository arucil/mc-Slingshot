package plodsoft.slingshot.events;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import plodsoft.slingshot.Slingshot;
import plodsoft.slingshot.network.MessageSyncConfig;

public class EventHandlerPlayerLogin {
	@SubscribeEvent
	public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent e) {
		if (e.player instanceof EntityPlayerMP) {
			Slingshot.log.info("send sync-config packet to player " + e.player.getName());
			Slingshot.proxy.networkWrapper.sendTo(new MessageSyncConfig(), (EntityPlayerMP) e.player);
		}
	}
}
