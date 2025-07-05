package ru.stickoroch.testingtask.rulecommands;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.bukkit.plugin.java.JavaPlugin;
import ru.stickoroch.testingtask.rulecommands.command.ObjectRuleCommand;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class RuleCommandPlugin extends JavaPlugin {

    ObjectRulesService objectRulesService;

    @Override
    public void onLoad() {
        objectRulesService = new ObjectRulesService(this);
        objectRulesService.init();
        objectRulesService.load();

    }

    @Override
    public void onEnable() {
        objectRulesService.startAutoSave();
        initObjectCommand();
    }

    private void initObjectCommand() {
        ObjectRuleCommand objectRuleCommand = new ObjectRuleCommand(objectRulesService);
        getCommand("object").setExecutor(objectRuleCommand);
        getCommand("object").setTabCompleter(objectRuleCommand);
    }

    @Override
    public void onDisable() {
        objectRulesService.saveAll();
    }
}
