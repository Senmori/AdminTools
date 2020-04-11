package net.senmori.admintools.config.builder;

import com.electronwill.nightconfig.core.CommentedConfig;
import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.file.CommentedFileConfigBuilder;
import com.electronwill.nightconfig.toml.TomlFormat;
import net.senmori.admintools.AdminTools;
import net.senmori.admintools.asset.assets.JarFileAsset;
import net.senmori.admintools.asset.assets.LocalFileAsset;
import net.senmori.admintools.config.value.ConfigValue;

import java.io.File;

public class ConfigBuilder
{
    private LocalFileAsset localFileAsset;
    private JarFileAsset sourceFileAsset;

    private TomlFormat instance = TomlFormat.instance();

    CommentedFileConfig config;
    CommentedFileConfigBuilder builder;

    private ConfigBuilder(LocalFileAsset asset)
    {
        this.localFileAsset = asset;
        instance.supportsType(ConfigValue.class);
        this.config = CommentedFileConfig.of(asset.getFile());
        this.builder = CommentedFileConfig.builder(config.getFile(), instance);
    }

    public static CommentedConfig newConfig(String configName)
    {
        LocalFileAsset configAsset = LocalFileAsset.of(AdminTools.get().getConfigDirectory(), new File(configName));
        JarFileAsset sourceAsset = JarFileAsset.of(AdminTools.get(), configName);
        return ConfigBuilder.newBuilder(configAsset).source(sourceAsset).build();
    }

    public static ConfigBuilder newBuilder(LocalFileAsset asset)
    {
        return new ConfigBuilder(asset);
    }

    public ConfigBuilder source(JarFileAsset asset)
    {
        this.sourceFileAsset = asset;
        this.builder.defaultData(asset.getFile());
        return this;
    }

    public CommentedFileConfigBuilder getFileConfigBuilder()
    {
        return builder;
    }

    public CommentedConfig build()
    {
        this.config = builder.build();
        return this.config;
    }
}
