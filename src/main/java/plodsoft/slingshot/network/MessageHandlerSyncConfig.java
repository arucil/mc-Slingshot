package plodsoft.slingshot.network;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import plodsoft.slingshot.Config;

public class MessageHandlerSyncConfig implements IMessageHandler<MessageSyncConfig, IMessage> {

	@Override
	public IMessage onMessage(MessageSyncConfig msg, MessageContext ctx) {
		Config.stoneballDamage = msg.stoneballDamage;
		Config.metalballDamage = msg.metalballDamage;
		Config.enderballDamage = msg.enderballDamage;
		return null;
	}
}
