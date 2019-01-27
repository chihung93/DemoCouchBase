package hungnc.com.couchbasedemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.DataSource;
import com.couchbase.lite.MutableDocument;
import com.couchbase.lite.Query;
import com.couchbase.lite.QueryBuilder;
import com.couchbase.lite.Result;
import com.couchbase.lite.ResultSet;
import com.couchbase.lite.SelectResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    TextView textView;
    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.text);
        gson = new Gson();
    }

    public void save(View view) {
        Type type = new TypeToken<Map<String, Object>>(){}.getType();
        Map<String, Object> myMap = gson.fromJson("{ \"name\": \"ModifiersGroup\", \"fields\": [ { \"fieldName\": \"storeId\", \"fieldType\": \"Integer\" }, { \"fieldName\": \"modifierGroupID\", \"fieldType\": \"Integer\" }, { \"fieldName\": \"modifierName\", \"fieldType\": \"String\" }, { \"fieldName\": \"modifierID\", \"fieldType\": \"Integer\" }, { \"fieldName\": \"modifierNum\", \"fieldType\": \"Integer\" }, { \"fieldName\": \"modifierPrice1\", \"fieldType\": \"Double\" } ], \"relationships\": [], \"changelogDate\": \"20181005160707\", \"entityTableName\": \"modifiers_group\", \"dto\": \"mapstruct\", \"pagination\": \"no\", \"service\": \"serviceImpl\", \"jpaMetamodelFiltering\": false, \"fluentMethods\": true, \"clientRootFolder\": \"\", \"applications\": \"*\" }", type);
        MutableDocument newTask = new MutableDocument(TAG);
        newTask.setData(myMap);
        try {
            DemoApp.instances.database.save(newTask);
        } catch (CouchbaseLiteException e) {
            com.couchbase.lite.internal.support.Log.e("MainActivity", e.toString());
        }
    }

    public void getFromDatabase(View view) {
        Query query = QueryBuilder
                .select(SelectResult.all())
                .from(DataSource.database(DemoApp.instances.database));
        try {
            ResultSet rs = query.execute();
            StringBuilder text = new StringBuilder();
            for (Result result : rs) {
                text.append(result.toMap().toString());
            }
            textView.setText(text);
        } catch (CouchbaseLiteException e) {
            Log.e("Sample", e.getLocalizedMessage());
        }
    }
}
