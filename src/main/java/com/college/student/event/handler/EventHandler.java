package com.college.student.event.handler;

import com.college.student.event.IEvent;
import com.college.student.listener.IEventListener;
import com.college.student.pojo.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class EventHandler {
    private static final Map<Class<? extends IEvent>, List<IEventListener<? extends IEvent>>> listeners = new HashMap<>();  // "?" is a wildcard means anytype here anytype of class or child class of IEvent can be accepted; // for specific eventType we have a listOf Listeners;
    private static volatile EventHandler instance;
    private static final BlockingQueue<IEvent> iEventQueue = new LinkedBlockingQueue<>();
    private volatile static boolean sync;
    private static final Logger logger = LoggerFactory.getLogger(EventHandler.class);

    private EventHandler(boolean sync) {
        EventHandler.sync = sync;
    }

    public static EventHandler getInstance(boolean sync) {
        if (instance == null) {
            synchronized (EventHandler.class) {
                if (instance == null) {
                    instance = new EventHandler(sync);
                }
            }
        }
        return instance;
    }

    public static void registerListener(Class<? extends IEvent> eventType, IEventListener<? extends IEvent> listener) {
//        List<IEventListener> iEventListeners = listeners.getOrDefault(eventType, new ArrayList<>());  //retrieves the value of eventType(key) if key is exists else it returns the default (new ArrayList<>)
//        iEventListeners.add(listener);  //add the specific listener to the list of eventListeners and
//        listeners.put(eventType, iEventListeners);
        List<IEventListener<? extends IEvent>> iEventListeners = listeners.get(eventType);
        if (iEventListeners == null) {
            iEventListeners = new ArrayList<>();
            listeners.put(eventType, iEventListeners);
        }
        iEventListeners.add(listener);
    }

    public void unRegisterListener(Class<? extends IEvent> eventType, IEventListener<? extends IEvent> listener) {
//        //this method retrieves the value(here List<IEventListener>) of eventType, and we removed the specific listener from that list;
//        listeners.getOrDefault(eventType, new ArrayList<>()).remove(listener);
        List<IEventListener<? extends IEvent>> iEventListeners = listeners.get(eventType);
        if (iEventListeners != null) {
            iEventListeners.remove(listener);
        }
    }

    public void publishEvent(IEvent event) throws InterruptedException {
        logger.info("New Event Started to Publish");
        iEventQueue.put(event);
        logger.info("Event added to Queue in PublishEvent()");
    }


    public static void invokeListeners() {
        logger.info("Listeners waiting for an event just before while loop");
        while (true) {
            try {
                logger.info("from invokeListeners(), Listeners entered into loop, and are waiting for an Event");
                IEvent event = iEventQueue.take(); // Wait for an event to be available
                logger.info("A new Event Occurred to Listen");
                List<IEventListener<? extends IEvent>> listenerList = listeners.get(event.getClass());
                if (listenerList != null) {
                    for (IEventListener<? extends IEvent> iEventListener : listenerList) {
                        if (sync) {
                            notifyListeners(event, iEventListener);
                        } else {
                            new Thread(() -> notifyListeners(event, iEventListener)).start();
                        }
                    }
                }
            } catch (InterruptedException e) {
                ErrorResponse errorResponse = new ErrorResponse(401, e.getMessage());
                logger.info("Exception Occurred while listening the Event : {}", errorResponse);
            }
        }
    }


    private static void notifyListeners(IEvent event, IEventListener listener) {
        listener.onEvent(event);
    }

}
