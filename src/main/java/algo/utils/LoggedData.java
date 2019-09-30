package algo.utils;

import static com.google.common.base.Stopwatch.createStarted;
import static java.util.Objects.requireNonNull;
import static java.util.concurrent.TimeUnit.MICROSECONDS;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.google.common.base.Stopwatch;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public final class LoggedData {
    private static final Gson GSON = new Gson();

    private transient final Stopwatch stopwatch;
    private final Map<String, Object> data;
    private final boolean             allowNullValue;
    private boolean                   frozen;

    private LoggedData(boolean allowNullValue) {
        this.data = new HashMap<>();
        this.stopwatch = createStarted();
        this.allowNullValue = allowNullValue;
        this.frozen = false;
    }

    /**
     * @return loggerData Object which can not store Null values.
     */
    public static LoggedData newInstance() {
        return new LoggedData(false);
    }

    /**
     * @return loggerData Object which can store Null values.
     */
    public static LoggedData newInstanceWithNullValAllowed() {
        return new LoggedData(true);
    }

    /**
     * throws Exception if logger does not have the key.
     * 
     * @param <T>
     * @param key
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        if (!data.containsKey(key))
            throw new IllegalArgumentException("Logger does not have key=" + key);

        return (T) data.get(key);
    }

    /**
     * stores data for key in the logger.
     * 
     * Key can not be null. 
     * If storage is not allowed the throws exception.
     * If null value is not allowed then nullity of data is checked.
     * 
     * If key already exists in the logger, throws exception.
     * 
     * @param <T>
     * @param key
     * @param data
     */
    public <T> void put(String key, T data) {
        if (frozen)
            throw new UnsupportedOperationException("No more logging data allowed as Logger state has been forzen");

        requireNonNull(key);

        if (!allowNullValue)
            requireNonNull(data);

        if (this.data.containsKey(key))
            throw new IllegalArgumentException("Logger has already the key=" + key);

        this.data.put(key, data);
    }

    /**
     * Freezes the logger.
     * After this operation, logger can not store more data.
     * 
     * @return
     */
    public LoggedData freeze() {
        if (frozen)
            new UnsupportedOperationException("Can not freeze the Logger again");

        this.frozen = true;
        this.data.put("timeTaken", this.stopwatch.stop().elapsed(MICROSECONDS));

        return this;
    };

    public JsonObject serialize() {
        return GSON.toJsonTree(this.data, Map.class).getAsJsonObject();
    }

    /**
     * 
     * @param unit
     * @return time taken by logger until logger freezes.
     * 
     */
    public long timeTaken(TimeUnit unit) {
        return this.stopwatch.elapsed(unit);
    }

    @Override
    public String toString() {
        return this.data.toString();
    }
}
