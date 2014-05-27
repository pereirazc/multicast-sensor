package models;

import br.ufes.inf.lprm.situation.Role;
import br.ufes.inf.lprm.situation.SituationType;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import  play.libs.Json;

/**
 * Created by pereirazc on 23/05/14.
 */
public class AlertSituation extends SituationType {

    private static long idControl = 0;

    private long alertId;
    public AlertSituation() {
        idControl++;
        this.alertId = idControl;
    }

    @Role(label="current")
    private CurrentData current;

    public CurrentData getCurrent() {
        return current;
    }

    public void setFeed(CurrentData current) {
        this.current = current;
    }

    public ObjectNode toJson() {
        ObjectNode node = JsonNodeFactory.instance.objectNode();
        node.put("id", this.alertId);
        node.put("active", this.isActive());
        node.put("activation", this.getActivation().getTimestamp());
        if (!this.isActive()) {
            node.put("deactivation", this.getDeactivation().getTimestamp());
        }
        node.put("current", play.libs.Json.toJson(this.getCurrent()));
        return node;
    }
}
