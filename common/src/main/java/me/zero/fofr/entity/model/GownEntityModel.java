package me.zero.fofr.entity.model;

import me.zero.fofr.FOFR;
import me.zero.fofr.entity.GownEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class GownEntityModel extends AnimatedGeoModel<GownEntity> {
    @Override
    public Identifier getModelLocation(GownEntity object) {
        return new Identifier(FOFR.MOD_ID, "geo/gown.geo.json");
    }

    @Override
    public Identifier getTextureLocation(GownEntity object) {
        return new Identifier(FOFR.MOD_ID, "textures/entity/gown.png");
    }

    @Override
    public Identifier getAnimationFileLocation(GownEntity animatable) {
        return new Identifier(FOFR.MOD_ID, "animations/gown.animation.json");
    }
}
