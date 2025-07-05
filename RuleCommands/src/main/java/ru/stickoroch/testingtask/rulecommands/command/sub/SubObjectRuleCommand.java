package ru.stickoroch.testingtask.rulecommands.command.sub;

import lombok.NonNull;
import org.bukkit.command.CommandSender;
import ru.stickoroch.testingtask.rulecommands.ObjectRulesService;
import ru.stickoroch.testingtask.rulecommands.command.ObjectRuleCommand;

import java.util.List;

public interface SubObjectRuleCommand {

    @NonNull
    ObjectRulesService getObjectRulesService();

    @NonNull
    String getName();

    int getMinArgs();

    @NonNull
    String getUsageMessage();

    @NonNull
    ObjectRuleCommand getRuleCommand();

    @NonNull
    List<String> tabComplete(@NonNull String[] args);

    boolean isNeedExistsObjectForExecute();

    void execute(@NonNull CommandSender sender, @NonNull String[] args);
}
