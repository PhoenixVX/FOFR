package me.zero.fofr.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.EnumSet;

public class GownEntity extends HostileEntity implements IAnimatable {
    private final AnimationFactory animationFactory = new AnimationFactory(this);

    public GownEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.moveControl = new GownMoveControl(this);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new GownEntity.SwimmingGoal(this));
        this.goalSelector.add(4, new GownEntity.FaceTowardTargetGoal(this));
        this.goalSelector.add(3, new GownEntity.RandomLookGoal(this));
        this.goalSelector.add(5, new GownEntity.MoveGoal(this));

        this.targetSelector.add(7, new ActiveTargetGoal<>(this, PlayerEntity.class, 10, true, false, (livingEntity) -> Math.abs(livingEntity.getY() - this.getY()) <= 8.0D));
    }

    public static DefaultAttributeContainer.Builder createEldritchGownAttributes() {
        return GownEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 8.0D)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 40)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2625D);
    }

    // Animation
    private <E extends IAnimatable> PlayState animationPredicate(AnimationEvent<E> event) {
        if (event.isMoving()) {
            // Assume we are walking
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.gown.walk", true));
            return PlayState.CONTINUE;
        }

        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.gown.idle", true));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController<>(this, "controller", 20, this::animationPredicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.animationFactory;
    }
    
    // AI
    private static class GownMoveControl extends MoveControl {
        private float targetYaw;

        public GownMoveControl(GownEntity gownEntity) {
            super(gownEntity);
            this.targetYaw = 180.0F * gownEntity.getYaw() / 3.1415927F;
        }

        public void look(float targetYaw) {
            this.targetYaw = targetYaw;
        }

        public void move(double speed) {
            this.speed = speed;
            this.state = State.MOVE_TO;
        }

        public void tick() {
            this.entity.setYaw(this.wrapDegrees(this.entity.getYaw(), this.targetYaw, 90.0F));
            this.entity.headYaw = this.entity.getYaw();
            this.entity.bodyYaw = this.entity.getYaw();
            if (this.state != State.MOVE_TO) {
                this.entity.setForwardSpeed(0.0F);
            } else {
                this.state = State.WAIT;
                if (this.entity.isOnGround()) {
                    this.entity.setMovementSpeed((float) (this.speed * this.entity.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED)));
                } else {
                    this.entity.setMovementSpeed((float) (this.speed * this.entity.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED)));
                }

            }
        }
    }

    static class SwimmingGoal extends Goal {
        private final GownEntity gown;

        public SwimmingGoal(GownEntity gown) {
            this.gown = gown;
            this.setControls(EnumSet.of(Control.JUMP, Control.MOVE));
            gown.getNavigation().setCanSwim(true);
        }

        public boolean canStart() {
            return (this.gown.isTouchingWater() || this.gown.isInLava()) && this.gown.getMoveControl() instanceof GownEntity.GownMoveControl;
        }

        public boolean shouldRunEveryTick() {
            return true;
        }

        public void tick() {
            ((GownEntity.GownMoveControl)this.gown.getMoveControl()).move(1.2D);
        }
    }

    static class FaceTowardTargetGoal extends Goal {
        private final GownEntity gown;
        private int ticksLeft;

        public FaceTowardTargetGoal(GownEntity gown) {
            this.gown = gown;
            this.setControls(EnumSet.of(Control.LOOK));
        }

        public boolean canStart() {
            LivingEntity livingEntity = this.gown.getTarget();
            if (livingEntity == null) {
                return false;
            } else {
                return this.gown.canTarget(livingEntity) && this.gown.getMoveControl() instanceof GownMoveControl;
            }
        }

        public void start() {
            this.ticksLeft = toGoalTicks(300);
            super.start();
        }

        public boolean shouldContinue() {
            LivingEntity livingEntity = this.gown.getTarget();
            if (livingEntity == null) {
                return false;
            } else if (!this.gown.canTarget(livingEntity)) {
                return false;
            } else {
                return --this.ticksLeft > 0;
            }
        }

        public boolean shouldRunEveryTick() {
            return true;
        }

        public void tick() {
            LivingEntity livingEntity = this.gown.getTarget();
            if (livingEntity != null) {
                this.gown.lookAtEntity(livingEntity, 10.0F, 10.0F);
            }

            ((GownEntity.GownMoveControl) this.gown.getMoveControl()).look(this.gown.getYaw());
        }
    }

    static class RandomLookGoal extends Goal {
        private final GownEntity gown;
        private float targetYaw;
        private int timer;

        public RandomLookGoal(GownEntity gown) {
            this.gown = gown;
            this.setControls(EnumSet.of(Control.LOOK));
        }

        public boolean canStart() {
            return this.gown.getTarget() == null && (this.gown.onGround || this.gown.isTouchingWater() || this.gown.isInLava() || this.gown.hasStatusEffect(StatusEffects.LEVITATION)) && this.gown.getMoveControl() instanceof GownEntity.GownMoveControl;
        }

        public void tick() {
            if (--this.timer <= 0) {
                this.timer = this.getTickCount(40 + this.gown.getRandom().nextInt(60));
                this.targetYaw = (float)this.gown.getRandom().nextInt(360);
            }

            ((GownEntity.GownMoveControl) this.gown.getMoveControl()).look(this.targetYaw);
        }
    }

    static class MoveGoal extends Goal {
        private final GownEntity gown;

        public MoveGoal(GownEntity gown) {
            this.gown = gown;
            this.setControls(EnumSet.of(Control.JUMP, Control.MOVE));
        }

        public boolean canStart() {
            return !this.gown.hasVehicle();
        }

        public void tick() {
            ((GownEntity.GownMoveControl) this.gown.getMoveControl()).move(1.0D);
        }
    }
}
