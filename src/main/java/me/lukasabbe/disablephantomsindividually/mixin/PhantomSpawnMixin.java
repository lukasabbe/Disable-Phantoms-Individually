package me.lukasabbe.disablephantomsindividually.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import me.lukasabbe.disablephantomsindividually.playerdata.InsomniaData;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.spawner.PhantomSpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PhantomSpawner.class)
public class PhantomSpawnMixin {
    @ModifyExpressionValue(method = "spawn", at= @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayerEntity;isSpectator()Z"))
    public boolean isSpawningForPlayer(boolean original, @Local ServerPlayerEntity player){
        return original || !InsomniaData.getPlayerState(player).insomniaToggle;
    }
}
