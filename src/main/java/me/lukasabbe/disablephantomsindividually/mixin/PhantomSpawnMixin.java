package me.lukasabbe.disablephantomsindividually.mixin;

import me.lukasabbe.disablephantomsindividually.playerdata.InsomniaData;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.spawner.PhantomSpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PhantomSpawner.class)
public class PhantomSpawnMixin {
    @Redirect(method = "spawn", at= @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayerEntity;isSpectator()Z"))
    public boolean isSpawningForPlayer(ServerPlayerEntity instance){
        return instance.isSpectator() || !InsomniaData.getPlayerState(instance).insomniaToggle;
    }
}
