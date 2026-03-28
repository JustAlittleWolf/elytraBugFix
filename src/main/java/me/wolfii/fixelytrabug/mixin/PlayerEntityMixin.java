package me.wolfii.fixelytrabug.mixin;

import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@SuppressWarnings("DataFlowIssue")
@Mixin(Player.class)
public class PlayerEntityMixin {
    @Inject(method = "tryToStartFallFlying", at = @At("HEAD"), cancellable = true)
    private void injectedStartFallFlying(CallbackInfoReturnable<Boolean> cir) {
        if (((Player) (Object) this).isShiftKeyDown()) return;
        if (((Player) (Object) this).isFallFlying()) return;
        if (((Player) (Object) this).isInLava()) return;
        double offset = 4.0 / 16.0;
        if (((Player) (Object) this).isSprinting()) offset = 12.0 / 16.0;
        if (this.doesCollideY(offset) && this.doesCollideY(-offset)) {
            cir.setReturnValue(false);
        }
    }

    @Unique
    private boolean doesCollideY(double offsetY) {
        return !((Player) (Object) this).isFree(0, offsetY, 0);
    }
}
