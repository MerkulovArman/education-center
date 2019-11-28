package ru.neoflex.rest;


import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.*;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Stateless
@Path("/hello")
@Produces(MediaType.TEXT_PLAIN)
public class Producer {

    @Resource(name = "jms/__defaultConnectionFactory")
    private ConnectionFactory factory;

    @Resource(name = "topic")
    private Destination topicDestination;

    @Resource(name = "queue")
    private Destination queueDestination;

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.TEXT_PLAIN)
    public Response hello(String name){
        String helloMessage = "";
        try(Connection connection = factory.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE)) {
            MessageProducer topicProducer = session.createProducer(topicDestination);
            MessageProducer queueProducer = session.createProducer(queueDestination);
            helloMessage = "Hello " + name + "!";
            topicProducer.send(session.createTextMessage(helloMessage));
            queueProducer.send(session.createTextMessage(helloMessage));
            System.out.println("--------------------------------------------------");
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return Response.ok().entity(helloMessage).build();
    }
}