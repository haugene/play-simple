package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

/**
 * Controller for the Dashboard.
 *
 * @author Kristian Haugene
 */
@Security.Authenticated(Secured.class)
public class Dashboard extends Controller {

    public static Result index() {

        return ok(views.html.index.render(session("username")));
    }
}
