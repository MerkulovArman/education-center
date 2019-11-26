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
    private Destination destination;

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.TEXT_PLAIN)
    public Response hello(String name){
        String helloMessage = "";
        try(Connection connection = factory.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE)) {
            MessageProducer producer = session.createProducer(destination);
            helloMessage = "Hello " + name + "!";
            producer.send(session.createTextMessage(helloMessage));
            System.out.println("--------------------------------------------------");
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return Response.ok().entity(helloMessage).build();
    }
}