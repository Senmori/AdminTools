package net.senmori.custommobs.config;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class ServerConfig
{

    public static class Server
    {

        //TODO: replace ? with String
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> INVALID_ENTITIES;


        Server(ForgeConfigSpec.Builder builder)
        {
            builder.comment("General server settings")
                   .push("server");

            List<String> badEntitiesList = new ArrayList<>();
            Collection<EntityType> types = GameRegistry.findRegistry(EntityType.class).getValues();
            for ( EntityType type : types )
            {
                if ( type.equals(EntityType.PLAYER) || type.getClassification() == EntityClassification.MISC )
                {
                    badEntitiesList.add(type.getRegistryName().toString());
                }
            }
            Predicate<Object> entityValidator = (obj) -> {
                EntityType<?> entityType = null;
                Collection<EntityType> entityTypes = GameRegistry.findRegistry(EntityType.class).getValues();
                if ( obj instanceof String )
                {
                    entityType = ( EntityType<?> ) GameRegistry.findRegistry(EntityType.class).getValue(new ResourceLocation(( String ) obj));
                }
                if ( obj instanceof EntityType )
                {
                    entityType = ( EntityType ) obj;
                }
                return obj != null && entityTypes.contains(entityType);
            };
            Function<Object, String> converter = obj -> {
                if ( obj instanceof String )
                {
                    String str = ( String ) obj;
                    EntityType entityType = types.stream()
                                                 .filter(entry -> entry.getRegistryName().equals(str))
                                                 .findFirst()
                                                 .orElseThrow(() -> new IllegalStateException("Unknown entity: " + str));
                    return entityType.getRegistryName().toString();
                }
                if ( obj instanceof EntityType )
                {
                    return ( ( EntityType ) obj ).getRegistryName().toString();
                }
                throw new IllegalStateException("Unknown configuration item. Cannot convert to EntityType nor String: " + obj.getClass());
            };

            this.INVALID_ENTITIES = builder.defineList("invalid_entities", badEntitiesList, entityValidator);


            builder.pop();
        }
    }

    public static class Client
    {
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> INVALID_ENTITIES;

        Client(ForgeConfigSpec.Builder builder)
        {
            builder.comment("General server settings")
                   .push("server");

            List<String> badEntitiesList = new ArrayList<>();
            Collection<EntityType> types = GameRegistry.findRegistry(EntityType.class).getValues();
            for ( EntityType type : types )
            {
                if ( type.equals(EntityType.PLAYER) || type.getClassification() == EntityClassification.MISC )
                {
                    badEntitiesList.add(type.getRegistryName().toString());
                }
            }
            Predicate<Object> entityValidator = (obj) -> {
                EntityType<?> entityType = null;
                Collection<EntityType> entityTypes = GameRegistry.findRegistry(EntityType.class).getValues();
                if ( obj instanceof String )
                {
                    entityType = ( EntityType<?> ) GameRegistry.findRegistry(EntityType.class).getValue(new ResourceLocation(( String ) obj));
                }
                if ( obj instanceof EntityType )
                {
                    entityType = ( EntityType ) obj;
                }
                return obj != null && entityTypes.contains(entityType);
            };
            Function<Object, String> converter = obj -> {
                if ( obj instanceof String )
                {
                    String str = ( String ) obj;
                    EntityType entityType = types.stream()
                                                 .filter(entry -> entry.getRegistryName().equals(str))
                                                 .findFirst()
                                                 .orElseThrow(() -> new IllegalStateException("Unknown entity: " + str));
                    return entityType.getRegistryName().toString();
                }
                if ( obj instanceof EntityType )
                {
                    return ( ( EntityType ) obj ).getRegistryName().toString();
                }
                throw new IllegalStateException("Unknown configuration item. Cannot convert to EntityType nor String: " + obj.getClass());
            };

            INVALID_ENTITIES = builder.defineList("invalid_entities", badEntitiesList, entityValidator);


            builder.pop();

        }
    }


    public static final ForgeConfigSpec SERVER_SPEC;
    public static final Server SERVER;

    static
    {
        final Pair<Server, ForgeConfigSpec> pair = new ForgeConfigSpec.Builder().configure(Server::new);
        SERVER_SPEC = pair.getRight();
        SERVER = pair.getLeft();
    }
}
