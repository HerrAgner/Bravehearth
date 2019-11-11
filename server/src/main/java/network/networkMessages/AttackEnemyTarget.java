
package network.networkMessages;

import java.util.UUID;

public class AttackEnemyTarget {
    private int attacker;
    private int target;
    private String attackerUnit;
    private String targetUnit;
    private String attackType;
    private HealthChange hc;

    public AttackEnemyTarget(int attacker, int target, String attackerUnit, String targetUnit, String attackType, HealthChange hc) {
        this.attacker = attacker;
        this.target = target;
        this.attackerUnit = attackerUnit;
        this.targetUnit = targetUnit;
        this.attackType = attackType;
        this.hc = hc;
    }

    public AttackEnemyTarget() {

    }

    public int getAttacker() {
        return attacker;
    }

    public void setAttacker(int attacker) {
        this.attacker = attacker;
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public String getAttackerUnit() {
        return attackerUnit;
    }

    public void setAttackerUnit(String attackerUnit) {
        this.attackerUnit = attackerUnit;
    }

    public String getTargetUnit() {
        return targetUnit;
    }

    public void setTargetUnit(String targetUnit) {
        this.targetUnit = targetUnit;
    }

    public String getAttackType() {
        return attackType;
    }

    public void setAttackType(String attackType) {
        this.attackType = attackType;
    }
}

