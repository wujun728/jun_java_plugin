package org.ws.httphelper.request.handler.impl.pre;

import org.ws.httphelper.WSHttpHelperConstant;
import org.ws.httphelper.exception.WSException;
import org.ws.httphelper.model.WSRequestContext;
import org.ws.httphelper.request.handler.RequestPreHandler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by gz on 15/12/4.
 */
public class DefaultURLBuilderHandlerImpl implements RequestPreHandler {
    @Override
    public boolean handler(WSRequestContext context) throws WSException {
        String url = context.getUrl();
        Pattern pattern = Pattern.compile("(\\{([^&/\\}]+)\\})");
        Matcher matcher = pattern.matcher(url);
        String key = null, value = null;
        while (matcher.find()) {
            key = matcher.group(1);
            value = String.valueOf(context.getInputDataMap().get(matcher.group(2)));

            key = key.replace("{", "\\{").replace("}", "\\}");
            url = url.replaceAll(key, value);
        }
        context.setUrl(url);
        return true;
    }

    @Override
    public int level() {
        return WSHttpHelperConstant.PRE_HANDLER_BUILD_PARAM;
    }
}
