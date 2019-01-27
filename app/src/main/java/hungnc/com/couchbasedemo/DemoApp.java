package hungnc.com.couchbasedemo;

import android.app.Application;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.DatabaseConfiguration;
import com.couchbase.lite.LogDomain;
import com.couchbase.lite.LogLevel;

public class DemoApp extends Application {

    static DemoApp instances;

    Database database;

    @Override
    public void onCreate() {
        super.onCreate();
        instances = this;
        DatabaseConfiguration config = new DatabaseConfiguration(this);
        // Get the database (and create it if it doesn’t exist).
        try {
            database = new Database("DemoApp", config);
            Database.setLogLevel(LogDomain.REPLICATOR, LogLevel.VERBOSE);
            Database.setLogLevel(LogDomain.QUERY, LogLevel.VERBOSE);
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }
    }
}
