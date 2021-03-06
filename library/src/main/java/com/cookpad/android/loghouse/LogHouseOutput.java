package com.cookpad.android.loghouse;

import com.cookpad.android.loghouse.handlers.AfterFlushAction;
import com.cookpad.android.loghouse.handlers.BeforeEmitAction;
import com.cookpad.android.loghouse.storage.LogHouseStorage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public abstract class LogHouseOutput {
    protected Configuration conf;
    protected LogHouseStorage storage;
    protected AfterFlushAction afterFlushAction;
    protected BeforeEmitAction beforeEmitAction;
    protected boolean isTest = false;

    public void initialize(LogHouseConfiguration logHouseConfiguration, LogHouseStorage storage) {
        this.isTest = logHouseConfiguration.isTest();
        this.afterFlushAction = logHouseConfiguration.getAfterFlushAction();
        this.beforeEmitAction = logHouseConfiguration.getBeforeEmitAction();
        this.storage = storage;
        this.conf = configure(new Configuration());
    }

    public void start(JSONObject serializedLog) {
        try {
            serializedLog = beforeEmitAction.call(serializedLog);
            emit(serializedLog);

            List<JSONObject> serializedLogs = new ArrayList<>();
            serializedLogs.add(serializedLog);
            afterFlushAction.call(type(), serializedLogs);
        } catch (JSONException e) {
            // do nothing
        }
    }

    public abstract String type();

    public abstract Configuration configure(Configuration conf);

    public abstract void emit(JSONObject serializedLog);

    public static class Configuration {
        private int flushInterval = 2 * 60 * 1000;
        private int logsPerRequest = 100;

        public int getFlushInterval() {
            return flushInterval;
        }

        public void setFlushInterval(int flushInterval) {
            this.flushInterval = flushInterval;
        }

        public int getLogsPerRequest() {
            return logsPerRequest;
        }

        public void setLogsPerRequest(int logsPerRequest) {
            this.logsPerRequest = logsPerRequest;
        }
    }
}

