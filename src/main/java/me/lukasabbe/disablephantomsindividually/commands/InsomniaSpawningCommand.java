package me.lukasabbe.disablephantomsindividually.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import me.lukasabbe.disablephantomsindividually.playerdata.InsomniaData;
import me.lukasabbe.disablephantomsindividually.playerdata.PlayerData;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class InsomniaSpawningCommand {
    public void RegisterCommand(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        dispatcher.register(CommandManager
                .literal("insomnia")
                .executes(this::runStatus)
                .then(CommandManager
                        .literal("enable")
                        .executes(ctx -> runCommand(ctx,true)))
                .then(CommandManager
                        .literal("disable")
                        .executes(ctx -> runCommand(ctx,false))));
    }

    private int runCommand(CommandContext<ServerCommandSource> ctx,boolean b) {
        if(!ctx.getSource().isExecutedByPlayer()){
            ctx.getSource().sendError(Text.literal("You need to be a player to run this command"));
            return 0;
        }
        PlayerData playerData = InsomniaData.getPlayerState(ctx.getSource().getPlayer());
        playerData.insomniaToggle = b;
        String message;
        if(b)
            message = "You have now turned on phantom spawning";
        else
            message = "You have now turned off phantom spawning";

        ctx.getSource().sendFeedback(()->Text.literal(message),false);
        return 1;
    }

    private int runStatus(CommandContext<ServerCommandSource> ctx) {
        if(!ctx.getSource().isExecutedByPlayer()){
            ctx.getSource().sendError(Text.literal("You need to be a player to run this command"));
            return 0;
        }
        PlayerData playerData = InsomniaData.getPlayerState(ctx.getSource().getPlayer());
        String message;
        if(playerData.insomniaToggle)
            message = "You have phantoms ON, they can spawn around you";
        else
            message = "You have phantoms OFF, they can't spawn around you";
        ctx.getSource().sendFeedback(()->Text.literal(message),false);
        return 1;
    }

}
