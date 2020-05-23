package xyz.ufactions.libs;

import com.google.common.base.Preconditions;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import net.minecraft.server.v1_15_R1.*;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.craftbukkit.v1_15_R1.CraftServer;
import org.bukkit.craftbukkit.v1_15_R1.CraftWorld;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.generator.ChunkGenerator;

import java.io.File;

/**
 * MegaBukkit class (c) Ricardo Barrera 2017-2020
 * <p>
 * CONVERSION TO 1.15.2 PROVIDED BY ProFewGames
 * ORIGINAL CODE PROVIDED BY MD_5
 */
public class WorldUtil {

    /**
     * This method is provided by MegaBukkit to reduce the amount of weight put on the server by
     * terrain generation and entity spawning by doing as little calculations as needed to get the
     * world functioning without compromising anything...
     *
     * @param creator The world creator
     * @return A new world
     */
    public static World fastCreateWorld(WorldCreator creator) {
        CraftServer server = (CraftServer) Bukkit.getServer();
        MinecraftServer console = server.getServer();

        Preconditions.checkState(!console.worldServer.isEmpty(), "Cannot create additional worlds on STARTUP");
        Validate.notNull(creator, "Creator may not be null");

        String name = creator.name();
        ChunkGenerator generator = creator.generator();
        File folder = new File(server.getWorldContainer(), name);
        World world = server.getWorld(name);
        WorldType type = WorldType.getType(creator.type().getName());
        boolean generateStructures = creator.generateStructures();

        if (world != null) {
            return world;
        }

        if ((folder.exists()) && (!folder.isDirectory())) {
            throw new IllegalArgumentException("File exists with the name '" + name + "' and isn't a folder");
        }

        if (generator == null) {
            generator = server.getGenerator(name);
        }

        console.convertWorld(name);

        int dimension = CraftWorld.CUSTOM_DIMENSION_OFFSET + console.worldServer.size();
        boolean used = false;
        do {
            for (WorldServer s : console.getWorlds()) {
                used = s.getWorldProvider().getDimensionManager().getDimensionID() == dimension;
                if (used) {
                    dimension++;
                    break;
                }
            }
        } while (used);
        boolean hardcore = false;

        WorldNBTStorage sdm = new WorldNBTStorage(server.getWorldContainer(), name, server.getServer(), server.getHandle().getServer().dataConverterManager);
        WorldData worlddata = sdm.getWorldData();
        WorldSettings worldSettings;
        // See MinecraftServer.a(String, String, long, WorldType, JsonElement)
        if (worlddata == null) {
            worldSettings = new WorldSettings(creator.seed(), EnumGamemode.getById(server.getDefaultGameMode().getValue()), generateStructures, hardcore, type);
            JsonElement parsedSettings = new JsonParser().parse(creator.generatorSettings());
            if (parsedSettings.isJsonObject()) {
                worldSettings.setGeneratorSettings(parsedSettings.getAsJsonObject());
            }
            worlddata = new WorldData(worldSettings, name);
        } else {
            worlddata.setName(name);
            worldSettings = new WorldSettings(worlddata);
        }

        DimensionManager actualDimension = DimensionManager.a(creator.environment().getId());
        DimensionManager internalDimension = DimensionManager.register(name.toLowerCase(java.util.Locale.ENGLISH), new DimensionManager(dimension, actualDimension.getSuffix(), actualDimension.folder, (w, manager) -> actualDimension.providerFactory.apply(w, manager), actualDimension.hasSkyLight(), actualDimension.getGenLayerZoomer(), actualDimension));
        WorldServer internal = (WorldServer) new WorldServer(console, console.executorService, sdm, worlddata, internalDimension, console.getMethodProfiler(), server.getServer().worldLoadListenerFactory.create(11), creator.environment(), generator);

        if (!(server.getWorld(name.toLowerCase(java.util.Locale.ENGLISH)) != null)) {
            return null;
        }

        console.initWorld(internal, worlddata, worldSettings);

        internal.worldData.setDifficulty(EnumDifficulty.EASY);
        internal.setSpawnFlags(true, true);
        console.worldServer.put(internal.getWorldProvider().getDimensionManager(), internal);

        Bukkit.getPluginManager().callEvent(new WorldInitEvent(internal.getWorld()));
        Bukkit.getPluginManager().callEvent(new WorldLoadEvent(internal.getWorld()));
        return internal.getWorld();
    }

    private static void convertWorld(String name) {
        MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
        if (server.getConvertable().isConvertable(name)) {
            server.info("Converting map!");
            server.getConvertable().convert(name, new IProgressUpdate() {
                private long b = SystemUtils.getMonotonicMillis();

                @Override
                public void a(IChatBaseComponent iChatBaseComponent) {
                }

                @Override
                public void c(IChatBaseComponent iChatBaseComponent) {
                }

                @Override
                public void a(int i) {
                    if (SystemUtils.getMonotonicMillis() - this.b >= 1000L) {
                        this.b = SystemUtils.getMonotonicMillis();
                        MinecraftServer.LOGGER.info("Converting... {}%", i);
                    }
                }
            });
        }

        // XXX Force Upgrade?
    }
}