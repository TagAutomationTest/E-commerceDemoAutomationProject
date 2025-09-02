package com.SwagLab.utils.CDP;

import com.SwagLab.utils.JsonUtils;
import com.google.common.collect.ImmutableList;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v138.fetch.Fetch;
import org.openqa.selenium.devtools.v138.fetch.model.HeaderEntry;
import org.openqa.selenium.devtools.v138.fetch.model.RequestPattern;
import org.openqa.selenium.devtools.v138.network.model.ErrorReason;

import java.nio.charset.StandardCharsets;
import java.util.*;

public class ApiMockingUtility {
    List<HeaderEntry> responseHeaders;
    private final DevTools devTools;
    String jsonFilePath;
    String fakeResponse;
    String mockedUrl;
    JsonUtils jsonUtils;

    public ApiMockingUtility(DevTools devTools) {
        this.devTools = devTools;
    }

    /**
     * Create custom headers for mocked responses
     */
    public List<HeaderEntry> createHeaders(Map<String, String> headerMap) {
        List<HeaderEntry> headers = new ArrayList<>();
        headerMap.forEach((key, value) -> headers.add(new HeaderEntry(key, value)));
        return headers;
    }

    /**
     * Enable request interception for a given URL pattern
     */
    public void enableInterception(String urlPattern) {
        RequestPattern pattern = new RequestPattern(Optional.of(urlPattern),
                Optional.empty(), Optional.empty());
        devTools.send(Fetch.enable(Optional.of(ImmutableList.of(pattern)), Optional.of(false)));
    }

    /**
     * Mock a response for a given request
     */

    public void mockResponse(String urlPattern, String urlPart, int statusCode, List<HeaderEntry> headers, String JsonFileName) {
        jsonFilePath = System.getProperty("user.dir") + "\\src\\test\\resources\\" + JsonFileName + ".json";
        fakeResponse = JsonUtils.readJsonFile(jsonFilePath);

        enableInterception("*" + urlPart + "*");
        ;
        devTools.addListener(Fetch.requestPaused(), request -> {
            String requestUrl = request.getRequest().getUrl();
            if (requestUrl.contains(urlPart)) {
                responseHeaders = new ArrayList<>();

                devTools.send(Fetch.fulfillRequest(
                        request.getRequestId(),
                        statusCode,
                        Optional.of(responseHeaders),
                        Optional.empty(),
                        Optional.of(Base64.getEncoder().encodeToString(
                                fakeResponse.getBytes(StandardCharsets.UTF_8))),
                        Optional.empty()
                ));
            } else {
                devTools.send(Fetch.continueRequest(
                        request.getRequestId(),
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty()
                ));
            }
        });
    }

    /**
     * Block a request (simulate failure)
     */
    public void blockRequest(String urlPart) {
        enableInterception("*" + urlPart + "*");
        devTools.addListener(Fetch.requestPaused(), request -> {
            String requestUrl = request.getRequest().getUrl();
            if (requestUrl.contains(urlPart)) {
                devTools.send(Fetch.failRequest(request.getRequestId(), ErrorReason.FAILED));
            } else {
                devTools.send(Fetch.continueRequest(
                        request.getRequestId(),
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty()
                ));
            }
        });
    }

    /**
     * Rewrite API request URL before continuing
     * (e.g., modify query params dynamically)
     */
    public void mockUrl(String urlPart, String oldUrlPart, String newUrlPart) {
        enableInterception("*" + urlPart + "*");

        devTools.addListener(Fetch.requestPaused(), request -> {
            if (request.getRequest().getUrl().contains(urlPart)) {
                mockedUrl = request.getRequest().getUrl()
                        .replace(oldUrlPart, newUrlPart);

                devTools.send(Fetch.continueRequest(
                        request.getRequestId(),
                        Optional.of(mockedUrl),
                        Optional.of(request.getRequest().getMethod()),
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty()
                ));
            } else {
                devTools.send(Fetch.continueRequest(request.getRequestId(),
                        Optional.empty(), Optional.empty(),
                        Optional.empty(), Optional.empty(), Optional.empty()));
            }
        });
    }

    public void mockStatusCode(String urlPart, int statusCode, String body) {
        enableInterception("*" + urlPart + "*");

        devTools.addListener(Fetch.requestPaused(), request -> {
            if (request.getRequest().getUrl().contains(urlPart)) {
                devTools.send(Fetch.fulfillRequest(
                        request.getRequestId(),
                        statusCode,
                        Optional.of(List.of(new HeaderEntry("Content-Type", "application/json"))),
                        Optional.empty(),
                        Optional.of(Base64.getEncoder().encodeToString(body.getBytes())),
                        Optional.empty()
                ));
            } else {
                devTools.send(Fetch.continueRequest(request.getRequestId(),
                        Optional.empty(), Optional.empty(),
                        Optional.empty(), Optional.empty(), Optional.empty()));
            }
        });
    }

}
