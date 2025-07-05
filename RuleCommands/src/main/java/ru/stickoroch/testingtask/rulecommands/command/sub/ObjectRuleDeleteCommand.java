package ru.stickoroch.testingtask.rulecommands.command.sub;

import lombok.NonNull;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import ru.stickoroch.testingtask.rulecommands.ObjectRulesService;
import ru.stickoroch.testingtask.rulecommands.command.ObjectRuleCommand;

public class ObjectRuleDeleteCommand extends AbstractSubObjectRuleCommand {

    public ObjectRuleDeleteCommand(@NonNull ObjectRulesService objectRulesService, @NonNull ObjectRuleCommand objectRuleCommand) {
        super(objectRulesService, objectRuleCommand, "delete", 1,
                String.format("§6/%s delete <object name> - удалить объект", objectRuleCommand.getMAIN_COMMAND()), true);
    }

    @Override
    public void execute(@NonNull CommandSender sender, @NotNull @NonNull String[] args) {
        String objectName = args[0];

        getObjectRulesService().deleteRuleObject(objectName);

        sender.sendMessage(String.format("§6Вы удалили объект §с%s§6.", objectName));
    }
}