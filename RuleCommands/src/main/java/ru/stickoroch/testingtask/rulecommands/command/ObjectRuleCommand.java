package ru.stickoroch.testingtask.rulecommands.command;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stickoroch.testingtask.rulecommands.ObjectRulesService;
import ru.stickoroch.testingtask.rulecommands.RuleType;
import ru.stickoroch.testingtask.rulecommands.command.sub.*;

import java.util.*;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ObjectRuleCommand implements CommandExecutor, TabCompleter {

    @Getter
    String MAIN_COMMAND = "object";

    @NonNull
    ObjectRulesService objectRulesService;
    @NonNull
    Map<String, SubObjectRuleCommand> subCommands;

    public ObjectRuleCommand(@NotNull ObjectRulesService objectRulesService) {
        this.objectRulesService = objectRulesService;
        this.subCommands = new HashMap<>();

        init();
    }

    private void init() {
        subCommands.put("add", new ObjectRuleAddCommand(objectRulesService, this));
        subCommands.put("remove", new ObjectRuleRemoveCommand(objectRulesService, this));
        subCommands.put("info", new ObjectRuleInfoCommand(objectRulesService, this));
        subCommands.put("delete", new ObjectRuleDeleteCommand(objectRulesService, this));
        subCommands.put("create", new ObjectRuleCreateCommand(objectRulesService, this));
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
            sendHelpList(commandSender);
            return true;
        }

        if (!subCommands.containsKey(args[0])) {
            commandSender.sendMessage("&6Не известный аргумент. Для помощи напишите /" + getMAIN_COMMAND());
            return true;
        }

        SubObjectRuleCommand subObjectRuleCommand = subCommands.get(args[0]);

        if (args.length <= subObjectRuleCommand.getMinArgs()) {
            commandSender.sendMessage("§cНедостаточно аргументов!");
            commandSender.sendMessage(subObjectRuleCommand.getUsageMessage());
            return true;
        }

        if (subObjectRuleCommand.isNeedExistsObjectForExecute()) {
            if (!objectRulesService.hasRuleObject(args[1])) {
                commandSender.sendMessage(String.format("§cОбъект %s не существует!", args[1]));
                return true;
            }
        }

        subObjectRuleCommand.execute(commandSender, Arrays.copyOfRange(args, 1, args.length));
        return true;
    }

    private void sendHelpList(@NonNull CommandSender sender) {
        sender.sendMessage("§6Команды для работы с объектами");
        subCommands.values().forEach(x -> sender.sendMessage("§c- " + x.getUsageMessage()));
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length == 0) {
            return new ArrayList<>(subCommands.keySet());
        }
        if (args.length == 1) {
            return subCommands.keySet().stream().filter(x -> x.startsWith(args[0])).toList();
        }

        SubObjectRuleCommand subObjectRuleCommand = subCommands.get(args[0]);
        if (subObjectRuleCommand == null) return Collections.emptyList();

        if (args.length == 2) {
            if (subObjectRuleCommand.isNeedExistsObjectForExecute()) {
                return objectRulesService.getObjectNames().stream().filter(x -> x.startsWith(args[1])).toList();
            }
        }

        if (args.length == 3) {
            if (subObjectRuleCommand.isNeedExistsObjectForExecute() && !objectRulesService.hasRuleObject(args[1])) {
                return Collections.emptyList();
            }
            return subObjectRuleCommand.tabComplete(Arrays.copyOfRange(args, 1, args.length));
        }

        return Collections.emptyList();
    }

    @Nullable
    public RuleType parseRule(@NonNull String arg) {
        try {
            return RuleType.valueOf(arg.toUpperCase());
        } catch (Exception e) {
            return null;
        }
    }
}
