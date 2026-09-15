import com.sap.gateway.ip.core.customdev.util.Message
//import java.io.InputStream

Message processData(Message message) {
    // Retrieve the XML body after Graphical + Value Mapping
    def body = message.getBody(String.class)
    
    // Fetch the Message Processing Log handle
    def messageLog = messageLogFactory.getMessageLog(message)
    
    if (messageLog != null) {
        // Log custom status headers
        messageLog.addCustomHeaderProperty("Stage", "Post-MessageMapping")
        
        // Attach the transformed XML to MPL as a readable attachment
        if (body != null && !body.trim().isEmpty()) {
            messageLog.addAttachmentAsString("Mapped_Target_XML", body, "text/xml")
        } else {
            messageLog.addAttachmentAsString("Mapped_Target_XML", "<Warning>Payload is empty</Warning>", "text/xml")
        }
    }
    
    return message
}