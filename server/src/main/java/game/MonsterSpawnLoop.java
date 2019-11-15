package game;

public class MonsterSpawnLoop implements Runnable {
    @Override
    public void run() {
        long prevtime = System.currentTimeMillis();

        while (true) {
            long time = System.currentTimeMillis();
            float delta = (float) ((time - prevtime) / 1000.0);

            GameServer.getInstance().getMh().getActiveMonsterSpawners().forEach(monsterSpawner -> {
                monsterSpawner.setTimeCounter(monsterSpawner.getTimeCounter() + delta);
                if (monsterSpawner.getTimeCounter() > monsterSpawner.getSpawnTimer()) {
                    monsterSpawner.spawnMonster();
                    monsterSpawner.setTimeCounter(delta);
                }
            });
            try {
                prevtime = time;
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
