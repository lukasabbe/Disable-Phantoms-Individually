package me.lukasabbe.disablephantomsindividually;

import me.lukasabbe.disablephantomsindividually.commands.InsomniaSpawningCommand;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class Disablephantomsindividually implements DedicatedServerModInitializer {
    public static final String MOD_ID = "disablephantomsindividually";
    @Override
    public void onInitializeServer() {
        registerCommands();
    }
    public void registerCommands(){
        CommandRegistrationCallback.EVENT.register(new InsomniaSpawningCommand()::RegisterCommand);
    }
}
