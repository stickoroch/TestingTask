package ru.stickoroch.testingtask.rulecommands.command.sub;

import lombok.NonNull;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import ru.stickoroch.testingtask.rulecommands.ObjectRulesService;
import ru.stickoroch.testingtask.rulecommands.command.ObjectRuleCommand;

public class ObjectRuleCreateCommand extends AbstractSubObjectRuleCommand {

    public ObjectRuleCreateCommand(@NonNull ObjectRulesService objectRulesService, @NonNull ObjectRuleCommand objectRuleCommand) {
        super(objectRulesService, objectRuleCommand, "create", 1,
                String.format("§6/%s create <object name> - создать объект", objectRuleCommand.getMAIN_COMMAND()), false);
    }

    @Override
    public void execute(@NonNull CommandSender sender, @NotNull @NonNull String[] args) {
        String objectName = args[0];

        if (getObjectRulesService().hasRuleObject(objectName)) {
            sender.sendMessage(String.format("§cОбъект %s уже существует!", objectName));
            return;
        }

        getObjectRulesService().createRuleObject(objectName);

        sender.sendMessage(String.format("§6Вы создали объект §с%s§6.", objectName));
    }
}
