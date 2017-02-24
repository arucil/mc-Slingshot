package plodsoft.slingshot.network;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import plodsoft.slingshot.Config;

public class MessageSyncConfig implements IMessage {
	public float stoneballDamage;
	public float metalballDamage;
	public float enderballDamage;

	public MessageSyncConfig() {
		this.stoneballDamage = Config.stoneballDamage;
		this.metalballDamage = Config.metalballDamage;
		this.enderballDamage = Config.enderballDamage;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		stoneballDamage = buf.readFloat();
		metalballDamage = buf.readFloat();
		enderballDamage = buf.readFloat();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeFloat(stoneballDamage);
		buf.writeFloat(metalballDamage);
		buf.writeFloat(enderballDamage);
	}
}
