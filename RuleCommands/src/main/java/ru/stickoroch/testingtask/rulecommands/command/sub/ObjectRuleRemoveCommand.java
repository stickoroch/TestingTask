package ru.stickoroch.testingtask.rulecommands.command.sub;

import lombok.NonNull;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import ru.stickoroch.testingtask.rulecommands.ObjectRulesService;
import ru.stickoroch.testingtask.rulecommands.RuleObject;
import ru.stickoroch.testingtask.rulecommands.RuleType;
import ru.stickoroch.testingtask.rulecommands.command.ObjectRuleCommand;

import java.util.ArrayList;
import java.util.List;

public class ObjectRuleRemoveCommand extends AbstractSubObjectRuleCommand {

    public ObjectRuleRemoveCommand(@NonNull ObjectRulesService objectRulesService, @NonNull ObjectRuleCommand objectRuleCommand) {
        super(objectRulesService, objectRuleCommand, "remove", 2,
                String.format("§6/%s remove <object name> <rule> - удалить правило из объекта", objectRuleCommand.getMAIN_COMMAND()),
                true);
    }

    @Override
    public void execute(@NonNull CommandSender sender, @NotNull @NonNull String[] args) {
        String objectName = args[0];

        RuleObject ruleObject = getObjectRulesService().getRuleObject(objectName);
        if (ruleObject.getRules().isEmpty()) {
            sender.sendMessage(String.format("§6Объект §с%s§6 не содержит правил.", objectName));
        } else {
            RuleType rule = getRuleCommand().parseRule(args[1]);
            if (rule == null) {
                sender.sendMessage(String.format("§cНе ясное правило %s. Выберите одно из таб-комплита!", args[1]));
                return;
            }

            getObjectRulesService().getRuleObject(objectName).removeRule(rule);

            sender.sendMessage(String.format("§6Вы удалили правило §с%s§6 для объекта §с%s§6.", args[1], objectName));
        }
    }

    @Override
    public @NonNull List<String> tabComplete(@NotNull @NonNull String[] args) {
        return new ArrayList<>(getObjectRulesService().getRuleObject(args[0]).getRulesAsStringList());
    }
}
