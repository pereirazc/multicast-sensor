import engine.SceneEngine;
import models.User;
import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.mvc.Result;
import play.libs.F;
import play.mvc.Http;
import play.mvc.Results;

public class Global extends GlobalSettings {

    @Override
    public void onStart(Application application) {

        Logger.info("Adding users");

        User user = new User();
        user.setEmail("pereira.zc@gmail.com");
        user.setFirstName("Isaac");
        user.setLastName("Pereira");
        user.setPassword("12345678");

        SceneEngine.getInstance().getSession().insert(user);

        User user2 = new User();
        user2.setEmail("sergio@multicast.com.br");
        user2.setFirstName("Sérgio");
        user2.setLastName("Teixeira");
        user2.setPassword("12345678");

        SceneEngine.getInstance().getSession().insert(user2);

        User user3 = new User();
        user3.setEmail("jadirlucas@hotmail.com");
        user3.setFirstName("Jadir");
        user3.setLastName("Lucas");
        user3.setPassword("12345678");

        SceneEngine.getInstance().getSession().insert(user3);
        // load the demo data in dev mode
        //if (Play.isDev() && (User.find.all().size() == 0)) {
        //    DemoData.loadDemoData();
        //}

        super.onStart(application);
    }

    /*@Override
    public F.Promise<Result> onHandlerNotFound(Http.RequestHeader arg0) {
        return F.Promise.<Result>pure(Results.notFound(views.html.error.render()));
    }
    @Override
    public F.Promise<Result> onError(Http.RequestHeader arg0, Throwable arg1) {
        return F.Promise.<Result>pure(Results.internalServerError(views.html.error.render()));
    }

    @Override
    public F.Promise<Result> onBadRequest(Http.RequestHeader arg0, String error) {
        return F.Promise.<Result>pure(Results.badRequest(views.html.error.render()));
    }
    */
}
