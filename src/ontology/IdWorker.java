package ontology;

/**
 * Created by Ԭʤ�� on Intellij IDEA
 *
 * @date 2015/3/31
 * @email 745861642@qq.com
 * @decription
 */
public class IdWorker {
    private final long workerId = 100;
    private final long twepoch = 1303895660503L;
    private long sequence = 0L;
    private final long workerIdBits = 10L;
    private final long maxWorkerId = -1L ^ -1L << this.workerIdBits;
    private final long sequenceBits = 12L;

    private final long workerIdShift = this.sequenceBits;
    private final long timestampLeftShift = this.sequenceBits + this.workerIdBits;
    private final long sequenceMask = -1L ^ -1L << this.sequenceBits;

    private long lastTimestamp = -1L;

    public IdWorker() {
    }

    public static IdWorker instance = new IdWorker();

    public static long getNextId() {
        try {
            return instance.nextId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public synchronized long nextId() throws Exception {
        long timestamp = this.timeGen();
        if (this.lastTimestamp == timestamp) {
            this.sequence = this.sequence + 1 & this.sequenceMask;
            if (this.sequence == 0) {
                timestamp = this.tilNextMillis(this.lastTimestamp);
            }
        } else {
            this.sequence = 0;
        }
        if (timestamp < this.lastTimestamp) {
            throw new Exception(String
                    .format("Clock moved backwards.  Refusing to generate id for %d milliseconds",
                            (this.lastTimestamp - timestamp)));
        }

        this.lastTimestamp = timestamp;
        return timestamp - this.twepoch << this.timestampLeftShift | this.workerId << this.workerIdShift
                | this.sequence;
    }


    private long tilNextMillis(long lastTimestamp) {
        long timestamp = this.timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = this.timeGen();
        }
        return timestamp;
    }


    private long timeGen() {
        return System.currentTimeMillis();
    }

    public static void main(String[] args) throws Exception {
        System.out.println(IdWorker.getNextId());
    }

}
