package models;

import org.apache.commons.lang3.StringUtils;
import play.Logger;
import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.ebean.Model;
import utils.PasswordHelper;

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
    public String passwordHash;

    // Store a salt value for security reasons
    private String passwordSalt;

    /**
     * Default constructor
     */
    public User()
    {
        if(StringUtils.isBlank(passwordSalt))
        {
            passwordSalt = PasswordHelper.nextSalt();
        }
    }

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

        User user = findByUsername(username);
        if(user == null)
        {
            // No user found with that username
            Logger.error("No user found for username" + username);
            return null;
        }

        // Hash the salt and given password
        String passwordHash = PasswordHelper.hash(user.passwordSalt + password);

        // Check if the hash is the same as we have stored
        if(passwordHash.equals(user.passwordHash))
        {
            return user;
        }

        return null;
    }

    public void setPassword(String password)
    {
        Logger.info("Setting password for user: " + username);
        passwordSalt = String.valueOf(PasswordHelper.nextSalt());
        passwordHash = PasswordHelper.hash(passwordSalt + password);
    }

    // --

    public String toString() {
        return "User(" + username + ")";
    }
}