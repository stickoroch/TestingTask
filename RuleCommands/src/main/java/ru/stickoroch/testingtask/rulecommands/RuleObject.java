package ru.stickoroch.testingtask.rulecommands;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RuleObject {

    @NonNull
    @Getter
    String name;
    @NonNull
    Set<RuleType> ruleTypes;

    public RuleObject(@NonNull String name) {
        this.name = name;
        this.ruleTypes = new HashSet<>();
    }

    public boolean hasRule(@NonNull RuleType ruleType) {
        return ruleTypes.contains(ruleType);
    }

    public void addRule(@NonNull RuleType ruleType) {
        ruleTypes.add(ruleType);
    }

    public List<RuleType> getRules() {
        return new ArrayList<>(ruleTypes);
    }

    public void removeRule(@NonNull RuleType ruleType) {
        ruleTypes.remove(ruleType);
    }

    @NonNull
    public Set<String> getRulesAsStringList() {
        Set<String> rulesAsStringList = new HashSet<>();
        for (RuleType ruleType : ruleTypes) {
            rulesAsStringList.add(ruleType.toString());
        }
        return rulesAsStringList;
    }
}
