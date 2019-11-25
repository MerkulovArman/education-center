package ru.neoflex.jms;


import javax.annotation.PostConstruct;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;


@MessageDriven(name = "FirstSubscriber", activationConfig = {
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "topic"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
        @ActivationConfigProperty(propertyName = "subscriptionDurability", propertyValue = "Durable"),
        @ActivationConfigProperty(propertyName = "subscriptionName", propertyValue = "FirstSubscriber")
})
public class FirstSubscriber implements MessageListener {


    private static final Logger logger = Logger.getLogger(SecondSubscriber.class.getName());

    @PostConstruct
    public void init() {
        try {
            TimeUnit.MINUTES.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onMessage(Message message) {
        try {
            logger.info(((TextMessage)message).getText() + " " + this.getClass().getSimpleName());
        } catch (JMSException ex) {
            logger.log(Level.SEVERE, "Exception in onMessage " + ex.getMessage(), ex);
        }
    }
}