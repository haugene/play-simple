import play.*;
import play.libs.*;
import java.util.*;
import com.avaje.ebean.*;
import models.*;

/**
 * Set global settings.
 * Load initial data into database on startup.
 *
 * @author Kristian Haugene
 */
public class Global extends GlobalSettings {

    public void onStart(Application app) {
        InitialData.insert(app);
    }

    static class InitialData {

        public static void insert(Application app)
        {
            if(Ebean.find(User.class).findRowCount() == 0)
            {
                Map<String,List<Object>> all = (Map<String,List<Object>>)Yaml.load("initial-data.yml");
                Ebean.save(all.get("users"));
            }
        }
    }
}