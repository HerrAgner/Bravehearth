
package network.networkMessages;

import java.util.UUID;

public class AttackEnemyTarget {
    private int attacker;
    private int target;
    private int attackerType;

    public AttackEnemyTarget(int attacker, int target) {
        this.attacker = attacker;
        this.target = target;
    }
    public AttackEnemyTarget(){ }

    public int getAttacker() {
        return attacker;
    }

    public int getTarget() {
        return target;
    }
}

