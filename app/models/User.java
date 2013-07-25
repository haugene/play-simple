package models;

import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "account")
public class User extends Model {

    @Id
    @Constraints.Required
    @Formats.NonEmpty
    public String username;

    @Constraints.Required
    public String name;

    @Constraints.Required
    public String password;

    // -- Queries

    public static Model.Finder<String, User> find = new Model.Finder(String.class, User.class);

    /**
     * Retrieve all users.
     */
    public static List<User> findAll() {
        return find.all();
    }

    /**
     * Retrieve a User from username.
     */
    public static User findByUsername(String username) {
        return find.where().eq("username", username).findUnique();
    }

    /**
     * Authenticate a User.
     */
    public static User authenticate(String username, String password) {
        return find.where()
                .eq("username", username)
                .eq("password", password)
                .findUnique();
    }

    // --

    public String toString() {
        return "User(" + username + ")";
    }
}