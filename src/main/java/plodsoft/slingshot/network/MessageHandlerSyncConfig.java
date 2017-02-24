package plodsoft.slingshot.network;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import plodsoft.slingshot.Config;
import plodsoft.slingshot.Slingshot;

public class MessageHandlerSyncConfig implements IMessageHandler<MessageSyncConfig, IMessage> {

	@Override
	public IMessage onMessage(MessageSyncConfig msg, MessageContext ctx) {
		Slingshot.log.info("received sync-config packet");

		Config.stoneballDamage = msg.stoneballDamage;
		Config.metalballDamage = msg.metalballDamage;
		Config.enderballDamage = msg.enderballDamage;
		return null;
	}
}
