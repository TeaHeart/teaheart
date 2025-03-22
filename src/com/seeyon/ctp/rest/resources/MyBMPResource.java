package com.seeyon.ctp.rest.resources;

import com.seeyon.client.CTPRestClient;
import com.seeyon.client.CTPServiceClientManager;
import com.seeyon.ctp.common.AppContext;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Map;

@Slf4j
@Path("my-bpm")
@Consumes("application/json")
@Produces("application/json")
public class MyBMPResource extends BaseResource {
    private final CTPRestClient client;

    public MyBMPResource() {
        CTPServiceClientManager clientManager = CTPServiceClientManager
                .getInstance("http://localhost:8008");
        client = clientManager.getRestClient();
        String userName = AppContext.getSystemProperty("app.rest-userName");
        String password = AppContext.getSystemProperty("app.rest-password");
        client.authenticate(userName, password);
        log.info("rest client auth by {}, {}", userName, password);
    }

    @POST
    @Path("process/start")
    public Response processStart(Map<String, Object> map) {
        String post = client.post("bpm/process/start", map, String.class);
        return success(post);
    }

    @POST
    @Path("process/stop")
    public Response processStop(Map<String, Object> map) {
        String post = client.post("bpm/process/stop", map, String.class);
        return success(post);
    }

    @POST
    @Path("process/repeal")
    public Response processRepeal(Map<String, Object> map) {
        String post = client.post("bpm/process/repeal", map, String.class);
        return success(post);
    }

    @GET
    @Path("process/diagramImg")
    @Consumes("application/octet-stream")
    public Response processDiagramImg(Map<String, Object> map) {
        String post = client.post("bpm/process/diagramImg", map, String.class);
        return success(post);
    }

    @POST
    @Path("process/addNode")
    public Response processAddNode(Map<String, Object> map) {
        String post = client.post("bpm/process/addNode", map, String.class);
        return success(post);
    }

    @POST
    @Path("process/deleteNode")
    public Response processDeleteNode(Map<String, Object> map) {
        String post = client.post("bpm/process/deleteNode", map, String.class);
        return success(post);
    }

    @POST
    @Path("process/freeReplaceNode")
    public Response processFreeReplaceNode(Map<String, Object> map) {
        String post = client.post("bpm/process/freeReplaceNode", map, String.class);
        return success(post);
    }

    @POST
    @Path("process/replaceItem")
    public Response processReplaceItem(Map<String, Object> map) {
        String post = client.post("bpm/process/replaceItem", map, String.class);
        return success(post);
    }

    @POST
    @Path("workitem/finish")
    public Response workitemFinish(Map<String, Object> map) {
        String post = client.post("bpm/workitem/finish", map, String.class);
        return success(post);
    }

    @POST
    @Path("workitem/takeback")
    public Response workitemTakeBack(Map<String, Object> map) {
        String post = client.post("bpm/workitem/takeback", map, String.class);
        return success(post);
    }

    @POST
    @Path("workitem/stepBack")
    public Response workitemStepBack(Map<String, Object> map) {
        String post = client.post("bpm/workitem/stepBack", map, String.class);
        return success(post);
    }

    @POST
    @Path("workitem/specifyback")
    public Response workitemSpecifyback(Map<String, Object> map) {
        String post = client.post("bpm/workitem/specifyback", map, String.class);
        return success(post);
    }
}
