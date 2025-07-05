package ru.stickoroch.testingtask.rulecommands.command.sub;

import lombok.NonNull;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import ru.stickoroch.testingtask.rulecommands.ObjectRulesService;
import ru.stickoroch.testingtask.rulecommands.RuleObject;
import ru.stickoroch.testingtask.rulecommands.RuleType;
import ru.stickoroch.testingtask.rulecommands.command.ObjectRuleCommand;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class ObjectRuleAddCommand extends AbstractSubObjectRuleCommand {

    public ObjectRuleAddCommand(@NonNull ObjectRulesService objectRulesService, @NonNull ObjectRuleCommand objectRuleCommand) {
        super(objectRulesService, objectRuleCommand, "add", 2,
                String.format("§6/%s add <object name> <rule> - добавить правило для объекта", objectRuleCommand.getMAIN_COMMAND()),
                true);
    }

    @Override
    public void execute(@NonNull CommandSender sender, @NotNull @NonNull String[] args) {
        String objectName = args[0];

        RuleType rule = getRuleCommand().parseRule(args[1]);
        if (rule == null) {
            sender.sendMessage(String.format("§cНе ясное правило %s. Выберите одно из таб-комплита!", args[1]));
            return;
        }

        RuleObject ruleObject = getObjectRulesService().getRuleObject(objectName);
        if (ruleObject.hasRule(rule)) {
            sender.sendMessage(String.format("§cПравило §с%s§6 уже есть у объекта §с%s§6.", args[1], objectName));
            return;
        } else {
            ruleObject.addRule(rule);
        }


        sender.sendMessage(String.format("§6Вы добавили правило §с%s§6 для объекта §с%s§6.", args[1], objectName));
    }

    @Override
    public @NonNull List<String> tabComplete(@NotNull @NonNull String[] args) {
        Set<String> rules = getObjectRulesService().getRuleObject(args[0]).getRulesAsStringList();
        return Arrays.stream(RuleType.values()).map(Enum::toString).filter(x -> !rules.contains(x)).toList();
    }
}