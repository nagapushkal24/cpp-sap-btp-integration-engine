import com.sap.gateway.ip.core.customdev.util.Message;
import groovy.json.JsonSlurper;

def Message processData(Message message) {
    def body = message.getBody(String.class);
    
    if (body) {
        def jsonSlurper = new JsonSlurper();
        def data = jsonSlurper.parseText(body);
        
        def projectId = data.ProjectHeader?.ProjectID ?: "UNKNOWN_PROJECT";
        def vesselTag = data.ProjectHeader?.VesselTag ?: "UNKNOWN_VESSEL";
        def itemCount = data.MaterialsList ? data.MaterialsList.size().toString() : "0";
        
        // 1. Set Exchange Properties (Read by Content Modifier ${property.X})
        message.setProperty("Custom_ProjectID", projectId);
        message.setProperty("Custom_VesselTag", vesselTag);
        message.setProperty("Custom_MaterialCount", itemCount);
        
        // 2. Set Custom Header Properties (Displayed in BTP Monitoring UI)
        def messageLog = messageLogFactory.getMessageLog(message);
        if (messageLog != null) {
            messageLog.addCustomHeaderProperty("Custom_ProjectID", projectId);
            messageLog.addCustomHeaderProperty("Custom_VesselTag", vesselTag);
            messageLog.addCustomHeaderProperty("Custom_MaterialCount", itemCount);
        }
    }
    return message;
}