package plodsoft.slingshot;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import plodsoft.slingshot.entities.EntityEnderBall;
import plodsoft.slingshot.entities.EntityMetalBall;
import plodsoft.slingshot.entities.EntityStoneBall;

public class ModEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = new DeferredRegister<>(ForgeRegistries.ENTITIES, Slingshot.MODID);

    public static final RegistryObject<EntityType<EntityStoneBall>> STONE_BALL = ENTITY_TYPES.register("stone_ball", () ->
            EntityType.Builder.<EntityStoneBall>create(EntityStoneBall::new, EntityClassification.MISC)
                    .size(0.25f, 0.25f)
                    .build(new ResourceLocation(Slingshot.MODID, "stone_ball").toString())
    );

    public static final RegistryObject<EntityType<EntityMetalBall>> METAL_BALL = ENTITY_TYPES.register("metal_ball", () ->
            EntityType.Builder.<EntityMetalBall>create(EntityMetalBall::new, EntityClassification.MISC)
                    .size(0.25f, 0.25f)
                    .build(new ResourceLocation(Slingshot.MODID, "metal_ball").toString())
    );

    public static final RegistryObject<EntityType<EntityEnderBall>> ENDER_BALL = ENTITY_TYPES.register("ender_ball", () ->
            EntityType.Builder.<EntityEnderBall>create(EntityEnderBall::new, EntityClassification.MISC)
                    .size(0.25f, 0.25f)
                    .build(new ResourceLocation(Slingshot.MODID, "ender_ball").toString())
    );
}
