package engine;

import br.ufes.inf.lprm.scene.SituationKnowledgeBaseFactory;
import br.ufes.inf.lprm.scene.SituationKnowledgeBuilderFactory;
import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseConfiguration;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.*;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import play.Play;

import java.io.File;
import java.io.InputStream;

public class SceneEngine extends Thread {

	private StatefulKnowledgeSession ksession;	
	private static SceneEngine instance;

	public SceneEngine() {
        // load up the knowledge base
        KnowledgeBase kbase;
		try {
			kbase = readKnowledgeBase();
			this.ksession = kbase.newStatefulKnowledgeSession();
			this.start();
		} catch (Exception e) {
            play.Logger.error(e.getMessage());
			//e.printStackTrace();
		}
		
	}

	public void run() {
		this.ksession.fireUntilHalt();
	}
   
	public static SceneEngine getInstance() {
        if (instance==null) instance = new SceneEngine();
		return instance;
	}
	
	public StatefulKnowledgeSession getSession() {
		return this.ksession;
	}

    private static KnowledgeBase readKnowledgeBase() throws Exception {
    	
    	KnowledgeBuilderConfiguration kBuilderConfiguration = KnowledgeBuilderFactory.newKnowledgeBuilderConfiguration(null, play.Play.application().classloader());
    	KnowledgeBuilder kbuilder = SituationKnowledgeBuilderFactory.newKnowledgeBuilder(kBuilderConfiguration);

    	InputStream input = Play.application().resourceAsStream("/public/resources/query/QueryHelper.drl");
        kbuilder.add(ResourceFactory.newInputStreamResource(input), ResourceType.DRL);

        input = Play.application().resourceAsStream("/public/resources/situation/Validation.drl");
        kbuilder.add(ResourceFactory.newInputStreamResource(input), ResourceType.DRL);

        //q = Play.application().getFile("resources/situation/Situation.drl");
        //kbuilder.add(ResourceFactory.newFileResource(q), ResourceType.DRL);

        KnowledgeBuilderErrors errors = kbuilder.getErrors();
        if (errors.size() > 0) {
            for (KnowledgeBuilderError error: errors) {
                play.Logger.error(error.toString());
            }
            throw new IllegalArgumentException("Could not parse knowledge.");
        }

    	KnowledgeBaseConfiguration kbaseConfig = KnowledgeBaseFactory.newKnowledgeBaseConfiguration(null, play.Play.application().classloader());
   	
        KnowledgeBase kbase = SituationKnowledgeBaseFactory.newKnowledgeBase(kbuilder, kbaseConfig);
        return kbase;
    }

}

