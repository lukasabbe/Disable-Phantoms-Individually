package me.lukasabbe.disablephantomsindividually.playerdata;

import me.lukasabbe.disablephantomsindividually.Disablephantomsindividually;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InsomniaData extends PersistentState {
    public Map<UUID, PlayerData> players = new HashMap<>();

    public static PlayerData getPlayerState(ServerPlayerEntity player){
        InsomniaData data = getServerState(player.getWorld().getServer());
        return data.players.computeIfAbsent(player.getUuid(), uuid -> new PlayerData());
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        NbtCompound playersNbt = new NbtCompound();
        players.forEach(((uuid, playerData) -> {
            NbtCompound playerNbt = new NbtCompound();
            playerNbt.putBoolean("disablephantomsindividually",playerData.insomniaToggle);
            playersNbt.put(uuid.toString(),playerNbt);
        }));
        nbt.put("players",playersNbt);
        return nbt;
    }
    public static InsomniaData createFromNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup registerLookup){
        InsomniaData data = new InsomniaData();
        NbtCompound playersNbt = nbtCompound.getCompound("players");
        playersNbt.getKeys().forEach(key ->{
            PlayerData playerData = new PlayerData();
            playerData.insomniaToggle = playersNbt.getCompound(key).getBoolean("disablephantomsindividually");

            UUID uuid = UUID.fromString(key);
            data.players.put(uuid,playerData);
        });
        return data;
    }
    private static Type<InsomniaData> type = new Type<>(
            InsomniaData::new,
            InsomniaData::createFromNbt,
            null
    );
    public static InsomniaData getServerState(MinecraftServer server){
        PersistentStateManager manager = server.getWorld(World.OVERWORLD).getPersistentStateManager();
        InsomniaData data = manager.getOrCreate(type, Disablephantomsindividually.MOD_ID);
        data.markDirty();
        return data;
    }
}
