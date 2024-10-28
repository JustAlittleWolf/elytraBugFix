package me.wolfii.fixelytrabug.mixin;

import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
    @Inject(method = "checkGliding", at = @At("HEAD"), cancellable = true)
    private void injectedStartFallFlying(CallbackInfoReturnable<Boolean> cir) {
        if (((PlayerEntity) (Object) this).isSneaking()) return;
        if (((PlayerEntity) (Object) this).isGliding()) return;
        double offset = 4.0 / 16.0;
        if (((PlayerEntity) (Object) this).isSprinting()) offset = 12.0 / 16.0;
        if (this.doesCollideY(offset) && this.doesCollideY(-offset)) {
            cir.setReturnValue(false);
        }
    }

    @Unique
    private boolean doesCollideY(double offsetY) {
        return !((PlayerEntity) (Object) this).doesNotCollide(0, offsetY, 0);
    }
}
