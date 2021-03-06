package controllers;


import models.User;
import play.data.Form;
import play.mvc.*;
import static play.data.Form.form;

import views.html.*;

/**
 * Application controller. Deals with login, logout and authentication request.
 * Will redirect to Dashboard index when logged in.
 *
 * @author Kristian Haugene
 */
public class Application extends Controller {

    // -- Authentication

    public static class Login {

        public String username;
        public String password;

        public String validate() {
            if(User.authenticate(username, password) == null) {
                return "Invalid user or password";
            }
            return null;
        }

    }

    /**
     * Login page.
     */
    public static Result login() {
        return ok(login.render(form(Login.class)));
    }

    /**
     * Handle login form submission.
     */
    public static Result authenticate() {
        Form<Login> loginForm = form(Login.class).bindFromRequest();
        if(loginForm.hasErrors()) {
            return badRequest(login.render(loginForm));
        } else {
            session("username", loginForm.get().username);
            return redirect("/");
        }
    }

    /**
     * Logout and clean the session.
     */
    public static Result logout() {
        session().clear();
        flash("success", "You've been logged out");
        return Application.login();
    }
  
}
