package controllers;

import play.mvc.*;
import play.mvc.Http.*;

/**
 * Security authenticator implementation to secure controllers
 *
 * @author Kristian Haugene
 */
public class Secured extends Security.Authenticator {

    @Override
    public String getUsername(Context ctx) {
        return ctx.session().get("username");
    }

    @Override
    public Result onUnauthorized(Context ctx) {
        return Application.login();
    }
}