import engine.SceneEngine;
import models.User;
import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.Play;

public class Global extends GlobalSettings {

    @Override
    public void onStart(Application application) {

        Logger.info("Adding users");

        User user = new User();

        user.setUserId("001");
        user.setEmail("pereira.zc@gmail.com");
        user.setFirstName("Isaac");
        user.setLastName("Pereira");
        user.setPassword("12345678");

        SceneEngine.getInstance().getSession().insert(user);

        User user2 = new User();
        user2.setUserId("002");
        user2.setEmail("sergio@multicast.com.br");
        user2.setFirstName("Sérgio");
        user2.setLastName("Teixeira");
        user2.setPassword("12345678");

        SceneEngine.getInstance().getSession().insert(user2);
        // load the demo data in dev mode
        //if (Play.isDev() && (User.find.all().size() == 0)) {
        //    DemoData.loadDemoData();
        //}

        super.onStart(application);
    }
}
