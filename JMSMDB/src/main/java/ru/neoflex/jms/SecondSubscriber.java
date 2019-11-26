package ru.neoflex.jms;


import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.logging.Level;
import java.util.logging.Logger;

@MessageDriven(name = "SecondSubscriber", activationConfig = {
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "topic"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic")
})
public class SecondSubscriber implements MessageListener {

  private static final Logger logger = Logger.getLogger(SecondSubscriber.class.getName());


  @Override
  public void onMessage(Message message) {
    try {
      logger.info(((TextMessage)message).getText() + " " + this.getClass().getSimpleName());
    } catch (JMSException ex) {
      logger.log(Level.SEVERE, "Exception in onMessage " + ex.getMessage(), ex);
    }
  }
}