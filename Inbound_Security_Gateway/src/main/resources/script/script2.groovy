import com.sap.gateway.ip.core.customdev.util.Message;

Message processData(Message message) {
    // Extract Exception Details
    def ex = message.getProperty("CamelExceptionCaught");
    String errorMessage = ex ? ex.getMessage() : "Unknown Integration Exception";
    
    // Fetch existing MPL ID or assign fallback
    String mplId = message.getProperty("SAP_MessageProcessingLogID") ?: "N/A";

    // Store clean properties for downstream response formatting
    message.setProperty("EX_Message", errorMessage);
    message.setProperty("EX_MPL_ID", mplId);
    
    return message;
}