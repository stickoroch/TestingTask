package ru.stickoroch.testingtask.rulecommands.command.sub;

import lombok.NonNull;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import ru.stickoroch.testingtask.rulecommands.ObjectRulesService;
import ru.stickoroch.testingtask.rulecommands.RuleObject;
import ru.stickoroch.testingtask.rulecommands.command.ObjectRuleCommand;

import java.util.ArrayList;
import java.util.List;

public class ObjectRuleInfoCommand extends AbstractSubObjectRuleCommand {

    public ObjectRuleInfoCommand(@NonNull ObjectRulesService objectRulesService, @NonNull ObjectRuleCommand objectRuleCommand) {
        super(objectRulesService, objectRuleCommand, "info", 1,
                String.format("§6/%s info <object name> - список текущих правил объекта", objectRuleCommand.getMAIN_COMMAND()),
                true);
    }

    @Override
    public void execute(@NonNull CommandSender sender, @NotNull @NonNull String[] args) {
        String objectName = args[0];

        RuleObject ruleObject = getObjectRulesService().getRuleObject(objectName);
        if (ruleObject.getRules().isEmpty()) {
            sender.sendMessage(String.format("§6Объект §с%s§6 не содержит правил.", objectName));
        } else {
            StringBuilder str = new StringBuilder();
            List<String> rules = new ArrayList<>(ruleObject.getRulesAsStringList());
            for (int i = 0; i < rules.size(); i++) {
                str.append("§с").append(rules.get(i));
                if (i + 1 != rules.size()) {
                    str.append("§6, ");
                }
            }

            sender.sendMessage(String.format("§6Объект §с%s§6 содержит правила: %s§6.", objectName, str));
        }

    }
}
