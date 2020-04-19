package net.senmori.admintools.config.spec;

import com.electronwill.nightconfig.core.CommentedConfig;
import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.file.CommentedFileConfigBuilder;
import com.electronwill.nightconfig.core.file.FileNotFoundAction;
import com.electronwill.nightconfig.core.io.ParsingMode;
import com.electronwill.nightconfig.core.io.WritingMode;
import com.electronwill.nightconfig.toml.TomlFormat;
import net.minecraftforge.fml.loading.FMLPaths;
import net.senmori.admintools.AdminTools;
import net.senmori.admintools.asset.assets.JarFileAsset;
import net.senmori.admintools.asset.assets.LocalFileAsset;
import net.senmori.admintools.util.Directory;

import java.io.File;

public class ConfigBuilder
{
    private LocalFileAsset localFileAsset;

    CommentedFileConfigBuilder builder;

    private ConfigBuilder(LocalFileAsset asset)
    {
        this.localFileAsset = asset;
        this.builder = CommentedFileConfig.builder(asset.getFile(), TomlFormat.instance());
    }

    public static CommentedConfig newConfig(String configName)
    {
        LocalFileAsset configAsset = LocalFileAsset.of(new File(FMLPaths.CONFIGDIR.get().toFile(), configName));
        return ConfigBuilder.newBuilder(configAsset).build();
    }

    public static ConfigBuilder newBuilder(LocalFileAsset asset)
    {
        return new ConfigBuilder(asset);
    }

    public CommentedConfig build()
    {
        this.builder.autoreload()
                .autosave()
                .preserveInsertionOrder()
                .onFileNotFound(FileNotFoundAction.CREATE_EMPTY)
                .concurrent();
        return builder.build();
    }
}
