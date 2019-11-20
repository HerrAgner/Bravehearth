package network.networkMessages;

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

    public AttackEnemyTarget() { }

    public int getAttacker() {
        return attacker;
    }

    public int getTarget() {
        return target;
    }

    public String getTargetUnit() {
        return targetUnit;
    }
}

