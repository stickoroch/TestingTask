package ru.stickoroch.testingtask.rulecommands.command.sub;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;
import ru.stickoroch.testingtask.rulecommands.ObjectRulesService;
import ru.stickoroch.testingtask.rulecommands.command.ObjectRuleCommand;

import java.util.List;

@Getter
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public abstract class AbstractSubObjectRuleCommand implements SubObjectRuleCommand {

    @NonNull
    ObjectRulesService objectRulesService;

    @NonNull
    ObjectRuleCommand ruleCommand;

    @NonNull
    String name;

    int minArgs;

    @NonNull
    String usageMessage;

    boolean needExistsObjectForExecute;

    protected AbstractSubObjectRuleCommand(@NonNull ObjectRulesService objectRulesService,
                                           @NonNull ObjectRuleCommand objectRuleCommand,
                                           @NonNull String name, int minArgs, @NonNull String usageMessage,
                                           boolean needExistsObjectForExecute) {
        this.objectRulesService = objectRulesService;
        this.ruleCommand = objectRuleCommand;
        this.name = name;
        this.minArgs = minArgs;
        this.usageMessage = usageMessage;
        this.needExistsObjectForExecute = needExistsObjectForExecute;
    }

    @Override
    public @NonNull List<String> tabComplete(@NotNull @NonNull String[] args) {
        return List.of();
    }
}
