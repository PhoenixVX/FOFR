package me.zero.fofr.entity.renderer;

import me.zero.fofr.FOFR;
import me.zero.fofr.entity.GownEntity;
import me.zero.fofr.entity.model.GownEntityModel;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class GownEntityRenderer extends GeoEntityRenderer<GownEntity> {
    private static final Identifier texture = new Identifier(FOFR.MOD_ID, "textures/entity/gown.png");

    public GownEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new GownEntityModel());
    }

    @Override
    public Identifier getTexture (GownEntity entity) {
        return texture;
    }

    @Override
    public RenderLayer getRenderType (GownEntity animatable, float partialTicks, MatrixStack stack, VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, Identifier textureLocation) {
        return RenderLayer.getEntityCutoutNoCull(texture);
    }
}
