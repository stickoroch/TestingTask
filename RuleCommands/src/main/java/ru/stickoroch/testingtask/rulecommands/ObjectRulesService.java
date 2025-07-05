package ru.stickoroch.testingtask.rulecommands;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ObjectRulesService {

    @NonNull
    Plugin plugin;
    @NonNull
    Map<String, RuleObject> rules;
    @NonNull
    File file;
    @NonFinal
    YamlConfiguration config;

    public ObjectRulesService(@NonNull Plugin plugin) {
        this.plugin = plugin;
        this.rules = new HashMap<>();
        this.file = new File(plugin.getDataFolder(), "rules.yml");
    }

    public void init() {
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
            } catch (IOException e) {
                plugin.getLogger().severe("Cannot create rules.yml file");
                e.printStackTrace();
            }
        }

        config = YamlConfiguration.loadConfiguration(file);
    }

    public void startAutoSave() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, this::saveAll, 600, 600);
    }

    public void load() {
        rules.clear();

        for (String objectName : config.getKeys(false)) {
            if (!rules.containsKey(objectName)) {
                rules.put(objectName, new RuleObject(objectName));
            }
            for (String ruleName : config.getStringList(objectName)) {
                rules.get(objectName).addRule(RuleType.valueOf(ruleName));
            }
        }
    }

    public void createRuleObject(@NonNull String objectName) {
        if (rules.containsKey(objectName)) {
            throw new IllegalArgumentException(String.format("Object %s already exists", objectName));
        }

        rules.put(objectName, new RuleObject(objectName));
    }

    public boolean hasRuleObject(@NonNull String objectName) {
        return rules.containsKey(objectName);
    }

    public void deleteRuleObject(@NonNull String objectName) {
        rules.remove(objectName);
    }

    public void saveAll() {
        Set<String> oldObjectNames = config.getKeys(false);
        for (String oldObjectName : oldObjectNames) {
            if (!rules.containsKey(oldObjectName)) {
                config.set(oldObjectName, null);
            }
        }

        Set<String> newObjectNames = rules.keySet();
        for (String newObjectName : newObjectNames) {
            config.set(newObjectName, new ArrayList<>(rules.get(newObjectName).getRulesAsStringList()));
        }

        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @NonNull
    public RuleObject getRuleObject(@NonNull String objectName) {
        return rules.get(objectName);
    }

    @NonNull
    public Set<String> getObjectNames() {
        return rules.keySet();
    }
}
