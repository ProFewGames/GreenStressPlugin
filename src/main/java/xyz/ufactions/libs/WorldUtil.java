package xyz.ufactions.libs;

import net.minecraft.server.v1_8_R3.*;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.generator.ChunkGenerator;

import java.io.File;

/**
 * MegaBukkit class (c) Ricardo Barrera 2017-2020
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
        Validate.notNull(creator, "Creator may not be null");

        CraftServer server = (CraftServer) Bukkit.getServer();
        MinecraftServer console = server.getServer();

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

        Convertable converter = new WorldLoaderServer(server.getWorldContainer());
        if (converter.isConvertable(name)) {
            server.getLogger().info("Converting world '" + name + "'");
            converter.convert(name, new IProgressUpdate() {
                private long b = System.currentTimeMillis();

                public void a(String s) {
                }

                public void a(int i) {
                    if (System.currentTimeMillis() - this.b >= 1000L) {
                        this.b = System.currentTimeMillis();
                        MinecraftServer.LOGGER.info("Converting... " + i + "%");
                    }

                }

                public void c(String s) {
                }
            });
        }

        int dimension = CraftWorld.CUSTOM_DIMENSION_OFFSET + console.worlds.size();
        boolean used = false;
        do {
            for (WorldServer s : console.worlds) {
                used = s.dimension == dimension;
                if (used) {
                    dimension++;
                    break;
                }
            }
        } while (used);
        boolean hardcore = false;

        IDataManager sdm = new ServerNBTManager(server.getWorldContainer(), name, true);
        WorldData worlddata = sdm.getWorldData();
        if (worlddata == null) {
            WorldSettings worldSettings = new WorldSettings(creator.seed(), WorldSettings.EnumGamemode.getById(server.getDefaultGameMode().getValue()), generateStructures, hardcore, type);
            worldSettings.setGeneratorSettings(creator.generatorSettings());
            worlddata = new WorldData(worldSettings, name);
        }
        worlddata.checkName(name); // CraftBukkit - Migration did not rewrite the level.dat; This forces 1.8 to take the last loaded world as respawn (in this case the end)
        WorldServer internal = (WorldServer) new WorldServer(console, sdm, worlddata, dimension, console.methodProfiler, creator.environment(), generator).b();

        if (!(server.getWorld(name.toLowerCase()) != null)) {
            return null;
        }

        internal.scoreboard = server.getScoreboardManager().getMainScoreboard().getHandle();

        internal.tracker = new EntityTracker(internal);
        internal.addIWorldAccess(new WorldManager(console, internal));
        internal.worldData.setDifficulty(EnumDifficulty.EASY);
        internal.setSpawnFlags(true, true);
        console.worlds.add(internal);

        if (generator != null) {
            internal.getWorld().getPopulators().addAll(generator.getDefaultPopulators(internal.getWorld()));
        }

        Bukkit.getPluginManager().callEvent(new WorldInitEvent(internal.getWorld()));
        Bukkit.getPluginManager().callEvent(new WorldLoadEvent(internal.getWorld()));
        return internal.getWorld();
    }
}