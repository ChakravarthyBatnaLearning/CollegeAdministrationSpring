package com.college.student.listener;

import com.college.student.event.IEvent;

public interface IEventListener <T extends IEvent>{
    public  void onEvent(T event);

}
